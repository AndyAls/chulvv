package com.qlckh.chunlvv.qidian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.Scan1Activity;
import com.qlckh.chunlvv.activity.ScanActivity;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;

/**
 * @author Andy
 * @date 2019/5/31 17:41
 * Desc:
 */
public class PhoneScanActivity extends BaseActivity {
    @BindView(R.id.tvTong)
    TextView tvTong;
    @BindView(R.id.tvTongHint)
    TextView tvTongHint;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.bt_scan)
    Button btScan;
    @BindView(R.id.bt_huan)
    Button btHuan;

    private final static String ANDY = "andy";
    private String andy;
    @Override
    protected int getContentView() {
        return R.layout.activity_qidiann5;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {
        ibRight.setVisibility(View.GONE);
        setTitle("扫一扫");
    }

    @Override
    public void initDate() {
        setTong();
    }

    private boolean setTong() {
        andy = SpUtils.getStringParam(this, ANDY, "");
        if (isEmpty(andy)) {
            tvTong.setText("");
            tvTongHint.setText("请先扫描捅上的二维码再扫描垃圾袋");
            btHuan.setVisibility(View.GONE);
            return true;
        } else {
            tvTong.setText(andy.split("IMEI:")[1]);
            tvTongHint.setText("请确定桶的编号是否正确");
            btHuan.setVisibility(View.VISIBLE);
            return false;
        }
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
    }


    @OnClick({R.id.bt_scan, R.id.bt_huan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_scan:
               /* Intent intent=new Intent(this, Scan1Activity.class);
                intent.setAction("andy");
                startActivityForResult(intent,1001);*/

               scanResult();
                break;
            case R.id.bt_huan:
                SpUtils.putParam(this, ANDY, "");
                setTong();
                break;
                default:
        }
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
            public void onScanSuccess(String json) {
                //还没扫垃圾桶
                if (isEmpty(andy)) {
                    SpUtils.putParam(PhoneScanActivity.this, ANDY, json);
                    setTong();
                } else {
                    //扫描的是垃圾袋
                    chooseTong(json);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null&&resultCode==RESULT_OK&&requestCode==1001){
            String json = data.getStringExtra("andy");
            //还没扫垃圾桶
            if (isEmpty(andy)) {
                SpUtils.putParam(PhoneScanActivity.this, ANDY, json);
                setTong();
            } else {
                //扫描的是垃圾袋
                chooseTong(json);
            }
        }
    }

    private void chooseTong(String json) {
        loading();
        RxHttpUtils.createApi(ApiService.class)
                .putStore(andy.split("IMEI:")[1], json, UserConfig.getUserid())
                .compose(Transformer.<StoreDao>switchSchedulers())
                .subscribe(new CommonObserver<StoreDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        cancelLoading();
                        showLong(errorMsg);
                    }

                    @Override
                    protected void onSuccess(StoreDao responeDao) {
                        cancelLoading();
                        tvCode.setText(responeDao.getData());
                    }
                });

    }

}
