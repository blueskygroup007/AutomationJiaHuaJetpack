package com.bluesky.automationjiahua.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:
 * TODO:注意：当在具有 SELECT语句的 Query方法上使用时，此查询生成的代码将在事务中运行。
 * 有两种主要的情况，你可能想要这样做:
 * 如果查询的结果相当大，那么最好在事务中运行它，来接收一致的结果。否则，如果查询结果不适合单个数据操作，由于游标窗口交换之间数据库中的更改，查询结果可能被损坏。
 * 如果查询的结果是一个带有relationship字段的POJO，则分别查询这些字段。要在这些查询之间接收一致的结果，还需要在单个事务中运行它们。
 * 如果查询是异步的，例如，返回lifecycle。或者RxJava Flowable，在运行查询时正确处理事务，而不是在调用方法时。
 * 将此注释放在 Insert、Update或Delete方法上没有影响，因为这些方法总是在事务中运行。类似地，如果一个方法使用Query注释，但是运行INSERT、UPDATE或DELETE语句，那么它将自动包装在一个事务中，而这个注释没有效果。
 * TODO:Room一次最多只执行一个事务，其他事务按先到先得顺序排队执行。
 */
@Dao
public interface DeviceDao {

    /*插入*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Device... devices);

    /*删除所有*/
    @Query("DELETE FROM DEVICE")
    void deleteAll();

    /*删除指定*/
    @Delete
    void deleteDevices(Device... devices);

    /*查询所有记录(List)*/
    @Query("SELECT * FROM device")
    List<Device> getAllDevices();

    /*根据domain过滤查询*/
    @Query("SELECT * FROM DEVICE WHERE domain LIKE :domain")
    List<Device> queryListDevicesByDomain(String domain);

    /*根据domain过滤查询*/
    @Query("SELECT * FROM DEVICE WHERE domain LIKE :domain")
    LiveData<List<Device>> queryLiveDataDevicesByDomain(String domain);

    /*根据tag过滤查询*/
    @Query("SELECT * FROM DEVICE WHERE TAG LIKE :tag")
    LiveData<List<Device>> queryDevicesByTag(String tag);

    /*根据affect过滤查询*/
    @Query("SELECT * FROM DEVICE WHERE AFFECT LIKE :affect")
    LiveData<List<Device>> queryDevicesByAffect(String affect);

    /*根据name过滤查询*/
    @Query("SELECT * FROM DEVICE WHERE NAME LIKE :name")
    LiveData<List<Device>> queryDevicesByName(String name);

    /*更新数据*/
    @Update
    void updateDevices(Device... devices);

    /*根据standard过滤查询*/
    @Query("SELECT * FROM DEVICE WHERE standard LIKE :standard")
    LiveData<List<Device>> queryDevicesByStandard(String standard);

    /*根据type过滤查询*/
    @Query("SELECT * FROM DEVICE WHERE TYPE LIKE :type")
    LiveData<List<Device>> queryDevicesByType(String type);


    /**
     * bservable query return type (LiveData, Flowable, DataSource, DataSourceFactory etc)
     * can only be used with SELECT queries that directly or indirectly (via @Relation, for example) access at least one table.
     * For @RawQuery, you should specify the list of tables to be observed via the observedEntities field.
     * RawQuery 可以返回observable类型， 但是你需要指定在查询中访问的表所对应的实体类
     *
     * @param query 查询
     * @return 返回list
     */
    @RawQuery(observedEntities = Device.class)
    List<Device> rawQueryDevicesByPattern(SupportSQLiteQuery query);




/*    @Query("pragma table_info(device)")
    List<TableInfo> queryDeviceTableColumn();*/
}
