package com.qlckh.chunlvv.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.fragment.PointExchangeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/9/10 16:25
 * Desc:
 */
public class PointExchangeActivity extends BaseActivity {
    @BindView(R.id.message_tabs)
    TabLayout messageTabs;
    @BindView(R.id.message_vg)
    ViewPager messageVg;
    private String[] titles = {"待处理","已发货","已完成"};
    private List<Fragment> fragments =new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_point_exchange;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {

        setTitle("兑换记录");
        goBack();
    }

    @Override
    public void initDate() {
        fragments.add(PointExchangeFragment.getInstance(0));
        fragments.add(PointExchangeFragment.getInstance(1));
        fragments.add(PointExchangeFragment.getInstance(2));
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        messageVg.setAdapter(adapter);
        messageTabs.setupWithViewPager(messageVg);
        messageVg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PointExchangeFragment fragment= (PointExchangeFragment) fragments.get(position);
                fragment.onReshow(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }

    class TabAdapter extends FragmentPagerAdapter {


        private TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
