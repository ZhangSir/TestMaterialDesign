package com.zs.it.testmaterialdesign;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TestActivityCompactActivity extends AppCompatActivity {

    private ImageView iv, ivAnim;

    private Button btnStart, btnPause, btnResume, btnStop, btnStateListAnimActive, btnStateListAnimDrawable, btnAnimatedVectorDrawable;

    private ObjectAnimator oa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activity_compact);

        iv = (ImageView) this.findViewById(R.id.iv);
        ivAnim = (ImageView) this.findViewById(R.id.iv_anim);
        btnStart = (Button) this.findViewById(R.id.btn_start);
        btnPause = (Button) this.findViewById(R.id.btn_pause);
        btnResume = (Button) this.findViewById(R.id.btn_resume);
        btnStop = (Button) this.findViewById(R.id.btn_stop);

        btnStateListAnimActive = (Button) this.findViewById(R.id.btn_state_list_anim_active);
//        btnStateListAnimDrawable = (Button) this.findViewById(R.id.btn_state_list_anim_drawable);

//        AnimatedStateListDrawable a = AnimatedStateListDrawable.createFro

        if(Build.VERSION.SDK_INT >= 21){
//            给视图分配动画使用android:stateListAnimator属性。
//            在代码中使用，使用AnimatorInflater.loadStateListAnimator()方法，并且使用View.setStateListAnimator()方法。
//            当你的主题是继承的Material主题，按钮默认有一个Z动画。如果需要避免这个动画，设置android:stateListAnimator属性为@null即可。
            StateListAnimator slAnim = AnimatorInflater.loadStateListAnimator(this, R.drawable.state_list_anim);
            btnStateListAnimActive.setStateListAnimator(slAnim);
        }

        btnAnimatedVectorDrawable = (Button) this.findViewById(R.id.btn_animated_vector_drawable);

        btnAnimatedVectorDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable = btnAnimatedVectorDrawable.getBackground();
                if(drawable instanceof Animatable){
                    //启动矢量图动画
                    ((Animatable) drawable).start();
                }
            }
        });

        if(Build.VERSION.SDK_INT >= 21){
            //起始颜色为红色
            int startColor = 0xffff0000;
            //终止颜色为绿色
            int endColor = 0xff00ff00;
            ObjectAnimator oa1 = ObjectAnimator.ofArgb(getWindow().getDecorView(), "backgroundColor", startColor, endColor);
            oa1.setDuration(5000);
            oa1.setRepeatCount(ValueAnimator.INFINITE);
            oa1.setRepeatMode(ValueAnimator.REVERSE);
            oa1.setInterpolator(new PathInterpolator(0.4f, 0, 1, 1));
            oa1.start();
        }


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 21){
                    //PathInterpolator的代码使用方法
                    Path path = new Path();
//                    path.addCircle(360, 360, 200, Path.Direction.CW);//无动画
//                    Path path = new Path();
                    path.moveTo(100, 100);
//                    path.lineTo(600, 100);
//                    path.lineTo(100, 600);
//                    path.lineTo(600, 600);
//                    path.arcTo(new RectF(100, 100, 600, 900), 90, 180);//无动画
                    path.cubicTo(100, 100, 500, 500, 200, 800);
                    path.close();

                    oa = ObjectAnimator.ofFloat(ivAnim, ivAnim.X, ivAnim.Y, path);
                    oa.setDuration(5000);
                    oa.setRepeatCount(ValueAnimator.INFINITE);
                    oa.setRepeatMode(ValueAnimator.REVERSE);
                    oa.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            Toast.makeText(TestActivityCompactActivity.this, "开始", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            Toast.makeText(TestActivityCompactActivity.this, "结束", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            Toast.makeText(TestActivityCompactActivity.this, "取消", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            Toast.makeText(TestActivityCompactActivity.this, "重复", Toast.LENGTH_SHORT).show();
                        }
                    });
                    oa.addPauseListener(new Animator.AnimatorPauseListener() {
                        @Override
                        public void onAnimationPause(Animator animation) {
                            Toast.makeText(TestActivityCompactActivity.this, "暂停", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAnimationResume(Animator animation) {
                            Toast.makeText(TestActivityCompactActivity.this, "恢复", Toast.LENGTH_SHORT).show();
                        }
                    });
                    oa.start();
                }

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != oa){
                    if(Build.VERSION.SDK_INT >= 19){
                        oa.pause();
                    }
                }
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != oa){
                    if(Build.VERSION.SDK_INT >= 19){
                        oa.resume();
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != oa){
                    oa.cancel();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_activity_compact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //使用兼容包关闭
//        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
//        overridePendingTransition(0, R.transition.change_image_transform);
    }
}
