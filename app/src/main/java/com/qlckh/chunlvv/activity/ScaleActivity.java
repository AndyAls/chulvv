package com.qlckh.chunlvv.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.common.SendThread;
import com.qlckh.chunlvv.common.TcpHelper;
import com.qlckh.chunlvv.common.TestSocket;
import com.qlckh.chunlvv.dao.ScaleBean;
import com.qlckh.chunlvv.http.utils.GsonUtils;
import com.qlckh.chunlvv.utils.JsonUtil;
import com.qlckh.chunlvv.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/8/9 12:54
 * Desc: 电子秤
 */
public class ScaleActivity extends BaseActivity {
    public static final String SCALE_INFO = "SCALE_INFO";
    @BindView(R.id.show_weight_tv)
    TextView showWeightTv;
    @BindView(R.id.ip_ed)
    EditText ipEd;
    @BindView(R.id.port_ed)
    EditText portEd;
    @BindView(R.id.connection_but)
    Button connectionBut;
    @BindView(R.id.calibration_weight_ed)
    EditText calibrationWeightEd;
    @BindView(R.id.zero_ad_ed)
    EditText zeroAdEd;
    @BindView(R.id.calibration_ad_ed)
    EditText calibrationAdEd;
    @BindView(R.id.save_but)
    Button saveBut;
    private byte[] TcpRecDataHH;
    private String TcpRecData;
    private MyHandler handler;
    private int len;
    private TcpHelper tcpHelper;

    @Override
    protected int getContentView() {
        return R.layout.activity_scale;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("设置标定值");
        goBack();
        ImmersionBar.with(this).keyboardEnable(true)
                .init();

    }

    @Override
    public void initDate() {

        String param = SpUtils.getStringParam(this, SCALE_INFO, "");
        ScaleBean info=JsonUtil.json2Object2(param,ScaleBean.class);
        if (!isEmpty(info)){
            ipEd.setText(info.getIp());
            portEd.setText(info.getHost());
            calibrationWeightEd.setText(info.getCalibrationWeight());
            zeroAdEd.setText(info.getZeroAd());
            calibrationAdEd.setText(info.getAlibrationAd());
            tcpConnect();
        }
        ipEd.setSelection(ipEd.getText().length());

    }

    private void tcpConnect() {
        if (tcpHelper==null){
            tcpHelper = new TcpHelper(ipEd.getText().toString().trim(), Integer.parseInt(portEd.getText().toString().trim()));
            tcpHelper.SendString("WS0000EF");
            TcpReceive tcpReceive = new TcpReceive();
            tcpHelper.setReceiveEvent(tcpReceive);
            handler = new MyHandler();
        }


    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

        if (tcpHelper!=null){
            tcpHelper.setStop();
            tcpHelper=null;

        }
    }


    private boolean checkData(){
        if (!TcpHelper.isIP(ipEd.getText().toString().trim())){
            showShort("请填写正确的ip");
            return false;
        }
        if (!TcpHelper.checkPort(Integer.parseInt(portEd.getText().toString().trim()))){
            showShort("请填写正确的端口号");
            return false;
        }
        if (isEmpty(calibrationWeightEd)){
            showShort("请填写标定重量");
            return false;
        }
        if (isEmpty(zeroAdEd)){
            showShort("请填写零点AD");
            return false;
        }
        if (isEmpty(calibrationAdEd)){
            showShort("请填写标定AD");
            return false;
        }
        return true;
    }
    @OnClick({R.id.connection_but, R.id.save_but})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.connection_but:
                if (!checkData()){
                    return;
                }
                tcpConnect();
                break;
            case R.id.save_but:
                savaData();
                break;
                default:
        }
    }

    private void savaData() {
        if (checkData()){
            ScaleBean bean = new ScaleBean();
            bean.setIp(ipEd.getText().toString().trim());
            bean.setHost(portEd.getText().toString().trim());
            bean.setAlibrationAd(calibrationAdEd.getText().toString().trim());
            bean.setCalibrationWeight(calibrationWeightEd.getText().toString().trim());
            bean.setZeroAd(zeroAdEd.getText().toString().trim());
            String s = JsonUtil.object2Json(bean);
            SpUtils.putParam(this,SCALE_INFO,s);
            showShort("数据已保存...");
            finish();
        }
    }

    /**
     * 广播通知接收数据
     */
    public class TcpReceive implements TcpHelper.OnReceiveEvent{
        @Override
        public  synchronized void ReceiveBytes(byte[] iData){
            TcpRecDataHH = iData;
        }
        @Override
        public  synchronized  void ReceiveString(String iData){
            TcpRecData=iData;
            Message msg=new Message();
            msg.what=1;
            handler.sendMessage(msg);
        }
    }


    class MyHandler extends Handler {
        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    while (TcpRecDataHH != null) {
                        byte[] buf = TcpRecDataHH;
                        len = buf.length;
                        int d = 0;
                        for (; d < len; d++) {
                            if (buf[d] == 'W') {
                                break;
                            }
                        }
                        len -= d;
                        for (int i = 0; i < len / 8; i++) {
                            if (buf[d + i * 8] != 'W' || buf[d + i * 8 + 1] != 'D' || buf[d + i * 8 + 6] != 'E' || buf[d + i * 8 + 7] != 'F') {
                                continue;
                            }
                            int ad = byteArrayToInt(buf, d + i * 8 + 2);
                            if (showWeightTv!=null){
                                showWeightTv.setText(String.valueOf(ad));
                            }
                        }
                        break;

                    }
                    default:
                    super.handleMessage(msg);
            }
        }
    }

    public  int byteArrayToInt(byte[] b, int offset) {
        int value= 0;
        for (int i = 0; i < 4; i++) {
            int shift= i * 8;//(4 - 1 - i)
            value +=(b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }
}
