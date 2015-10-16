package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.iec.dwx.timer.Adapters.WishDetailsPagerAdapter;
import com.iec.dwx.timer.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/16 0016.
 */
public class MyWishDetails extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    private final String TAG = MyWishDetails.class.getSimpleName();
    private ViewPager viewPager=null;
    private WishDetailsPagerAdapter madapter=null;
    private List<View> viewList=new ArrayList<View>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager= (ViewPager) findViewById(R.id.detail_viewPager);
        View view1=LayoutInflater.from(this).inflate(R.layout.detail_textview,null);
        View view2=LayoutInflater.from(this).inflate(R.layout.detail_textview,null);
        viewList.add(view1);
        viewList.add(view2);
        madapter=new WishDetailsPagerAdapter(viewList);
        viewPager.setAdapter(madapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((Toolbar)findViewById(R.id.toolbar_my_wishes_details)).inflateMenu(R.menu.menu_wishes_details);
        ((Toolbar)findViewById(R.id.toolbar_my_wishes_details)).setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_my_wishes_detail_share:
                Toast.makeText(this,"Shared",Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_my_wishes_detail_delete:
                Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }

    @Override
    protected int getEdgeSize() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_my_wishes_detail;
    }

}
