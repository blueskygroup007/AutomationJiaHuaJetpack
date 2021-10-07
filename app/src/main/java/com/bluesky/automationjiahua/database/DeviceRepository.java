package com.bluesky.automationjiahua.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.LinkedList;
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
        mLiveData.setValue(new LinkedList<>());
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
        new QueryAllTask(mDeviceDao);
    }

    public LiveData<List<Device>> findDeviceByTag(String pattern) {
        return mDeviceDao.queryDevicesByTag("%" + pattern + "%");
    }

    public LiveData<List<Device>> findDeviceByAffect(String pattern) {
        return mDeviceDao.queryDevicesByAffect("%" + pattern + "%");
    }

    public LiveData<List<Device>> findDeviceByName(String pattern) {
        return mDeviceDao.queryDevicesByName("%" + pattern + "%");
    }

    public LiveData<List<Device>> findDeviceByStandard(String pattern) {
        return mDeviceDao.queryDevicesByStandard("%" + pattern + "%");
    }

    public LiveData<List<Device>> findDeviceByType(String pattern) {
        return mDeviceDao.queryDevicesByType("%" + pattern + "%");

    }

    public void findDeviceByPattern(String domain, String column, String keyWords) {
        StringBuilder pattern = new StringBuilder();
        if (!domain.isEmpty()) {
            pattern.append("domain='" + domain);
        } else {
            return;
        }
        if (!keyWords.isEmpty()) {
            pattern.append("' and " + column + " like ");
            pattern.append("'%" + keyWords + "%'");
        } else {
            pattern.append("'");
        }

        Log.d(Tag, "查询语句:  " + "select * from device where " + pattern.toString());
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
            mLiveData.setValue(devices);
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
