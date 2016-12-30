package com.qi.newselect;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.melnykov.fab.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.qi.newselect.base.BaseActivity;
import com.qi.newselect.edit.EditAct;
import com.qi.newselect.top_frag.TopFragment;
import com.qi.newselect.utils.EventMsg;
import com.qi.newselect.utils.LogUtil;
import com.qi.newselect.utils.ToastUtil;
import com.qi.newselect.utils.UserUtils;
import com.qi.newselect.utils.Utils;
import com.qi.newselect.utils.ViewUtil;

public class MainActivity extends BaseActivity<IMain,MainPresenter> implements  IMain{

    private static final String TAG = "MainActivity";
    public static final int DRAWER_GRAVITY = Gravity.LEFT;

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    ActionBarDrawerToggle toggle ;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.mToolbar)
    Toolbar toolbar;
    @BindView(R.id.imgToolbarHead)
    ImageView imgToolbarHead;
    @BindView(R.id.nav_view)
    NavigationView nav_view;

    View navViewLayout;

    CircleImageView imgNavHead;

    TextView tvNavHeadName;

    MainVpAdapter mFragAdapter;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate_(@Nullable Bundle savedInstanceState) {
        super.onCreate_(savedInstanceState);
        EventBus.getDefault().register(this);
        initActionBar();
        initNavLayout();
        initTab();
        initViewPager();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.launchAct(getCtx(), EditAct.class);
            }
        });
    }

    private void initTab() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(ColorStateList.valueOf(
                ContextCompat.getColor(getApplicationContext(),R.color.colorActionTitleColor)));

    }


    private void setFabListView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        fab.attachToRecyclerView(recyclerView);
    }

    private void initNavLayout() {
        if (nav_view.getHeaderCount() > 0) {
            navViewLayout = nav_view.getHeaderView(0);
            imgNavHead = ViewUtil.$(navViewLayout,R.id.imgNavHead);
            Glide.with(getCtx()).load(UserUtils.getImg(getApplicationContext()))
                    .dontTransform()
                    .dontAnimate()
                    .error(R.mipmap.ic_user_def)
                    .into(imgNavHead);
            tvNavHeadName = ViewUtil.$(navViewLayout,R.id.tvNavHeadName);
            tvNavHeadName.setText(UserUtils.getUsername(getCtx()));
        }


        DrawerLayout.LayoutParams drawLp = (DrawerLayout.LayoutParams)nav_view.getLayoutParams();
        drawLp.gravity =DRAWER_GRAVITY;
        nav_view.setLayoutParams(drawLp);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_info:
                        Snackbar.make(nav_view,"个人信息", Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        closeDrawer();
                        break;

                }
                closeDrawer();
                return false;
            }
        });



    }

    private void initActionBar() {
        toggle = new ActionBarDrawerToggle(this, drawer,
                0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (Utils.isAndroidLOLLIPOP()) {
            toolbar.setElevation(25);
        }
        imgToolbarHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });
        LogUtil.d(TAG,""+UserUtils.getImg(getApplicationContext()));
        Glide.with(getCtx()).load(UserUtils.getImg(getApplicationContext()))
                .dontTransform()
                .dontAnimate()
                .error(R.mipmap.ic_user_def)
                .into(imgToolbarHead);
    }

    private void initViewPager() {
        mFragAdapter = new MainVpAdapter(getSupportFragmentManager());
        List<String> titleList = new ArrayList<>(5);
        titleList.add("排名");
        titleList.add("最新");
//        titleList.add(0,"");
        mFragAdapter.setTitleList(titleList);
        mViewPager.setAdapter(mFragAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = mFragAdapter.getItem(position);
                if (fragment instanceof TopFragment) {
                    setFabListView(((TopFragment)fragment).getRecycleView());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(getApplicationContext(),msg);
    }

    @Override
    public void showProgress(boolean cancel) {
        ViewUtil.createLoadingDialog(this,"加载中...",cancel);
    }

    @Override
    public void hideProgress() {
        ViewUtil.cancelLoadingDialog();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (drawer.isDrawerOpen(DRAWER_GRAVITY)) {
                    drawer.closeDrawer(DRAWER_GRAVITY);
                    return true;
                }
                exit();
                break;
        }
        return true;
    }
    private void exit() {
        if (isWaitingExit) {// 连续按两次返回键
            isWaitingExit = false;
            finish();
        } else {// 按一次返回键
            firstClickBackKey();
        }
    }
    boolean isWaitingExit;
    private void firstClickBackKey() {
        Snackbar.make(getRootView(R.id.mRootViewGroup),"再按退出", Snackbar.LENGTH_SHORT).show();
        isWaitingExit = true;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isWaitingExit = false;
            }
        }, 2000);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Subscribe
    public void onEventTopFragmentRecycle(EventMsg.TopFragmentMsg msg) {
        setFabListView(msg.getRecyclerView());
    }

    public void openDrawer() {
        if (!drawer.isDrawerOpen(DRAWER_GRAVITY)) {
            drawer.openDrawer(DRAWER_GRAVITY);
        }
    }

    public void closeDrawer() {
        if (drawer.isDrawerOpen(DRAWER_GRAVITY)) {
            drawer.closeDrawer(DRAWER_GRAVITY);
        }
    }



}