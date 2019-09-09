package com.qlckh.chunlvv.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/8/20 15:35
 * Desc:
 */
public class RecoveryOrderFragment extends BaseFragment {
    @BindView(R.id.message_tabs)
    TabLayout messageTabs;
    @BindView(R.id.message_vg)
    ViewPager messageVg;

    private String[] titles = {"待处理","处理中","已处理","已完成"};
    private List<Fragment> fragments =new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.fragment_recovery_order;
    }

    @Override
    public void initView() {
        fragments.add(OrderFragment.getInstance(0));
        fragments.add(OrderFragment.getInstance(1));
        fragments.add(OrderFragment.getInstance(2));
        fragments.add(OrderFragment.getInstance(3));
        TabAdapter adapter = new TabAdapter(getFragmentManager());
        messageVg.setAdapter(adapter);
        messageTabs.setupWithViewPager(messageVg);
        messageVg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               OrderFragment fragment= (OrderFragment) fragments.get(position);
               fragment.onReshow(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    @Override
    public void initDate() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }

    class TabAdapter extends FragmentPagerAdapter{


        public TabAdapter(FragmentManager fm) {
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
