package com.geotracker

import android.graphics.*
import android.os.Handler
import android.os.Looper
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import kotlin.math.*
import kotlin.random.Random

class WindVectorOverlay(private val mapView: MapView) : Overlay() {

    // ── public state ────────────────────────────────────────────────────────
    var windSpeedMs: Float = 0f
    var windDirectionDeg: Float = 0f
    var windGustsMs: Float = 0f
    var showField: Boolean = true          // animated particle flow
    var showCurrentArrow: Boolean = true   // instrument panel
    var boatGeoPoint: GeoPoint? = null

    // ── particle system ──────────────────────────────────────────────────────
    private data class Particle(
        var x: Float, var y: Float,
        var px: Float, var py: Float,      // previous position for trail
        var age: Float, val maxAge: Float
    )

    private val particles = ArrayList<Particle>(220)
    private var lastFrameMs = 0L
    private var canvasW = 0; private var canvasH = 0

    // ── animation loop ────────────────────────────────────────────────────────
    private val handler = Handler(Looper.getMainLooper())
    private val animRunnable = object : Runnable {
        override fun run() {
            try {
                if (showField && windSpeedMs > 0.1f) {
                    tick()
                    mapView.invalidate()
                }
            } catch (_: Throwable) {
                // Never let an animation tick kill the main looper
            }
            handler.postDelayed(this, 32L)   // ~30 fps
        }
    }

    fun startAnimation() { handler.removeCallbacks(animRunnable); handler.post(animRunnable) }
    fun stopAnimation()  { handler.removeCallbacks(animRunnable); lastFrameMs = 0L }

    // ── paint objects (allocated once) ────────────────────────────────────────
    private val dp = mapView.resources.displayMetrics.density

    private val trailPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE }
    private val textBig = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE; textAlign = Paint.Align.CENTER; typeface = Typeface.DEFAULT_BOLD
    }
    private val textMid = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER; typeface = Typeface.DEFAULT_BOLD
    }
    private val textSmall = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }
    private val path = Path()
    private val rectF = RectF()
    private val ovalF = RectF()

    // ── color scale (calm → storm) ────────────────────────────────────────────
    private val speedStops = floatArrayOf(0f, 3f, 7f, 12f, 18f, 25f)
    private val speedColors = intArrayOf(
        Color.parseColor("#90CAF9"),   // 0  m/s – pale blue
        Color.parseColor("#4FC3F7"),   // 3  m/s – sky blue
        Color.parseColor("#66BB6A"),   // 7  m/s – green
        Color.parseColor("#FFA726"),   // 12 m/s – amber
        Color.parseColor("#EF5350"),   // 18 m/s – red
        Color.parseColor("#CE93D8")    // 25 m/s – purple
    )

    private fun speedColor(ms: Float): Int {
        if (ms <= speedStops.first()) return speedColors.first()
        if (ms >= speedStops.last())  return speedColors.last()
        for (i in 1 until speedStops.size) {
            if (ms <= speedStops[i]) {
                val t = (ms - speedStops[i - 1]) / (speedStops[i] - speedStops[i - 1])
                return lerpColor(speedColors[i - 1], speedColors[i], t)
            }
        }
        return speedColors.last()
    }

    private fun lerpColor(a: Int, b: Int, t: Float): Int = Color.rgb(
        (Color.red(a)   + t * (Color.red(b)   - Color.red(a))).toInt().coerceIn(0,255),
        (Color.green(a) + t * (Color.green(b) - Color.green(a))).toInt().coerceIn(0,255),
        (Color.blue(a)  + t * (Color.blue(b)  - Color.blue(a))).toInt().coerceIn(0,255)
    )

    // ── particle tick ─────────────────────────────────────────────────────────
    private fun tick() {
        if (canvasW == 0) return
        val now = System.currentTimeMillis()
        val dt  = if (lastFrameMs > 0) (now - lastFrameMs).toFloat().coerceAtMost(100f) else 32f
        lastFrameMs = now

        val goingRad = Math.toRadians(((windDirectionDeg + 180.0) % 360.0))
        // Scale: 10 m/s → ~70 px/s visually comfortable on phone
        val pxMs = windSpeedMs * 7f / 1000f
        val dx = sin(goingRad).toFloat() * pxMs * dt
        val dy = -cos(goingRad).toFloat() * pxMs * dt

        // Mutating `particles` inside the iterator (add after remove) throws
        // ConcurrentModificationException. Count expired slots, then refill
        // in a second pass.
        var recycle = 0
        val iter = particles.iterator()
        while (iter.hasNext()) {
            val p = iter.next()
            p.px = p.x; p.py = p.y
            p.x += dx; p.y += dy
            p.age += dt
            val gone = p.x < -60 || p.x > canvasW + 60 ||
                       p.y < -60 || p.y > canvasH + 60 ||
                       p.age >= p.maxAge
            if (gone) { iter.remove(); recycle++ }
        }
        repeat(recycle) { particles.add(spawn()) }

        val target = (canvasW * canvasH / 6500).coerceIn(90, 200)
        repeat((target - particles.size).coerceAtLeast(0)) { particles.add(spawn()) }
    }

    private fun spawn(): Particle {
        val x = Random.nextFloat() * canvasW
        val y = Random.nextFloat() * canvasH
        val age = Random.nextFloat() * 800f          // stagger birth so field fills instantly
        val maxAge = 1800f + Random.nextFloat() * 2200f
        return Particle(x, y, x, y, age, maxAge)
    }

    // ── draw ──────────────────────────────────────────────────────────────────
    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
        if (shadow) return
        try {
            canvasW = canvas.width
            canvasH = canvas.height

            val color = speedColor(windSpeedMs)

            if (showField && windSpeedMs > 0.1f) drawParticles(canvas, color)
            if (showCurrentArrow)                drawInstrument(canvas, color)
        } catch (_: Throwable) {
            // Never let a drawing error crash the activity
        }
    }

    private fun drawParticles(canvas: Canvas, color: Int) {
        val strokeW = lerp(1.5f, 4.5f, (windSpeedMs / 20f).coerceIn(0f, 1f))
        trailPaint.strokeWidth = strokeW

        for (p in particles) {
            val life = (p.age / p.maxAge).coerceIn(0f, 1f)
            // Smooth bell: fade in 0→0.25, hold 0.25→0.65, fade out 0.65→1.0
            val alpha = when {
                life < 0.25f -> (life / 0.25f * 210).toInt()
                life < 0.65f -> 210
                else         -> ((1f - (life - 0.65f) / 0.35f) * 210).toInt()
            }.coerceIn(0, 255)

            trailPaint.color = color
            trailPaint.alpha = alpha
            canvas.drawLine(p.px, p.py, p.x, p.y, trailPaint)
        }
    }

    // ── instrument panel ─────────────────────────────────────────────────────
    private fun drawInstrument(canvas: Canvas, accentColor: Int) {
        val margin = dp * 10f
        val pW = dp * 112f
        val pH = dp * 146f
        val left = canvasW - pW - margin
        val top  = margin

        // ── glass background ──
        fillPaint.color = Color.argb(195, 6, 9, 20)
        rectF.set(left, top, left + pW, top + pH)
        canvas.drawRoundRect(rectF, dp * 12f, dp * 12f, fillPaint)

        // ── speed-colour top accent bar ──
        fillPaint.color = accentColor
        fillPaint.alpha = 220
        rectF.set(left + dp * 4f, top + dp * 3f, left + pW - dp * 4f, top + dp * 6f)
        canvas.drawRoundRect(rectF, dp * 3f, dp * 3f, fillPaint)

        val cx  = left + pW / 2f
        val cR  = dp * 34f           // compass radius
        val cCy = top + dp * 53f     // compass circle centre Y

        // ── Beaufort arc ring (outside compass) ──
        val bf     = toBeaufort(windSpeedMs)
        val bfFrac = (bf / 12f).coerceIn(0f, 1f)
        if (bfFrac > 0f) {
            strokePaint.color        = accentColor
            strokePaint.alpha        = 180
            strokePaint.strokeWidth  = dp * 4f
            strokePaint.style        = Paint.Style.STROKE
            ovalF.set(cx - cR - dp*5f, cCy - cR - dp*5f, cx + cR + dp*5f, cCy + cR + dp*5f)
            canvas.drawArc(ovalF, -90f, bfFrac * 360f, false, strokePaint)
        }

        // ── compass face ──
        fillPaint.color = Color.argb(70, 25, 45, 85)
        fillPaint.alpha = 255
        canvas.drawCircle(cx, cCy, cR, fillPaint)

        // ── tick marks ──
        strokePaint.color       = Color.argb(90, 160, 200, 255)
        strokePaint.strokeWidth = dp * 0.8f
        for (i in 0 until 12) {
            val r = Math.toRadians(i * 30.0)
            val inner = if (i % 3 == 0) cR * 0.72f else cR * 0.82f
            canvas.drawLine(
                cx + sin(r).toFloat() * inner, cCy - cos(r).toFloat() * inner,
                cx + sin(r).toFloat() * cR,    cCy - cos(r).toFloat() * cR,
                strokePaint
            )
        }

        // ── compass ring ──
        strokePaint.color       = Color.argb(120, 80, 130, 210)
        strokePaint.strokeWidth = dp * 1.2f
        canvas.drawCircle(cx, cCy, cR, strokePaint)

        // ── cardinal labels ──
        textSmall.textSize  = dp * 10f
        textSmall.color     = Color.argb(220, 200, 225, 255)
        val cardLabels = arrayOf("N", "E", "S", "W")
        val cardRads   = doubleArrayOf(0.0, Math.PI/2, Math.PI, 3*Math.PI/2)
        val lr = cR * 0.52f
        for (i in cardLabels.indices) {
            val r = cardRads[i]
            canvas.drawText(
                cardLabels[i],
                cx + sin(r).toFloat() * lr,
                cCy - cos(r).toFloat() * lr + textSmall.textSize * 0.38f,
                textSmall
            )
        }

        // ── wind needle (FROM direction) ──
        // The head points toward where the wind comes FROM – like a wind vane
        val fromRad  = Math.toRadians(windDirectionDeg.toDouble())
        val headX    = cx + sin(fromRad).toFloat()  * cR * 0.68f
        val headY    = cCy - cos(fromRad).toFloat() * cR * 0.68f
        val tailX    = cx - sin(fromRad).toFloat()  * cR * 0.55f
        val tailY    = cCy + cos(fromRad).toFloat() * cR * 0.55f

        // Tail – white filled rectangle-ish
        val nx = (headX - tailX); val ny = (headY - tailY)
        val nl = sqrt(nx * nx + ny * ny)
        val unx = nx / nl; val uny = ny / nl
        val px  = -uny; val py  = unx
        val hw  = dp * 3.5f
        path.reset()
        path.moveTo(tailX + px * hw, tailY + py * hw)
        path.lineTo(tailX - px * hw, tailY - py * hw)
        path.lineTo(headX - unx * dp * 14f - px * hw, headY - uny * dp * 14f - py * hw)
        path.lineTo(headX - unx * dp * 14f + px * hw, headY - uny * dp * 14f + py * hw)
        path.close()
        fillPaint.color = Color.argb(160, 180, 210, 255)
        canvas.drawPath(path, fillPaint)

        // Head – coloured pointed triangle
        val hw2 = dp * 6f
        path.reset()
        path.moveTo(headX, headY)
        path.lineTo(headX - unx * dp * 20f + px * hw2, headY - uny * dp * 20f + py * hw2)
        path.lineTo(headX - unx * dp * 20f - px * hw2, headY - uny * dp * 20f - py * hw2)
        path.close()
        fillPaint.color = accentColor
        fillPaint.alpha = 240
        canvas.drawPath(path, fillPaint)

        // Centre dot
        fillPaint.color = Color.WHITE; fillPaint.alpha = 200
        canvas.drawCircle(cx, cCy, dp * 2.5f, fillPaint)

        // ── speed (large) ──
        val knots = windSpeedMs * 1.944f
        textBig.textSize = dp * 26f
        textBig.color    = Color.WHITE
        canvas.drawText("%.1f".format(knots), cx, top + pH - dp * 78f, textBig)

        textSmall.textSize = dp * 10f
        textSmall.color    = Color.argb(220, 200, 225, 255)
        textSmall.letterSpacing = 0.15f
        canvas.drawText("KNOTS", cx, top + pH - dp * 62f, textSmall)
        textSmall.letterSpacing = 0f

        // ── cardinal + Beaufort ──
        textMid.textSize = dp * 13f
        textMid.color    = accentColor
        textMid.alpha    = 255
        canvas.drawText("%s  Bf %d".format(toCardinal(windDirectionDeg), bf),
            cx, top + pH - dp * 42f, textMid)

        // ── gust (if notable) ──
        if (windGustsMs > windSpeedMs + 0.8f) {
            textSmall.textSize = dp * 10f
            textSmall.color    = Color.argb(230, 255, 210, 140)
            canvas.drawText("G %.1f kt".format(windGustsMs * 1.944f),
                cx, top + pH - dp * 24f, textSmall)
        }
    }

    // ── utility ───────────────────────────────────────────────────────────────
    private fun lerp(a: Float, b: Float, t: Float) = a + t * (b - a)

    fun toCardinal(deg: Float): String {
        val dirs = arrayOf("N","NNE","NE","ENE","E","ESE","SE","SSE",
                           "S","SSW","SW","WSW","W","WNW","NW","NNW")
        return dirs[((deg / 22.5f + 0.5f).toInt() % 16).coerceIn(0, 15)]
    }

    fun toBeaufort(ms: Float): Int = when {
        ms <  0.3f -> 0; ms <  1.6f -> 1; ms <  3.4f -> 2
        ms <  5.5f -> 3; ms <  8.0f -> 4; ms < 10.8f -> 5
        ms < 13.9f -> 6; ms < 17.2f -> 7; ms < 20.8f -> 8
        ms < 24.5f -> 9; ms < 28.5f -> 10; ms < 32.7f -> 11
        else        -> 12
    }
}
