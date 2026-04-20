package com.geotracker.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class JourneyDao_Impl implements JourneyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Journey> __insertionAdapterOfJourney;

  private final EntityDeletionOrUpdateAdapter<Journey> __deletionAdapterOfJourney;

  private final EntityDeletionOrUpdateAdapter<Journey> __updateAdapterOfJourney;

  public JourneyDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfJourney = new EntityInsertionAdapter<Journey>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `journeys` (`id`,`name`,`startTime`,`endTime`,`totalDistance`,`avgSpeed`,`maxSpeed`,`minElevation`,`maxElevation`,`elevationGain`,`pointCount`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Journey entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getStartTime());
        statement.bindLong(4, entity.getEndTime());
        statement.bindDouble(5, entity.getTotalDistance());
        statement.bindDouble(6, entity.getAvgSpeed());
        statement.bindDouble(7, entity.getMaxSpeed());
        statement.bindDouble(8, entity.getMinElevation());
        statement.bindDouble(9, entity.getMaxElevation());
        statement.bindDouble(10, entity.getElevationGain());
        statement.bindLong(11, entity.getPointCount());
      }
    };
    this.__deletionAdapterOfJourney = new EntityDeletionOrUpdateAdapter<Journey>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `journeys` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Journey entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfJourney = new EntityDeletionOrUpdateAdapter<Journey>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `journeys` SET `id` = ?,`name` = ?,`startTime` = ?,`endTime` = ?,`totalDistance` = ?,`avgSpeed` = ?,`maxSpeed` = ?,`minElevation` = ?,`maxElevation` = ?,`elevationGain` = ?,`pointCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Journey entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getStartTime());
        statement.bindLong(4, entity.getEndTime());
        statement.bindDouble(5, entity.getTotalDistance());
        statement.bindDouble(6, entity.getAvgSpeed());
        statement.bindDouble(7, entity.getMaxSpeed());
        statement.bindDouble(8, entity.getMinElevation());
        statement.bindDouble(9, entity.getMaxElevation());
        statement.bindDouble(10, entity.getElevationGain());
        statement.bindLong(11, entity.getPointCount());
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public Object insertJourney(final Journey journey, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfJourney.insertAndReturnId(journey);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteJourney(final Journey journey, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfJourney.handle(journey);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateJourney(final Journey journey, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfJourney.handle(journey);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Journey>> getAllJourneys() {
    final String _sql = "SELECT * FROM journeys ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"journeys"}, false, new Callable<List<Journey>>() {
      @Override
      @Nullable
      public List<Journey> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDistance");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfMinElevation = CursorUtil.getColumnIndexOrThrow(_cursor, "minElevation");
          final int _cursorIndexOfMaxElevation = CursorUtil.getColumnIndexOrThrow(_cursor, "maxElevation");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfPointCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pointCount");
          final List<Journey> _result = new ArrayList<Journey>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Journey _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final float _tmpTotalDistance;
            _tmpTotalDistance = _cursor.getFloat(_cursorIndexOfTotalDistance);
            final float _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getFloat(_cursorIndexOfAvgSpeed);
            final float _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getFloat(_cursorIndexOfMaxSpeed);
            final double _tmpMinElevation;
            _tmpMinElevation = _cursor.getDouble(_cursorIndexOfMinElevation);
            final double _tmpMaxElevation;
            _tmpMaxElevation = _cursor.getDouble(_cursorIndexOfMaxElevation);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final int _tmpPointCount;
            _tmpPointCount = _cursor.getInt(_cursorIndexOfPointCount);
            _item = new Journey(_tmpId,_tmpName,_tmpStartTime,_tmpEndTime,_tmpTotalDistance,_tmpAvgSpeed,_tmpMaxSpeed,_tmpMinElevation,_tmpMaxElevation,_tmpElevationGain,_tmpPointCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Journey>> getRecentJourneys() {
    final String _sql = "SELECT * FROM journeys ORDER BY startTime DESC LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"journeys"}, false, new Callable<List<Journey>>() {
      @Override
      @Nullable
      public List<Journey> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDistance");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfMinElevation = CursorUtil.getColumnIndexOrThrow(_cursor, "minElevation");
          final int _cursorIndexOfMaxElevation = CursorUtil.getColumnIndexOrThrow(_cursor, "maxElevation");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfPointCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pointCount");
          final List<Journey> _result = new ArrayList<Journey>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Journey _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final float _tmpTotalDistance;
            _tmpTotalDistance = _cursor.getFloat(_cursorIndexOfTotalDistance);
            final float _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getFloat(_cursorIndexOfAvgSpeed);
            final float _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getFloat(_cursorIndexOfMaxSpeed);
            final double _tmpMinElevation;
            _tmpMinElevation = _cursor.getDouble(_cursorIndexOfMinElevation);
            final double _tmpMaxElevation;
            _tmpMaxElevation = _cursor.getDouble(_cursorIndexOfMaxElevation);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final int _tmpPointCount;
            _tmpPointCount = _cursor.getInt(_cursorIndexOfPointCount);
            _item = new Journey(_tmpId,_tmpName,_tmpStartTime,_tmpEndTime,_tmpTotalDistance,_tmpAvgSpeed,_tmpMaxSpeed,_tmpMinElevation,_tmpMaxElevation,_tmpElevationGain,_tmpPointCount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getJourneyById(final long id, final Continuation<? super Journey> $completion) {
    final String _sql = "SELECT * FROM journeys WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Journey>() {
      @Override
      @Nullable
      public Journey call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfTotalDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "totalDistance");
          final int _cursorIndexOfAvgSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "avgSpeed");
          final int _cursorIndexOfMaxSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "maxSpeed");
          final int _cursorIndexOfMinElevation = CursorUtil.getColumnIndexOrThrow(_cursor, "minElevation");
          final int _cursorIndexOfMaxElevation = CursorUtil.getColumnIndexOrThrow(_cursor, "maxElevation");
          final int _cursorIndexOfElevationGain = CursorUtil.getColumnIndexOrThrow(_cursor, "elevationGain");
          final int _cursorIndexOfPointCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pointCount");
          final Journey _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            final long _tmpEndTime;
            _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            final float _tmpTotalDistance;
            _tmpTotalDistance = _cursor.getFloat(_cursorIndexOfTotalDistance);
            final float _tmpAvgSpeed;
            _tmpAvgSpeed = _cursor.getFloat(_cursorIndexOfAvgSpeed);
            final float _tmpMaxSpeed;
            _tmpMaxSpeed = _cursor.getFloat(_cursorIndexOfMaxSpeed);
            final double _tmpMinElevation;
            _tmpMinElevation = _cursor.getDouble(_cursorIndexOfMinElevation);
            final double _tmpMaxElevation;
            _tmpMaxElevation = _cursor.getDouble(_cursorIndexOfMaxElevation);
            final double _tmpElevationGain;
            _tmpElevationGain = _cursor.getDouble(_cursorIndexOfElevationGain);
            final int _tmpPointCount;
            _tmpPointCount = _cursor.getInt(_cursorIndexOfPointCount);
            _result = new Journey(_tmpId,_tmpName,_tmpStartTime,_tmpEndTime,_tmpTotalDistance,_tmpAvgSpeed,_tmpMaxSpeed,_tmpMinElevation,_tmpMaxElevation,_tmpElevationGain,_tmpPointCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getJourneyCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM journeys WHERE endTime > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalDistance(final Continuation<? super Float> $completion) {
    final String _sql = "SELECT SUM(totalDistance) FROM journeys WHERE endTime > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getOverallMaxSpeed(final Continuation<? super Float> $completion) {
    final String _sql = "SELECT MAX(maxSpeed) FROM journeys WHERE endTime > 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
