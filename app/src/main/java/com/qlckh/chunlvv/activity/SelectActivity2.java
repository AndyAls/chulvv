package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.FastAdapter;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.BaojieDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CuntryDao;
import com.qlckh.chunlvv.dao.GuanDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.SelectPresenterImpl;
import com.qlckh.chunlvv.presenter.SelectPresenter;
import com.qlckh.chunlvv.view.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/5/26 13:40
 * Desc:
 */
public class SelectActivity2 extends BaseMvpActivity<SelectPresenter> implements SelectPresenter.SelectView {
    public static final String GUAN_ID = "GUAN_ID";
    public static final String BAOJIE_ID = "BAOJIE_ID";
    public static final String GUAN_NAME = "GUAN_NAME";
    public static final String BAOJIE_NAME = "BAOJIE_NAME";
    @BindView(R.id.cun_list)
    ListView cunList;
    @BindView(R.id.guan_list)
    ListView guanList;
    @BindView(R.id.baojie_list)
    ListView baojieList;
    @BindView(R.id.bt_sure)
    Button btSure;
    private ArrayList<CuntryDao.Cuntry> cuntryList;
    private int cunPos = -1;
    private int guanPos = -1;
    private int baoPos = -1;
    private FastAdapter guanAdapter;
    private FastAdapter cunAdapter;
    private FastAdapter baojieAdapter;
    private List<BaojieDao.BaojieBean> baoJies;
    private List<GuanDao.Guan> guans;
    private String gunTel;
    private String baojieTel;

    @Override
    protected SelectPresenter initPresenter() {
        return new SelectPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select2;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("选择发送人");
        goBack();
        cunList.setOnItemClickListener((parent, view, position, id) -> {
            mPresenter.getGuanList(cuntryList.get(position).getName(),position);
            mPresenter.getBaojie(cuntryList.get(position).getName(),position);
            cunPos = position;
            cunAdapter.notifyDataSetChanged();
        });

        guanList.setOnItemClickListener((parent, view, position, id) -> {

            guanPos = position;
            gunTel = guans.get(position).getTel();
            guanAdapter.notifyDataSetChanged();
        });
        baojieList.setOnItemClickListener((parent, view, position, id) -> {
            baoPos = position;
            baojieTel = baoJies.get(position).getPhone();
            baojieAdapter.notifyDataSetChanged();


        });
    }

    private void back() {
        Intent intent = new Intent();
        intent.putExtra(BAOJIE_NAME, gunTel+","+baojieTel);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void initDate() {
        cuntryList = getIntent().getParcelableArrayListExtra(SendActivity.CUNTRY_LIST);
        if (cuntryList == null) {
            cuntryList = new ArrayList<>();
            showError("获取村列表失败");
        }
        setCunAdapter();
    }

    private void setCunAdapter() {
//        adapter = new SelectAdapter(this, cuntryList, null, null);
        cunAdapter = new FastAdapter() {
            @Override
            public int getCount() {
                return cuntryList == null ? 0 : cuntryList.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(SelectActivity2.this).inflate(R.layout.select_list_item, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                CuntryDao.Cuntry cuntry = cuntryList.get(position);
                holder.ivCheck.setVisibility(View.GONE);
                holder.tvName.setText(cuntry.getName());
                if (cunPos != -1 && cunPos == position) {
                    holder.llPrent.setBackgroundColor(Color.WHITE);
                    holder.tvName.setTextColor(Color.RED);
                } else {
                    holder.llPrent.setBackgroundColor(Color.TRANSPARENT);
                    holder.tvName.setTextColor(getResources().getColor(R.color.text_color_normal));
                }

                return convertView;
            }
        };
        cunList.setAdapter(cunAdapter);
    }

    @Override
    public void showError(String msg) {

        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    @Override
    public void onGuanSuccess(GuanDao guanDao,int pos) {
        guans = guanDao.getData();
        if (guans == null) {
            guans = new ArrayList<>();
            showError("没有村管理员");
        }
        setGuanAdapter(guans);
    }

    private void setGuanAdapter(List<GuanDao.Guan> guans) {
        guanAdapter = new FastAdapter() {
            @Override
            public int getCount() {
                return guans.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(SelectActivity2.this).inflate(R.layout.select_list_item, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                GuanDao.Guan guan = guans.get(position);
                holder.ivCheck.setVisibility(View.VISIBLE);
                holder.tvName.setText(guan.getFullname());
                if (guanPos != -1 && guanPos == position) {
                    holder.ivCheck.setSelected(true);
                } else {
                    holder.ivCheck.setSelected(false);
                }

                return convertView;
            }
        };
        guanList.setAdapter(guanAdapter);
    }

    @Override
    public void onBaojieSuccess(BaojieDao baojieDao,int pos) {
        baoJies = baojieDao.getData();
        if (baoJies == null) {
            baoJies = new ArrayList<>();
            showError("没有保洁员");
        }
        setBaoAdapter(baoJies);
    }

    private void setBaoAdapter(List<BaojieDao.BaojieBean> baoJies) {
        baojieAdapter = new FastAdapter() {
            @Override
            public int getCount() {
                return baoJies.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = LayoutInflater.from(SelectActivity2.this).inflate(R.layout.select_list_item, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                BaojieDao.BaojieBean baoJie = baoJies.get(position);
                holder.ivCheck.setVisibility(View.VISIBLE);
                holder.tvName.setText(baoJie.getFullname());
                if (baoPos != -1 && baoPos == position) {
                    holder.ivCheck.setSelected(true);
                } else {
                    holder.ivCheck.setSelected(false);
                }

                return convertView;
            }
        };
        baojieList.setAdapter(baojieAdapter);
    }

    @Override
    public void onSuccess(CommonDao dao) {

    }

    @Override
    public void showLoading() {

        LoadingView.showLoading(this, "", false);
    }

    @Override
    public void dissmissLoading() {

        LoadingView.cancelLoading();
    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        back();
    }

    private class ViewHolder {
        TextView tvName;
        ImageView ivCheck;
        LinearLayout llPrent;

        ViewHolder(View view) {
            tvName = view.findViewById(R.id.tv_name);
            ivCheck = view.findViewById(R.id.iv_check);
            llPrent = view.findViewById(R.id.ll_prent);
        }
    }
}
