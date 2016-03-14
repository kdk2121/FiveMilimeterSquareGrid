package kr.blogspot.halt20.fivemilimetersquaregrid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    private final static String SERVICE_ENABLED_KEY = "serviceEnabledKey";

    private PermissionChecker mPermissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.on).setOnClickListener(this);        //시작버튼
        findViewById(R.id.off).setOnClickListener(this);            //중시버튼
        mPermissionChecker = new PermissionChecker(this);
        if(!mPermissionChecker.isRequiredPermissionGranted()){
            Intent intent = mPermissionChecker.createRequiredPermissionIntent();
            startActivityForResult(intent, PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if(view == R.id.on){
            startService(new Intent(this, GridService.class));    //서비스 시작
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE);
                }
            }
        }
        else
            stopService(new Intent(this, GridService.class));    //서비스 종료
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PermissionChecker.REQUIRED_PERMISSION_REQUEST_CODE) {
            if (!mPermissionChecker.isRequiredPermissionGranted()) {
                Toast.makeText(this, "Required permission is not granted. Please restart the app and grant required permission.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "이제 그리드를 그릴 수 있어요!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(SERVICE_ENABLED_KEY.equals(key)) {
            boolean enabled = sharedPreferences.getBoolean(key, false);
            if(enabled) {
                startGridService();
            } else {
                stopGridService();
            }
        }
    }

    private void startGridService() {
        startService(new Intent(this, GridService.class));
    }

    private void stopGridService() {
        stopService(new Intent(this, GridService.class));
    }
}
