package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.TestActivity;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.impl.LoginPresenterImpl;
import com.qlckh.chunlvv.presenter.LoginPresenter;
import com.qlckh.chunlvv.qidian.HomeSysActivity;
import com.qlckh.chunlvv.qidian.N5ScanActivity;
import com.qlckh.chunlvv.qidian.PhoneScanActivity;
import com.qlckh.chunlvv.user.UseDo;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.NetworkUtils;
import com.qlckh.chunlvv.utils.PhoneUtil;
import com.qlckh.chunlvv.view.BottomDialog;
import com.qlckh.chunlvv.view.LoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/15 16:53
 * Desc:
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginPresenter.LoginView {
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.ll_role)
    LinearLayout llRole;
    @BindView(R.id.tv_role)
    TextView tvRole;

    private int userType = 0;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        header.setVisibility(View.GONE);
        ImmersionBar.with(this).transparentBar()
                .keyboardEnable(true)
                .init();


//        XXPermissions.with(this)
//                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
//                .permission(Permission.Group.STORAGE, Permission.Group.CAMERA,Permission.Group.CONTACTS) //不指定权限则自动获取清单中的危险权限
//                .request(new OnPermission() {
//
//                    @Override
//                    public void hasPermission(List<String> granted, boolean isAll) {
//
//                    }
//
//                    @Override
//                    public void noPermission(List<String> denied, boolean quick) {
//
//                    }
//                });
    }

    @Override
    public void initDate() {
//        requestPermissions();
    }

    private void requestPermissions() {
        if (!XXPermissions.isHasPermission(this, Permission.Group.STORAGE, Permission.Group.CAMERA, Permission.Group.LOCATION)) {
            XXPermissions.with(this)
                    .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    .permission(Permission.Group.STORAGE, Permission.Group.CAMERA, Permission.Group.LOCATION)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {

                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            XXPermissions.gotoPermissionSettings(LoginActivity.this);
                        }
                    });
        }
    }

    /**
     * 登录接口失败
     *
     * @param msg 错误信息
     */
    @Override
    public void showError(String msg) {
        showShort(msg);

    }

    @Override
    public void release() {
//        RxHttpUtils.cancelAllRequest();

    }

    @Override
    public void showLoading() {

        LoadingView.showLoading(this, "", false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    /**
     * 登录接口成功
     *
     * @param info
     */
    @Override
    public void getUser(UseDo info) {
        String msg = info.getMsg();
        int status = info.getStatus();
        //登录成功
        if (status == 1) {
            UserConfig.savaLogin(true);
            UserConfig.savaUserName(phoneEdit.getText().toString().trim());
            UserConfig.savaType(userType);
            UserConfig.savaPwd(passwordEdit.getText().toString().trim());
            UserConfig.savaUserid(info.getData().getId());
            UserConfig.userInfo = info.getData();
            if (userType == 0) {
                //入库
                if (PhoneUtil.isN5s()){
                    startActivity(new Intent(this, N5ScanActivity.class));
                }else {
                    startActivity(new Intent(this, PhoneScanActivity.class));
                }
                finish();
                overridePendingTransition(0, 0);
//                toMian();
            } else {
                toXmain();
            }
        } else {
            showShort("请确定密码账号和用户角色选择是否正确");
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
    }

    @OnClick({R.id.ll_role, R.id.login_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_role:
//                showRolePup();
                break;
            case R.id.login_button:
//                test();
                login();
                break;
            case R.id.bt1:
                startActivity(new Intent(this,BluetoothDemo1.class));
                break;
            case R.id.bt2:
                startActivity(new Intent(this,BluetoothDemo2.class));
                break;
            case R.id.bt3:
                startActivity(new Intent(this,BluetoothDemo3.class));
                break;
            default:
        }
    }


    private void showRolePup() {
        final BottomDialog dialog = new BottomDialog.Builder(this).create();
        dialog.show();
        dialog.setonDialogItemClickListener((position, mDatas) -> {
            tvRole.setText(mDatas[position]);
            dialog.dismiss();
//            "采集员","巡检","普通用户"
            switch (position) {
                case 0:
                    userType = 0;
                    break;
                case 1:
                    userType = 1;
                    break;
                case 2:
                    userType = 2;
                    break;
                default:
            }
        });

    }

    /**
     * 登录
     */
    private void login() {

        String name = phoneEdit.getText().toString().trim();
        String pwd = passwordEdit.getText().toString().trim();
        if (checkData()) {
//            if (userType == 0) {
//                if (!PhoneUtil.isN5s()) {
//                    showShort("请选择正确的角色,采集员只能终端进行登录");
//                    return;
//                }
//            }
            // TODO: 2018/9/12 采集员只能终端登录
            mPresenter.login(name, pwd, userType);
        }

    }

    private boolean checkData() {
        if (!NetworkUtils.isNetWorkAvailable()) {
            showShort("网络不可用,请设置网络");
            return false;
        }
        if (isEmpty(phoneEdit.getText().toString().trim())) {
            showShort("请输入用户名");
            return false;
        }
        if (isEmpty(passwordEdit.getText().toString().trim())) {
            showShort("请输入密码");
            return false;
        }
        return true;
    }

}
