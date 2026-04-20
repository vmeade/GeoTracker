package com.geotracker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\u0010\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0006H\u0002J$\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0082@\u00a2\u0006\u0002\u0010\u0015J\u0012\u0010\u0016\u001a\u00020\u000f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u000fH\u0014J\b\u0010!\u001a\u00020\u000fH\u0014J\b\u0010\"\u001a\u00020\u001aH\u0016J\u0010\u0010#\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0006H\u0002J\u0016\u0010$\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0016\u0010%\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/geotracker/JourneyDetailActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/geotracker/databinding/ActivityJourneyDetailBinding;", "currentJourney", "Lcom/geotracker/database/Journey;", "currentPoints", "", "Lcom/geotracker/database/TrackPoint;", "db", "Lcom/geotracker/database/AppDatabase;", "journeyId", "", "bindStats", "", "j", "deleteJourney", "journey", "exportJourney", "points", "(Lcom/geotracker/database/Journey;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPause", "onResume", "onSupportNavigateUp", "renameJourney", "setupCharts", "setupMap", "app_debug"})
public final class JourneyDetailActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.geotracker.databinding.ActivityJourneyDetailBinding binding;
    private com.geotracker.database.AppDatabase db;
    private long journeyId = -1L;
    @org.jetbrains.annotations.Nullable()
    private com.geotracker.database.Journey currentJourney;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.geotracker.database.TrackPoint> currentPoints;
    
    public JourneyDetailActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    private final void renameJourney(com.geotracker.database.Journey journey) {
    }
    
    private final void deleteJourney(com.geotracker.database.Journey journey) {
    }
    
    private final java.lang.Object exportJourney(com.geotracker.database.Journey journey, java.util.List<com.geotracker.database.TrackPoint> points, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void bindStats(com.geotracker.database.Journey j) {
    }
    
    private final void setupMap(java.util.List<com.geotracker.database.TrackPoint> points) {
    }
    
    private final void setupCharts(java.util.List<com.geotracker.database.TrackPoint> points) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}