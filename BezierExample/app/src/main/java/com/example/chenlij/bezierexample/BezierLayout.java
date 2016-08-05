package com.example.chenlij.bezierexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by Chenlij on 2016/8/5.
 */
public class BezierLayout extends RelativeLayout{

    private Drawable a;
    private int drawableWidth;
    private int drawableHeight;
    private LayoutParams mParameters;
    private int mWidth;
    private int mHeight;
    private Random mRandom = new Random();
    private Interpolator[] interpolators = {new AccelerateDecelerateInterpolator()//加速减速
            , new AccelerateInterpolator()//加速
            , new LinearInterpolator()
            , new DecelerateInterpolator()//减速
    };

    public BezierLayout(Context context) {
        super(context);
    }

    public BezierLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialized();
    }

    private void initialized() {

        /*使a映射资源a*/
        a = getResources().getDrawable(R.mipmap.a);

        /*获取图片Drawable的高和宽*/
        drawableHeight = a.getIntrinsicHeight();
        drawableWidth = a.getIntrinsicWidth();

        /*初始化Parameters*/
        mParameters = new LayoutParams(drawableWidth, drawableHeight);

        /*贴近父容器水平居中并且贴近底部*/
        mParameters.addRule(CENTER_HORIZONTAL, TRUE);
        mParameters.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }

    /*onMeasure是View类的方法*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    public void addKeda(){

        /*生成一个新的imageView对象*/
        final ImageView imageView = new ImageView(getContext());

        /*imageView对象装填图片资源a*/
        imageView.setImageDrawable(a);

        /*设置imageView对象的宽高参数*/
        imageView.setLayoutParams(mParameters);

        /*添加这个imageView使其出现*/
        addView(imageView);

        AnimatorSet animatorSet = getAnimation(imageView);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(imageView);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet getAnimation(ImageView imageView) {

        /*Alpha动画*/
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.2f, 1f);

        /*缩放动画*/
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.2f, 1f);

        /*添加起始动画集合*/
        AnimatorSet startAnimatorSet = new AnimatorSet();
        startAnimatorSet.setDuration(500);
        startAnimatorSet.playTogether(alpha, scaleX, scaleY);
        startAnimatorSet.setTarget(imageView);

        /*贝塞尔曲线动画(不断的修改ImageView的坐标---PointF)*/
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(imageView);
        AnimatorSet bezierSet = new AnimatorSet();
        bezierSet.setTarget(imageView);
        bezierSet.playSequentially(startAnimatorSet, bezierValueAnimator);//将所有动画添加到一起：起始动画---->贝塞尔曲线动画
        //需要注意的是当时用AnimatorSet时，尽量不要设置Duration，API会根据所包含的动画集自动计算持续时间
//        bezierSet.setDuration(1000);
        return bezierSet;
    }

    private ValueAnimator getBezierValueAnimator(final ImageView iv) {
        PointF pointf0 = new PointF(mWidth / 2 - drawableWidth / 2, mHeight - drawableHeight);
        PointF pointf1 = new PointF(mRandom.nextInt(mWidth), mRandom.nextInt(mHeight / 2) + mHeight / 2);
        PointF pointf2 = new PointF(mRandom.nextInt(mWidth), mRandom.nextInt(mHeight / 2));
        PointF pointf3 = new PointF(mRandom.nextInt(mWidth), 0);

        //通过贝塞尔曲线公式，自定义估值器
        final BezierEvaluator evaluator = new BezierEvaluator(pointf1, pointf2);
        //将估值器传入属性动画，不断的修改控件的坐标
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointf0, pointf3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointf = (PointF) animation.getAnimatedValue();
                iv.setX(pointf.x);
                iv.setY(pointf.y);
                //为了美观，再设置alpha
                iv.setAlpha(1 - animation.getAnimatedFraction());//getAnimatedFraction返回动画进行的百分比，api12以上支持
            }
        });
        animator.setTarget(iv);
        animator.setDuration(3000);
        //同样，为了美观我们还可以添加加速度,减速度，弹射等效果(插值器)
        animator.setInterpolator(interpolators[mRandom.nextInt(interpolators.length)]);
        return animator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
