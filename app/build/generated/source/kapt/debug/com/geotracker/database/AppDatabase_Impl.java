package com.geotracker.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile JourneyDao _journeyDao;

  private volatile TrackPointDao _trackPointDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `journeys` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `totalDistance` REAL NOT NULL, `avgSpeed` REAL NOT NULL, `maxSpeed` REAL NOT NULL, `minElevation` REAL NOT NULL, `maxElevation` REAL NOT NULL, `elevationGain` REAL NOT NULL, `pointCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `track_points` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `journeyId` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `altitude` REAL NOT NULL, `speed` REAL NOT NULL, `accuracy` REAL NOT NULL, FOREIGN KEY(`journeyId`) REFERENCES `journeys`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_track_points_journeyId` ON `track_points` (`journeyId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '27e3d7a9421daa71ba20b0f57e48d57b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `journeys`");
        db.execSQL("DROP TABLE IF EXISTS `track_points`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsJourneys = new HashMap<String, TableInfo.Column>(11);
        _columnsJourneys.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("endTime", new TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("totalDistance", new TableInfo.Column("totalDistance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("avgSpeed", new TableInfo.Column("avgSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("maxSpeed", new TableInfo.Column("maxSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("minElevation", new TableInfo.Column("minElevation", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("maxElevation", new TableInfo.Column("maxElevation", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("elevationGain", new TableInfo.Column("elevationGain", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJourneys.put("pointCount", new TableInfo.Column("pointCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysJourneys = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesJourneys = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoJourneys = new TableInfo("journeys", _columnsJourneys, _foreignKeysJourneys, _indicesJourneys);
        final TableInfo _existingJourneys = TableInfo.read(db, "journeys");
        if (!_infoJourneys.equals(_existingJourneys)) {
          return new RoomOpenHelper.ValidationResult(false, "journeys(com.geotracker.database.Journey).\n"
                  + " Expected:\n" + _infoJourneys + "\n"
                  + " Found:\n" + _existingJourneys);
        }
        final HashMap<String, TableInfo.Column> _columnsTrackPoints = new HashMap<String, TableInfo.Column>(8);
        _columnsTrackPoints.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("journeyId", new TableInfo.Column("journeyId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("altitude", new TableInfo.Column("altitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("speed", new TableInfo.Column("speed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrackPoints.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrackPoints = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTrackPoints.add(new TableInfo.ForeignKey("journeys", "CASCADE", "NO ACTION", Arrays.asList("journeyId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTrackPoints = new HashSet<TableInfo.Index>(1);
        _indicesTrackPoints.add(new TableInfo.Index("index_track_points_journeyId", false, Arrays.asList("journeyId"), Arrays.asList("ASC")));
        final TableInfo _infoTrackPoints = new TableInfo("track_points", _columnsTrackPoints, _foreignKeysTrackPoints, _indicesTrackPoints);
        final TableInfo _existingTrackPoints = TableInfo.read(db, "track_points");
        if (!_infoTrackPoints.equals(_existingTrackPoints)) {
          return new RoomOpenHelper.ValidationResult(false, "track_points(com.geotracker.database.TrackPoint).\n"
                  + " Expected:\n" + _infoTrackPoints + "\n"
                  + " Found:\n" + _existingTrackPoints);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "27e3d7a9421daa71ba20b0f57e48d57b", "7136fc532f301c4b14455c38d4c8cd39");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "journeys","track_points");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `journeys`");
      _db.execSQL("DELETE FROM `track_points`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(JourneyDao.class, JourneyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TrackPointDao.class, TrackPointDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public JourneyDao journeyDao() {
    if (_journeyDao != null) {
      return _journeyDao;
    } else {
      synchronized(this) {
        if(_journeyDao == null) {
          _journeyDao = new JourneyDao_Impl(this);
        }
        return _journeyDao;
      }
    }
  }

  @Override
  public TrackPointDao trackPointDao() {
    if (_trackPointDao != null) {
      return _trackPointDao;
    } else {
      synchronized(this) {
        if(_trackPointDao == null) {
          _trackPointDao = new TrackPointDao_Impl(this);
        }
        return _trackPointDao;
      }
    }
  }
}
