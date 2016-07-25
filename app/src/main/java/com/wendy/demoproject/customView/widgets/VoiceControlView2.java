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
import android.view.VelocityTracker;
import android.view.View;

import com.eagle.androidlib.utils.DensityUtil;
import com.eagle.androidlib.utils.Logger;
import com.wendy.demoproject.R;

/**
 * Created by Administrator on 2016/7/22 0022.
 */
public class VoiceControlView2 extends View {


    /**
     * 第一圈的颜色
     */
    private int mFirstColor;

    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mCurrentCount ;

    /**
     * 中间的图片
     */
    private Bitmap mImage;
    /**
     * 每个块块间的间隙
     */
    private int mItemSize ;
    /**
     * 个数
     */
    private int mCount ;

    private Rect mRect;

    public VoiceControlView2(Context context) {
        this(context,null);
    }

    public VoiceControlView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VoiceControlView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCurrentCount = 3;//默认有几个被选中
        //初始化各个控件
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VoiceControlView,defStyleAttr,0);
        mCircleWidth = typedArray.getDimensionPixelSize( R.styleable.VoiceControlView_circleWidth, DensityUtil.dp2px(context,1));
        mImage = BitmapFactory.decodeResource(getResources(),typedArray.getResourceId(R.styleable.VoiceControlView_bg,0));
        mCount = typedArray.getInt(R.styleable.VoiceControlView_dotCount,20);
        mItemSize = typedArray.getInt(R.styleable.VoiceControlView_itemSize,20);
        mFirstColor = typedArray.getColor(R.styleable.VoiceControlView_firstColor, Color.WHITE);
        mSecondColor = typedArray.getColor(R.styleable.VoiceControlView_secondColor,Color.GRAY);
        typedArray.recycle();//清除

        mPaint = new Paint();
        mRect = new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeWidth(mCircleWidth);//设置圆环的宽度
//        mPaint.setStrokeCap(Paint.Cap.ROUND);//定义线段形状为圆头
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
        if ( mImage.getWidth() < Math.sqrt(2) * redRadius){
            mRect.left = (int)(mRect.left + Math.sqrt(2)/2 * redRadius - mImage.getWidth()/2 );
            mRect.top = (int)(mRect.top + Math.sqrt(2)/2 * redRadius - mImage.getWidth()/2);
            mRect.right = mRect.left + mImage.getWidth();
            mRect.bottom = mRect.top + mImage.getHeight();
        }

        canvas.drawBitmap(mImage,null,mRect,mPaint);
    }

    /**
     * 画每块
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas,int centre,int radius){
        float splitItem = ( 180  - mItemSize*mCount )/(mCount-1);//计算出每块所占比例,阴影部分总数小于块的部分
        float split = 180-(mItemSize*mCount + splitItem*(mCount-1));//180度中，未被计算进去的部分。将其加在左右两个空隙中
        RectF oval = new RectF(centre-radius,centre-radius,centre+radius,centre+radius);//定义圆弧的形状和大小界限
        mPaint.setColor(mFirstColor);//设置圆环的基础颜色，即未选中部分的颜色
        float itemSize = -180;
        for (int i =0 ;i <mCount ;i++){
            canvas.drawArc(oval,itemSize , mItemSize,false ,mPaint);
            if (i == 0 || i==mCount-2 )
                itemSize += mItemSize+splitItem + split/2;
            else
                itemSize += mItemSize+splitItem;
        }

        mPaint.setColor(mSecondColor);
        float currentItemSize = -180;
        for (int i=0 ; i< mCurrentCount ; i++){
            canvas.drawArc(oval,currentItemSize , mItemSize,false ,mPaint);
            if (i == 0 || i == mCount-2 )
                currentItemSize += mItemSize+splitItem + split/2;
            else
                currentItemSize += mItemSize+splitItem;
        }

    }

    /**
     * 当前数量+1，向上划
     */
    public void up() {
        if (mCurrentCount < mCount){
            mCurrentCount++;
            postInvalidate();
        }
    }

    /**
     * 当前数量-1，向下滑
     */
    public void down() {
        if (mCurrentCount > 0){
            mCurrentCount--;
            postInvalidate();
        }
    }
    private float xDown, xUp ;
    private int mMoveCount ;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = event.getX();
                mMoveCount = 0;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMoveCount /2 == 0){
                    xUp = event.getX();
                    if ((xUp-xDown)>0) up();
                    else down();
                }
                mMoveCount++;
                break;
            case MotionEvent.ACTION_OUTSIDE:

                break;
        }
        return true;
    }

}
