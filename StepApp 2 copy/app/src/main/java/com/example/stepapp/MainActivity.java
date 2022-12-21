package com.example.stepapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattService;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stepapp.ui.home.HomeFragment;
import com.example.stepapp.ui.profile.ProfileFragment;
import com.example.stepapp.ui.report.DayFragment;
import com.example.stepapp.ui.report.HourFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private AppBarConfiguration mAppBarConfiguration;
    private BluetoothAdapter bluetoothAdapter;
    private final int LOCATION_PERMISSION_REQUEST = 101;
    private final int SELECT_DEVICE = 102;
    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 45;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 46;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 47;

    private ListView listMainChat;
    private EditText edCreateMessage;
    private Button btnSendMessage;
    private ArrayAdapter<String> adapterMainChat;

    public static final int MESSAGE_STATE_CHANGED = 0;
    public static final int MESSAGE_READ = 1;
    public static final int MESSAGE_WRITE = 2;
    public static final int MESSAGE_DEVICE_NAME = 3;
    public static final int MESSAGE_TOAST = 4;

    public static final String DEVICE_NAME = "deviceName";
    public static final String TOAST = "toast";
    private String connectedDevice;
    private ChatUtils chatUtils;

    private boolean runningQOrLater =
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        // Setup drawer view

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_hour, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setupDrawerContent(navigationView);

        getWriteExternalStorage();
        getReadExternalStorage();

        // Ask for activity recognition permission
        if (runningQOrLater) {
            getActivity();
        }
//        context = this;
//        chatUtils = new ChatUtils(context, handler);
//        listMainChat = findViewById(R.id.list_conversation);
//        edCreateMessage = findViewById(R.id.ed_enter_message);
//        btnSendMessage = findViewById(R.id.btn_send_msg);
//
//        adapterMainChat = new ArrayAdapter<String>(context, R.layout.message_layout);
//        listMainChat.setAdapter(adapterMainChat);
//
//        btnSendMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String message = edCreateMessage.getText().toString();
//                if (!message.isEmpty()) {
//                    edCreateMessage.setText("");
//                    chatUtils.write(message.getBytes());
//                }
//            }
//        });
//        initBluetooth();

    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragmentClass = HomeFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Steps");
                }
                break;
            case R.id.nav_hour:
                fragmentClass = HourFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Hour");
                }
                break;
            case R.id.nav_day:
                fragmentClass = DayFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Weekly");
                }
                break;
            case R.id.nav_profile:
                fragmentClass = ProfileFragment.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Profile");
                }
                break;
            case R.id.nav_db:
                fragmentClass = EditDataActivity.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Database");
                }
                break;
            case R.id.nav_db_view:
                fragmentClass = ViewCourses.class;
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Database View");
                }
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawer.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                checkPermissions();
//                return true;
//            case R.id.action_bt:
//                enableBluetooth();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // Ask for permission
    private void getActivity() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACTIVITY_RECOGNITION},
                    REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
        }
    }

    // Ask for write external storage permission
    private void getWriteExternalStorage() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    // Ask for read external storage permission
    private void getReadExternalStorage() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);
        }
    }
//
//    private void initBluetooth() {
//        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (bluetoothAdapter == null) {
//            Toast.makeText(context, "No bluetooth found", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void enableBluetooth() {
//        if (!bluetoothAdapter.isEnabled()) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT},LOCATION_PERMISSION_REQUEST);
//
//                return;
//            }
//            bluetoothAdapter.enable();
//        }
//
//        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
//            Intent discoveryIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//            discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
//            startActivity(discoveryIntent);
//        }
//
//    }
//    private void checkPermissions() {
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
//        } else {
//            Intent intent = new Intent(context, DeviceListActivity.class);
//            startActivityForResult(intent, SELECT_DEVICE);
//        }
//    }
//
//
//    private Handler handler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message message) {
//            switch (message.what) {
//                case MESSAGE_STATE_CHANGED:
//                    switch (message.arg1) {
//                        case ChatUtils.STATE_NONE:
//                            setState("Not Connected");
//                            break;
//                        case ChatUtils.STATE_LISTEN:
//                            setState("Not Connected");
//                            break;
//                        case ChatUtils.STATE_CONNECTING:
//                            setState("Connecting...");
//                            break;
//                        case ChatUtils.STATE_CONNECTED:
//                            setState("Connected: " + connectedDevice);
//                            break;
//                    }
//                    break;
//                case MESSAGE_WRITE:
//                    byte[] buffer1 = (byte[]) message.obj;
//                    String outputBuffer = new String(buffer1);
//                    adapterMainChat.add("Me: " + outputBuffer);
//                    break;
//                case MESSAGE_READ:
//                    byte[] buffer = (byte[]) message.obj;
//                    String inputBuffer = new String(buffer, 0, message.arg1);
//                    adapterMainChat.add(connectedDevice + ": " + inputBuffer);
//                    break;
//                case MESSAGE_DEVICE_NAME:
//                    connectedDevice = message.getData().getString(DEVICE_NAME);
//                    Toast.makeText(context, connectedDevice, Toast.LENGTH_SHORT).show();
//                    break;
//                case MESSAGE_TOAST:
//                    Toast.makeText(context, message.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
//                    break;
//            }
//            return false;
//        }
//    });
//
//    private void setState(CharSequence subTitle) {
//        getSupportActionBar().setSubtitle(subTitle);
//    }








    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ACTIVITY_RECOGNITION_PERMISSION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getActivity();
                }  else {
                    Toast.makeText(this,
                            R.string.permission_denied,
                            Toast.LENGTH_SHORT).show();
                }

            case REQUEST_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getWriteExternalStorage();
                }  else {
                    Toast.makeText(this,
                            R.string.permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
            case REQUEST_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getReadExternalStorage();
                }  else {
                    Toast.makeText(this,
                            R.string.permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
//            case LOCATION_PERMISSION_REQUEST:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent intent = new Intent(context, DeviceListActivity.class);
//                    startActivityForResult(intent, SELECT_DEVICE);
//                } else {
//                    new AlertDialog.Builder(context)
//                            .setCancelable(false)
//                            .setMessage("Location permission is required.\n Please grant")
//                            .setPositiveButton("Grant", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    checkPermissions();
//                                }
//                            })
//                            .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    MainActivity.this.finish();
//                                }
//                            }).show();
//                }
        }
    }
}