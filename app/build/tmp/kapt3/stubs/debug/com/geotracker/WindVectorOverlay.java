package com.geotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0011\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001UB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J \u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010A\u001a\u00020!H\u0016J\u0018\u0010B\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010C\u001a\u00020\u000eH\u0002J\u0018\u0010D\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\u0006\u0010E\u001a\u00020\u000eH\u0002J \u0010F\u001a\u00020\u00112\u0006\u0010G\u001a\u00020\u00112\u0006\u0010H\u001a\u00020\u00112\u0006\u0010I\u001a\u00020\u0011H\u0002J \u0010J\u001a\u00020\u000e2\u0006\u0010G\u001a\u00020\u000e2\u0006\u0010H\u001a\u00020\u000e2\u0006\u0010I\u001a\u00020\u0011H\u0002J\b\u0010K\u001a\u00020\u001cH\u0002J\u0010\u0010L\u001a\u00020\u000e2\u0006\u0010M\u001a\u00020\u0011H\u0002J\u0006\u0010N\u001a\u00020>J\u0006\u0010O\u001a\u00020>J\b\u0010P\u001a\u00020>H\u0002J\u000e\u0010Q\u001a\u00020\u000e2\u0006\u0010M\u001a\u00020\u0011J\u000e\u0010R\u001a\u00020S2\u0006\u0010T\u001a\u00020\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010 \u001a\u00020!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010#\"\u0004\b(\u0010%R\u000e\u0010)\u001a\u00020*X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u00102\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001a\u00107\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u00104\"\u0004\b9\u00106R\u001a\u0010:\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u00104\"\u0004\b<\u00106\u00a8\u0006V"}, d2 = {"Lcom/geotracker/WindVectorOverlay;", "Lorg/osmdroid/views/overlay/Overlay;", "mapView", "Lorg/osmdroid/views/MapView;", "(Lorg/osmdroid/views/MapView;)V", "animRunnable", "Ljava/lang/Runnable;", "boatGeoPoint", "Lorg/osmdroid/util/GeoPoint;", "getBoatGeoPoint", "()Lorg/osmdroid/util/GeoPoint;", "setBoatGeoPoint", "(Lorg/osmdroid/util/GeoPoint;)V", "canvasH", "", "canvasW", "dp", "", "fillPaint", "Landroid/graphics/Paint;", "handler", "Landroid/os/Handler;", "lastFrameMs", "", "ovalF", "Landroid/graphics/RectF;", "particles", "Ljava/util/ArrayList;", "Lcom/geotracker/WindVectorOverlay$Particle;", "path", "Landroid/graphics/Path;", "rectF", "showCurrentArrow", "", "getShowCurrentArrow", "()Z", "setShowCurrentArrow", "(Z)V", "showField", "getShowField", "setShowField", "speedColors", "", "speedStops", "", "strokePaint", "textBig", "textMid", "textSmall", "trailPaint", "windDirectionDeg", "getWindDirectionDeg", "()F", "setWindDirectionDeg", "(F)V", "windGustsMs", "getWindGustsMs", "setWindGustsMs", "windSpeedMs", "getWindSpeedMs", "setWindSpeedMs", "draw", "", "canvas", "Landroid/graphics/Canvas;", "shadow", "drawInstrument", "accentColor", "drawParticles", "color", "lerp", "a", "b", "t", "lerpColor", "spawn", "speedColor", "ms", "startAnimation", "stopAnimation", "tick", "toBeaufort", "toCardinal", "", "deg", "Particle", "app_debug"})
public final class WindVectorOverlay extends org.osmdroid.views.overlay.Overlay {
    @org.jetbrains.annotations.NotNull()
    private final org.osmdroid.views.MapView mapView = null;
    private float windSpeedMs = 0.0F;
    private float windDirectionDeg = 0.0F;
    private float windGustsMs = 0.0F;
    private boolean showField = true;
    private boolean showCurrentArrow = true;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.util.GeoPoint boatGeoPoint;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.geotracker.WindVectorOverlay.Particle> particles = null;
    private long lastFrameMs = 0L;
    private int canvasW = 0;
    private int canvasH = 0;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable animRunnable = null;
    private final float dp = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint trailPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint fillPaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint strokePaint = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint textBig = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint textMid = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Paint textSmall = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.Path path = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.RectF rectF = null;
    @org.jetbrains.annotations.NotNull()
    private final android.graphics.RectF ovalF = null;
    @org.jetbrains.annotations.NotNull()
    private final float[] speedStops = {0.0F, 3.0F, 7.0F, 12.0F, 18.0F, 25.0F};
    @org.jetbrains.annotations.NotNull()
    private final int[] speedColors = null;
    
    public WindVectorOverlay(@org.jetbrains.annotations.NotNull()
    org.osmdroid.views.MapView mapView) {
        super(null);
    }
    
    public final float getWindSpeedMs() {
        return 0.0F;
    }
    
    public final void setWindSpeedMs(float p0) {
    }
    
    public final float getWindDirectionDeg() {
        return 0.0F;
    }
    
    public final void setWindDirectionDeg(float p0) {
    }
    
    public final float getWindGustsMs() {
        return 0.0F;
    }
    
    public final void setWindGustsMs(float p0) {
    }
    
    public final boolean getShowField() {
        return false;
    }
    
    public final void setShowField(boolean p0) {
    }
    
    public final boolean getShowCurrentArrow() {
        return false;
    }
    
    public final void setShowCurrentArrow(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final org.osmdroid.util.GeoPoint getBoatGeoPoint() {
        return null;
    }
    
    public final void setBoatGeoPoint(@org.jetbrains.annotations.Nullable()
    org.osmdroid.util.GeoPoint p0) {
    }
    
    public final void startAnimation() {
    }
    
    public final void stopAnimation() {
    }
    
    private final int speedColor(float ms) {
        return 0;
    }
    
    private final int lerpColor(int a, int b, float t) {
        return 0;
    }
    
    private final void tick() {
    }
    
    private final com.geotracker.WindVectorOverlay.Particle spawn() {
        return null;
    }
    
    @java.lang.Override()
    public void draw(@org.jetbrains.annotations.NotNull()
    android.graphics.Canvas canvas, @org.jetbrains.annotations.NotNull()
    org.osmdroid.views.MapView mapView, boolean shadow) {
    }
    
    private final void drawParticles(android.graphics.Canvas canvas, int color) {
    }
    
    private final void drawInstrument(android.graphics.Canvas canvas, int accentColor) {
    }
    
    private final float lerp(float a, float b, float t) {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String toCardinal(float deg) {
        return null;
    }
    
    public final int toBeaufort(float ms) {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020$H\u00d6\u0001R\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000b\"\u0004\b\u0012\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000b\"\u0004\b\u0014\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000b\"\u0004\b\u0016\u0010\r\u00a8\u0006%"}, d2 = {"Lcom/geotracker/WindVectorOverlay$Particle;", "", "x", "", "y", "px", "py", "age", "maxAge", "(FFFFFF)V", "getAge", "()F", "setAge", "(F)V", "getMaxAge", "getPx", "setPx", "getPy", "setPy", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    static final class Particle {
        private float x;
        private float y;
        private float px;
        private float py;
        private float age;
        private final float maxAge = 0.0F;
        
        public Particle(float x, float y, float px, float py, float age, float maxAge) {
            super();
        }
        
        public final float getX() {
            return 0.0F;
        }
        
        public final void setX(float p0) {
        }
        
        public final float getY() {
            return 0.0F;
        }
        
        public final void setY(float p0) {
        }
        
        public final float getPx() {
            return 0.0F;
        }
        
        public final void setPx(float p0) {
        }
        
        public final float getPy() {
            return 0.0F;
        }
        
        public final void setPy(float p0) {
        }
        
        public final float getAge() {
            return 0.0F;
        }
        
        public final void setAge(float p0) {
        }
        
        public final float getMaxAge() {
            return 0.0F;
        }
        
        public final float component1() {
            return 0.0F;
        }
        
        public final float component2() {
            return 0.0F;
        }
        
        public final float component3() {
            return 0.0F;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        public final float component5() {
            return 0.0F;
        }
        
        public final float component6() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.geotracker.WindVectorOverlay.Particle copy(float x, float y, float px, float py, float age, float maxAge) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}