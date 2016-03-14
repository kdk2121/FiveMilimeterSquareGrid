package kr.blogspot.halt20.fivemilimetersquaregrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by halt20 on 16. 3. 14..
 */
public class GridLayer extends View{


    private Context mContext;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;


    public GridLayer(Context context) {
        super(context);
        mContext = context;
        mFrameLayout = new FrameLayout(mContext);

        addToWindowManager();
    }

    private void addToWindowManager() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,  //항상 최 상위. 터치 이벤트 받을 수 있음
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT); //투명
        params.gravity = Gravity.CENTER; //중앙에오도록함

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mFrameLayout, params);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Here is the place where you can inject whatever layout you want.
//        layoutInflater.inflate(R.layout.grid, mFrameLayout);
        mFrameLayout.addView(new GridView(mContext));
//        layoutInflater.inflate(new GridView(mContext), mFrameLayout);
    }

    public void destroy() {
        mWindowManager.removeView(mFrameLayout);
    }
}
