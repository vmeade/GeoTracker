package com.geotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\bH\u0002J\u0010\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\bH\u0002J\u0012\u0010)\u001a\u00020$2\b\u0010*\u001a\u0004\u0018\u00010+H\u0014J\b\u0010,\u001a\u00020$H\u0014J\b\u0010-\u001a\u00020$H\u0014J\b\u0010.\u001a\u00020$H\u0014J\b\u0010/\u001a\u00020\u0010H\u0016J\b\u00100\u001a\u00020$H\u0002J\u0010\u00101\u001a\u00020$2\u0006\u00102\u001a\u000203H\u0002J\u0016\u00104\u001a\u00020$2\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0002J\b\u00106\u001a\u00020$H\u0002J\b\u00107\u001a\u00020$H\u0002J\u0010\u00108\u001a\u00020$2\u0006\u00102\u001a\u000203H\u0002J\b\u00109\u001a\u00020$H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006:"}, d2 = {"Lcom/geotracker/PlaybackActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/geotracker/databinding/ActivityPlaybackBinding;", "boatMarker", "Lorg/osmdroid/views/overlay/Marker;", "currentOffsetMs", "", "db", "Lcom/geotracker/database/AppDatabase;", "frameRunnable", "Ljava/lang/Runnable;", "handler", "Landroid/os/Handler;", "isPlaying", "", "journeyDurationMs", "journeyId", "pausedOffsetMs", "routePolyline", "Lorg/osmdroid/views/overlay/Polyline;", "speedMultiplier", "", "trackPoints", "", "Lcom/geotracker/database/TrackPoint;", "trailAddedToMap", "trailEndIndex", "trailPolyline", "wallClockStartMs", "windData", "Lcom/geotracker/WindData;", "windOverlay", "Lcom/geotracker/WindVectorOverlay;", "applyFrame", "", "offsetMs", "interpolatePosition", "Lorg/osmdroid/util/GeoPoint;", "targetTs", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onPause", "onResume", "onSupportNavigateUp", "pausePlayback", "pushWind", "sample", "Lcom/geotracker/WindSample;", "setupMap", "points", "startPlayback", "stopPlayback", "updateWindHud", "wireControls", "app_debug"})
public final class PlaybackActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.geotracker.databinding.ActivityPlaybackBinding binding;
    private com.geotracker.database.AppDatabase db;
    private long journeyId = -1L;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.geotracker.database.TrackPoint> trackPoints;
    @org.jetbrains.annotations.Nullable()
    private com.geotracker.WindData windData;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Polyline routePolyline;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Polyline trailPolyline;
    @org.jetbrains.annotations.Nullable()
    private org.osmdroid.views.overlay.Marker boatMarker;
    @org.jetbrains.annotations.Nullable()
    private com.geotracker.WindVectorOverlay windOverlay;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    private boolean isPlaying = false;
    private int speedMultiplier = 10;
    private long journeyDurationMs = 0L;
    private long currentOffsetMs = 0L;
    private long wallClockStartMs = 0L;
    private long pausedOffsetMs = 0L;
    private int trailEndIndex = -1;
    private boolean trailAddedToMap = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable frameRunnable = null;
    
    public PlaybackActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void wireControls() {
    }
    
    private final void startPlayback() {
    }
    
    private final void pausePlayback() {
    }
    
    private final void stopPlayback() {
    }
    
    private final void applyFrame(long offsetMs) {
    }
    
    private final org.osmdroid.util.GeoPoint interpolatePosition(long targetTs) {
        return null;
    }
    
    private final void pushWind(com.geotracker.WindSample sample) {
    }
    
    private final void updateWindHud(com.geotracker.WindSample sample) {
    }
    
    private final void setupMap(java.util.List<com.geotracker.database.TrackPoint> points) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}