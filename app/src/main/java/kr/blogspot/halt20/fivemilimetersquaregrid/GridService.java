package kr.blogspot.halt20.fivemilimetersquaregrid;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by halt20 on 16. 3. 13..
 */
public class GridService extends Service{

    private final static int FOREGROUND_ID = 999;
    private GridLayer mGridLayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logServiceStarted();

        initHeadLayer();

        PendingIntent pendingIntent = createPendingIntent();
        Notification notification = createNotification(pendingIntent);

        startForeground(FOREGROUND_ID, notification);

        return START_STICKY;
    }

    private Notification createNotification(PendingIntent pendingIntent) {
        return new Notification.Builder(this)
                .setContentTitle("그리드 그리기")
                .setContentText("뭘까요")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void initHeadLayer() {
        mGridLayer = new GridLayer(this);
    }

    @Override
    public void onDestroy() {
        destroyGridLayer();
        stopForeground(true);

        logServiceEnded();
    }

    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private void logServiceStarted() {
        Toast.makeText(this, "그리드를 그릴게요", Toast.LENGTH_SHORT).show();
    }

    private void logServiceEnded() {
        Toast.makeText(this, "그리드를 없앨게요", Toast.LENGTH_SHORT).show();
    }

    private void destroyGridLayer() {
        mGridLayer.destroy();
        mGridLayer = null;
    }
}
