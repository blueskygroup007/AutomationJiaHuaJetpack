package com.bluesky.automationjiahua.ui.database;

import static java.util.regex.Pattern.compile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.base.AppConstant;
import com.bluesky.automationjiahua.database.DBHelper;
import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.database.DeviceDao;
import com.bluesky.automationjiahua.database.DeviceDataBase;
import com.bluesky.automationjiahua.database.DeviceRepository;
import com.bluesky.automationjiahua.databinding.FragmentDatabaseBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseFragment extends Fragment implements View.OnClickListener {

    private DatabaseViewModel mViewModel;
    private FragmentDatabaseBinding binding;
    private DeviceRepository mDeviceRepository;

    public static DatabaseFragment newInstance() {
        return new DatabaseFragment();
    }


    /*View初始化*/
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDatabaseBinding.inflate(inflater, container, false);

        //return inflater.inflate(R.layout.fragment_database, container, false);

        binding.button1.setOnClickListener(this);
        binding.button2.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        binding.button4.setOnClickListener(this);
        binding.button5.setOnClickListener(this);
        binding.button6.setOnClickListener(this);
        binding.button7.setOnClickListener(this);
        binding.button8.setOnClickListener(this);
        binding.button9.setOnClickListener(this);
        return binding.getRoot();
    }

    /*Data初始化*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDeviceRepository = new DeviceRepository(requireContext());
        mViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        mViewModel.getContent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvOutput.setText(s);
            }
        });
        mViewModel.getDevices().observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                StringBuilder builder = new StringBuilder(binding.tvOutput.getText());
                if (devices != null && !devices.isEmpty()) {
                    Log.e("checkRoom:", Arrays.toString(devices.toArray()));
                    builder.append(devices.get(0).getDomain()).append(":").append(devices.size()).append("\n");
                } else {
                    builder.append("Room 数据库为空!");
                }
                binding.tvOutput.setText(builder);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1://检查sqlite数据库
                checkSqlite();
                break;
            case R.id.button2://转化某表到room
                translateToRoom();
                break;
            case R.id.button3://检查room数据库
                checkRoom();
                break;
            case R.id.button4://格式化sqlite某表
                formatSqlite();
                break;
            case R.id.button5://格式化room某表
                formatRoom();
                break;
            case R.id.button6://全部删除sqlite
                deleteSqlite();
                break;
            case R.id.button7://删除room某表
                deleteRoom();
                break;
            case R.id.button8://检查sqlite某表
                listSqlite();
                break;
            case R.id.button9://检查room某表
                listRoom();
                break;
            default:
        }
    }

    private void listRoom() {
        String table = binding.etTableName.getText().toString();
        mDeviceRepository.findLiveDataDeviceByDomain(table).observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                for (Device device :
                        devices) {
                    Log.e("checkRoom:", device.toString() + "\n");
                }
            }
        });
    }

    private void listSqlite() {
        String table = binding.etTableName.getText().toString();
        DBHelper dbHelper = new DBHelper(requireActivity(), "total_new_source.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Device device = new Device(cursor.getString(0)
                        , cursor.getString(1)
                        , cursor.getString(2)
                        , cursor.getString(3)
                        , cursor.getString(4)
                        , cursor.getString(5)
                        , cursor.getString(6)
                        , cursor.getString(7)
                        , cursor.getString(8)
                        , cursor.getString(9)
                        , cursor.getString(10)
                        , cursor.getString(11)
                        , cursor.getString(12)
                        , cursor.getString(13)
                        , cursor.getString(14)
                        , table);
                Log.e("listSqlite:", device.toString());
            }
        }
        cursor.close();
        db.close();
    }

    private void deleteRoom() {
        String table = binding.etTableName.getText().toString();
        mDeviceRepository.findLiveDataDeviceByDomain(table).observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                mDeviceRepository.deleteDevices(devices.toArray(new Device[0]));
            }
        });
    }

    /*慎用*/
    private void deleteSqlite() {
        String table = binding.etTableName.getText().toString();
        DBHelper dbHelper = new DBHelper(requireActivity(), "total_new_source.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Cursor cursor = db.rawQuery("select * from " + table, null);

        //cursor.close();
        db.close();
    }

    private void formatRoom() {
        mDeviceRepository.findLiveDataDeviceByDomain(binding.etTableName.getText().toString()).observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                for (Device device :
                        devices) {
                    //TODO 格式化每个记录的tag,去除\r\t\n
                    Log.e("formatRoom:", device.toString() + "\n");
                }
            }
        });
    }

    private void formatSqlite() {

    }

    private void checkRoom() {
        String table = binding.etTableName.getText().toString();
        mDeviceRepository.findLiveDataDeviceByDomain(table).observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
            @Override
            public void onChanged(List<Device> devices) {
                for (Device device : devices
                ) {
                    Log.e("checkRoom:", device.toString() + "\n");
                }
            }
        });
    }

    private void translateToRoom() {
        String table = binding.etTableName.getText().toString();
        DBHelper dbHelper = new DBHelper(requireActivity(), "total_new_source.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table, null);

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                Device device = new Device(cursor.getString(0)
                        , cursor.getString(1)
                        , cursor.getString(2)
                        , cursor.getString(3)
                        , cursor.getString(4)
                        , cursor.getString(5)
                        , cursor.getString(6)
                        , cursor.getString(7)
                        , cursor.getString(8)
                        , cursor.getString(9)
                        , cursor.getString(10)
                        , cursor.getString(11)
                        , cursor.getString(12)
                        , cursor.getString(13)
                        , cursor.getString(14)
                        , table);
                mDeviceRepository.insertDevices(device);
                Log.e("listSqlite:", device.toString());
            }
            cursor.close();
        }
        db.close();
    }

    private void checkSqlite() {
        DBHelper dbHelper = new DBHelper(requireActivity(), "total_new_source.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        StringBuilder builder = new StringBuilder("sqlite数据库包含:\n");
        for (String domain : AppConstant.TABLE_NAME
        ) {
            Log.e("DBHelper query for:", domain);
            //Cursor cursor = db.rawQuery("select * from ?", new String[]{domain});
            Cursor cursor = db.query(domain, null, null, null, null, null, null);
            //Cursor cursor=db.rawQuery("select COUNT(*) FROM " + domain, null)
            Log.e("checkSqlite:", cursor.toString());
            builder.append(domain).append(":").append(cursor.getCount()).append("\n");
            binding.tvOutput.setText(builder);
            cursor.close();
        }
        db.close();
    }

/*    class QueryTask extends AsyncTask<SupportSQLiteQuery, Void, List<Device>> {

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
            StringBuilder builder = new StringBuilder();
            Log.e(this.getClass().getSimpleName(), "rawQueryDevicesByPattern获取的记录个数:" + devices.size());

            builder.append(devices.get(0).getDomain()).append(":").append(devices.size()).append("\n");
            binding.tvOutput.setText(builder);
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * 从sqlite数据库生成room新数据库
     */
    public void createDatabase() {


        //依靠DatabaseHelper带全部参数的构造函数创建数据库
        DBHelper dbHelper = new DBHelper(requireActivity(), "total_new_source.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (String domain :
                AppConstant.TABLE_NAME) {

            Cursor cursor = db.rawQuery("select * from " + domain, null);
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    Log.d("test_TAG", "tag=" + cursor.getString(0) + "--affect=" + cursor.getString(1));
                    Device device = new Device(cursor.getString(0)
                            , cursor.getString(1)
                            , cursor.getString(2)
                            , cursor.getString(3)
                            , cursor.getString(4)
                            , cursor.getString(5)
                            , cursor.getString(6)
                            , cursor.getString(7)
                            , cursor.getString(8)
                            , cursor.getString(9)
                            , cursor.getString(10)
                            , cursor.getString(11)
                            , cursor.getString(12)
                            , cursor.getString(13)
                            , cursor.getString(14)
                            , domain);
                    DeviceRepository repository = new DeviceRepository(requireActivity().getApplicationContext());
                    repository.insertDevices(device);
                }
                cursor.close();
            }
        }
        db.close();
    }

    /**
     * 修复新数据库中，tag和affect中含有回车,换行,制表符的问题
     */
    public void fixDeviceFormat() {
        DeviceDataBase db = DeviceDataBase.getDatabase(requireContext());
        DeviceDao mDeviceDao = db.getDeviceDao();
        new getAllDevicesNoLivedataTask(mDeviceDao, requireContext()).execute();
    }


    /**
     * 多表合一线程
     */
    static class getAllDevicesNoLivedataTask extends AsyncTask<Void, Void, List<Device>> {
        DeviceDao mDeviceDao;
        Context mContext;

        public getAllDevicesNoLivedataTask(DeviceDao dao, Context context) {
            mDeviceDao = dao;
            mContext = context;
        }

        @Override
        protected List<Device> doInBackground(Void... voids) {
            return mDeviceDao.getAllDevices();
        }

        @Override
        protected void onPostExecute(List<Device> devices) {
            super.onPostExecute(devices);
            DeviceRepository repository = new DeviceRepository(mContext);
            //todo 应该包涵换行，再更新该项,但是，如果更改了tag。那么更新就无意义。因为主键已经变化了。
            //这里应采用删除旧行,增加新行。增加事务应该后处理。

            List<Device> delDevices = new ArrayList<>();//需要删除的条目
            List<Device> modDevices = new ArrayList<>();//需要插入的条目
            for (Device device :
                    devices) {
                delDevices.add(device);
                String tag = device.getTag();
                String affect = device.getAffect();
                //如果tag包含回车,换行,删除旧条目,增加新条目
                if (tag.contains("\r") || tag.contains("\n") || tag.contains("/t")) {
                    //repository.deleteDevices(device);
                    Log.d("修改前的tag:  ", device.getTag());
                    Pattern pattern = compile("\\s*|\t|\r|\n");
                    Matcher m = pattern.matcher(tag);
                    Device temp = device;
                    temp.setTag(m.replaceAll(""));
                    modDevices.add(temp);
                    //device.setTag(device.getTag().replaceAll("\r|\n|\t", ""));
                    //device.setTag(device.getTag().replaceAll("[\\t\\n\\r]",""));
                    Log.d("修改后的tag:  ", device.getTag());
                    //repository.insertDevices(device);

                }
            }
            for (Device device : delDevices
            ) {
                synchronized (repository) {
                    repository.deleteDevices(device);
                }
            }
            for (Device device : modDevices
            ) {
                synchronized (repository) {
                    repository.insertDevices(device);
                }
            }
        }
    }

}