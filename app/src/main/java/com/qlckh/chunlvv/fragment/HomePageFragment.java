package com.qlckh.chunlvv.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.DeviceActivity;
import com.qlckh.chunlvv.activity.EntrantActivity;
import com.qlckh.chunlvv.activity.MainActivity;
import com.qlckh.chunlvv.activity.NewsActivity;
import com.qlckh.chunlvv.activity.NewsMoreActivity;
import com.qlckh.chunlvv.activity.RankActivity;
import com.qlckh.chunlvv.activity.WasteRecycleActivity;
import com.qlckh.chunlvv.adapter.HomepageClassifyAdapter;
import com.qlckh.chunlvv.adapter.NewsAdapter;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseMvpFragment;
import com.qlckh.chunlvv.common.GlideImageLoader;
import com.qlckh.chunlvv.common.VpRecyclerView;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.MainDao;
import com.qlckh.chunlvv.dao.TitleDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.impl.MainPresenterImpl;
import com.qlckh.chunlvv.presenter.MainPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.user.UserInfo;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.view.CustomGridView;
import com.qlckh.chunlvv.view.MainView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
//import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Andy
 * @date 2018/8/16 15:40
 * Desc: 巡检主页
 */
public class HomePageFragment extends BaseMvpFragment<MainPresenter> implements MainView {
    private static final int REQUEST_CODE_PICK_CITY = 10000;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.braner)
    Banner braner;
    @BindView(R.id.classify_gd)
    CustomGridView classifyGd;
    @BindView(R.id.news_more_img)
    ImageView newsMoreImg;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.recyclerView)
    VpRecyclerView recyclerView;

    private List<BannerDao.ImgBean> imgs;
    private NewsAdapter newsAdapter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void initView() {

        if (UserConfig.getType() != 0) {
            UserInfo userInfo = UserConfig.getUserInfo();
            if (!isEmpty(userInfo.getFull())) {
                setTitle(userInfo.getFull());
            } else {
                setTitle("首页");
            }
        } else {
            getTopTitle();
        }

        initRv();

        List<MainDao> classifyDatas = mPresenter.getClassifyDatas();
        if (UserConfig.getType() != 1) {
            classifyDatas.remove(0);
        }
        HomepageClassifyAdapter adapter = new HomepageClassifyAdapter(mContext, classifyDatas);
        classifyGd.setAdapter(adapter);
        classifyGd.setOnItemClickListener((parent, view, position, id) -> {

            int ids = classifyDatas.get(position).getId();
            switch (ids) {
//                巡检
                case 0:
                    startActivity(new Intent(mContext, MainActivity.class));
                    break;
//                数据回收
                case 1:
                    startActivity(new Intent(mContext, WasteRecycleActivity.class));
                    break;
//                设备展示
                case 2:
                    startActivity(new Intent(mContext, DeviceActivity.class));
                    break;
//                景区设备
                case 3:
                    break;
//                历史排行
                case 4:
                    startActivity(new Intent(mContext, RankActivity.class));
                    break;
//                成功入驻
                case 5:
                    startActivity(new Intent(mContext, EntrantActivity.class));
                    break;
                default:
            }

        });
    }

    private void initRv() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayout.VERTICAL));
        newsAdapter = new NewsAdapter(mContext);
        recyclerView.setAdapter(newsAdapter);

    }

    private void setTitle(String full) {
        tvTitle.setText(full);
    }

    private void getTopTitle() {

        RxHttpUtils.createApi(ApiService.class)
                .getTitle(UserConfig.getUserid(), UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<TitleDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        setTopTitle(new TitleDao.TitleBean());
                    }

                    @Override
                    protected void onSuccess(TitleDao titleDao) {

                        if (titleDao != null) {
                            setTopTitle(titleDao.getRow());
                        } else {
                            setTopTitle(new TitleDao.TitleBean());
                        }
                    }
                });
    }

    private void setTopTitle(TitleDao.TitleBean dao) {
        if (dao != null && !isEmpty(dao.getFull())) {
            setTitle(dao.getFull());
        } else {
            setTitle("首页");
        }
    }

    @Override
    public void initDate() {
        mPresenter.getBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPersonInfo(UserConfig.getUserid());
    }

    public void setLocationTv(String address) {
        locationTv.setText(address);
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
        showErrorBranner();
    }

    private void showErrorBranner() {
        braner.setImageLoader(new GlideImageLoader());
        List<Integer> mList = new ArrayList<>();
        mList.add(R.drawable.error);
        mList.add(R.drawable.error);
        braner.setImages(mList);
        braner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        braner.isAutoPlay(true);
        braner.setIndicatorGravity(BannerConfig.RIGHT);
        braner.start();
    }

    @Override
    public void release() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY) {
            if (data != null) {
//                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
//                locationTv.setText(city);
            }
        }
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void onSuccess(BannerDao dao) {

        XLog.e("---", dao);
        if (dao == null) {
            showError("轮播图获取失败");
            return;
        }
        if (dao.getStatus() == 1) {
            imgs = dao.getRow();
            Collections.sort(imgs, (o1, o2) -> Integer.compare(Integer.parseInt(o2.getFlag()), Integer.parseInt(o1.getFlag())));
            mPresenter.showBanner(mContext, imgs, braner);

            setNews(imgs);

        } else {
            showError(dao.getMsg());
        }
    }

    private void setNews(List<BannerDao.ImgBean> imgBean) {
        newsAdapter.setNewsData(imgBean);
    }

    @OnClick({R.id.location_tv, R.id.tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_tv:
                //定位
                /*startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);*/
                break;
            case R.id.tv_more:
                //新闻更多
                startActivity(new Intent(mContext, NewsMoreActivity.class));
                break;
            default:
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
