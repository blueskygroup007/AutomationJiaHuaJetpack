package com.bluesky.automationjiahua.database;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.bluesky.automationjiahua.base.App;
import com.bluesky.automationjiahua.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:
 */
public class DeviceRepository {
    private static DeviceRepository INSTANCE = null;

    DeviceDao mDeviceDao;
    InterLockDao mInterLockDao;
    static MutableLiveData<List<Device>> mLiveDataDevices;
    static MutableLiveData<List<InterLock>> mLiveDataInterLocks;
    String Tag = DeviceRepository.class.getSimpleName();
    AppExecutors mExecutors = new AppExecutors();


    public interface LoadDataCallback<T> {
        void onDataLoaded(List<T> t);

        void onDataNotAvailable();
    }

    /**
     * 因为静态的Repository持有context的话,会造成泄露,所以使用getApplicationContext代替.
     */
    private DeviceRepository() {
        DeviceDataBase db = DeviceDataBase.getDatabase(App.instance.getApplicationContext());
        mDeviceDao = db.getDeviceDao();
        mInterLockDao = db.getInterLockDao();
        mLiveDataDevices = new MutableLiveData<>(new ArrayList<>());
        mLiveDataInterLocks = new MutableLiveData<>(new ArrayList<>());
    }

    /**
     * 返回单例
     *
     * @return
     */
    public static DeviceRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeviceRepository();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public InterLockDao getInterLockDao() {
        return mInterLockDao;
    }

    public DeviceDao getDeviceDao() {
        return mDeviceDao;
    }

    /*以下是Interlock的方法----------------------------------------------------------*/

    public MutableLiveData<List<InterLock>> getmLiveDataInterLocks() {
        return mLiveDataInterLocks;
    }

    /*-------只做数据库页面调试用*/
    public void insertInterLocks(InterLock... interLocks) {
        mExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mInterLockDao.insertAllInterLock(interLocks);
            }
        });
    }

    public void deleteAllInterLocks() {
        mExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mInterLockDao.deleteAllInterLock();
            }
        });
    }

    /**
     * 异步的返回所有联锁记录,
     */
    public void getAllInterLocks(LoadDataCallback<InterLock> callback) {
        mExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<InterLock> allInterLocks = mInterLockDao.getAllInterLocks();
                mExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDataLoaded(allInterLocks);
                    }
                });
            }
        });
    }
    /*-------只做数据库页面调试用*/


    /**
     * 使用LiveData获取所有联锁记录
     */

    public void getAllInterlocks() {
        mExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLiveDataInterLocks.postValue(mInterLockDao.loadAllInterLocks());
            }
        });
    }

    public void getInterLocksByDomain(String domian) {
        mExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLiveDataInterLocks.postValue(mInterLockDao.queryInterLocksByDomain(domian));

            }
        });
    }


    /*以下是Device的方法--------------------------------------------------------------*/
    public static MutableLiveData<List<Device>> getmLiveDataDevices() {
        return mLiveDataDevices;
    }

    public void insertDevices(Device... devices) {
        new InsertTask(mDeviceDao).execute(devices);
    }

    public void deleteAllDevices() {
        new DeleteAllTask(mDeviceDao).execute();
    }

    public void deleteDevices(Device... devices) {
        new DeleteTask(mDeviceDao).execute(devices);
    }

    public void updateDevices(Device... devices) {
        new UpdateTask(mDeviceDao).execute(devices);
    }

    public void getAllDevices() {
        new QueryAllTask(mDeviceDao).execute();
    }

    /*返回LiveData*/
    public LiveData<List<Device>> findLiveDataDeviceByDomain(String domain) {
        return mDeviceDao.queryLiveDataDevicesByDomain("%" + domain + "%");
    }


    public void findDeviceByPattern(String domain, String column, String[] keyWords) {
        StringBuilder pattern = new StringBuilder();
        if (!domain.isEmpty()) {
            pattern.append("domain='" + domain);
            pattern.append("' and ");

        }
        if (keyWords != null && keyWords.length > 0) {
            pattern.append(column + " like '");
            for (String keyWord : keyWords
            ) {
                pattern.append("%" + keyWord);
            }
            pattern.append("%'");
        } else {
            pattern.append(column + " like " + "'%'");
        }

        Log.e(Tag, "查询语句:  " + "select * from device where " + pattern.toString());
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from device where " + pattern);
        new QueryTask(mDeviceDao).execute(query);

    }

    static class QueryAllTask extends AsyncTask<Void, Void, List<Device>> {

        DeviceDao mDeviceDao;

        public QueryAllTask(DeviceDao deviceDao) {
            mDeviceDao = deviceDao;
        }

        @Override
        protected List<Device> doInBackground(Void... voids) {
            return mDeviceDao.getAllDevices();
        }

        @Override
        protected void onPostExecute(List<Device> devices) {
            super.onPostExecute(devices);
            mLiveDataDevices.postValue(devices);
        }
    }

    static class QueryTask extends AsyncTask<SupportSQLiteQuery, Void, List<Device>> {

        DeviceDao mDeviceDao;

        public QueryTask(DeviceDao deviceDao) {
            mDeviceDao = deviceDao;
        }

        @Override
        protected List<Device> doInBackground(SupportSQLiteQuery... query) {
            return mDeviceDao.rawQueryDevicesByPattern(query[0]);
        }

        @Override
        protected void onPostExecute(List<Device> devices) {
            super.onPostExecute(devices);
            Log.e(this.getClass().getSimpleName(), "rawQueryDevicesByPattern获取的记录个数:" + devices.size());
            //mLiveData.setValue(devices);
            mLiveDataDevices.postValue(devices);
        }
    }

    static class InsertTask extends AsyncTask<Device, Void, Void> {

        DeviceDao mDeviceDao;

        public InsertTask(DeviceDao deviceDao) {
            mDeviceDao = deviceDao;
        }

        @Override
        protected Void doInBackground(Device... devices) {
            mDeviceDao.insertAll(devices);
            return null;
        }
    }

    static class UpdateTask extends AsyncTask<Device, Void, Void> {

        DeviceDao mDeviceDao;

        public UpdateTask(DeviceDao deviceDao) {
            mDeviceDao = deviceDao;
        }

        @Override
        protected Void doInBackground(Device... devices) {
            mDeviceDao.updateDevices(devices);
            return null;
        }
    }

    static class DeleteTask extends AsyncTask<Device, Void, Integer> {

        DeviceDao mDeviceDao;

        public DeleteTask(DeviceDao deviceDao) {
            mDeviceDao = deviceDao;
        }

        @Override
        protected Integer doInBackground(Device... devices) {

            mDeviceDao.deleteDevices(devices);
            return null;
        }
    }

    static class DeleteAllTask extends AsyncTask<Void, Void, Void> {

        DeviceDao mDeviceDao;

        public DeleteAllTask(DeviceDao deviceDao) {
            mDeviceDao = deviceDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDeviceDao.deleteAll();
            return null;
        }
    }

    /**
     * 通过表名查询
     */
/*    private static class QueryListByDomainTask extends AsyncTask<String, Void, List<Device>> {
        DeviceDao mDeviceDao;

        public QueryListByDomainTask(DeviceDao deviceDao) {
            mDeviceDao = deviceDao;
        }

        @Override
        protected List<Device> doInBackground(String... strings) {
            return mDeviceDao.queryListDevicesByDomain(strings[0]);
        }
    }*/
}
