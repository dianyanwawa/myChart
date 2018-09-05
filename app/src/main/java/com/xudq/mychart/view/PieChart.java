package com.xudq.mychart.view;

import android.view.View;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


import com.xudq.mychart.bean.PieDataEntity;
import com.xudq.mychart.utils.CalculateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by TJZM-14
 * AUTHOR: xudingqiang@teejo.com.cn
 * DATE: 2018/1/24
 * DESC:饼状图表
 */

public class PieChart extends View {
    /**
     * 视图的宽和高
     */
    private int mTotalWidth, mTotalHeight;
    /**
     * 绘制区域的半径
     */
    private float mRadius;

    private Paint mPaint, mLinePaint, mTextPaint;

    private Path mPath;
    /**
     * 扇形的绘制区域
     */
    private RectF mRectF;
    /**
     * 点击之后的扇形的绘制区域
     */
    private RectF mRectFTouch;

    private List<PieDataEntity> mDataList;
    /**
     * 所有的数据加起来的总值
     */
    private float mTotalValue;
    /**
     * 起始角度的集合
     */
    private float[] angles;
    /**
     * 手点击的部分的position
     */
    private int position = -1;
    /**
     * 点击监听
     */
    private OnItemPieClickListener mOnItemPieClickListener;

    public void setOnItemPieClickListener(OnItemPieClickListener onItemPieClickListener) {
        mOnItemPieClickListener = onItemPieClickListener;
    }

    public interface OnItemPieClickListener {
        void onClick(int position);
    }

    public PieChart(Context context) {
        super(context);
        init(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRectF = new RectF();
        mRectFTouch = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.BLACK);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(24);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w - getPaddingLeft() - getPaddingRight();
        mTotalHeight = h - getPaddingTop() - getPaddingBottom();

        mRadius = (float) (Math.min(mTotalWidth, mTotalHeight) / 2 * 0.7);

        mRectF.left = -mRadius;
        mRectF.top = -mRadius;
        mRectF.right = mRadius;
        mRectF.bottom = mRadius;

        mRectFTouch.left = -mRadius - 16;
        mRectFTouch.top = -mRadius - 16;
        mRectFTouch.right = mRadius + 16;
        mRectFTouch.bottom = mRadius + 16;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataList == null)
            return;
        canvas.translate(mTotalWidth / 4, mTotalHeight / 2);
        //绘制饼图的每块区域
        drawPiePath(canvas);
    }

    private float percent = 0f;
    private TimeInterpolator pointInterpolator = new DecelerateInterpolator();

    public void startAnimation(int duration) {
        ValueAnimator mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(pointInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                percent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.start();
    }

    /**
     * 绘制饼图的每块区域 和文本
     *
     * @param canvas
     */
    private void drawPiePath(Canvas canvas) {
        //起始地角度
        float startAngle = 0;
//        LogTool.INSTANCE.i("mDataList =  " + mDataList.size());

        for (int i = 0; i < mDataList.size(); i++) {
            float sweepAngle = mDataList.get(i).getValue() / mTotalValue * 360;//每个扇形的角度
            sweepAngle = sweepAngle * percent;
            mPaint.setColor(mDataList.get(i).getColor());
            //*******下面的两种方法选其一就可以 一个是通过画路径来实现 一个是直接绘制扇形***********
//            mPath.moveTo(0,0);
//            if(position-1==i){
//                mPath.arcTo(mRectFTouch,startAngle,sweepAngle);
//            }else {
//                mPath.arcTo(mRectF,startAngle,sweepAngle);
//            }
//            canvas.drawPath(mPath,mPaint);
//            mPath.reset();
//            canvas.drawArc(mRectF,startAngle,sweepAngle,true,mPaint);
            if (position - 1 == i) {
                canvas.drawArc(mRectFTouch, startAngle, sweepAngle, true, mPaint);
            } else {
                canvas.drawArc(mRectF, startAngle, sweepAngle, true, mPaint);
            }
            Log.i("toRadians", (startAngle + sweepAngle / 2) + "****" + Math.toRadians(startAngle + sweepAngle / 2));
            //确定直线的起始和结束的点的位置
            float pxs = (float) (mRadius * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float pys = (float) (mRadius * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            float pxt = (float) ((mRadius + 10) * Math.cos(Math.toRadians(startAngle + sweepAngle / 2)));
            float pyt = (float) ((mRadius + 10) * Math.sin(Math.toRadians(startAngle + sweepAngle / 2)));
            angles[i] = startAngle;
            startAngle += sweepAngle;
            //绘制线和文本
            canvas.drawLine(pxs, pys, pxt, pyt, mLinePaint);
            float res = mDataList.get(i).getValue() / mTotalValue * 100;
            //提供精确的小数位四舍五入处理。
            double resToRound = CalculateUtil.round(res, 2);
            float v = startAngle % 360;
            if (startAngle % 360.0 >= 90.0 && startAngle % 360.0 <= 270.0) {//2 3 象限
                canvas.drawLine(pxt, pyt, pxt - 10, pyt, mLinePaint);
                canvas.drawText(resToRound + "%", pxt - mTextPaint.measureText(resToRound + "%") - 10, pyt, mTextPaint);
            } else {
                canvas.drawLine(pxt, pyt, pxt + 10, pyt, mLinePaint);
                canvas.drawText(resToRound + "%", pxt + 10, pyt, mTextPaint);
            }

            float h = mTotalHeight/2-mRadius;
            if(i<5){
                canvas.drawCircle(mRadius * 2, h - mRadius + (i * mRadius / 2)-20, 20, mPaint);
                canvas.drawText(mDataList.get(i).getName(), mRadius * 2 + 40, h - mRadius + (i * mRadius / 2)-10, mTextPaint);
            }else {
                canvas.drawCircle(mRadius * 3+10, h - mRadius + (0 * mRadius / 2)-20, 20, mPaint);
                canvas.drawText(mDataList.get(i).getName(), mRadius * 3 + 50, h - mRadius + (0 * mRadius / 2)-10, mTextPaint);
            }

//            if (i == 0) {
//                canvas.drawCircle(mRadius * 2, pys, 20, mPaint);
//                canvas.drawText(mDataList.get(i).getName(), mRadius * 2 + 40, pys, mTextPaint);
//            } else if ((i % 2) == 1) {
//                int j = i / 2 + 1;
//                float y = h + 40 * j;
////                LogTool.INSTANCE.i("y =  " + y);
//                canvas.drawCircle(mRadius * 2, y, 20, mPaint);
//                canvas.drawText(mDataList.get(i).getName(), mRadius * 2 + 40, y, mTextPaint);
//            } else {
//                int j = i / 2;
//                float yy = h - 40 * j;
////                LogTool.INSTANCE.i("yy =  " + yy);
//                canvas.drawCircle(mRadius * 2, yy, 20, mPaint);
//                canvas.drawText(mDataList.get(i).getName(), mRadius * 2 + 40, yy, mTextPaint);
//            }
        }

    }

    public void setDataList(List<PieDataEntity> dataList) {
        this.mDataList = dataList;
        mTotalValue = 0;
        for (PieDataEntity pieData : mDataList) {
            mTotalValue += pieData.getValue();
        }
        angles = new float[mDataList.size()];
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX() - (mTotalWidth / 2);
                float y = event.getY() - (mTotalHeight / 2);
                float touchAngle = 0;
                if (x < 0 && y < 0) {  //2 象限
                    touchAngle += 180;
                } else if (y < 0 && x > 0) {  //1象限
                    touchAngle += 360;
                } else if (y > 0 && x < 0) {  //3象限
                    touchAngle += 180;
                }
                //Math.atan(y/x) 返回正数值表示相对于 x 轴的逆时针转角，返回负数值则表示顺时针转角。
                //返回值乘以 180/π，将弧度转换为角度。
                touchAngle += Math.toDegrees(Math.atan(y / x));
                if (touchAngle < 0) {
                    touchAngle = touchAngle + 360;
                }
                float touchRadius = (float) Math.sqrt(y * y + x * x);
                if (touchRadius < mRadius) {
                    position = -Arrays.binarySearch(angles, (touchAngle)) - 1;
                    invalidate();
                    if (mOnItemPieClickListener != null) {
                        mOnItemPieClickListener.onClick(position - 1);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
