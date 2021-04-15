package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.adapter.MainAdapter;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.api.TestActivity;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.common.GlideImageLoader;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.HomeDao;
import com.qlckh.chunlvv.dao.MainDao;
import com.qlckh.chunlvv.dao.TitleDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.impl.MainPresenterImpl;
import com.qlckh.chunlvv.presenter.MainPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.user.UserInfo;
import com.qlckh.chunlvv.usercase.BadgeEvent;
import com.qlckh.chunlvv.usercase.MessageEvent;
import com.qlckh.chunlvv.utils.JsonUtil;
import com.qlckh.chunlvv.utils.ScreenUtils;
import com.qlckh.chunlvv.utils.SpUtils;
import com.qlckh.chunlvv.view.MainView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/5/15 16:47
 * Desc: 主界面
 */
public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainView {
    private static final String BADGE = "BADGE";
    private static final String MESSAGE = "MESSAGE";
    @BindView(R.id.braner)
    Banner braner;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<BannerDao.ImgBean> imgs;
    private BadgeEvent mBadge;
    private MainAdapter adapter;
    private MessageEvent message;
    private boolean isN5;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isN5 = SpUtils.getBooleanParam(this, "N5", false);
        mPresenter.getPersonInfo(UserConfig.getUserid());
    }

    @Override
    public void initView() {

        if (UserConfig.getType() == 0) {
            ibBack.setVisibility(View.GONE);
        } else {
            ibBack.setVisibility(View.VISIBLE);
            goBack();
        }

        isN5 = SpUtils.getBooleanParam(this, "N5", false);
        setTopTitle();
        mPresenter.getBanner();
        EventBus.getDefault().register(this);
    }

    private void setTopTitle() {

        UserInfo userInfo = UserConfig.getUserInfo();


        if (userInfo==null||isEmpty(userInfo.getFull())){
           getTopTitle();
        }else {
            setTitle(userInfo.getFull());
        }
    }

    private void setTopTtilebean(TitleDao.TitleBean dao) {
        if (dao!=null&&!isEmpty(dao.getFull())) {
            setTitle(dao.getFull());
        } else {
            setTitle("首页");
        }
    }

    private void getTopTitle() {

        RxHttpUtils.createApi(ApiService.class)
                .getTitle(UserConfig.getUserid(),UserConfig.getType())
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<TitleDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                        setTopTtilebean(new TitleDao.TitleBean());
                    }

                    @Override
                    protected void onSuccess(TitleDao titleDao) {

                        if (titleDao!=null) {
                            setTopTtilebean(titleDao.getRow());
                        }else {
                            setTopTtilebean(new TitleDao.TitleBean());
                        }
                    }
                });
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        List<MainDao> mDatas = mPresenter.getDatas();
        adapter = new MainAdapter(this, mDatas, mBadge, message);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int spacing = ScreenUtils.px2dp(MainActivity.this, 15);
                int column = position % 3;
                outRect.left = column * spacing / 3;
                outRect.right = spacing - (column + 1) * spacing / 3;
                if (position >= 3) {
                    outRect.top = spacing;
                }
            }
        });

        adapter.setonItemClickListener((view, position) -> {
            MainDao mainDao = mDatas.get(position);
            int id = mainDao.getId();
            Intent intent = new Intent();
            switch (id) {
                case 0:
                    //签到ok
                    intent.setClass(MainActivity.this, AttendanceActivity.class);
                    break;
                //评分ok
                case 1:
                    if (isN5) {
                        intent.setClass(MainActivity.this, MarkActivity.class);
                    }else {
                        intent.setClass(MainActivity.this,Mark1Activity.class);
                    }
                    break;
                //事件处理
                case 2:
                    intent.setClass(MainActivity.this, TaskWorkingAcitivty.class);
                    break;
                //桶报废ok
                case 3:
                    if (isN5) {
                        intent.setClass(MainActivity.this, Scrap1Activity.class);
                    }else {
                        intent.setClass(MainActivity.this,ScanActivity.class);
                    }
                    break;
                //意见反馈ok
                case 4:
                    intent.setClass(MainActivity.this, FeedBackActivity.class);
                    break;
                //我的消息
                case 5:
                    intent.setClass(MainActivity.this, MessageActivity.class);
                    break;
                //设置
                case 6:
                    intent.setClass(MainActivity.this, SettingActivity.class);
                    break;
                //下发任务
                case 7:
                    intent.setClass(MainActivity.this, OrderActivity.class);
                    overridePendingTransition(0, 0);
                    break;
                //即时通讯
                case 8:
                    intent.setClass(MainActivity.this, SendActivity.class);
                    break;
                //任务反馈
                case 9:
                    intent.setClass(MainActivity.this, TaskWorkingAcitivty.class);
                    break;
                case 11:
                    intent.setClass(MainActivity.this, PurifierManagerActivity.class);
                    break;
                //称重
                case 15:
                    intent.setClass(MainActivity.this, ScaleActivity.class);
                    break;
                //回收
                case 16:
                    intent.setClass(MainActivity.this, RecycleActivity.class);
                    break;
                default:


            }
            startActivity(intent);
        });
    }

    @Override
    protected void initSlidr() {

        if (UserConfig.getType() != 0) {
            super.initSlidr();
        }
    }

    @Override
    public void initDate() {
        mPresenter.getBanner();
        String s = SpUtils.getStringParam(this, BADGE, "");
        this.mBadge = JsonUtil.json2Object2(s, BadgeEvent.class);
        String param = SpUtils.getStringParam(this, MESSAGE, "");
        this.message = JsonUtil.json2Object2(param, MessageEvent.class);
        initRecyclerView();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshBadge(BadgeEvent badgeEvent) {
        this.mBadge = badgeEvent;
        if (adapter != null) {
            adapter.setmBadge(badgeEvent);
            adapter.notifyDataSetChanged();
        }
        String json = JsonUtil.object2Json(badgeEvent);
        SpUtils.putParam(this, BADGE, json);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageBadge(MessageEvent messageEvent) {
        this.message = messageEvent;
        if (adapter != null) {
            adapter.setMsgBadge(messageEvent);
            adapter.notifyDataSetChanged();
        }
        String json = JsonUtil.object2Json(messageEvent);
        SpUtils.putParam(this, MESSAGE, json);
    }

    @Override
    public void release() {
        RxHttpUtils.cancelAllRequest();
        EventBus.getDefault().unregister(this);

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
            mPresenter.showBanner(this, imgs, braner);

        } else {
            showError(dao.getMsg());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        braner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        braner.stopAutoPlay();
    }

    long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (UserConfig.getType() == 0) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showShort("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        } else {
            super.onKeyDown(keyCode, event);
        }

        return true;

    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }
}
