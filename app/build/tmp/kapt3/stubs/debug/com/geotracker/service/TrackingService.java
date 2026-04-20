package com.geotracker.service;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 /2\u00020\u0001:\u0001/B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\nH\u0002J\b\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\rH\u0002J\u0012\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\'H\u0016J\b\u0010(\u001a\u00020!H\u0016J\b\u0010)\u001a\u00020!H\u0016J\"\u0010*\u001a\u00020\u00172\b\u0010&\u001a\u0004\u0018\u00010\'2\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u0017H\u0016J\b\u0010-\u001a\u00020!H\u0003J\b\u0010.\u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0018\u00010\u001bR\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/geotracker/service/TrackingService;", "Landroidx/lifecycle/LifecycleService;", "()V", "db", "Lcom/geotracker/database/AppDatabase;", "elevationGain", "", "journeyId", "", "journeyName", "", "lastElevation", "lastLocation", "Landroid/location/Location;", "locationListener", "Landroid/location/LocationListener;", "locationManager", "Landroid/location/LocationManager;", "maxElevation", "maxSpeed", "", "minElevation", "pointCount", "", "startTime", "totalDistance", "wakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "buildNotification", "Landroid/app/Notification;", "text", "createNotificationChannel", "", "handleLocation", "location", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "startTracking", "stopTracking", "Companion", "app_debug"})
public final class TrackingService extends androidx.lifecycle.LifecycleService {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START = "com.geotracker.START";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP = "com.geotracker.STOP";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_UPDATE = "com.geotracker.UPDATE";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_JOURNEY_NAME = "journey_name";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_JOURNEY_ID = "journey_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_LATITUDE = "latitude";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_LONGITUDE = "longitude";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_SPEED = "speed";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_DISTANCE = "distance";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_ELAPSED = "elapsed";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_ALTITUDE = "altitude";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_ACCURACY = "accuracy";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_ID = "tracking_channel";
    private static final int NOTIFICATION_ID = 1001;
    private long journeyId = -1L;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String journeyName = "";
    private long startTime = 0L;
    private float totalDistance = 0.0F;
    private float maxSpeed = 0.0F;
    private double minElevation = 1.7976931348623157E308;
    private double maxElevation = 4.9E-324;
    private double elevationGain = 0.0;
    private double lastElevation = 0.0;
    private int pointCount = 0;
    @org.jetbrains.annotations.Nullable()
    private android.location.Location lastLocation;
    @org.jetbrains.annotations.Nullable()
    private android.os.PowerManager.WakeLock wakeLock;
    private android.location.LocationManager locationManager;
    private com.geotracker.database.AppDatabase db;
    @org.jetbrains.annotations.NotNull()
    private final android.location.LocationListener locationListener = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.geotracker.service.TrackingService.Companion Companion = null;
    
    public TrackingService() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void startTracking() {
    }
    
    private final void stopTracking() {
    }
    
    private final void handleLocation(android.location.Location location) {
    }
    
    private final android.app.Notification buildNotification(java.lang.String text) {
        return null;
    }
    
    private final void createNotificationChannel() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/geotracker/service/TrackingService$Companion;", "", "()V", "ACTION_START", "", "ACTION_STOP", "ACTION_UPDATE", "CHANNEL_ID", "EXTRA_ACCURACY", "EXTRA_ALTITUDE", "EXTRA_DISTANCE", "EXTRA_ELAPSED", "EXTRA_JOURNEY_ID", "EXTRA_JOURNEY_NAME", "EXTRA_LATITUDE", "EXTRA_LONGITUDE", "EXTRA_SPEED", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}