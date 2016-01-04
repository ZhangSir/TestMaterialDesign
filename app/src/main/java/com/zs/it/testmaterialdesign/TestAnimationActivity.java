package com.zs.it.testmaterialdesign;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

public class TestAnimationActivity extends AppCompatActivity {

    private ImageView ivPic, ivTransition1, ivTransition2, ivTransition4, ivTransition5, ivTransition6, ivCompact;
    private TextView tvTransition3;
    private Button btnControl, btnOne, btnMuch, btnActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if(Build.VERSION.SDK_INT >= 21){
//            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);

        ivPic = (ImageView) this.findViewById(R.id.iv_anim);
        ivTransition1 = (ImageView) this.findViewById(R.id.iv_transition1);
        ivTransition2 = (ImageView) this.findViewById(R.id.iv_transition2);
        tvTransition3 = (TextView) this.findViewById(R.id.tv_transition3);
        ivTransition4 = (ImageView) this.findViewById(R.id.iv_transition4);
        ivTransition5 = (ImageView) this.findViewById(R.id.iv_transition5);
        ivTransition6 = (ImageView) this.findViewById(R.id.iv_transition6);

        ivCompact = (ImageView) this.findViewById(R.id.iv_compact);

        btnControl = (Button) this.findViewById(R.id.btn_control);
        btnOne = (Button) this.findViewById(R.id.btn_one);
        btnMuch = (Button) this.findViewById(R.id.btn_much);
        btnActive = (Button) this.findViewById(R.id.btn_active);

        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 21){
                    if(ivPic.getVisibility() == View.VISIBLE){
                        //当前可视，执行隐藏

                        // get the center for the clipping circle
                        int cx = (ivPic.getLeft() + ivPic.getRight()) / 2;
                        int cy = (ivPic.getTop() + ivPic.getBottom()) / 2;

                        // get the initial radius for the clipping circle
                        int initialRadius = ivPic.getWidth();

                        // create the animation (the final radius is zero)
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(ivPic, cx, cy, initialRadius, 0);

                        // make the view invisible when the animation is done
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                ivPic.setVisibility(View.INVISIBLE);
                            }
                        });

                        // start the animation
                        anim.start();

                        btnControl.setText("显示");
                    }else{
                        //当前不可视，执行显示

                        // get the center for the clipping circle
                        int cx = (ivPic.getLeft() + ivPic.getRight()) / 2;
                        int cy = (ivPic.getTop() + ivPic.getBottom()) / 2;

                        // get the final radius for the clipping circle
                        int finalRadius = Math.max(ivPic.getWidth(), ivPic.getHeight());

                        // create the animator for this view (the start radius is zero)
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(ivPic, cx, cy, 0, finalRadius);

                        // make the view visible and start the animation
                        ivPic.setVisibility(View.VISIBLE);
                        anim.start();
                        btnControl.setText("隐藏");
                    }
                }

            }
        });


        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(TestAnimationActivity.this, CoordinaterLayoutActivity.class);
//                if(Build.VERSION.SDK_INT >= 21){
//                    // shareView: 需要共享的视图
//                    // shareName: 设置的android:transitionName=shareName
////                    如果不想使用transition可以设置options bundle为null。
////                    如果需要逆转该过渡动画，使用Activity.finishAfterTransition()方法代替Activity.finish()
//                    ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(TestAnimationActivity.this, ivTransition1, getString(R.string.transition_name1));
//                    startActivity(it, option.toBundle());
//                }else{
//                    startActivity(it);
//                }


                //兼容包的这种启动方式，只对5.x有效果，低版本的只是不报错，但没有动画效果
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        TestAnimationActivity.this, ivTransition1, getString(R.string.transition_name1));
                ActivityCompat.startActivity(TestAnimationActivity.this, new Intent(TestAnimationActivity.this, CoordinaterLayoutActivity.class),
                        options.toBundle());

                Toast.makeText(TestAnimationActivity.this, "一个共享视图", Toast.LENGTH_SHORT).show();
            }
        });

        btnMuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //多个共享视图
                Intent it = new Intent();
                it.setClass(TestAnimationActivity.this, MuchSharedTransitionActivity.class);
                if(Build.VERSION.SDK_INT >= 21){
                    // shareView: 需要共享的视图
                    // shareName: 设置的android:transitionName=shareName
//                    如果不想使用transition可以设置options bundle为null。
//                    如果需要逆转该过渡动画，使用Activity.finishAfterTransition()方法代替Activity.finish()
                    ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(TestAnimationActivity.this,
                            Pair.create((View)ivTransition2, getString(R.string.transition_name2)), Pair.create((View)tvTransition3, getString(R.string.transition_name3)), Pair.create((View)ivTransition4, getString(R.string.transition_name4)));
                    startActivity(it, option.toBundle());
                }else{
                    startActivity(it);
                }
                Toast.makeText(TestAnimationActivity.this, "多个共享视图", Toast.LENGTH_SHORT).show();
            }
        });

        btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态添加共享视图

                Intent it = new Intent();
                it.setClass(TestAnimationActivity.this, ActiveSharedTransitionActivity.class);
                if(Build.VERSION.SDK_INT >= 21){
                    ivTransition5.setTransitionName(getString(R.string.transition_name5));
                    ivTransition6.setTransitionName(getString(R.string.transition_name6));
                    // shareView: 需要共享的视图
                    // shareName: 设置的android:transitionName=shareName
//                    如果不想使用transition可以设置options bundle为null。
//                    如果需要逆转该过渡动画，使用Activity.finishAfterTransition()方法代替Activity.finish()
                    ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(TestAnimationActivity.this,
                            Pair.create((View)ivTransition5, getString(R.string.transition_name5)),  Pair.create((View)ivTransition6, getString(R.string.transition_name6)));
                    startActivity(it, option.toBundle());
                }else{
                    startActivity(it);
                }
                Toast.makeText(TestAnimationActivity.this, "动态添加共享视图", Toast.LENGTH_SHORT).show();
            }
        });


        ivCompact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                ActivityOptionsCompat是一个静态类，提供了为数不多的几个方法，下面我们来罗列一下：   
//                ActivityOptionsCompat.makeCustomAnimation(Context context, int enterResId, int exitResId)   
//                      makeCustomAnimation在实现效果上和overridePendingTransition也是相同的 
//                ActivityOptionsCompat.makeScaleUpAnimation(View source,int startX, int startY, int startWidth, int startHeight)  
//                ActivityOptionsCompat.makeThumbnailScaleUpAnimation(View source,Bitmap thumbnail, int startX, int startY)   
//                ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName)  
//                ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity,Pair<View, String>… sharedElements)  

                //让新的Activity从一个小的范围扩大到全屏
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeScaleUpAnimation(v, //The View that the new activity is animating from
                                (int)v.getWidth()/2, (int)v.getHeight()/2, //拉伸开始的坐标
                                0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
                Intent intent = new Intent(TestAnimationActivity.this, TestActivityCompactActivity.class);
                ActivityCompat.startActivity(TestAnimationActivity.this, intent, options.toBundle());

                Toast.makeText(TestAnimationActivity.this, "支持5.X以下的兼容包动画", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_animation, menu);
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
}
