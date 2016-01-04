package com.zs.it.testmaterialdesign;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextInputLayout tilAccount, tilPassword;
    private EditText etAccount, etPassword;

    private Button btn1, btn5;

    private FloatingActionButton fabtn, fabtn2, fab, fab2;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if(Build.VERSION.SDK_INT >= 21){
//            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)this.findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) this.findViewById(R.id.navigationview);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.mipmap.ic_brightness_medium_white_36dp);
        ab.setDisplayHomeAsUpEnabled(true);

        //设置actionBar返回按钮跟随DrawerLayout的侧滑状态改变
        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        abdt.syncState();
        drawerLayout.setDrawerListener(abdt);

        setupDrawerContent(navigationView);

        fabtn = (FloatingActionButton) this.findViewById(R.id.fabtn);

        fabtn2 = (FloatingActionButton) this.findViewById(R.id.fabtn2);

        fab = (FloatingActionButton) this.findViewById(R.id.fab);

        fab2 = (FloatingActionButton)this.findViewById(R.id.fab2);

        coordinatorLayout = (CoordinatorLayout) this.findViewById(R.id.coordinatorlayout);

        tilAccount = (TextInputLayout) this.findViewById(R.id.tilayout_account);
        etAccount = tilAccount.getEditText();
        tilPassword = (TextInputLayout) this.findViewById(R.id.tilayout_password);
        etPassword = tilPassword.getEditText();

        btn1 = (Button) this.findViewById(R.id.btn1);

//        RippleDrawable.createRipple(btn1, getColor(android.R.color.holo_red_dark));


        btn5 = (Button) this.findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent();
                it.setClass(MainActivity.this, TestAnimationActivity.class);
                if(Build.VERSION.SDK_INT >= 21){
                    startActivity(it,
                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                }else{
                    startActivity(it);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return false;
            }
        });


        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tilAccount.getError())) {
                    tilAccount.setError("账户格式不正确");
                    tilPassword.setError("密码有误");
                } else {
                    tilAccount.setError(null);
                    tilPassword.setError(null);
                }

                Snackbar snackbar = Snackbar.make(v, "哈哈哈", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("test", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "嘎嘎嘎", Toast.LENGTH_SHORT).show();
                    }
                });
                snackbar.show();
            }
        });

        fabtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "test snackbar", Snackbar.LENGTH_LONG);
                snackbar.setAction("test", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "snackbar action", Toast.LENGTH_SHORT).show();
                    }
                });
                snackbar.getView().setBackgroundResource(android.R.color.holo_orange_light);
                snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_purple));
                snackbar.show();

                Intent it = new Intent();
                it.setClass(MainActivity.this, TabLayoutActivity.class);
                startActivity(it);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(MainActivity.this, CoordinaterLayoutActivity.class);
                startActivity(it);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(MainActivity.this, CollapsingToolbarLayoutActivity.class);
                startActivity(it);
            }
        });

        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.action_list,
                android.R.layout.simple_spinner_dropdown_item);
        /**
         * 在这里配合Fragment，实现不同的页面导航
         */
        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
//                Fragment newFragment = null;
//                switch (position) {
//                    case 0:
//                        newFragment = new Fragment1();
//                        break;
//                    case 1:
//                        newFragment = new Fragment2();
//                        break;
//                    case 2:
//                        newFragment = new Fragment3();
//                        break;
//                    default:
//                        break;
//                }
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, newFragment, strings[position])
//                        .commit();
                return true;
            }
        };

        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);//导航模式必须设为NAVIGATION_MODE_LIST
        ab.setListNavigationCallbacks(mSpinnerAdapter,
                mOnNavigationListener);


        setOverflowShowingAlways();
    }

    /**
     * Google认为隐藏在overflow中的Action按钮都应该只显示文字;
     * overflow中的Action按钮应不应该显示图标，是由MenuBuilder这个类的setOptionalIconsVisible方法来决定的，
     * 如果我们在overflow被展开的时候给这个方法传入true，那么里面的每一个Action按钮对应的图标就都会显示出来了。
     * 调用的方法当然仍然是用反射了
     * @param featureId
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener()
                {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //搜索视窗，因为showAsAction="ifRoom"，所以图三中出现了搜索按钮
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchItem.expandActionView();//搜索框默认展开
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setIconified(false);
//        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);//显示提交搜索按钮

        setSearchView(searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            /**
             * Called when a menu item with
             * is expanded.
             *
             * @param item Item that was expanded
             * @return true if the item should expand, false if expansion should be suppressed.
             */
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "展开", Toast.LENGTH_SHORT).show();
                return true;
            }

            /**
             * Called when a menu item with
             * is collapsed.
             *
             * @param item Item that was collapsed
             * @return true if the item should collapse, false if collapsing should be suppressed.
             */
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "收起", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

//分享视窗，因为showAsAction="never"，所以只能在溢出菜单中才看见到
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_share));
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        mShareActionProvider.setShareIntent(shareIntent);

//设置视窗，MyActionProvider就是我们自定义的ActionProvider
        MyActionProvider myActionProvider = (MyActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.menu_setting));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_setting:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_theme:
                Toast.makeText(this, "主题", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_system:
                Toast.makeText(this, "系统设置", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_account://第二种实现自定义ActionProvider的方式，见MyActionProvider2
                Toast.makeText(this, "账户", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_more://第一种实现自定义ActionProvider的方式，见MyActionProvider
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_help:
                Toast.makeText(this, "帮助", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * 设置SearchView，这里通过id映射获取searchView中的各个组件，并为其设置样式
     * @param mSearchView
     */
    private void setSearchView(SearchView mSearchView) {
        final float density = getResources().getDisplayMetrics().density;
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        final int closeImgId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
        ImageView closeImg = (ImageView) mSearchView.findViewById(closeImgId);
        if (closeImg != null) {
            LinearLayout.LayoutParams paramsImg = (LinearLayout.LayoutParams) closeImg.getLayoutParams();
            paramsImg.topMargin = (int) (-2 * density);
            closeImg.setImageResource(android.R.drawable.ic_delete);
            closeImg.setLayoutParams(paramsImg);
        }
        final int editViewId = getResources().getIdentifier("search_src_text", "id", getPackageName());
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) mSearchView.findViewById(editViewId);
        if (mEdit != null) {
            mEdit.setHintTextColor(getResources().getColor(android.R.color.holo_blue_light));
            mEdit.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            mEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            mEdit.setHint("请输入关键字");
        }
        LinearLayout rootView = (LinearLayout) mSearchView.findViewById(R.id.search_bar);
        rootView.setBackgroundResource(android.R.color.holo_orange_dark);
        rootView.setClickable(true);
        LinearLayout editLayout = (LinearLayout) mSearchView.findViewById(R.id.search_plate);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editLayout.getLayoutParams();
        LinearLayout tipLayout = (LinearLayout) mSearchView.findViewById(R.id.search_edit_frame);
        LinearLayout.LayoutParams tipParams = (LinearLayout.LayoutParams) tipLayout.getLayoutParams();
        tipParams.leftMargin = 0;
        tipParams.rightMargin = 0;
        tipLayout.setLayoutParams(tipParams);

        ImageView icTip = (ImageView) mSearchView.findViewById(R.id.search_mag_icon);
        icTip.setImageResource(android.R.drawable.ic_menu_search);
//        icTip.setVisibility(View.GONE);

        ImageView icGo = (ImageView) mSearchView.findViewById(R.id.search_go_btn);
        icGo.setImageResource(android.R.drawable.ic_media_play);
//        icGo.setVisibility(View.VISIBLE);

        params.topMargin = (int) (4 * density);
        editLayout.setLayoutParams(params);

//        mSearchView.setSubmitButtonEnabled(false);

    }


    /**
     * 如果手机没有物理Menu键的话，overflow按钮就可以显示，如果有物理Menu键的话，overflow按钮就不会显示出来;
     * 在ViewConfiguration这个类中有一个叫做sHasPermanentMenuKey的静态变量，系统就是根据这个变量的值来判断手机有没有物理Menu键的。
     * 当然这是一个内部变量，我们无法直接访问它，但是可以通过反射的方式修改它的值，让它永远为false就可以了
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
