package com.zs.it.testmaterialdesign;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

public class ActiveSharedTransitionActivity extends AppCompatActivity {

    private ImageView iv1, iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if(Build.VERSION.SDK_INT >= 21){
//            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_shared_transition);

        iv1 = (ImageView) this.findViewById(R.id.iv_t1);
        iv2 = (ImageView) this.findViewById(R.id.iv_t2);

        if(Build.VERSION.SDK_INT >= 21){
            //动态设置共享视图
            iv1.setTransitionName(getString(R.string.transition_name5));
            iv2.setTransitionName(getString(R.string.transition_name6));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_active_shared_transition, menu);
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
        if(Build.VERSION.SDK_INT >= 21){
            //如果需要逆转该过渡动画，使用Activity.finishAfterTransition()方法代替Activity.finish()
            this.finishAfterTransition();
        }else{
            super.onBackPressed();
        }
    }
}
