package com.fox.shake;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fox.shake.view.MarqueeTextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ImageView ivCloudView1;
    private ImageView ivCloudView2;
    private ImageView ivCloudView3;
    private ImageView ivCloudView4;
    private ImageView ivTreeView;
    private ImageView ivLeafView;
    private ImageView ivFruitView;
    private ImageView ivSunshine;
    private RelativeLayout layoutBtnView;
    private ImageView ivShake;
    private TextView tvRemindTime;
    View landLayout;
    MarqueeTextView tvMarquee;

    private void initView() {
        tvMarquee = (MarqueeTextView) findViewById(R.id.tvMarquee);
        ivCloudView1 = (ImageView) findViewById(R.id.ivCloudView1);
        ivCloudView2 = (ImageView) findViewById(R.id.ivCloudView2);
        ivCloudView3 = (ImageView) findViewById(R.id.ivCloudView3);
        ivCloudView4 = (ImageView) findViewById(R.id.ivCloudView4);
        ivTreeView = (ImageView) findViewById(R.id.ivTreeView);
        ivLeafView = (ImageView) findViewById(R.id.ivLeafView);
        ivFruitView = (ImageView) findViewById(R.id.ivFruitView);
        ivSunshine = (ImageView) findViewById(R.id.ivSunshine);
        layoutBtnView = (RelativeLayout) findViewById(R.id.layoutBtnView);
        ivShake = (ImageView) findViewById(R.id.ivShake);
        tvRemindTime = (TextView) findViewById(R.id.tvRemindTime);
        landLayout = findViewById(R.id.landLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initView();

        layoutBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) {
                    hearShake();
                }
            }

        });

        initAnimations();
    }

    int count = 3;

    private void updateViews() {
        if (count > 0) {
            tvRemindTime.setVisibility(View.VISIBLE);
            tvRemindTime.setText(String.valueOf(count));
        } else {
            tvRemindTime.setVisibility(View.GONE);
        }
        tvMarquee.setText(String.format("摇到111%s积分", count));

    }

    private RotateAnimation treeAfterShakeAnimation = null;
    private final AccelerateDecelerateInterpolator adInterpolator = new AccelerateDecelerateInterpolator();
    private final int DURATION = 400;

    private void hearShake() {
        ivTreeView.clearAnimation();
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }

        if (treeAfterShakeAnimation == null) {
            treeAfterShakeAnimation = new RotateAnimation(1, -1, Animation.RELATIVE_TO_SELF, 1f / 2, Animation.RELATIVE_TO_SELF, 1f);
            treeAfterShakeAnimation.setInterpolator(adInterpolator);
            treeAfterShakeAnimation.setDuration(DURATION / 4);
            treeAfterShakeAnimation.setRepeatCount(2);
            treeAfterShakeAnimation.setRepeatMode(Animation.REVERSE);
            treeAfterShakeAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(final Animation animation) {

                }

                @Override
                public void onAnimationRepeat(final Animation animation) {

                }

                @Override
                public void onAnimationEnd(final Animation animation) {
                    // Toast.makeText(ShakeActivity.this, "after shake",
                    // Toast.LENGTH_SHORT).show();
                    if (count == 1) {
                        dropFruit();
                    } else {
                        floatingLeaf();
                    }
                }
            });
        }
        ivTreeView.startAnimation(treeAfterShakeAnimation);
    }


    private TranslateAnimation landAnimation = null;
    private TranslateAnimation cloudAnimation = null;
    private AlphaAnimation sunshineAnimation = null;
    private AnimationSet treeAnimationSet = null;

    /**
     * 初始化动画
     */
    private void initAnimations() {
        landAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0);
        landAnimation.setDuration(DURATION);
        landAnimation.setInterpolator(adInterpolator);
        landAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                ivCloudView1.setVisibility(View.VISIBLE);
                ivCloudView2.setVisibility(View.VISIBLE);
                ivCloudView3.setVisibility(View.VISIBLE);
                ivCloudView4.setVisibility(View.VISIBLE);
                ivSunshine.setVisibility(View.VISIBLE);
                ivCloudView1.startAnimation(cloudAnimation);
                ivCloudView2.startAnimation(cloudAnimation);
                ivCloudView3.startAnimation(cloudAnimation);
                ivCloudView4.startAnimation(cloudAnimation);
                ivSunshine.startAnimation(sunshineAnimation);
                showShakeBtn();
                updateViews();
            }
        });

        treeAnimationSet = new AnimationSet(true);
        ScaleAnimation treeScaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        AlphaAnimation treeAlphaAnimation = new AlphaAnimation(0f, 1);
        treeAnimationSet.addAnimation(treeScaleAnimation);
        treeAnimationSet.addAnimation(treeAlphaAnimation);
        treeAnimationSet.setDuration(DURATION);
        treeAnimationSet.setInterpolator(adInterpolator);
        treeAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {

            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {

            }
        });

        cloudAnimation = new TranslateAnimation(MyApplication.getScreenWidth(), -MyApplication.getScreenWidth(), 0, 0);
        cloudAnimation.setDuration(50 * DURATION);
        cloudAnimation.setRepeatCount(Animation.INFINITE);
        cloudAnimation.setInterpolator(adInterpolator);

        sunshineAnimation = new AlphaAnimation(0, 1);
        sunshineAnimation.setDuration(20 * DURATION);
        sunshineAnimation.setRepeatMode(Animation.REVERSE);
        sunshineAnimation.setRepeatCount(Animation.INFINITE);
        sunshineAnimation.setInterpolator(adInterpolator);

        ivTreeView.setVisibility(View.VISIBLE);
        ivTreeView.startAnimation(treeAnimationSet);


        landLayout.setVisibility(View.VISIBLE);
        landLayout.startAnimation(landAnimation);
    }

    /**
     * 摇晃按钮的顺时针动画
     */
    private RotateAnimation clockwiseAnimation = null;
    /**
     * 摇晃按钮的逆时针动画
     */
    private RotateAnimation antiClockwiseAnimation = null;

    /**
     * 摇晃按钮定时器
     */
    private final Timer shakingTimer = new Timer();

    /**
     * 摇晃按钮加载动画
     */
    public void showShakeBtn() {
        if (clockwiseAnimation == null) {
            clockwiseAnimation = new RotateAnimation(3, -3, Animation.RELATIVE_TO_SELF, 1f / 2, Animation.RELATIVE_TO_SELF, 1f);
            clockwiseAnimation.setInterpolator(adInterpolator);
            clockwiseAnimation.setDuration(DURATION / 5);
        }
        if (antiClockwiseAnimation == null) {
            antiClockwiseAnimation = new RotateAnimation(-3, 3, Animation.RELATIVE_TO_SELF, 1f / 2, Animation.RELATIVE_TO_SELF, 1f);
            antiClockwiseAnimation.setInterpolator(adInterpolator);
            antiClockwiseAnimation.setDuration(DURATION / 5);
        }

        layoutBtnView.setVisibility(View.VISIBLE);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) {
                    final int index = i;
                    layoutBtnView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (index % 2 == 1) {
                                layoutBtnView.startAnimation(clockwiseAnimation);
                            } else {
                                layoutBtnView.startAnimation(antiClockwiseAnimation);
                            }
                        }
                    }, 10);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        shakingTimer.scheduleAtFixedRate(timerTask, 1000, 6 * DURATION);

        // if (top10Bean != null
        // && TextUtils.equals("0000", top10Bean.getStatusCode())) {
        // initRankingView(top10Bean);
        // } else {
        // requestTop10(INDEX_GETTOP10_BONUS);
        // }

//        if(count == 1){
//
//            dropFruit();
//        } else {
//            floatingLeaf();
//
//        }
//        hearShake();
//        ivTreeView.clearAnimation();
    }


    static class XInterpolator implements android.view.animation.Interpolator {

        @Override
        public float getInterpolation(final float input) {
            if (input < 0.5) {
                return (float) (Math.cos(input * Math.PI * 2.0f)) / 2;
            } else if (input < 0.75) {
                return (float) (Math.sqrt((input - 0.5) * 4));
            } else if (input < 0.875) {
                return (float) (Math.sqrt((0.875 - input) * 4 + 0.5));
            } else {
                return (float) (Math.sqrt((input - 0.875) * 4 + 0.5));
            }
        }
    }

    /**
     * 掉果实
     */
    private void dropFruit() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        ivFruitView.setVisibility(View.VISIBLE);
        ivLeafView.setVisibility(View.INVISIBLE);
        // float x1 = fruitView.getLeft() - dipToPixel(10);
        // float x2 = fruitView.getLeft();
        float y1 = ivLeafView.getTop() + dipToPixel(90);
        float y2 = ivFruitView.getTop();
        // float x = x1 - x2;
        float y = y1 - y2;
        int duration = DURATION * 4;
        AnimationSet set = new AnimationSet(false);

        TranslateAnimation translateAnimationX1 = new TranslateAnimation(-dipToPixel(10), 0, 0, 0);
        translateAnimationX1.setInterpolator(linearInterpolator);
        translateAnimationX1.setDuration(duration);
        TranslateAnimation translateAnimationY1 = new TranslateAnimation(0, 0, y, 0);
        translateAnimationY1.setInterpolator(new BounceInterpolator());
        translateAnimationY1.setDuration(duration);
        set.addAnimation(translateAnimationY1);
        // set.addAnimation(translateAnimationX1);
        translateAnimationY1.setFillEnabled(false);
        translateAnimationX1.setFillEnabled(false);
        set.setFillEnabled(false);
        set.setDuration(duration);
        ivFruitView.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
            }

            @Override
            public void onAnimationEnd(final Animation animation) {

                count--;
                updateViews();
            }
        });
    }

    DisplayMetrics mDisplayMetrics;

    /**
     * dip to pixel
     */
    private int dipToPixel(final float dip) {
        if (null == mDisplayMetrics) {
            mDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, mDisplayMetrics);
    }

    /**
     * 漂浮树叶
     */
    private void floatingLeaf() {
        XInterpolator xInterpolator = new XInterpolator();
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        ivTreeView.setVisibility(View.VISIBLE);
        ivFruitView.setVisibility(View.INVISIBLE);
        float x1 = ivTreeView.getLeft() + ivTreeView.getWidth() / 2 + dipToPixel(10);
        float x2 = ivLeafView.getLeft();
        float y1 = ivTreeView.getTop() + dipToPixel(30);
        float y2 = ivLeafView.getTop();
        float x = x1 - x2;
        float y = y1 - y2;
        int duration = DURATION * 4;
        AnimationSet set = new AnimationSet(false);

        TranslateAnimation translateAnimationX1 = new TranslateAnimation(x, 0, 0, 0);
        translateAnimationX1.setInterpolator(xInterpolator);
        translateAnimationX1.setDuration(duration);
        TranslateAnimation translateAnimationY1 = new TranslateAnimation(0, 0, y, 0);
        translateAnimationY1.setInterpolator(linearInterpolator);
        translateAnimationY1.setDuration(duration);
        set.addAnimation(translateAnimationY1);
        set.addAnimation(translateAnimationX1);
        translateAnimationY1.setFillEnabled(false);
        translateAnimationX1.setFillEnabled(false);
        set.setFillEnabled(false);
        set.setDuration(duration);
        ivLeafView.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                // dialogTools.showOneButtonAlertDialog(message,
                // ShakeActivity.this, false);
                //requestAd();
                count--;
                updateViews();
            }
        });
    }

}
