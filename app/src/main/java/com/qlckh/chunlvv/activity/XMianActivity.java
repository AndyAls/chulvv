package com.qlckh.chunlvv.activity;

import android.os.Build;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.qlckh.chunlvv.App;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.common.LocationService;
import com.qlckh.chunlvv.fragment.HomePageFragment;
import com.qlckh.chunlvv.fragment.IntegralMallFragment;
import com.qlckh.chunlvv.fragment.MyFragment;
import com.qlckh.chunlvv.fragment.RecoveryOrderFragment;

import butterknife.BindView;


/**
 * @author Andy
 * @date 2018/8/15 16:40
 * Desc:
 */
public class XMianActivity extends BaseActivity implements View.OnClickListener {
    private Fragment mTab1;
    private Fragment mTab2;
    private Fragment mTab3;
    private Fragment mTab4;
    //底部Tab
    @BindView(R.id.tab_one_ll)
    LinearLayout tab_one_ll;
    @BindView(R.id.tab_two_ll)
    LinearLayout tab_two_ll;
    @BindView(R.id.tab_three_ll)
    LinearLayout tab_three_ll;
    @BindView(R.id.tab_four_ll)
    LinearLayout tab_four_ll;
    //底部的4个图片
    @BindView(R.id.tab_one_img)
    ImageView tab_one_img;
    @BindView(R.id.tab_two_img)
    ImageView tab_two_img;
    @BindView(R.id.tab_three_img)
    ImageView tab_three_img;
    @BindView(R.id.tab_four_img)
    ImageView tab_four_img;
    //底部的4个文字标题
    @BindView(R.id.tab_one_tv)
    TextView tab_one_tv;
    @BindView(R.id.tab_two_tv)
    TextView tab_two_tv;
    @BindView(R.id.tab_three_tv)
    TextView tab_three_tv;
    @BindView(R.id.tab_four_tv)
    TextView tab_four_tv;
    private LocationService locationService;

    @Override
    protected int getContentView() {
        return R.layout.activity_xmain;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        header.setVisibility(View.GONE);
        initEvent(); //绑定事件
        setSelect(0); //默认选择第一个
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        immersionBar
                .fitsSystemWindows(true)
                .statusBarColor(R.color.comm_header_color)
                .init();
    }

    /**
     * 为控件添加触发事件（或数据）
     */
    private void initEvent() {
        tab_one_ll.setOnClickListener(this);
        tab_two_ll.setOnClickListener(this);
        tab_three_ll.setOnClickListener(this);
        tab_four_ll.setOnClickListener(this);

    }

    /**
     * 设置字体颜色和图片亮度
     * 设置内容区域
     */
    private void setSelect(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (i) {
            case 0:
                if (mTab1 == null) {
                    mTab1 = new HomePageFragment();
                    fragmentTransaction.add(R.id.main_fragment, mTab1);
                } else {
                    fragmentTransaction.show(mTab1);
                }
                tab_one_img.setImageResource(R.drawable.home);
                tab_one_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 1:
                if (mTab2 == null) {
                    mTab2 = new IntegralMallFragment();
                    fragmentTransaction.add(R.id.main_fragment, mTab2);
                } else {
                    fragmentTransaction.show(mTab2);
                }
                tab_two_img.setImageResource(R.drawable.gouwuche);
                tab_two_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 2:
                if (mTab3 == null) {
                    mTab3 = new RecoveryOrderFragment();
                    fragmentTransaction.add(R.id.main_fragment, mTab3);
                } else {
                    fragmentTransaction.show(mTab3);
                }
                tab_three_img.setImageResource(R.drawable.dingdan);
                tab_three_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            case 3:
                if (mTab4 == null) {
                    mTab4 = new MyFragment();
                    fragmentTransaction.add(R.id.main_fragment, mTab4);
                } else {
                    fragmentTransaction.show(mTab4);
                }
                tab_four_img.setImageResource(R.drawable.mine);
                tab_four_tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            default:
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mTab1 != null) {
            fragmentTransaction.hide(mTab1);
        }
        if (mTab2 != null) {
            fragmentTransaction.hide(mTab2);
        }
        if (mTab3 != null) {
            fragmentTransaction.hide(mTab3);
        }
        if (mTab4 != null) {
            fragmentTransaction.hide(mTab4);
        }
    }

    @Override
    public void onClick(View view) {
        resetImg();
        switch (view.getId()) {
            case R.id.tab_one_ll:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                setSelect(0);
                break;
            case R.id.tab_two_ll:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                setSelect(1);
                break;
            case R.id.tab_three_ll:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                setSelect(2);
                break;
            case R.id.tab_four_ll:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
                setSelect(3);
                break;
            default:
        }
    }

    /**
     * 将所有的图片、文字切换为默认状态
     */
    private void resetImg() {
        tab_one_img.setImageResource(R.drawable.homehui);
        tab_two_img.setImageResource(R.drawable.gouwuchehui);
        tab_three_img.setImageResource(R.drawable.dingdanhui);
        tab_four_img.setImageResource(R.drawable.minehui);

        tab_one_tv.setTextColor(getResources().getColor(R.color.font_three_color));
        tab_two_tv.setTextColor(getResources().getColor(R.color.font_three_color));
        tab_three_tv.setTextColor(getResources().getColor(R.color.font_three_color));
        tab_four_tv.setTextColor(getResources().getColor(R.color.font_three_color));
    }

    @Override
    public void initDate() {
        ibBack.setVisibility(View.GONE);
    }

    @Override
    protected void initSlidr() {


    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (System.currentTimeMillis() - exitTime > 2000) {
            showShort("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((App) getApplication()).locationService;
        locationService.registerListener(listener);
        locationService.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService.unregisterListener(listener);
    }

    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            String address = "无法定位到位置";
            if (bdLocation != null) {
               address =bdLocation.getCity();

            }
            if (isEmpty(address)) {
                ((HomePageFragment) mTab1).setLocationTv("定位失败");
                return;
            }
            ((HomePageFragment) mTab1).setLocationTv(address);
            locationService.stop();
        }
    };

}
