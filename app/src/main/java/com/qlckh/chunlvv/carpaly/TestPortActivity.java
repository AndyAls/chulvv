package com.qlckh.chunlvv.carpaly;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.manager.Constant;
import com.qlckh.chunlvv.manager.OnOpenSerialPortListener;
import com.qlckh.chunlvv.manager.OnSerialPortDataListener;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/17 0:41
 * Desc:
 */
public class TestPortActivity extends BaseActivity {


    private static final int WEIGHT_WHAT = 100000;
    private static final int SCAN_WHAT = 1010100;
    private static final int PRINT_WHAT = 19990;
    private static final int PANEL_WHAT = 39999;
    @BindView(R.id.qing1)
    TextView qing1;
    @BindView(R.id.qing2)
    TextView qing2;
    @BindView(R.id.qing3)
    TextView qing3;
    @BindView(R.id.men1)
    TextView men1;
    @BindView(R.id.men2)
    TextView men2;
    @BindView(R.id.men3)
    TextView men3;
    @BindView(R.id.zhong1)
    TextView zhong1;
    @BindView(R.id.zhong2)
    TextView zhong2;
    @BindView(R.id.zhong3)
    TextView zhong3;
    @BindView(R.id.dagyin1)
    TextView dagyin1;
    @BindView(R.id.dagyin2)
    TextView dagyin2;
    @BindView(R.id.dagyin3)
    TextView dagyin3;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.bt_send2)
    Button btSend2;
    private String weightNode;
    private int weightRate;
    private String panelNode;
    private int panelRate;
    private String scanNode;
    private int scanRate;
    private String printNode;
    private int printRate;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            StringBuffer b;
            byte[] bytes;
            int what = msg.what;
            switch (what) {
                case WEIGHT_WHAT:
                    b = new StringBuffer();
                    bytes = (byte[]) msg.obj;
                    for (int i = 0; i < bytes.length; i++) {
                        b.append("[");
                        byte aByte = bytes[i];
                        String s = aByte + ",";
                        b.append(s);
                        b.append("]");
                    }
                    zhong1.setText(b.toString());
                    zhong2.setText(new String(bytes));
                    zhong3.setText(ConvertUtils.bytes2HexString(bytes));
                    break;

                case PRINT_WHAT:

                    b = new StringBuffer();
                    bytes = (byte[]) msg.obj;
                    for (int i = 0; i < bytes.length; i++) {
                        b.append("[");
                        byte aByte = bytes[i];
                        String s = aByte + ",";
                        b.append(s);
                        b.append("]");
                    }
                    dagyin1.setText(b.toString());
                    dagyin2.setText(new String(bytes));
                    dagyin3.setText(ConvertUtils.bytes2HexString(bytes));
                    break;

                case SCAN_WHAT:

                    handScan(msg);
                    break;

                case PANEL_WHAT:

                    handPanel((byte[])msg.obj);
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    StringBuffer buffer = new StringBuffer();

    private void handPanel(byte[] obj) {
        buffer.append(ConvertUtils.bytes2HexString(obj));
        if (buffer.length() == 12) {
            men3.setText(buffer.toString());
            buffer.delete(0, 12);
        }
    }

    StringBuffer b=new StringBuffer();
    StringBuffer b1=new StringBuffer();

    private void handScan(Message msg) {
        byte[]bytes = (byte[]) msg.obj;
        qing1.append(Arrays.toString(bytes));
        qing2.append(new String(bytes));
        qing3.append(ConvertUtils.bytes2HexString(bytes));
       /* b.append(ConvertUtils.bytes2HexString(bytes));
        if (b.length()==24){
            String conver = b.toString();
            qing3.setText(conver);
            b.delete(0,24);
        }
        b1.append(new String(bytes));
        if (b1.length()==10){
            qing2.setText(b1.toString());
            b1.delete(0,10);
        }*/
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_test;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    @Override
    public void initView() {

        initNoteAndRote();
        setSerialListener();

    }

    private void setSerialListener() {
        //发送再主线程 接收子线程
        setWeightListener();
        setScanListerer();
        setPrintLisener();
        setPanelListener();
    }

    private void setPanelListener() {
        mPanelManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File device) {

            }

            @Override
            public void onFail(File device, Status status) {
                showLong("开关门串口打开失败");
            }
        });

        mPanelManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {
                reciveData(bytes, PANEL_WHAT);
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });


    }

    private void setPrintLisener() {

        mPrintManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File device) {


            }

            @Override
            public void onFail(File device, Status status) {
                showLong("打印机串口打开失败");
            }
        });
        mPrintManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {

                reciveData(bytes, PRINT_WHAT);
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });
    }

    private void setScanListerer() {
        mScanManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File device) {

            }

            @Override
            public void onFail(File device, Status status) {
                showLong("扫码枪串口打开失败");
            }
        });
        mScanManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {
                reciveData(bytes, SCAN_WHAT);
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });
    }

    private void setWeightListener() {
        mWeightManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File device) {

            }

            @Override
            public void onFail(File device, Status status) {

                showLong("称重串口打开失败");
            }
        });

        mWeightManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(byte[] bytes) {

                reciveData(bytes, WEIGHT_WHAT);
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });
    }

    private void reciveData(byte[] bytes, int what) {
        Message message = new Message();
        message.what = what;
        message.obj = bytes;
        mHandler.sendMessage(message);
    }

    private void initNoteAndRote() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
        weightNode = sp.getString(Constant.WEGHT_NODE, "");
        weightRate = Integer.decode(sp.getString(Constant.WEGHT_RATE, "-1"));

        SharedPreferences psp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
        panelNode = psp.getString(Constant.PANEL_NODE, "");
        panelRate = Integer.decode(sp.getString(Constant.PRINT_RATE, "-1"));

        SharedPreferences ssp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
        scanNode = ssp.getString(Constant.SCAN_NODE, "");
        scanRate = Integer.decode(sp.getString(Constant.SCAN_RATE, "-1"));

        SharedPreferences prsp = getSharedPreferences(Constant.SP_NAME, MODE_PRIVATE);
        printNode = prsp.getString(Constant.PRINT_NODE, "");
        printRate = Integer.decode(sp.getString(Constant.PRINT_RATE, "-1"));

        checkNodeAndRate();
    }

    /**
     * 检查设备节点和波特率 没设置 跳转到设置界面
     */
    private void checkNodeAndRate() {

        if (weightNode.length() == 0 || weightRate == -1
                || panelNode.length() == 0 || panelRate == -1
                || scanNode.length() == 0 || scanRate == -1
                || printNode.length() == 0 || printRate == -1) {

            Toast.makeText(this, "请设置全部的设备节点和波特率", Toast.LENGTH_LONG).show();

            startActivity(new Intent(this, SettingPortActivity.class));
        }
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

    @OnClick({R.id.bt_send, R.id.bt_send2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                startActivity(new Intent(this,SendHexActivity.class));
                break;
            case R.id.bt_send2:
                startActivity(new Intent(this, SettingPortActivity.class));
                break;
                default:
        }
    }

//    @OnClick(R.id.bt_send)
//    public void onViewClicked() {
//
//
//    }
}
