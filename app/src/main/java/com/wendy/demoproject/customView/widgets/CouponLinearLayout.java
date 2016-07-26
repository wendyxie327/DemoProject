package com.wendy.demoproject.customView.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class CouponLinearLayout extends LinearLayout{

    private Paint mPaint;
    private int mCount = 20 ;

    public CouponLinearLayout(Context context) {
        this(context,null);
    }

    public CouponLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CouponLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(0,30,16,mPaint);
    }
}
