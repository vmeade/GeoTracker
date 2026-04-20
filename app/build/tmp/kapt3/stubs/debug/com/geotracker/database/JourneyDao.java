package com.geotracker.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0018\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0017"}, d2 = {"Lcom/geotracker/database/JourneyDao;", "", "deleteJourney", "", "journey", "Lcom/geotracker/database/Journey;", "(Lcom/geotracker/database/Journey;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllJourneys", "Landroidx/lifecycle/LiveData;", "", "getJourneyById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getJourneyCount", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOverallMaxSpeed", "", "getRecentJourneys", "getTotalDistance", "insertJourney", "updateJourney", "app_debug"})
@androidx.room.Dao()
public abstract interface JourneyDao {
    
    @androidx.room.Query(value = "SELECT * FROM journeys ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.geotracker.database.Journey>> getAllJourneys();
    
    @androidx.room.Query(value = "SELECT * FROM journeys ORDER BY startTime DESC LIMIT 5")
    @org.jetbrains.annotations.NotNull()
    public abstract androidx.lifecycle.LiveData<java.util.List<com.geotracker.database.Journey>> getRecentJourneys();
    
    @androidx.room.Query(value = "SELECT * FROM journeys WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJourneyById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.geotracker.database.Journey> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM journeys WHERE endTime > 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJourneyCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(totalDistance) FROM journeys WHERE endTime > 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalDistance(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Float> $completion);
    
    @androidx.room.Query(value = "SELECT MAX(maxSpeed) FROM journeys WHERE endTime > 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getOverallMaxSpeed(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Float> $completion);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertJourney(@org.jetbrains.annotations.NotNull()
    com.geotracker.database.Journey journey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateJourney(@org.jetbrains.annotations.NotNull()
    com.geotracker.database.Journey journey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteJourney(@org.jetbrains.annotations.NotNull()
    com.geotracker.database.Journey journey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}