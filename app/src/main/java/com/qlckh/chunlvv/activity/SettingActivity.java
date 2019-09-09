package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.MyTask;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.impl.MainPresenterImpl;
import com.qlckh.chunlvv.presenter.MainPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.user.UserRespDao;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.utils.ImgUtil;
import com.qlckh.chunlvv.utils.SpUtils;
import com.qlckh.chunlvv.view.MainView;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/20 1:54
 * Desc:
 */
public class SettingActivity extends BaseMvpActivity<MainPresenter> implements MainView {
    @BindView(R.id.offline_button)
    Button offlineButton;
    @BindView(R.id.bt_set_font)
    RelativeLayout btSetFont;
    @BindView(R.id.headImg)
    ImageView headImg;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.userinfoClick)
    RelativeLayout userinfoClick;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.myGroup)
    RelativeLayout myGroup;
    @BindView(R.id.myGroupLine)
    View myGroupLine;
    @BindView(R.id.tihuoquan)
    RelativeLayout tihuoquan;
    @BindView(R.id.sallerCenter)
    TextView sallerCenter;
    @BindView(R.id.switchUseBalance)
    SwitchCompat switchUseBalance;


    private String imgPath = "";
    private boolean isDone = true;
    private boolean isN5=false;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("设置");
        goBack();
        setUser();
        boolean n5 = SpUtils.getBooleanParam(this, "N5", false);
        switchUseBalance.setChecked(n5);
        switchUseBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isN5 = isChecked;
                SpUtils.putParam(SettingActivity.this,"N5",isN5);
            }
        });
    }

    private void setUser() {
        UserRespDao.UserResp userInfo = UserConfig.getUserResp();
        GlideUtil.displayCircleImg(this, ApiService.IMG_URL + userInfo.getImg(), headImg);
        username.setText(userInfo.getFullname());
        tvPhone.setText(userInfo.getPhone());
        tvScore.setText(MessageFormat.format("积分: {0}", userInfo.getJifen()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getPersonInfo(UserConfig.getUserid());
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

    @OnClick({R.id.bt_set_font, R.id.offline_button, R.id.myGroup, R.id.tihuoquan, R.id.headImg, R.id.rl_qrcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_set_font:
                startActivity(new Intent(this, SetingFontActivity.class));
                break;
            case R.id.offline_button:
                UserConfig.reset();
                UserConfig.userInfo = null;
                UserConfig.userResp = null;
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.myGroup:
                startActivity(new Intent(this, MotifyPwdActivity.class));
                break;
            case R.id.tihuoquan:
                startActivity(new Intent(this, MotifyPhoneActivity.class));
                break;
            case R.id.headImg:
                imagePick();
                break;
            case R.id.rl_qrcode:
                startActivity(new Intent(this, QrCodeActicvity.class));
                break;
            default:
        }
    }

    private ImagePicker imagePicker = ImagePicker.getInstance();

    private void imagePick() {
        imagePicker.setImageLoader(GlideImageLoader.getInstance());
        imagePicker.setShowCamera(true);
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setOutPutX(300);
        imagePicker.setOutPutY(300);
        imagePicker.setFocusHeight(600);
        imagePicker.setFocusWidth(600);
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                List<ImageItem> items = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem imageItem = items.get(0);
                File compress = ImgUtil.compress(new File(imageItem.path), 50, 2100000);
                doTask(compress);
                GlideUtil.displayCircleImg(this, imageItem.path, headImg);
            } else {
                showError("没有数据");
            }
        }
    }

    private void doTask(File compress) {

        MyTask task = new MyTask(this, imgPath, new Handler(msg -> {

            if (msg.what == MyTask.COMPRESS_SUCCESS) {
                isDone = true;
                imgPath = (String) msg.obj;
                RxHttpUtils.createApi(ApiService.class)
                        .motifyHead(UserConfig.getUserid(), imgPath)
                        .compose(Transformer.switchSchedulers())
                        .subscribe(new CommonObserver<CommonDao>() {
                            @Override
                            protected void onError(String errorMsg) {
                                showError(errorMsg);
                            }

                            @Override
                            protected void onSuccess(CommonDao commonDao) {
                                showShort("修改头像成功");
                            }
                        });
            } else {
                isDone = false;
            }
            return false;
        }));
        task.execute(compress);

    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void onSuccess(BannerDao dao) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
