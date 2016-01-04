package com.zs.it.testmaterialdesign;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 自定义一个视窗操作器，通过重写hasSubMenu返回true和重写onPrepareSubMenu添加子菜单项实现
 * Created by zhangshuo on 2015/12/7.
 */
public class MyActionProvider2 extends ActionProvider {


    private Context context;

    public MyActionProvider2(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }


    @Override
    public View onCreateActionView() {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add("sub menu 1").setIcon(R.mipmap.ic_brightness_medium_white_36dp)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "sub menu 1", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
        subMenu.add("sub menu 2").setIcon(R.mipmap.ic_brightness_medium_white_36dp)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(context, "sub menu 2", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }
}
