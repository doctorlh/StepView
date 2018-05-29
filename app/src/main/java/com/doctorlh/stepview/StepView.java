package com.doctorlh.stepview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/29.
 */

public class StepView extends View {

    private Paint mProgressPaint;
    private Paint mDrawablePaint;
    private Paint mTextPaint;
    // 图标和文字与中心点的间距
    private int marginToCenter = 30;

    private SparseArray<Drawable> mCurrentDrawables;
    private List<StepInfo> mStepInfos = new ArrayList<>();

    @ColorInt
    private int mProgressColor = Color.RED;
    @ColorInt
    private int mProgressDisColor = Color.GRAY;
    private int mProgressWidth = 9;

    private int currentStep = 1;

    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressColor = Color.parseColor("#F20C59");
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(mProgressWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(mProgressColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(dp2px(context, 11));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mProgressDisColor = Color.parseColor("#D7D8D9");

        mDrawablePaint = new Paint();
        mDrawablePaint.setAntiAlias(true);

        mCurrentDrawables = new SparseArray<>();

        marginToCenter = (int) dp2px(context, 10);
    }

    public void setStepInfos(List<StepInfo> stepInfos) {
        mStepInfos = stepInfos;
        invalidate();
    }

    public void setStep(int step) {
        currentStep = step;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 290);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (mStepInfos == null || mStepInfos.size() == 0) {
            return;
        }
        int count = mStepInfos.size();
        int stepWidth = getWidth() / (count + 1);
        int top = getHeight() / 2;
        int left = stepWidth;
        for (int i = 0; i < count; i++) {
            // 绘制图标
            Drawable drawable = null;
            if (i <= currentStep - 1) {
                drawable = getResources().getDrawable(mStepInfos.get(i).drawableId);
                mTextPaint.setColor(mProgressColor);
            } else {
                drawable = getResources().getDrawable(mStepInfos.get(i).disDrawableId);
                mTextPaint.setColor(mProgressDisColor);
            }
            drawable.setBounds(left - drawable.getMinimumWidth() / 2,
                    top - drawable.getMinimumWidth() / 2 - marginToCenter,
                    drawable.getMinimumWidth() / 2 + left,
                    drawable.getMinimumHeight() / 2 + top - marginToCenter);
            mCurrentDrawables.put(i, drawable);
            drawable.draw(canvas);

            // 绘制文字
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            Rect rect = new Rect();
            rect.left = drawable.getBounds().left;
            rect.top = top + marginToCenter;
            rect.right = drawable.getBounds().right;
            rect.bottom = (int) (rect.top + fontMetrics.bottom);
            int baseLineY = (int) (rect.centerY() - fontMetrics.top / 2 - fontMetrics.bottom / 2);
            canvas.drawText(mStepInfos.get(i).des, rect.centerX(), baseLineY, mTextPaint);

            left += stepWidth;
        }

        // 绘制进度
        for (int j = 1; j < count; j++) {
            int startX = mCurrentDrawables.get(j - 1).getBounds().right;
            int stopX = mCurrentDrawables.get(j).getBounds().left;
            if (j <= currentStep - 1) {
                mProgressPaint.setColor(mProgressColor);
            } else {
                mProgressPaint.setColor(mProgressDisColor);
            }
            canvas.drawLine(startX, top - marginToCenter, stopX, top - marginToCenter, mProgressPaint);
        }
    }

    public static float dp2px(Context context, float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return value * scale / 160 + 0.5f;
    }
}

