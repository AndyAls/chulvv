package com.qlckh.chunlvv.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.impl.LoginPresenterImpl;
import com.qlckh.chunlvv.presenter.LoginPresenter;
import com.qlckh.chunlvv.user.UseDo;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/5/14 11:00
 * Desc:   启动界面
 */
public class SplashActivity extends BaseMvpActivity<LoginPresenter> implements LoginPresenter.LoginView {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_logo)
    TextView tvLogo;

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        header.setVisibility(View.GONE);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        ImmersionBar.with(this).transparentBar().init();

        showAnim();
    }

    private void showAnim() {

        GlideUtil.displayCircleImg(this, R.mipmap.app_icon, ivLogo);
        // 以view中心为缩放点，由初始状态放大两倍
        ScaleAnimation animation = new ScaleAnimation(
                0.2f, 1.0f, 0.2f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setRepeatCount(1);
        animation.setDuration(2000);
        tvLogo.startAnimation(animation);
        RotateAnimation rotateAnimation = new RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setDuration(2000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(animation);
        set.addAnimation(rotateAnimation);
        ivLogo.startAnimation(set);
    }

    @Override
    public void initDate() {

        if (UserConfig.isLogin()) {
            if (UserConfig.getType() == 0) {
                new Handler().postDelayed(this::toMian,2000);

            } else {
            mPresenter.login(UserConfig.getUserName(), UserConfig.getPwd(), UserConfig.getType());
            }
        } else {
            toLogin();
        }
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
        toLogin();
    }

    @Override
    public void release() {
//        RxHttpUtils.cancelAllRequest();

    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void dissmissLoading() {
    }

    @Override
    public void getUser(UseDo info) {
        if (info.getStatus() == 1) {

            if (UserConfig.getType() == 0) {
                toMian();
            } else {
                toXmain();
            }
            UserConfig.userInfo = info.getData();
            UserConfig.savaLogin(true);
        } else {
            showShort(info.getMsg());
            toLogin();
            UserConfig.savaLogin(false);


        }

    }

    private void toXmain() {
        startActivity(new Intent(this, XMianActivity.class));
        finish();
        overridePendingTransition(0, 0);
    }

    private void toMian() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(0, 0);
    }

    private void toLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        overridePendingTransition(0, 0);
    }
}
