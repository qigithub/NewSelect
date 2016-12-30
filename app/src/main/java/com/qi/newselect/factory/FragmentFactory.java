package com.qi.newselect.factory;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import com.qi.newselect.top_frag.TopFragment;

/**
 * Created by dongqi on 2016/11/28.
 */
public class FragmentFactory {
    public static final int TAB_HOME_TOP=0;
    /**
     * 记录所有的fragment，防止重复创建
     */
    public static Map<Integer, Fragment> mFragmentMap;

    /**
     * 采用工厂类进行创建Fragment，便于扩展，已经创建的Fragment不再创建
     */
    public static Fragment createFragment(int index) {
        if (mFragmentMap == null)
            mFragmentMap = new HashMap<Integer, Fragment>();
        Fragment fragment = mFragmentMap.get(index);

        if (fragment == null) {
            switch (index) {
                case TAB_HOME_TOP:
                    fragment = new TopFragment();
                    break;

//                case TAB_Notice:
//                    fragment = new FragmentGoodsManage();
//                    break;
            }
            mFragmentMap.put(index, fragment);
        }
        return fragment;
    }



    public static void removeFragment(int index) {
        try {
            mFragmentMap.remove(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeAll() {
        try {
            if (mFragmentMap != null) {
                mFragmentMap.clear();
                mFragmentMap = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
