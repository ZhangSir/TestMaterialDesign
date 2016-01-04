package com.zs.it.testmaterialdesign;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 自定义一个视窗操作器，实现构造函数和onCreateActionView即可;通过此种方式创建的自定义ActionView，
 * 只有显示在ActionBar上（即不在OverFlow中）时才能响应自定义ImageView的点击事件；
 * （在OverFlow中响应的是menu本身的事件，而非menu中的我们自定义的ImageView的事件）
 * 且显示的PopupMenu的点击事件无法在Activity的onOptionsItemSelected（）中监听到；
 * Created by zhangshuo on 2015/12/7.
 */
public class MyActionProvider extends ActionProvider {


    private Context context;
    private LayoutInflater inflater;
    private View view;
    private ImageView button;

    PopupMenu mPopupMenu;

    public MyActionProvider(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.myactionprovider, null);
    }


    @Override
    public View onCreateActionView() {
        // TODO Auto-generated method stub
        button = (ImageView) view.findViewById(R.id.iv);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showPopup(v);
                Toast.makeText(context, "显示PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    /**
     * show the popup menu.
     *
     * @param v
     */
    private void showPopup(View v) {
        mPopupMenu = new PopupMenu(context, v);
        mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.popup_menu1:
                        Toast.makeText(context, "popup_menu1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.popup_menu2:
                        Toast.makeText(context, "popup_menu2", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }

        });
        MenuInflater inflater = mPopupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, mPopupMenu.getMenu());
        mPopupMenu.show();
    }
}
