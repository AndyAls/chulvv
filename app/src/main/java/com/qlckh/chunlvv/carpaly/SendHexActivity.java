package com.qlckh.chunlvv.carpaly;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.manager.OnOpenSerialPortListener;
import com.qlckh.chunlvv.manager.OnSerialPortDataListener;
import com.qlckh.chunlvv.utils.HexStringUtils;
import com.qlckh.chunlvv.utils.HexUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/25 17:41
 * Desc:
 */
public class SendHexActivity extends BaseActivity {
    private static final String TAG = "SendHexActivity";
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.bt4)
    Button bt4;
    @BindView(R.id.zhong1)
    TextView zhong1;
    @BindView(R.id.zhong2)
    TextView zhong2;
    @BindView(R.id.zhong3)
    TextView zhong3;
    private static final int WEIGHT_WHAT = 100006000;

    DispQueueThread DispQueue;//刷新显示线程
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            StringBuffer b;

            int what = msg.what;
            switch (what) {
                case WEIGHT_WHAT:
                    b = new StringBuffer();
                    byte[]bytes= (byte[]) msg.obj;
                    zhong3.append(Arrays.toString(bytes));
                    zhong1.append(ConvertUtils.bytes2HexString(bytes));
                    zhong2.setText(ConvertUtils.bytes2HexString(bytes));
                    et4.setText(Arrays.toString(bytes));
                    b.append(ConvertUtils.bytes2HexString(bytes));
                    et3.setText(b.toString());
                    et1.setText(ConvertUtils.bytes2HexString(bytes));
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    @Override
    protected int getContentView() {
        return R.layout.activity_send_hex;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    StringBuffer buffer = new StringBuffer();
    @Override
    public void initView() {
        mWeightManager.sendBytes(et2.getText().toString().trim().getBytes());
        mWeightManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
            @Override
            public void onSuccess(File device) {
                showLong("称重串口打开成功");
            }

            @Override
            public void onFail(File device, Status status) {

                showLong("称重串口打开失败");
            }
        });

        mWeightManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @Override
            public void onDataReceived(final byte[] bytes) {
                reciveData(bytes, WEIGHT_WHAT);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        zhong3.append(Arrays.toString(bytes));
//                        zhong1.append(ConvertUtils.bytes2HexString(bytes));
//                        zhong2.setText(ConvertUtils.bytes2HexString(bytes));
//                        et4.setText(Arrays.toString(bytes));
//                        buffer.append(ConvertUtils.bytes2HexString(bytes));
//                        et1.setText(buffer.toString());
//                    }
//                });
//                ComBean comBean = new ComBean(bytes);
//                DispQueue.AddQueue(comBean);
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });
    }

    //----------------------------------------------------刷新显示线程
    private class DispQueueThread extends Thread{
        private Queue<ComBean> QueueList = new LinkedList<ComBean>();
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()) {
                final ComBean ComData;
                while((ComData=QueueList.poll())!=null)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            DispRecData(ComData);
                        }
                    });
                    try
                    {
                        Thread.sleep(100);//显示性能高的话，可以把此数值调小。
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        public synchronized void AddQueue(ComBean ComData){
            QueueList.add(ComData);
        }
    }

    //----------------------------------------------------显示接收数据
    private void DispRecData(ComBean ComRecData){
        if (ComRecData!=null){

            byte[] bRec = ComRecData.bRec;
            zhong2.append(Arrays.toString(bRec));
            return;
        }
        StringBuilder sMsg=new StringBuilder();
            sMsg.append(MyFunc.ByteArrToHex(ComRecData.bRec));
        zhong2.setText(sMsg);
    }

    private void reciveData(byte[] bytes, int what) {
        Message message = new Message();
        message.what = what;
        message.obj = bytes;
        mHandler.sendMessage(message);
    }

    @Override
    public void initDate() {
        DispQueue = new DispQueueThread();
        DispQueue.start();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                String trim = et1.getText().toString().trim();
                String s = ConvertUtils.bytes2HexString(trim.getBytes());
                mPanelManager.sendBytes(ConvertUtils.hexString2Bytes(s));
                break;
            case R.id.bt2:
                String trim1 = et2.getText().toString().trim();
                XLog.e(TAG,"getBytes-->"+ Arrays.toString(trim1.getBytes()),
                        "ConvertUtils-->"+ Arrays.toString(ConvertUtils.hexString2Bytes(trim1)),
                        "HexStringUtils-->"+ Arrays.toString(HexStringUtils.hexString2Bytes(trim1)),
                        "转16进制字符串-->"+ Arrays.toString(ConvertUtils.str2HexStr(trim1).getBytes()),
                        "转16进制字符串再转-->"+ Arrays.toString(ConvertUtils.hexString2Bytes(ConvertUtils.str2HexStr(trim1))),
                        "HexUtils-->"+ Arrays.toString(HexUtils.hexStringtoByteArray(trim1)),
                        "MyFunc-->"+ Arrays.toString(MyFunc.HexToByteArr(trim1)));
                boolean b = mWeightManager.sendBytes(ConvertUtils.hexString2Bytes(trim1));
//                showDialog(b?"发送成功":"发送失败");
                break;
            case R.id.bt3:
                String trim2 = et3.getText().toString().trim();
                String s2 = ConvertUtils.bytes2HexString(trim2.getBytes());
                mScanManager.sendBytes(ConvertUtils.hexString2Bytes(s2));
                break;
            case R.id.bt4:

                String trim3 = et4.getText().toString().trim();
                String s3 = null;
                try {
                    s3 = ConvertUtils.bytes2HexString(trim3.getBytes(ConvertUtils.GB2312));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String p = "1b40" + s3 + "0d0a0d0a0d0a0d0a0d0a0d0a1b69";
                mPrintManager.sendBytes(ConvertUtils.hexString2Bytes(p));
                break;
            default:
        }
    }
}
