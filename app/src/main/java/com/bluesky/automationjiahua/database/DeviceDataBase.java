package com.bluesky.automationjiahua.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bluesky.automationjiahua.base.App;

import org.jetbrains.annotations.NotNull;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:设备数据库
 * 数据库迁移：
 * 1.增加一个Migration，新增表，或新增字段。
 * 2.addMigrations()
 * 3.fallbackToDestructiveMigration()得取消，否则会丢弃旧数据库
 * 4.修改version为新版本号
 * 注意：migrete中的sql语句，有两种写法。
 */
//将exportSchema设为false了。默认应该为true
@Database(entities = {Device.class, InterLock.class}, version = 2, exportSchema = false)
public abstract class DeviceDataBase extends RoomDatabase {
    private static DeviceDataBase INSTANCE;

    public static synchronized DeviceDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            Migration migration_1_2 = new Migration(1, 2) {
                @Override
                public void migrate(@NotNull SupportSQLiteDatabase database) {
                    //增加新表的迁移策略
                    database.execSQL("CREATE TABLE interlock (" +
                            "number INTEGER PRIMARY KEY NOT NULL," +
                            "domain TEXT," +
                            "device_name TEXT," +
                            "tag TEXT," +
                            "control_value TEXT," +
                            "interlock_device_name TEXT," +
                            "interlock_device_tag TEXT," +
                            "action_type TEXT," +
                            "remark TEXT )");
                    //增加字段的迁移策略
                    //database.execSQL("ALTER TABLE word ADD COLUMN sw_chinese_visiable INTEGER NOT NULL");
                }
            };

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DeviceDataBase.class, App.HUACHAN_DEVICE_DATA_BASE_NAME)
                    .createFromAsset("huachan.db")
                    .addMigrations(migration_1_2)
                    //.fallbackToDestructiveMigration()//回滚
                    //.allowMainThreadQueries()//强制主线程查询
                    .build();

        }
        return INSTANCE;
    }

    public abstract DeviceDao getDeviceDao();

    public abstract InterLockDao getInterLockDao();
}
