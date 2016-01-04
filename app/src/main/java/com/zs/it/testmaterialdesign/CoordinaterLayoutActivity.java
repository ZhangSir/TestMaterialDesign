package com.zs.it.testmaterialdesign;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CoordinaterLayoutActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<String> listStr;

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if(Build.VERSION.SDK_INT >= 21){
//            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinater_layout);

        this.initData();

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerview);
        /*设置布局管理器
            LinearLayoutManager 现行管理器，支持横向、纵向。
            GridLayoutManager 网格布局管理器
            StaggeredGridLayoutManager 瀑布就式布局管理器*/
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));

        /*添加增减数据时的动画效果*/
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter = new MyAdapter());

    }

    private void initData(){
        listStr = new ArrayList<String>();
        for (int i = 0; i < 50; i++){
            listStr.add("" + i);
        }
    }

    /**
     * 新增一条数据
     */
    private void addData(){
        int position = listStr.size()/2;
        listStr.add(position, "ADD");
        mAdapter.notifyItemInserted(position);//更新显示列表
    }

    /**
     * 删除一条数据
     */
    private void deleteData(){
        int position = listStr.size()/2;
        listStr.remove(position);
        mAdapter.notifyItemRemoved(position);//更新显示列表
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coordinater_layout_activity, menu);
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
        }else if(id == android.R.id.home){
            this.finish();
            return true;
        }else if(id == R.id.action_add){
            this.addData();
            return true;
        }else if(id == R.id.action_delete){
            this.deleteData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p/>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p/>
         * The new ViewHolder will be used to display items of the adapter using
         * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(
                    LayoutInflater.from(CoordinaterLayoutActivity.this)
                            .inflate(R.layout.layout_simple_item, parent, false));
            return holder;
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p/>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p/>
         * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle effcient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tvNum.setText(listStr.get(position));

            int height = 100;
            if(position%5 == 1){
                height = 200;
            }else if(position%5 == 2){
                height = 100;
            }else if(position%5 == 3){
                height = 250;
            }else if(position%5 == 4){
                height = 150;
            }else if(position%5 == 0){
                height = 180;
            }
            holder.tvNum.setHeight(height);
        }

        /**
         * Returns the total number of items in the data set hold by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return listStr.size();
        }



        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
            TextView tvNum;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvNum = (TextView) itemView.findViewById(R.id.tv_simple_item);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);

            }

            /**
             * Called when a view has been clicked and held.
             *
             * @param v The view that was clicked and held.
             * @return true if the callback consumed the long click, false otherwise.
             */
            @Override
            public boolean onLongClick(View v) {
                Snackbar sb = Snackbar.make(v, "长按" + mRecyclerView.getChildLayoutPosition(v), Snackbar.LENGTH_LONG);
                sb.setAction("action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CoordinaterLayoutActivity.this, "action", Toast.LENGTH_SHORT).show();
                    }
                });
                sb.show();
                return false;
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
            Toast.makeText(CoordinaterLayoutActivity.this, "点击" + mRecyclerView.getChildLayoutPosition(v), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
//        if(Build.VERSION.SDK_INT >= 21){
//            //如果需要逆转该过渡动画，使用Activity.finishAfterTransition()方法代替Activity.finish()
//            this.finishAfterTransition();
//        }else{
//            super.onBackPressed();
//        }
        //使用兼容包关闭
        ActivityCompat.finishAfterTransition(this);
    }
}
