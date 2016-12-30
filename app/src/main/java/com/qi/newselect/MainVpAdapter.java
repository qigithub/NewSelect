package com.qi.newselect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import com.qi.newselect.factory.FragmentFactory;

/**
 * Created by dongqi on 2016/11/28.
 */
public class MainVpAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainVpAdapter";
    List<String> titleList;

    public MainVpAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = (titleList);
    }

    @Override
    public int getCount() {
        return 1;
    }


    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null ) {
            return titleList.get(position) == null ? "": titleList.get(position) ;
        }else {
            Log.e(TAG, "getPageTitle: titleList is null");
            return "";
        }

    }
}
