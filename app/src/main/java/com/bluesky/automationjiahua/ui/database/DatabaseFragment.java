package com.bluesky.automationjiahua.ui.database;

import static java.util.regex.Pattern.compile;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.bluesky.automationjiahua.database.DeviceRepository;
import com.bluesky.automationjiahua.databinding.FragmentDatabaseBinding;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        binding.button4.setOnClickListener(this);
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
        /*应该也可以改为直接监听Repository的内部LiveData*/
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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                //检查整个sqlite数据库,并列出所有表包含的记录数量
                checkSqlite();
                break;
            case R.id.button4:
                //格式化sqlite某表所有记录
                formatSqliteTableToRoom();
                break;
            case R.id.button7:
                //删除room某表
                deleteRoom();
                break;
            case R.id.button8:
                //查询sqlite某表
                listSqlite();
                break;
            case R.id.button9:
                //查询room某表
                listRoomFromTable();
                break;
            default:
        }
    }

    private void listRoomFromTable() {
        String table = binding.etTableName.getText().toString();
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("begin list Room::", "开始查询room表。。。");
                List<Device> devices = mDeviceRepository.getDeviceDao().queryListDevicesByDomain(table);
                int count = 0;
                for (Device device :
                        devices) {
                    count++;
                    Log.e("ListRoom:", device.toString() + "\n");
                    if (count >= 10) {
                        Log.e("列出room某个表(前10条):", table + "表一共" + devices.size() + "条.");
                        break;
                    }
                }
            }
        });
    }

    private void listSqlite() {
        Log.e("begin list Room::", "开始列出sqlite表。。。");

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
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("begin delete Room:", "开始删除room表。。。");

                List<Device> devices = mDeviceRepository.getDeviceDao().queryListDevicesByDomain(table);
                mDeviceRepository.deleteDevices(devices.toArray(new Device[0]));
            }
        });
    }


    private void formatSqliteTableToRoom() {
        Log.e("begin format to Room:", "开始格式化并转换到room表。。。");

        String table = binding.etTableName.getText().toString();
        DBHelper dbHelper = new DBHelper(requireActivity(), "total_new_source.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, null);
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
                if (device.getTag() != null && !device.getTag().isEmpty()) {
                    String formated_tag = compile("\\s*|\t|\r|\n").matcher(device.getTag()).replaceAll("");
                    Log.e("listSqlite_formated:", device.getTag() + "   to   " + formated_tag);
                    //添加到room中,调试时,先检查格式化是否正确再执行下面两行代码做插入.
                    device.setTag(formated_tag);
                    mDeviceRepository.insertDevices(device);
                }
            }
        }
        cursor.close();
        db.close();
    }

    private void translateToRoom() {
        Log.e("begin translate Room:", "开始转换至room表。。。");

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

    /**
     * 检查整个sqlite数据库,显示出所有表包含的记录数量.
     */
    private void checkSqlite() {
        Log.e("begin check sqlite:", "开始检查sqlite。。。");

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        // TODO: Use the ViewModel
    }

}