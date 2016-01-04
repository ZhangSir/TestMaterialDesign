package com.zs.it.testmaterialdesign;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewPager;

    private String[] mTitle = new String[20];
    private String[] mData = new String[20];

    private MyAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        tablayout = (TabLayout) this.findViewById(R.id.tablayout);
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.mipmap.ic_brightness_medium_white_36dp);
        ab.setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i < mTitle.length; i++){
            mTitle[i] = "title" + i;
            mData[i] = "data" + i;
        }

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mAdapter = new MyAdapter();

        tablayout.setTabsFromPagerAdapter(mAdapter);

        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(tablayout);

        viewPager.addOnPageChangeListener(listener);

        viewPager.setAdapter(mAdapter);

        //这个方法是addOnPageChangeListener和setOnTabSelectedListener的封装
//        tablayout.setupWithViewPager(viewPager);

    }


    class MyAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }


        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            TextView tv = new TextView(TabLayoutActivity.this);
            tv.setTextSize(30.f);
            tv.setText(mData[position]);
            ((ViewPager) container).addView(tv);
            return tv;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_settings:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
