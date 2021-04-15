package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.graphics.Color;

import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.dao.HomeInfo;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.qidian.HomeMarkActivity;
import com.qlckh.chunlvv.user.UserConfig;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;

/**
 * @author Andy
 * @date 2019/6/5 9:38
 * Desc:
 */
public class ScanMarkActivity extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.bt_scan)
    Button btScan;

    @Override
    protected int getContentView() {
        return R.layout.activity_home_sys;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {

        setTitle("巡检");
        goBack();
        ibRight.setVisibility(View.VISIBLE);
        ibRight.setText("退出登录");
        ibRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
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

    @OnClick({R.id.bt_scan,R.id.bt_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_scan:
//                Intent intent=new Intent(this, Scan1Activity.class);
//                intent.setAction("andy");
//                startActivityForResult(intent,1001);

                scanResult();
                break;
            case R.id.bt_logout:
                logout();
                break;
            default:
        }
    }

    private void logout() {
        UserConfig.reset();
        UserConfig.userInfo = null;
        UserConfig.userResp = null;
        Intent intent1 = new Intent(this, LoginActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }

    private void scanResult() {
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(识别二维码)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_QRCODE)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setNeedCrop(true)//从相册选择二维码之后再次截取二维码
                .setIsOnlyCenter(false)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描二维码")//设置Tilte文字
                .setTitleBackgroudColor(ContextCompat.getColor(this,R.color.colorPrimary))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .setShowZoom(false)//是否手动调整焦距
                .setAutoZoom(false)//是否自动调整焦距
                .setScreenOrientation(QrConfig.SCREEN_PORTRAIT)//设置屏幕方向
                .create();
        QrManager.getInstance().init(qrConfig).startScan(this, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result) {
                queryData(result);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null&&resultCode==RESULT_OK&&requestCode==1001){

            String nCode = data.getStringExtra("andy");
//            queryData(nCode);

        }
    }


    private void queryData(String json) {

        loading();
        RxHttpUtils.createApi(ApiService.class)
                .queryInfo(json)
                .compose(Transformer.<HomeInfo>switchSchedulers())
                .subscribe(new CommonObserver<HomeInfo>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelLoading();
                        showLong(errorMsg);
                    }

                    @Override
                    protected void onSuccess(HomeInfo homeInfo) {
                        cancelLoading();
                        if (homeInfo.getStatus()==1){
                            Intent intent=new Intent(ScanMarkActivity.this, HomeMarkActivity.class);
                            intent.putExtra("homeinfo",homeInfo);
                            intent.putExtra("ncode",json);
                            startActivity(intent);
                        }else {

                            showShort(homeInfo.getMsg());
                        }

                    }
                });
    }
}
