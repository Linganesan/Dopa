package com.kuviam.dopa.model;

import java.util.List;
import com.kuviam.dopa.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "LOCUS".
 */
public class Locus {

    private Long id;
    /** Not-null value. */
    private String name;
    private String creator;
    private String type;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient LocusDao myDao;

    private List<Locus_text_list> locus_text_listList;

    public Locus() {
    }

    public Locus(Long id) {
        this.id = id;
    }

    public Locus(Long id, String name, String creator, String type) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.type = type;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLocusDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Locus_text_list> getLocus_text_listList() {
        if (locus_text_listList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Locus_text_listDao targetDao = daoSession.getLocus_text_listDao();
            List<Locus_text_list> locus_text_listListNew = targetDao._queryLocus_Locus_text_listList(id);
            synchronized (this) {
                if(locus_text_listList == null) {
                    locus_text_listList = locus_text_listListNew;
                }
            }
        }
        return locus_text_listList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetLocus_text_listList() {
        locus_text_listList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
