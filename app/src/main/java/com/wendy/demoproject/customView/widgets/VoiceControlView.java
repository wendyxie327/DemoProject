package com.wendy.demoproject.customView.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.eagle.androidlib.utils.DensityUtil;
import com.wendy.demoproject.R;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class VoiceControlView extends View {


    /**
     * 第一圈的颜色
     */
    private int mFirstColor = Color.RED;

    /**
     * 第二圈的颜色
     */
    private int mSecondColor = Color.BLUE;
    /**
     * 圈的宽度
     */
    private int mCircleWidth = DensityUtil.dp2px(getContext(),10);
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mCurrentCount = 3;

    /**
     * 中间的图片
     */
    private Bitmap mImage;
    /**
     * 每个块块间的间隙
     */
    private int mSplitSize = 20;
    /**
     * 个数
     */
    private int mCount = 30;

    private Rect mRect;

    public VoiceControlView(Context context) {
        this(context,null);
    }

    public VoiceControlView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VoiceControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VoiceControlView,defStyleAttr,0);
        int len = typedArray.getIndexCount();
        if (len > 0 ){
            for (int i=0;i<len ; i++){
                int type = typedArray.getIndex(i);
                switch (type){
                    case R.styleable.VoiceControlView_circleWidth:
                        mCircleWidth = typedArray.getDimensionPixelSize(type, DensityUtil.dp2px(context,10));
                        break;
                    case R.styleable.VoiceControlView_bg:
                        mImage = BitmapFactory.decodeResource(getResources(),typedArray.getResourceId(type,0));
                        break;
                    case R.styleable.VoiceControlView_dotCount:
                        mCount = typedArray.getInt(type,30);
                        break;
                    case R.styleable.VoiceControlView_splitSize:
                        mSplitSize = typedArray.getInt(type,20);
                        break;
                    case R.styleable.VoiceControlView_firstColor:
                        mFirstColor = typedArray.getColor(type, Color.WHITE);
                        break;
                    case R.styleable.VoiceControlView_secondColor:
                        mSecondColor = typedArray.getColor(type,Color.GRAY);
                        break;
                }
            }
        }
        typedArray.recycle();//清除
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);//定义线段形状为圆头
        mPaint.setStyle(Paint.Style.STROKE);//设置为空心
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - mCircleWidth / 2;// 半径
        /**
         * 画块块去
         */
        drawOval(canvas, centre, radius);

        int redRadius = radius - mCircleWidth / 2 ; //获取到内圆的半径
        mRect.left =  (int)(redRadius - Math.sqrt(2)/2 *redRadius) + mCircleWidth;
        mRect.top = (int)(redRadius - Math.sqrt(2)/2 *redRadius) + mCircleWidth;
        mRect.bottom = mRect.top + mImage.getWidth();
        mRect.right = mRect.left + mImage.getHeight();

        //如果图片比较小，那么可以根据图片的大小放置到正中心
        if ( mImage.getWidth() < Math.sqrt(2)/2 * redRadius){
            mRect.left = (int)(mRect.left + Math.sqrt(2)/2 * redRadius - mImage.getWidth()/2 );
            mRect.top = (int)(mRect.top + Math.sqrt(2)/2 * redRadius - mImage.getWidth()/2);
            mRect.right = mRect.left + mImage.getWidth();
            mRect.bottom = mRect.top + mImage.getHeight();
        }

        canvas.drawBitmap(mImage,null,mRect,mPaint);
    }


    private void drawOval(Canvas canvas,int centre,int radius){
        float itemSize = ( 360 - mSplitSize*mCount )/mCount;//计算出每块所占比例
        RectF oval = new RectF(centre-radius,centre-radius,centre+radius,centre+radius);//定义圆弧的形状和大小界限
        mPaint.setColor(mFirstColor);//设置圆环的颜色
        for (int i =0 ;i <mCount ;i++){
            canvas.drawArc(oval,i *(mSplitSize+itemSize) , itemSize,false ,mPaint);
        }

        mPaint.setColor(mSecondColor);
        for (int i=0 ; i< mCurrentCount ; i++){
            canvas.drawArc(oval,i*(mSplitSize+itemSize) , itemSize,false,mPaint);
        }

    }

    /**
     * 当前数量+1
     */
    public void up()
    {
        mCurrentCount++;
        postInvalidate();
    }

    /**
     * 当前数量-1
     */
    public void down()
    {
        mCurrentCount--;
        postInvalidate();
    }
    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                if (xUp > xDown)// 下滑
                {
                    down();
                } else
                {
                    up();
                }
                break;
        }
        return true;
    }
}
