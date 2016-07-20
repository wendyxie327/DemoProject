package com.wendy.demoproject.customView.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.eagle.androidlib.utils.Logger;
import com.wendy.demoproject.R;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class CirProgressView extends View {

    private int mFirstColor;
    private int mSecondColor;
    private int mCircleWidth;// 圆环的宽度
    private Paint mPaint;
    private int mProgress;
    private int mSpeed;//速度
    private boolean isNext = false;//是否为第二个循环


    public CirProgressView(Context context) {
        this(context, null);
    }

    public CirProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CirProgressView, defStyle, 0);
        int len = typedArray.length();
        Logger.d(getContext(), "len=" + len + "---" + typedArray.getIndexCount());
        for (int i = 0; i < len; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CirProgressView_firstColor:
                    this.mFirstColor = typedArray.getColor(attr, Color.RED);
                    break;
                case R.styleable.CirProgressView_secondColor:
                    this.mSecondColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.CirProgressView_circleWidth:
                    this.mCircleWidth = typedArray.getDimensionPixelSize(attr, 16);
                    break;
                case R.styleable.CirProgressView_speed:
                    this.mSpeed = typedArray.getInt(attr, 20);
                    break;
            }
        }
        Logger.d(getContext(),"speed="+mSpeed);
        typedArray.recycle();//清除内存
        mPaint = new Paint();
        // 绘图线程
        new Thread() {
            public void run() {
                while (true) {
                    mProgress+=1;
                    if (mProgress >= 360) {
                        mProgress = 0;
                        if (!isNext)
                            isNext = true;
                        else
                            isNext = false;
                    }
                    Logger.d(getContext(),"mProgress="+mProgress);
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth() / 2;  //圆心X坐标
        int radius = centre - mCircleWidth / 2; //半径
        mPaint.setStrokeWidth(mCircleWidth);//设置画笔宽度
        mPaint.setAntiAlias(true); //取消锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        RectF rectf = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);//用于定义圆弧的大小和界限
        if (!isNext) {
            mPaint.setColor(mFirstColor);
            canvas.drawCircle(centre, centre, radius, mPaint);//画背景的大圆
            mPaint.setColor(mSecondColor);
            canvas.drawArc(rectf, -90, mProgress, false, mPaint);//圆弧
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(centre, centre, radius, mPaint);//画背景的大圆
            mPaint.setColor(mFirstColor);
            canvas.drawArc(rectf, -90, mProgress, false, mPaint);//圆弧
        }
        Logger.d(getContext(), "progress=" + mProgress);
    }
}
