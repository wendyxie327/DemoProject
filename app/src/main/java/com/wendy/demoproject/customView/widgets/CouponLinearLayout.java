package com.wendy.demoproject.customView.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.eagle.androidlib.utils.Logger;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class CouponLinearLayout extends LinearLayout{

    private Paint mPaint;
    private int mCount = 20 ;//有多少个圆圈

    public CouponLinearLayout(Context context) {
        this(context,null);
    }

    public CouponLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CouponLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() ;
        int height = getHeight();
        float radius = width * 1.0f / (mCount * 2 + 1) / 2;
        float start = radius*2 ;
        Logger.d(getContext(),"1start="+start+",radius="+radius);
        for (int i=0;i<mCount ;i++){
            canvas.drawCircle(start,0,radius,mPaint);
            canvas.drawCircle(start,height,radius,mPaint);
            Logger.d(getContext(),"2start="+start+",radius="+radius);
            start += radius * 4 ;
            Logger.d(getContext(),"3start="+start+",radius="+radius);
        }
        Logger.d(getContext(),"end----"+(getWidth()-start));

    }
}
