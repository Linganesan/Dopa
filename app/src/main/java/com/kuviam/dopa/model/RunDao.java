package com.kuviam.dopa.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.kuviam.dopa.model.Run;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RUN".
*/
public class RunDao extends AbstractDao<Run, Long> {

    public static final String TABLENAME = "RUN";

    /**
     * Properties of entity Run.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Discipline = new Property(1, String.class, "discipline", false, "DISCIPLINE");
        public final static Property Locus = new Property(2, String.class, "locus", false, "LOCUS");
        public final static Property No_of_items = new Property(3, Integer.class, "no_of_items", false, "NO_OF_ITEMS");
        public final static Property Practice_time = new Property(4, Long.class, "practice_time", false, "PRACTICE_TIME");
        public final static Property Recall_time = new Property(5, Long.class, "recall_time", false, "RECALL_TIME");
        public final static Property Assigned_practice_time = new Property(6, Long.class, "assigned_practice_time", false, "ASSIGNED_PRACTICE_TIME");
        public final static Property Assigned_recall_time = new Property(7, Long.class, "assigned_recall_time", false, "ASSIGNED_RECALL_TIME");
        public final static Property Status = new Property(8, String.class, "status", false, "STATUS");
        public final static Property Per_practice_time = new Property(9, Long.class, "per_practice_time", false, "PER_PRACTICE_TIME");
        public final static Property Per_recall_time = new Property(10, Long.class, "per_recall_time", false, "PER_RECALL_TIME");
        public final static Property Start_timestamp = new Property(11, java.util.Date.class, "start_timestamp", false, "START_TIMESTAMP");
    };

    private DaoSession daoSession;


    public RunDao(DaoConfig config) {
        super(config);
    }
    
    public RunDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RUN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"DISCIPLINE\" TEXT NOT NULL ," + // 1: discipline
                "\"LOCUS\" TEXT," + // 2: locus
                "\"NO_OF_ITEMS\" INTEGER," + // 3: no_of_items
                "\"PRACTICE_TIME\" INTEGER," + // 4: practice_time
                "\"RECALL_TIME\" INTEGER," + // 5: recall_time
                "\"ASSIGNED_PRACTICE_TIME\" INTEGER," + // 6: assigned_practice_time
                "\"ASSIGNED_RECALL_TIME\" INTEGER," + // 7: assigned_recall_time
                "\"STATUS\" TEXT," + // 8: status
                "\"PER_PRACTICE_TIME\" INTEGER," + // 9: per_practice_time
                "\"PER_RECALL_TIME\" INTEGER," + // 10: per_recall_time
                "\"START_TIMESTAMP\" INTEGER);"); // 11: start_timestamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RUN\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Run entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getDiscipline());
 
        String locus = entity.getLocus();
        if (locus != null) {
            stmt.bindString(3, locus);
        }
 
        Integer no_of_items = entity.getNo_of_items();
        if (no_of_items != null) {
            stmt.bindLong(4, no_of_items);
        }
 
        Long practice_time = entity.getPractice_time();
        if (practice_time != null) {
            stmt.bindLong(5, practice_time);
        }
 
        Long recall_time = entity.getRecall_time();
        if (recall_time != null) {
            stmt.bindLong(6, recall_time);
        }
 
        Long assigned_practice_time = entity.getAssigned_practice_time();
        if (assigned_practice_time != null) {
            stmt.bindLong(7, assigned_practice_time);
        }
 
        Long assigned_recall_time = entity.getAssigned_recall_time();
        if (assigned_recall_time != null) {
            stmt.bindLong(8, assigned_recall_time);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(9, status);
        }
 
        Long per_practice_time = entity.getPer_practice_time();
        if (per_practice_time != null) {
            stmt.bindLong(10, per_practice_time);
        }
 
        Long per_recall_time = entity.getPer_recall_time();
        if (per_recall_time != null) {
            stmt.bindLong(11, per_recall_time);
        }
 
        java.util.Date start_timestamp = entity.getStart_timestamp();
        if (start_timestamp != null) {
            stmt.bindLong(12, start_timestamp.getTime());
        }
    }

    @Override
    protected void attachEntity(Run entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Run readEntity(Cursor cursor, int offset) {
        Run entity = new Run( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // discipline
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // locus
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // no_of_items
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // practice_time
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // recall_time
            cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6), // assigned_practice_time
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // assigned_recall_time
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // status
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9), // per_practice_time
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // per_recall_time
            cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)) // start_timestamp
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Run entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDiscipline(cursor.getString(offset + 1));
        entity.setLocus(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNo_of_items(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setPractice_time(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setRecall_time(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setAssigned_practice_time(cursor.isNull(offset + 6) ? null : cursor.getLong(offset + 6));
        entity.setAssigned_recall_time(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setStatus(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPer_practice_time(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
        entity.setPer_recall_time(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setStart_timestamp(cursor.isNull(offset + 11) ? null : new java.util.Date(cursor.getLong(offset + 11)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Run entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Run entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
