package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.FastAdapter;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.DeviceDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.DevicePresenterImpl;
import com.qlckh.chunlvv.presenter.DevicePresenter;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.view.CustomGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/7 16:40
 * Desc:
 */
public class DeviceActivity extends BaseMvpActivity<DevicePresenter> implements DevicePresenter.DeviceView {
    @BindView(R.id.classify_gd)
    CustomGridView classifyGd;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private List<DeviceDao.DeviceBean> devices;

    @Override
    protected DevicePresenter initPresenter() {
        return new DevicePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_device;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void onDeviceSuccess(DeviceDao deviceDao) {

        if (deviceDao != null && deviceDao.getRow() != null && deviceDao.getRow().size() > 0) {
            classifyGd.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            devices = deviceDao.getRow();
            classifyGd.setAdapter(new MyAdapter());
        } else {
            classifyGd.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }

    }

   private class MyAdapter extends FastAdapter {

        @Override
        public int getCount() {
            return devices.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HodlerView hodlerView = null;
            if (convertView == null) {
                hodlerView = new HodlerView();
                convertView = LayoutInflater.from(DeviceActivity.this).inflate(R.layout.device_list_item, null);
                hodlerView.type_img = convertView.findViewById(R.id.type_img);
                hodlerView.type_name_tv = convertView.findViewById(R.id.type_name_tv);
                convertView.setTag(hodlerView);
            } else {
                hodlerView = (HodlerView) convertView.getTag();
            }
            DeviceDao.DeviceBean deviceBean = devices.get(position);
            GlideUtil.displayRoundConnerImg(DeviceActivity.this, ApiService.IMG_URL + deviceBean.getImg(), hodlerView.type_img);
            hodlerView.type_name_tv.setText(deviceBean.getTitle());
            return convertView;
        }
    }

    class HodlerView {
        ImageView type_img;
        TextView type_name_tv;
    }

    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Override
    public void showLoading() {

        loading();
    }

    @Override
    public void dissmissLoading() {

        cancelLoading();
    }

    @Override
    public void initView() {

        setTitle("设备展示");
        goBack();
    }

    @Override
    public void initDate() {

        mPresenter.getDevice();
        classifyGd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DeviceActivity.this, NewsActivity.class);
                intent.putExtra(NewsActivity.NEWS_CONTENT,devices.get(position).getContent());
                intent.putExtra(NewsActivity.NEWS_TIME,devices.get(position).getAddtime());
                intent.putExtra(NewsActivity.NEWS_TITLE,devices.get(position).getTitle());
                startActivity(intent);
            }
        });

    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
    }
}
