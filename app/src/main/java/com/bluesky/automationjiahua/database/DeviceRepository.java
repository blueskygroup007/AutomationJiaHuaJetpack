package com.bluesky.automationjiahua.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BlueSky
 * @date 2021/7/3
 * Description:
 */
public class DeviceRepository {
    DeviceDao mDeviceDao;
    Context mContext;
    static MutableLiveData<List<Device>> mLiveData;
    String Tag = DeviceRepository.class.getSimpleName();

    public DeviceRepository(Context context) {
        mContext = context;
        DeviceDataBase db = DeviceDataBase.getDatabase(context);
        mDeviceDao = db.getDeviceDao();
        mLiveData = new MutableLiveData<>();
        mLiveData.setValue(new ArrayList<>());
    }

    public DeviceDao getDeviceDao() {
        return mDeviceDao;
    }

    public static MutableLiveData<List<Device>> getmLiveData() {
        return mLiveData;
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

    /*返回List(要去监听mLiveData)*/
    public void findListDeviceByDomain(String domain) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from device where domain='" + domain + "'");
        new QueryTask(mDeviceDao).execute(query);
    }

    /*返回LiveData*/
    public LiveData<List<Device>> findLiveDataDeviceByDomain(String domain) {
        return mDeviceDao.queryLiveDataDevicesByDomain("%" + domain + "%");
    }

    public LiveData<List<Device>> findDeviceByTag(String tag) {
        return mDeviceDao.queryDevicesByTag("%" + tag + "%");
    }

    public LiveData<List<Device>> findDeviceByAffect(String affect) {
        return mDeviceDao.queryDevicesByAffect("%" + affect + "%");
    }

    public LiveData<List<Device>> findDeviceByName(String name) {
        return mDeviceDao.queryDevicesByName("%" + name + "%");
    }

    public LiveData<List<Device>> findDeviceByStandard(String standard) {
        return mDeviceDao.queryDevicesByStandard("%" + standard + "%");
    }

    public LiveData<List<Device>> findDeviceByType(String type) {
        return mDeviceDao.queryDevicesByType("%" + type + "%");

    }

    public void findDeviceByPattern(String domain, String column, String keyWords) {
        StringBuilder pattern = new StringBuilder();
        if (!domain.isEmpty()) {
            pattern.append("domain='" + domain);
            pattern.append("' and ");

        }
        pattern.append(column + " like ");
        pattern.append("'%" + keyWords + "%'");


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
            mLiveData.postValue(devices);
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
            mLiveData.postValue(devices);
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
}
