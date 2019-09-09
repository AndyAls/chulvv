package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.GlideApp;
import com.qlckh.chunlvv.common.MyTask;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.utils.IntentUtil;
import com.qlckh.chunlvv.impl.ScrapPresenterImpl;
import com.qlckh.chunlvv.presenter.CommView;
import com.qlckh.chunlvv.presenter.ScrapPresenter;
import com.qlckh.chunlvv.preview.ImgInfo;
import com.qlckh.chunlvv.preview.PrePictureActivity;
import com.qlckh.chunlvv.utils.ImgUtil;
import com.qlckh.chunlvv.view.LoadingView;
import com.qlckh.chunlvv.view.PicGridView;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/22 13:47
 * Desc:
 */
public class ScrapActivity extends BaseMvpActivity<ScrapPresenter> implements CommView {
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.picItems)
    PicGridView picModify;
    private String userAddress;
    private HomeDao homeDao;
    private String imgPath = "";
    private String photoPath = "";
    private boolean isDone = true;

    private ArrayList<ImgInfo> imgInfos = new ArrayList<>();
    private ArrayList<String> picFilePathList = new ArrayList<>();
    private static final int REQUEST_CODE_SELECT_PIC_FROM_CAMERA = 100;
    private static final int REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA = 120;

    @Override
    protected ScrapPresenter initPresenter() {
        return new ScrapPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_scrap;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onSuccess(CommonDao dao) {
        showShort(dao.getMsg());
        finish();
    }

    @Override
    public void showLoading() {
        LoadingView.showLoading(this, "", false);

    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    @Override
    public void initView() {

        setTitle("垃圾桶报废");
        goBack();
    }

    @Override
    public void initDate() {
        Intent intent = getIntent();
        String action = intent.getAction();
        homeDao = intent.getParcelableExtra(Scrap1Activity.HOME_DAO);
        String username = homeDao.getFullname();
        tvHome.setText(username);
        userAddress = MessageFormat.format("{0}{1}",
                homeDao.getAddress(),homeDao.getCompany());
        tvAddress.setText(userAddress);
        setPic();
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }


    /**
     * 设置照片
     */
    private void setPic() {
        picModify.setColumNum(4);
        picModify.removeAllViews();
        imgInfos.clear();
        for (int i = 0, picFilePathListSize = picFilePathList.size(); i < picFilePathListSize; i++) {
            String filePath = picFilePathList.get(i);
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(params);
            picModify.addView(iv);
            ImgInfo info = new ImgInfo();
            info.setUrl(filePath);
            imgInfos.add(info);
            GlideApp.with(this).load(filePath).into(iv);
            iv.setOnClickListener(this::startPre);
        }

        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
        iv.setLayoutParams(params);
        if (picModify.getChildCount() < 2) {
            picModify.addView(iv);
        }
        iv.setImageResource(R.drawable.task_iv_default);
        iv.setBackgroundColor(Color.WHITE);
        iv.setOnClickListener(v -> {
            photoPath = ImgUtil.getPicSavaPath(ScrapActivity.this) + "/" + System.currentTimeMillis() + ".jpg";
            if (Build.VERSION.SDK_INT > 23) {
                startActivityForResult(IntentUtil.getGrantPicFromCameraIntent(ScrapActivity.this, photoPath),
                        REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA);
            } else {
                startActivityForResult(IntentUtil.getPicFromCameraIntent(photoPath),
                        REQUEST_CODE_SELECT_PIC_FROM_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_PIC_FROM_CAMERA:
                    picFilePathList.add(photoPath);
                    File compress = ImgUtil.compress(new File(photoPath), 50, 2100000);
                    new Handler().post(this::setPic);
                    doTask(compress);
                    break;
                case REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA:
                    picFilePathList.add(photoPath);
                    File compress1 = ImgUtil.compress(new File(photoPath), 50, 2100000);
                    new Handler().post(this::setPic);
                    doTask(compress1);
                    break;
                default:
            }
        }
    }

    private void doTask(File compress) {

        MyTask task = new MyTask(this, imgPath, new Handler(msg -> {

            if (msg.what == MyTask.COMPRESS_SUCCESS) {
                isDone = true;
                imgPath = (String) msg.obj;
                dissmissLoading();
            } else {
                isDone = false;
                showLoading();
            }
            return false;
        }));
        task.execute(compress);

    }

    private void startPre(View v) {
        int currentIndex = picModify.indexOfChild(v);
        for (int j = 0; j < imgInfos.size(); j++) {
            Rect rect = new Rect();
            ImageView imageView = (ImageView) picModify.getChildAt(j);
            if (imageView != null) {
                imageView.getGlobalVisibleRect(rect);
                imgInfos.get(j).setBounds(rect);
            }
        }
        PrePictureActivity.start(this, imgInfos, currentIndex);
    }

    @OnClick(R.id.bt_submit)
    public void onViewClicked() {

        if (checkData()) {
            if (isDone) {
                mPresenter.scrapSubmit(homeDao, etContent.getText().toString().trim(), userAddress,imgPath);
            } else {
                showShort("等待图片上传,请稍后重试");
            }
        }
    }

    private boolean checkData() {

        if (isEmpty(etContent.getText().toString().trim())) {
            showShort("请输入内容");
            return false;
        }
        if (imgPath.length() <= 0) {
            showShort("请至少上传一张拍照");
            return false;
        }
        return true;
    }

}
