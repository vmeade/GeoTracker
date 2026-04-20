package com.geotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J0\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/geotracker/WeatherRepository;", "", "()V", "dateFmt", "Ljava/text/SimpleDateFormat;", "fetchWindData", "Lcom/geotracker/WindData;", "lat", "", "lon", "startMs", "", "endMs", "(DDJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class WeatherRepository {
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat dateFmt = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.geotracker.WeatherRepository INSTANCE = null;
    
    private WeatherRepository() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object fetchWindData(double lat, double lon, long startMs, long endMs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.geotracker.WindData> $completion) {
        return null;
    }
}