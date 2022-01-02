package com.bluesky.automationjiahua.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author BlueSky
 * @date 2021/12/3
 * Description:
 */
@Dao
public interface InterLockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllInterLock(InterLock... items);

    @Query("DELETE FROM InterLock")
    void deleteAllInterLock();

    @Delete
    void deleteInterLock(InterLock... items);

    /*查询所有记录(List)*/
    @Query("SELECT * FROM interlock")
    List<InterLock> getAllInterLocks();

    @Query("SELECT * FROM interlock")
    List<InterLock> loadAllInterLocks();

    /*更新数据*/
    @Update
    void updateInterLock(InterLock... items);

    /*根据tag过滤查询*/
    @Query("SELECT * FROM interlock WHERE TAG LIKE :tag")
    LiveData<List<InterLock>> queryInterLocksByTag(String tag);

    /*    *//*根据domain过滤查询*//*
    @Query("SELECT * FROM interlock WHERE domain LIKE :domain")
    List<InterLock> queryInterLocksByDomain(String domain);*/

    /*根据domain过滤查询*/
    @Query("SELECT * FROM interlock WHERE domain LIKE :domain")
    List<InterLock> queryInterLocksByDomain(String domain);
}
