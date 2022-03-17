package com.bluesky.automationjiahua.ui.database;

import static java.util.regex.Pattern.compile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bluesky.automationjiahua.R;
import com.bluesky.automationjiahua.base.App;
import com.bluesky.automationjiahua.base.AppConstant;
import com.bluesky.automationjiahua.database.DBHelper;
import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.database.DeviceRepository;
import com.bluesky.automationjiahua.database.InterLock;
import com.bluesky.automationjiahua.databinding.FragmentDatabaseBinding;
import com.bluesky.automationjiahua.utils.AppExecutors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseFragment extends Fragment implements View.OnClickListener {

    private DatabaseViewModel mViewModel;
    private FragmentDatabaseBinding binding;
    private DeviceRepository mDeviceRepository;
    private static final String SQLITE_DATABASE_FILE_NAME = "new_huachan_ganxijiao.db";

    public static DatabaseFragment newInstance() {
        return new DatabaseFragment();
    }


    /*View初始化*/
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDatabaseBinding.inflate(inflater, container, false);

        //return inflater.inflate(R.layout.fragment_database, container, false);

        binding.btnDeleteRoomTable.setOnClickListener(this);
        binding.btnQuerySqlite.setOnClickListener(this);
        binding.btnFormatTranslateTable.setOnClickListener(this);
        binding.btnQueryRoomTable.setOnClickListener(this);
        binding.btnQuerySqliteTable.setOnClickListener(this);

        binding.btnDeleteRoomInterlock.setOnClickListener(this);
        binding.btnFormatTranslateTableInterlock.setOnClickListener(this);
        binding.btnQueryRoomInterlock.setOnClickListener(this);
        binding.btnQuerySqliteInterlock.setOnClickListener(this);

        binding.btnTemp.setOnClickListener(this);
        return binding.getRoot();
    }

    /*Data初始化*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDeviceRepository = DeviceRepository.getInstance();
        mViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        mViewModel.getContent().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvOutput.setText(s);
            }
        });
        /*应该也可以改为直接监听Repository的内部LiveData*/
/*        mViewModel.getDevices().observe(getViewLifecycleOwner(), new Observer<List<Device>>() {
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
        });*/

        initDialog();
    }

    /**
     * 初始化一个进入密码对话框,进入密码为121221
     */
    private void initDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_database_enter, null, false);
        EditText etPassword = view.findViewById(R.id.et_dialog_database_password);
        AlertDialog.Builder alertDialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        alertDialogBuilder.setTitle(getResources().getString(R.string.str_dialog_title_database_password));
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("验证口令", null);

        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });
        AlertDialog dialog = alertDialogBuilder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwrod = etPassword.getText().toString().trim();
                if ("121221".equals(passwrod)) {
                    dialog.dismiss();
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query_sqlite:
                //检查整个sqlite数据库,并列出所有表包含的记录数量
                checkSqlite();
                break;
            case R.id.btn_format_translate_table:
                //格式化sqlite某表所有记录
                formatSqliteTableToRoom();
                break;
            case R.id.btn_delete_room_table:
                //删除room某表
                deleteRoom();
                break;
            case R.id.btn_query_sqlite_table:
                //查询sqlite某表
                listSqlite();
                break;
            case R.id.btn_query_room_table:
                //查询room某表
                listRoomFromTable();
                break;
            case R.id.btn_delete_room_interlock:
                deleteRoomInterlock();
                break;
            case R.id.btn_format_translate_table_interlock:
                formatInterlockAndInsert();
                break;
            case R.id.btn_query_room_interlock:
                queryRoomInterLock();
                break;
            case R.id.btn_query_sqlite_interlock:
                querySqliteInterLock();
                break;
            case R.id.btn_temp:
                //修改联锁的区域名称以便区分脱硫和脱硫脱硝
                modifyInterLockDomain();
                break;
            default:
        }
    }

    private void modifyInterLockDomain() {
        AppExecutors mExecutors = new AppExecutors();
        mExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<InterLock> tuoliu = DeviceRepository.getInstance().getInterLockDao().queryInterLocksByDomain("脱硫");
                for (InterLock interLock :
                        tuoliu) {
                    interLock.setDomain("化产脱硫");
                }
                DeviceRepository.getInstance().getInterLockDao().updateInterLock(tuoliu.toArray(new InterLock[0]));
            }
        });
    }

    /**
     * 查询sqlite联锁表
     */
    private void querySqliteInterLock() {
        String table = "interlock";
        DBHelper dbHelper = new DBHelper(requireActivity(), "liansuo.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                InterLock interLock = new InterLock(cursor.getInt(0)
                        , cursor.getString(1)
                        , cursor.getString(2)
                        , cursor.getString(3)
                        , cursor.getString(4)
                        , cursor.getString(5)
                        , cursor.getString(6)
                        , cursor.getString(7)
                        , cursor.getString(8));
                Log.e(App.TAG, interLock.toString());
            }
        }
        cursor.close();
        db.close();
    }

    /**
     * 格式化sqlite联锁表并插入room
     */
    private void formatInterlockAndInsert() {
        String table = "interlock";
        DBHelper dbHelper = new DBHelper(requireActivity(), "liansuo.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + table, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                InterLock interLock = new InterLock(cursor.getInt(0)
                        , cursor.getString(1)
                        , cursor.getString(2)
                        , cursor.getString(3)
                        , cursor.getString(4)
                        , cursor.getString(5)
                        , cursor.getString(6)
                        , cursor.getString(7)
                        , cursor.getString(8));
                Log.e(App.TAG, interLock.toString());
                //TODO 由于联锁表不完整,部分记录没有Tag,导致这里格式化TAG时,这部分被过滤掉了50个记录.
                if (interLock.getTag() != null && !interLock.getTag().isEmpty()) {
                    String formated_tag = compile("\\s*|\t|\r|\n").matcher(interLock.getTag()).replaceAll("");
                    Log.e("listSqlite_formated:", interLock.getTag() + "   to   " + formated_tag);
                    //添加到room中,调试时,先检查格式化是否正确再执行下面两行代码做插入.
                    interLock.setTag(formated_tag);
                }
                mDeviceRepository.insertInterLocks(interLock);
            }
        }
        cursor.close();
        db.close();
    }

    /**
     * 查询room联锁表
     */
    private void queryRoomInterLock() {
/*        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("begin list Room::", "开始查询room表。。。");
                List<InterLock> allInterLocks = mDeviceRepository.getInterLockDao().getAllInterLocks();
                for (InterLock interLock :
                        allInterLocks) {
                    Log.e("ListRoom:", interLock.toString() + "\n");
                }
                Log.e("ListRoom:数量=", allInterLocks.size() + "个.");
            }
        });*/
        mDeviceRepository.getAllInterLocks(new DeviceRepository.LoadDataCallback<InterLock>() {


            @Override
            public void onDataLoaded(List<InterLock> t) {
                Log.e("callback测试interlock的数量=", t.size() + "个...");
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /**
     * 删除room所有联锁
     */
    private void deleteRoomInterlock() {
        mDeviceRepository.deleteAllInterLocks();
    }

    /*--------------------------------以下为Device,以上为InterLock-------------------------------------------*/

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
        DBHelper dbHelper = new DBHelper(requireActivity(), SQLITE_DATABASE_FILE_NAME, null, 1);
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
        Log.e("查询sqlite某个表:", table + "表共有:" + cursor.getCount() + "个");
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
        DBHelper dbHelper = new DBHelper(requireActivity(), SQLITE_DATABASE_FILE_NAME, null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        if (cursor.getCount() != 0) {
            List<Device> temp = new ArrayList<>();
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
                    temp.add(device);
                }
            }
            //等全部格式化完成后,将list转换为array,再一次性写入到room
            Device[] array = temp.toArray(new Device[temp.size()]);
            mDeviceRepository.insertDevices(array);
        }
        cursor.close();
        db.close();
    }

    private void translateToRoom() {
        Log.e("begin translate Room:", "开始转换至room表。。。");

        String table = binding.etTableName.getText().toString();
        DBHelper dbHelper = new DBHelper(requireActivity(), SQLITE_DATABASE_FILE_NAME, null, 1);
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

        DBHelper dbHelper = new DBHelper(requireActivity(), SQLITE_DATABASE_FILE_NAME, null, 1);
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