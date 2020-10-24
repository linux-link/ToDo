package com.wujia.todo.clock;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Random r = new Random();
        int num = r.nextInt() % 10;
        if (num < 0) {
            num = -(num);
        }
        System.out.println(num);
        try {
            flip1(2);
            flip2(0);
            flip3(2);
            flip4(0);
        } catch (Exception e) {
        }
    }

    public void flip1(int changeNumber) {
        final ImageView up_back = findViewById(R.id.up_back1);
        Drawable img = up_back.getDrawable();

        final ImageView upView = findViewById(R.id.up1);
        upView.setImageDrawable(img);
        upView.setVisibility(View.INVISIBLE);

        final ImageView downView = findViewById(R.id.down1);
        downView.setVisibility(View.INVISIBLE);

        int resId = getResources().getIdentifier("up_" + changeNumber,
                "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resId);
        up_back.setImageDrawable(image);

        resId = getResources().getIdentifier("down_" + changeNumber,
                "drawable", getPackageName());
        image = getResources().getDrawable(resId);
        downView.setImageDrawable(image);

        Animation anim = new ScaleAnimation(1f, 1f, // Start and end values for the X axis scaling
                1f, 0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setDuration(100);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = new ScaleAnimation(1f, 1f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f);
                anim.setDuration(200);
                anim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ImageView back_down = (ImageView) findViewById(R.id.down_back1);
                        back_down.setImageDrawable(downView.getDrawable());
                    }
                });
                downView.startAnimation(anim);

            }
        });
        upView.startAnimation(anim);

    }

    public void flip2(int changeNumber) {
        final ImageView up_back = findViewById(R.id.up_back2);
        Drawable img = up_back.getDrawable();

        final ImageView upView = findViewById(R.id.up2);
        upView.setImageDrawable(img);
        upView.setVisibility(View.INVISIBLE);

        final ImageView downView = findViewById(R.id.down2);
        downView.setVisibility(View.INVISIBLE);

        int resId = getResources().getIdentifier("up_" + changeNumber,
                "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resId);
        up_back.setImageDrawable(image);

        resId = getResources().getIdentifier("down_" + changeNumber,
                "drawable", getPackageName());
        image = getResources().getDrawable(resId);
        downView.setImageDrawable(image);

        Animation anim = new ScaleAnimation(1f, 1f, // Start and end values for the X axis scaling
                1f, 0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setDuration(100);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = new ScaleAnimation(1f, 1f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f);
                anim.setDuration(200);
                anim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ImageView back_down = (ImageView) findViewById(R.id.down_back2);
                        back_down.setImageDrawable(downView.getDrawable());
                    }
                });
                downView.startAnimation(anim);

            }
        });
        upView.startAnimation(anim);

    }

    public void flip3(int changeNumber) {
        final ImageView up_back = findViewById(R.id.up_back3);
        Drawable img = up_back.getDrawable();

        final ImageView upView = findViewById(R.id.up3);
        upView.setImageDrawable(img);
        upView.setVisibility(View.INVISIBLE);

        final ImageView downView = findViewById(R.id.down3);
        downView.setVisibility(View.INVISIBLE);

        int resId = getResources().getIdentifier("up_" + changeNumber,
                "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resId);
        up_back.setImageDrawable(image);

        resId = getResources().getIdentifier("down_" + changeNumber,
                "drawable", getPackageName());
        image = getResources().getDrawable(resId);
        downView.setImageDrawable(image);

        Animation anim = new ScaleAnimation(1f, 1f, // Start and end values for the X axis scaling
                1f, 0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setDuration(100);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = new ScaleAnimation(1f, 1f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f);
                anim.setDuration(200);
                anim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ImageView back_down = (ImageView) findViewById(R.id.down_back3);
                        back_down.setImageDrawable(downView.getDrawable());
                    }
                });
                downView.startAnimation(anim);

            }
        });
        upView.startAnimation(anim);

    }

    public void flip4(int changeNumber) {
        final ImageView up_back = findViewById(R.id.up_back4);
        Drawable img = up_back.getDrawable();

        final ImageView upView = findViewById(R.id.up4);
        upView.setImageDrawable(img);
        upView.setVisibility(View.INVISIBLE);

        final ImageView downView = findViewById(R.id.down4);
        downView.setVisibility(View.INVISIBLE);

        int resId = getResources().getIdentifier("up_" + changeNumber,
                "drawable", getPackageName());
        Drawable image = getResources().getDrawable(resId);
        up_back.setImageDrawable(image);

        resId = getResources().getIdentifier("down_" + changeNumber,
                "drawable", getPackageName());
        image = getResources().getDrawable(resId);
        downView.setImageDrawable(image);

        Animation anim = new ScaleAnimation(1f, 1f, // Start and end values for the X axis scaling
                1f, 0f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setDuration(100);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = new ScaleAnimation(1f, 1f, 0f, 1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f);
                anim.setDuration(200);
                anim.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ImageView back_down = (ImageView) findViewById(R.id.down_back4);
                        back_down.setImageDrawable(downView.getDrawable());
                    }
                });
                downView.startAnimation(anim);

            }
        });
        upView.startAnimation(anim);

    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private boolean isClick;

    public void check(View v) {
        if (isClick){
            isClick = false;
            mHandler.postDelayed(() -> {
                flip1(0);
            }, 500);

            mHandler.postDelayed(() -> {
                flip2(9);
            }, 1000);

            mHandler.postDelayed(() -> {
                flip3(9);
            }, 1500);

            mHandler.postDelayed(() -> {
                flip4(6);
            }, 2000);
        }else {
            isClick = true;
            mHandler.postDelayed(() -> {
                flip1(1);
            }, 500);

            mHandler.postDelayed(() -> {
                flip2(0);
            }, 1000);

            mHandler.postDelayed(() -> {
                flip3(2);
            }, 1500);

            mHandler.postDelayed(() -> {
                flip4(4);
            }, 2000);
        }
    }

}
