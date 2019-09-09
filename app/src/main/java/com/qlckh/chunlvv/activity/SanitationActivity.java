package com.qlckh.chunlvv.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.qlckh.chunlvv.App;
import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.ScoreDB;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.GlideApp;
import com.qlckh.chunlvv.common.LocationService;
import com.qlckh.chunlvv.common.TcpHelper;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.dao.ScaleBean;
import com.qlckh.chunlvv.dao.ScoreBean;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.utils.IntentUtil;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.impl.SanitationPresenterImpl;
import com.qlckh.chunlvv.presenter.CompositeView;
import com.qlckh.chunlvv.presenter.SanitationPresenter;
import com.qlckh.chunlvv.preview.ImgInfo;
import com.qlckh.chunlvv.preview.PrePictureActivity;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.Base64Util;
import com.qlckh.chunlvv.utils.ImgUtil;
import com.qlckh.chunlvv.utils.JsonUtil;
import com.qlckh.chunlvv.utils.NetworkUtils;
import com.qlckh.chunlvv.utils.SpUtils;
import com.qlckh.chunlvv.view.LoadingView;
import com.qlckh.chunlvv.view.PicGridView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/20 1:12
 * Desc:
 */
public class SanitationActivity extends BaseMvpActivity<SanitationPresenter> implements CompositeView {
    private static final int REQUEST_CODE_SELECT_PIC_FROM_CAMERA = 100;
    private static final int REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA = 120;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rb_put_1)
    RadioButton rbPut1;
    @BindView(R.id.rb_put_2)
    RadioButton rbPut2;
    @BindView(R.id.rb_put_3)
    RadioButton rbPut3;
    @BindView(R.id.rg_put)
    RadioGroup rgPut;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.picItems)
    PicGridView picModify;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.tv_state)
    TextView tvState;
    private LocationService locationService;
    private List<String> picFilePathList = new ArrayList<>();
    private String photoPath;
    private int totalScore;
    private int envScore = 2;
    private boolean isDone = true;
    private String imgPath = "";
    ArrayList<ImgInfo> imgInfos = new ArrayList<>();
    private String userAddress;


    private byte[] TcpRecDataHH;
    private String TcpRecData;
    private MyHandler handler;
    private int len;
    private TcpHelper tcpHelper;
    private ScaleBean info;
    private String ip = "";
    private String alibrationAd;
    private String calibrationWeight;
    private String host;
    private String zeroAd;
    private List<ScoreBean> scoreBeanList;
    private BluetoothAdapter bluetoothAdapter;
    private final int BUFFER_SIZE = 1024;
    private ConnectThread connectThread;
    private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String NAME = "BT_DEMO";
    private ListenerThread listenerThread;

    @Override
    protected SanitationPresenter initPresenter() {
        return new SanitationPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sanitation;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
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
    public void onSuccess(CommonDao dao) {
        finish();
    }

    @Override
    public void onAddScanedSuccess() {

    }

    @Override
    public void initView() {
        goBack();
        setTitle("其他垃圾");
        totalScore = envScore;
        tvScore.setText(String.format(Locale.SIMPLIFIED_CHINESE, "%d", totalScore));
        addListener();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listenerThread = new ListenerThread();
        listenerThread.start();
        tvState.setEnabled(false);
//        searchDevices();
    }

    /**
     * 搜索蓝牙设备
     */
    private void searchDevices() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        getBoundedDevices();
        bluetoothAdapter.startDiscovery();
    }

    private BluetoothDevice bluetoothDevice;

    /**
     * 获取已经配对过的设备
     */
    private void getBoundedDevices() {
        //获取已经配对过的设备
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        //将其添加到设备列表中
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if ("FAYA".equalsIgnoreCase(device.getName())) {
                    bluetoothDevice = device;
                    connectDevice(device);
                }
            }
        }
    }

    /**
     * 连接蓝牙设备
     */
    private void connectDevice(BluetoothDevice device) {
        tvState.setText("蓝牙连接中....");
        try {
            //创建Socket
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(BT_UUID);
            //启动连接线程
            connectThread = new ConnectThread(socket, true);
            connectThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听线程
     */
    private class ListenerThread extends Thread {
        private BluetoothServerSocket serverSocket;
        private BluetoothSocket socket;

        @Override
        public void run() {
            try {
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, BT_UUID);
                while (true) {
                    //线程阻塞，等待别的设备连接
                    socket = serverSocket.accept();
                    tvState.post(new Runnable() {
                        @Override
                        public void run() {
                            tvState.setText("蓝牙连接中");
                        }
                    });
                    connectThread = new ConnectThread(socket, false);
                    connectThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接线程
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket socket;
        private boolean activeConnect;
        InputStream inputStream;
        OutputStream outputStream;

        private ConnectThread(BluetoothSocket socket, boolean connect) {
            this.socket = socket;
            this.activeConnect = connect;
        }

        @Override
        public void run() {
            try {
                //如果是自动连接 则调用连接方法
                if (activeConnect) {
                    socket.connect();
                }
                if (tvState!=null){
                    tvState.post(new Runnable() {
                        @Override
                        public void run() {
                            tvState.setText("蓝牙连接成功");
                        }
                    });
                }
                inputStream = socket.getInputStream();
//                outputStream = socket.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];//1024
                int bytes;
                while (true) {
                    //读取数据
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        final byte[] data = new byte[bytes];
                        System.arraycopy(buffer, 0, data, 0, bytes);
                        if (etWeight!=null){
                            etWeight.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (etWeight!=null){
                                        etWeight.setText(getWeight(new String(data)));
                                        etWeight.setEnabled(false);
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (tvState == null) {
                    return;
                }
                tvState.post(new Runnable() {
                    @Override
                    public void run() {
                        if (tvState == null) {
                            return;
                        }
                        tvState.setEnabled(true);
                        tvState.setText("蓝牙连接失败,请点击重试...");
                    }
                });
            }
        }
    }

    private String getWeight(String s) {
        if (isEmpty(s)) {
            return "";
        }
        String[] split = s.split("=");
        String source = split[0];
        String reverse = reverse(source);
        char c = reverse.charAt(0);
        if ("-".equals(reverse.charAt(0) + "")) {
            reverse = reverse.substring(1);
        }
        return String.valueOf(Double.parseDouble(reverse));
    }

    private String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }


    private void addListener() {

        rgPut.setOnCheckedChangeListener((group, checkedId) -> {

            View viewById = rgPut.findViewById(checkedId);
            if (!viewById.isPressed()) {
                return;
            }

            switch (checkedId) {
                case R.id.rb_put_1:
                    rbPut1.setChecked(true);
                    rbPut3.setChecked(false);
                    envScore = 2;
                    break;
                case R.id.rb_put_3:
                    rbPut1.setChecked(false);
                    rbPut3.setChecked(true);
                    envScore = 0;
                    break;
                default:
                    break;
            }
            updaScore();

        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private void updaScore() {
        totalScore = envScore;
        tvScore.setText(MessageFormat.format("{0}", totalScore));
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void initDate() {
        String param = SpUtils.getStringParam(this, ScaleActivity.SCALE_INFO, "");
        info = JsonUtil.json2Object2(param, ScaleBean.class);
        if (info != null) {
            ip = info.getIp();
            alibrationAd = info.getAlibrationAd();
            calibrationWeight = info.getCalibrationWeight();
            host = info.getHost();
            zeroAd = info.getZeroAd();
            tcpConnect();
        }

        HomeDao homeDao = getIntent().getParcelableExtra(MarkActivity.HOME_DAO);
        String username = homeDao.getFullname();
        tvHome.setText(username);
        userAddress = MessageFormat.format("{0}{1}",
                homeDao.getAddress(), homeDao.getCompany());
        tvAddress.setText(userAddress);
        ibRight.setVisibility(View.GONE);
        btSubmit.setOnClickListener(v -> {

            if (!NetworkUtils.isConnected(this)) {
                ScoreBean bean = new ScoreBean();
                bean.setAddress(userAddress);
                bean.setBucketScore(0);
                bean.setCategoryScore(0);
                bean.setFullId(homeDao.getId());
                bean.setFullname(homeDao.getFullname());
                bean.setImgs(picFilePathList);
                bean.setFullPhone(homeDao.getPhone());
                bean.setUserId(UserConfig.getUserid());
                bean.setUserName(UserConfig.getUserName());
                bean.setPutScore(0);
                bean.setWeight(etWeight.getText().toString().trim());
                bean.setFullItems(homeDao.getItems());
                bean.setTotalScore(totalScore);
                bean.setWhatType("1");
                bean.setTime(TimeUtil.formatTime(System.currentTimeMillis(), TimeUtil.Y_M_D_H_M_24));
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        ScoreDB.getInstance().getScoreDao().insert(bean);
                        return null;
                    }

                    @Override
                    protected void onPreExecute() {
                        LoadingView.showLoading(SanitationActivity.this, "", false);
                        super.onPreExecute();
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        LoadingView.cancelLoading();
                        startActivity(new Intent(SanitationActivity.this, CollectListActivity.class));
                        showLong("暂无网络连接,数据已保存,请联网提交");
                        finish();
                    }
                }.execute();


            } else {
                if (isDone) {
                    mPresenter.sanitationSubmit(homeDao, envScore, homeDao.getItems(), UserConfig.getUserName(),
                            imgPath, etWeight.getText().toString().trim());

                } else {

                    showShort("等待图片上传,请稍后重试");
                }
            }


        });
        setPic();
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {
        //取消搜索
        if (bluetoothAdapter != null && bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        try {
            if (listenerThread != null) {
                listenerThread.stop();
                listenerThread.destroy();
                listenerThread = null;
            }
            if (connectThread != null) {
                connectThread.stop();
                connectThread.destroy();
                connectThread = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RxHttpUtils.cancelAllRequest();
        if (tcpHelper != null) {
            tcpHelper.setStop();
            tcpHelper = null;

        }
    }

    private void tcpConnect() {
        if (tcpHelper == null) {
            tcpHelper = new TcpHelper(ip, Integer.parseInt(host));
            tcpHelper.SendString("WS0000EF");
            TcpReceive tcpReceive = new TcpReceive();
            tcpHelper.setReceiveEvent(tcpReceive);
            handler = new MyHandler();
        }


    }


    @OnClick(R.id.tv_state)
    public void onViewClicked() {
        connectDevice(bluetoothDevice);
    }

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {
        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 接收到数据显示到TextView上
                    while (true) {
                        byte[] buf = TcpRecDataHH;
                        if (buf == null) {
                            return;
                        }
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

                            float standardWeight = (ad - Long.parseLong(zeroAd));
                            BigDecimal b = new BigDecimal((Long.parseLong(alibrationAd) - Long.parseLong(zeroAd)));
                            float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                            float kg = Long.parseLong(calibrationWeight);
                            float weight = (standardWeight / f1) * kg;

                            //weight = ((ad-zeroADINT)/(calibrationADINT-zeroADINT))*calibrationWeightINT;

                            DecimalFormat dnf = new DecimalFormat("##0.00");
                            String add_price = dnf.format(weight);
                            if (etWeight != null) {
                                etWeight.setText(add_price);
                                etWeight.setEnabled(false);
                            }

                        }
                        break;
                    }
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = i * 8;//(4 - 1 - i)
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    /**
     * 广播通知接收数据
     */
    public class TcpReceive implements TcpHelper.OnReceiveEvent {
        @Override
        public synchronized void ReceiveBytes(byte[] iData) {
            TcpRecDataHH = iData;
        }

        @Override
        public synchronized void ReceiveString(String iData) {
            TcpRecData = iData;
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
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

    private String address;
    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {


            if (bdLocation != null) {
                String addrStr = bdLocation.getAddrStr();
                String locationDescribe = bdLocation.getLocationDescribe();
                if (locationDescribe != null) {
                    address = addrStr.concat(locationDescribe.substring(1, locationDescribe.length() - 2));
                } else {
                    address = addrStr;
                }
            }
            locationService.stop();
        }
    };

    /**
     * 设置照片
     */
    private void setPic() {
        picModify.setColumNum(4);
        picModify.removeAllViews();
        for (String filePath : picFilePathList) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(params);
            picModify.addView(iv);
            GlideApp.with(this).load(filePath).into(iv);
            ImgInfo info = new ImgInfo();
            info.setUrl(filePath);
            imgInfos.add(info);
            iv.setOnClickListener(this::startPre);
        }

        ImageView iv = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40);
        iv.setLayoutParams(params);
        if (picModify.getChildCount() < 4) {
            picModify.addView(iv);
        }
        iv.setImageResource(R.drawable.task_iv_default);
        iv.setBackgroundColor(Color.WHITE);
        iv.setOnClickListener(v -> {
            photoPath = ImgUtil.getPicSavaPath(this) + "/" + System.currentTimeMillis() + ".jpg";
            if (Build.VERSION.SDK_INT > 23) {
                startActivityForResult(IntentUtil.getGrantPicFromCameraIntent(SanitationActivity.this, photoPath),
                        REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA);
            } else {
                startActivityForResult(IntentUtil.getPicFromCameraIntent(photoPath),
                        REQUEST_CODE_SELECT_PIC_FROM_CAMERA);
            }
        });
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

    private StringBuilder builder = new StringBuilder();

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
//                    ThreadPool.getInstance().execute("thread", () ->
//                            builder.append(Base64Util.ioEncode(compress)).append("分"));
                    break;
                case REQUEST_CODE_SELECT_GRAINT_URI_FROM_CAMERA:
                    picFilePathList.add(photoPath);
                    File compress1 = ImgUtil.compress(new File(photoPath), 50, 2100000);
                    new Handler().post(this::setPic);
                    doTask(compress1);
//                    ThreadPool.getInstance().execute("thread", () ->
//                            builder.append(Base64Util.ioEncode(compress1)).append("分"));
                    break;
                default:
            }
        }
    }

    private void doTask(File compress) {

        MyTask task = new MyTask(this);
        task.execute(compress);

    }


    private static class MyTask extends AsyncTask<File, Void, String> {

        private final WeakReference<Activity> reference;

        MyTask(Activity activity) {

            reference = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(File... files) {
            String ioEncode = Base64Util.ioEncode(files[0]);
            String s = "data:image/png;base64,".concat(ioEncode);
            XLog.e("+++", "doInBackground", ioEncode);
            String source = s.concat("分");
            XLog.e("+++", "source", source);
            return source;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SanitationActivity activity = (SanitationActivity) reference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.imgPath += s;
            XLog.e("+++", "s", s);
            XLog.e("+++", "imgPath", activity.imgPath);
            activity.isDone = true;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            SanitationActivity activity = (SanitationActivity) reference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.isDone = true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            SanitationActivity activity = (SanitationActivity) reference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.isDone = false;
        }
    }
}
