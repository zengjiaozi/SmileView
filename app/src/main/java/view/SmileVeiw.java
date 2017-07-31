package view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richinfo.smileview.R;

import static utils.Utils.dip2px;

/**
 * @author
 * @time 2017/7/31  10:06
 * @desc 由于显示的效果是水平排列的  所以是继承linearlayout的布局
 */
public class SmileVeiw extends LinearLayout implements Animator.AnimatorListener {
    private int defaultGravity = Gravity.CENTER_HORIZONTAL;
    private int like = 20;
    private int disLike = 30;
    private float flike, Unflike;
    private ImageView imageLike;
    private AnimationDrawable animationlike;
    private TextView likeNum;
    private int defaultTextColor = Color.WHITE;
    private TextView likeText;
    private String defaultLike = "喜欢";
    private String unLike = "无感";
    private ImageView disLikeImage;
    private AnimationDrawable animationDis;
    private TextView disNum;
    private LinearLayout likeBack;
    private LinearLayout disBack;
    private int defaultSize = dip2px(getContext(), 25);
    private LinearLayout likeAll;
    private LinearLayout disAll;

    //    分割线之间的距离
    private int dividerMargin = 20;
    private int defalutBottom = 70;
    private TextView disText;
    //选择执行帧动画的笑脸 //0 笑脸 1 哭脸
    private int animType = 0;
    private ValueAnimator animatorBack;
    private boolean isClose = false; //判断收起动画
    private String defaluteShadow = "#7F484848";
    private float count;
    private float fLike;
    private float fDis;


    public SmileVeiw(Context context) {
        this(context, null);
    }

    public SmileVeiw(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setNum(int like, int dislike) {
        //设置百分比
        count = like + dislike;
        fLike = like / count;
        fDis = dislike / count;
        this.like = (int) (fLike* 100);
        this.disLike = (int) (count- this.like);
        setLike(this.like);
        setDisLike(this.disLike);

    }

    public void setLike(int like) {
        likeNum.setText(like + "%");
    }

    public void setDisLike(int disLike) {
        disNum.setText(disLike + "%");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public SmileVeiw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        初始化操作
        init();
        bindListener();

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {
//        先移除所有的view  初始化总布局
        this.removeAllViews();
//        设置linearLayout的布局为水平布局
        setOrientation(LinearLayout.HORIZONTAL);
//          设置布局的显示效果是底部 水平居中
        setGravity(defaultGravity | Gravity.BOTTOM);
//        设置开始的颜色透明
        setBackgroundColor(Color.TRANSPARENT);

        //设置百分比
        float count = like + disLike;
        fLike = like / count;
        fDis = disLike / count;
        like = (int) (fLike * 100);
        disLike = (int) (fDis * 100);
//       初始化图片
        imageLike = new ImageView(getContext());
//        添加动画资源   获得帧动画
        imageLike.setBackgroundResource(R.drawable.animation_like);
        animationlike = (AnimationDrawable) imageLike.getBackground();
//     初始化文字
        likeNum = new TextView(getContext());
        Log.i("TAGLike", "like = " + like);
        likeNum.setText(like + "%");
        likeNum.setTextColor(defaultTextColor);
//        设置字体的粗细和大小
        TextPaint likeNumPaint = likeNum.getPaint();
        likeNumPaint.setFakeBoldText(true);
        likeNum.setTextSize(20f);

//         喜欢的默认的文字
        likeText = new TextView(getContext());
        likeText.setText(defaultLike);
        likeText.setTextColor(defaultTextColor);


//       不喜欢的图片资源
        disLikeImage = new ImageView(getContext());
        disLikeImage.setBackgroundResource(R.drawable.animation_unlike);
        animationDis = (AnimationDrawable) disLikeImage.getBackground();
        disNum = new TextView(getContext());
        Log.i("TAGLike", "dislike = " + disLike);
        disNum.setText(disLike + "%");

        disNum.setTextColor(defaultTextColor);
        TextPaint paint = disNum.getPaint();
        paint.setFakeBoldText(true);
        disNum.setTextSize(20f);

        disText = new TextView(getContext());
        disText.setText(unLike);
        disText.setTextColor(defaultTextColor);

//        初始化布局   对应的textview 和 imageView
        likeBack = new LinearLayout(getContext());
        disBack = new LinearLayout(getContext());

        LayoutParams params = new LayoutParams(defaultSize, defaultSize);
        likeBack.addView(imageLike, params);
        disBack.addView(disLikeImage, params);
        likeBack.setBackgroundResource(R.drawable.white_background);
        disBack.setBackgroundResource(R.drawable.white_background);

//        单列的布局 设置属性布局和显示效果
        likeAll = new LinearLayout(getContext());
        disAll = new LinearLayout(getContext());
        likeAll.setOrientation(VERTICAL);
        disAll.setOrientation(VERTICAL);
        likeAll.setGravity(Gravity.CENTER_HORIZONTAL);
        disAll.setGravity(Gravity.CENTER_HORIZONTAL);
        likeAll.setBackgroundColor(Color.TRANSPARENT);
        disAll.setBackgroundColor(Color.TRANSPARENT);


//        添加文字  图片 放进去每一列
        LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(1, 10, 0, 0);
        params1.gravity = Gravity.CENTER;
        disAll.setGravity(Gravity.CENTER_HORIZONTAL);
        likeAll.setGravity(Gravity.RIGHT);
        disAll.addView(disNum, params1);
        disAll.addView(disText, params1);
        disAll.addView(disBack, params1);
        likeAll.addView(likeNum, params1);
        likeAll.addView(likeText, params1);
        likeAll.addView(likeBack, params1);


        //中间分隔线
        ImageView imageView = new ImageView(getContext());
        imageView.setBackground(new ColorDrawable(Color.GRAY));
        LayoutParams params4 = new LayoutParams(3, 80);
        params4.setMargins(dividerMargin, 10, dividerMargin, defalutBottom + 20);
        params4.gravity = Gravity.BOTTOM;


        LayoutParams params3 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params3.setMargins(30, 20, 30, defalutBottom);
        params3.gravity = Gravity.BOTTOM;
        addView(disAll, params3);
        addView(imageView, params4);
        addView(likeAll, params3);

        //隐藏文字
        setVisibities(GONE);


    }


    //     先隐藏所有的文字
    private void setVisibities(int i) {

        likeNum.setVisibility(i);
        disNum.setVisibility(i);
        likeText.setVisibility(i);
        disText.setVisibility(i);
    }


    private void bindListener() {
//         不喜欢的图片监听事件
        disLikeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                执行哭脸的帧动画
                animType = 1;
                animBack(); //拉伸背景
                setVisibities(VISIBLE); //隐藏文字
                setBackgroundColor(Color.parseColor(defaluteShadow));
//                被点击显示黄色  另一个显示白色
                likeBack.setBackgroundResource(R.drawable.white_background);
                disBack.setBackgroundResource(R.drawable.yellow_background);
                imageLike.setBackground(null);
                imageLike.setBackgroundResource(R.drawable.animation_like);
                animationlike = (AnimationDrawable) imageLike.getBackground();

            }
        });

        imageLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animType = 0;
                animBack();
                setVisibities(VISIBLE);
                setBackgroundColor(Color.parseColor(defaluteShadow));
                disBack.setBackgroundResource(R.drawable.white_background);
                likeBack.setBackgroundResource(R.drawable.yellow_background);
                disLikeImage.setBackground(null);
                disLikeImage.setBackgroundResource(R.drawable.animation_unlike);
                animationDis = (AnimationDrawable) disLikeImage.getBackground();
            }
        });
    }


    //    拉伸背景动画
    private void animBack() {
//        执行动画过程中 图片均不可以点击
        disLikeImage.setClickable(false);
        imageLike.setClickable(false);
        final int max = Math.max(like * 4, disLike * 4);
//         生成属性动画对象
        animatorBack = ValueAnimator.ofInt(5, max);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int magrin = (int) animation.getAnimatedValue();
                LayoutParams paramsLike = (LayoutParams) imageLike.getLayoutParams();
                paramsLike.bottomMargin = magrin;

                if (magrin <= like * 4) {
                    imageLike.setLayoutParams(paramsLike);
                }
                if (magrin <= disLike * 4) {
                    disLikeImage.setLayoutParams(paramsLike);
                }

            }
        });
        isClose = false;
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();


    }

    //     当帧动画结束的时候
    @Override
    public void onAnimationEnd(Animator animation) {
        animationlike.stop();
        animationDis.stop();
//        关闭的时候不执行帧动画
        if (isClose) {
//            动画结束 ，收回的时候可点击
            disLikeImage.setClickable(true);
            imageLike.setClickable(true);
            //隐藏文字
            setVisibities(GONE);
            //恢复透明
            setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        isClose = true;
        if (animType == 0) {
            animationlike.start();
            objectY(imageLike);
        } else {
            animationDis.start();
            objectX(disLikeImage);
        }
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }


    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    //    动画效果的伸缩和回弹
    private void objectY(ImageView view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        //animator.setRepeatCount(1);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp(); //执行回弹动画
            }


        });


    }

    private void setBackUp() {

        final int max = Math.max(like * 4, disLike * 4);
        animatorBack = ValueAnimator.ofInt(max, 5);
        animatorBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int magrin = (int) animation.getAnimatedValue();
                LayoutParams paramsLike = (LayoutParams) imageLike.getLayoutParams();
                paramsLike.bottomMargin = magrin;

                if (magrin <= like * 4) {
                    imageLike.setLayoutParams(paramsLike);
                }
                if (magrin <= disLike * 4) {
                    disLikeImage.setLayoutParams(paramsLike);
                }
            }
        });
        animatorBack.addListener(this);
        animatorBack.setDuration(500);
        animatorBack.start();

    }

    private void objectX(ImageView view) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        // animator.setRepeatCount(1);
        animator.setDuration(1500);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp(); //执行回弹动画
            }
        });
    }
}
