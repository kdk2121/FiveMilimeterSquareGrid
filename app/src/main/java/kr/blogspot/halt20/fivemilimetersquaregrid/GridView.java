package kr.blogspot.halt20.fivemilimetersquaregrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

/**
 * Created by halt20 on 16. 3. 14..
 */
public class GridView extends View {

    public GridView(Context context) {
        super(context);
    }

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1,
                getResources().getDisplayMetrics());

        float currentH = 0;
        float currentW = 0;

        int countX = 0;
        int countY = 0;

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        while (currentW < metrics.widthPixels) {
            if(countX % 10 == 0){
                paint.setStrokeWidth(2f);
                paint.setColor(Color.BLACK);
            } else {
                paint.setStrokeWidth(1f);
                paint.setColor(Color.BLACK);
            }
            canvas.drawLine(currentW, 0, currentW, metrics.heightPixels, paint);
            countX++;
            currentW += px;
        }
        while (currentH < metrics.heightPixels) {
            if(countY % 10 == 0){
                paint.setStrokeWidth(2f);
                paint.setColor(Color.BLACK);
            } else {
                paint.setStrokeWidth(1f);
                paint.setColor(Color.BLACK);
            }
            canvas.drawLine(0, currentH, metrics.widthPixels, currentH, paint);
            countY++;
            currentH += px;
        }
    }
}
