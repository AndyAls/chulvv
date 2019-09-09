package com.qlckh.chunlvv.impl;

import android.content.Context;
import android.content.Intent;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.NewsActivity;
import com.qlckh.chunlvv.activity.WebViewActivity;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.common.GlideImageLoader;
import com.qlckh.chunlvv.common.XLog;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.MainDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.presenter.MainPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.user.UserRespDao;
import com.qlckh.chunlvv.usercase.MainUserCase;
import com.qlckh.chunlvv.usercase.UserEvent;
import com.qlckh.chunlvv.view.MainView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author Andy
 * @date 2018/5/17 11:18
 * Desc:
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mView;
    private MainUserCase mainUserCase;

    @Override
    public void getBanner() {
        XLog.e("--");
        if (mainUserCase == null) {
            mainUserCase = new MainUserCase();
        }
        Observable<BannerDao> bannerImg = mainUserCase.getBannerImg();
        bannerImg.subscribe(new CommonObserver<BannerDao>() {
            @Override
            protected void onError(String errorMsg) {

                mView.showError(errorMsg);
            }

            @Override
            protected void onSuccess(BannerDao bannerDao) {
                try {
                    mView.onSuccess(bannerDao);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public List<MainDao> getClassifyDatas(){
        ArrayList<MainDao> daos = new ArrayList<>();
        String[] classlyFont = {"巡检","数据回收","设备展示","历史排行","成功入驻"};
        int [] classlyFontIcon = {R.drawable.jiayuan,R.drawable.huanwei,R.drawable.jingqu,R.drawable.ic_rank_icon,R.drawable.bang};
        int [] ids=new int[]{0,1,2,4,5};
        for (int i = 0; i < classlyFont.length; i++) {
            MainDao mainDao = new MainDao();
            mainDao.setTitle(classlyFont[i]);
            mainDao.setIcon(classlyFontIcon[i]);
            mainDao.setId(ids[i]);
            daos.add(mainDao);
        }
        return daos;
    }

    @Override
    public List<MainDao> getMeDatas() {
        ArrayList<MainDao> daos = new ArrayList<>();
        String[] classlyFont = {"我的数据","兑换记录","意见反馈","收货地址","我的评价","设置","操作文档","消息"};
        int [] classlyFontIcon = {R.drawable.wshouji,R.drawable.wduihuan,R.drawable.wfankui,R.drawable.wshouhuo,
                R.drawable.wpinglun,R.drawable.wshezhi
                ,R.drawable.sywenhao,R.drawable.message_icon};
        int [] ids=new int[]{0,1,2,3,4,5,6,7};
        for (int i = 0; i < classlyFont.length; i++) {
            MainDao mainDao = new MainDao();
            mainDao.setTitle(classlyFont[i]);
            mainDao.setIcon(classlyFontIcon[i]);
            mainDao.setId(ids[i]);
            daos.add(mainDao);
        }
        return daos;
    }

    @Override
    public void getPersonInfo(String id) {
        RxHttpUtils.createApi(ApiService.class)
                .personInfo(id,UserConfig.getType()+"")
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<UserRespDao>() {
                    @Override
                    protected void onError(String errorMsg) {

                        mView.showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(UserRespDao userRespDao) {
                        UserRespDao.UserResp row = userRespDao.getRow();
                        if (row.getJifen()==null){
                            row.setJifen("0");
                        }
                        UserConfig.userResp=row;
                        EventBus.getDefault().post(new UserEvent());
                    }
                });
    }

    @Override
    public List<MainDao> getDatas() {
        List<MainDao> daos = new ArrayList<>();
        int type = UserConfig.getType();
        String[] titles=null;
        int[] icons =null;
        int[]ids=null;
        switch (type) {
            case 0:
                titles = new String[]{
                        "考勤打卡",
                        "评分",
                        "事件处理",
                        "桶报废",
                        "意见反馈",
                        "我的消息",
                        "电子秤",
                        "回收",
                        "设置"
                };
                icons = new int[]{
                        R.drawable.attendance_icon,
                        R.drawable.mark_icon,
                        R.drawable.event_icon,
                        R.drawable.scrap_icon,
                        R.drawable.feedback_icon,
                        R.drawable.message_icon,
                        R.drawable.order_icon,
                        R.drawable.baojie_icon,
                        R.drawable.setting_icon,
                };
                ids=new int[]{
                        0,
                        1,
                        2,
                        3,
                        4,
                        5,
                        15,
                        16,
                        6};
                break;
            case 2:

                titles = new String[]{
                        "下发任务","事件处理","我的消息","桶报废","意见反馈","保洁员管理","设置"
                };
                icons = new int[]{
                        R.drawable.order_icon,
                        R.drawable.event_icon,
                        R.drawable.message_icon,
                        R.drawable.scrap_icon,
                        R.drawable.feedback_icon,
                        R.drawable.baojie_icon,
                        R.drawable.setting_icon,
                };
                ids=new int[]{7,2,5,3,4,11,6};
                break;
            case 1:
                titles = new String[]{
                        "下发任务","即时通讯","任务反馈","桶报废","保洁员管理","设置"
                };
                icons = new int[]{
                        R.drawable.order_icon,
                        R.drawable.send_icon,
                        R.drawable.feedback_icon,
                        R.drawable.scrap_icon,
                        R.drawable.baojie_icon,
                        R.drawable.setting_icon,
                };
                ids=new int[]{7,8,9,3,11,6};
                break;
            default:
        }

        //,"事件处理","桶报废","意见反馈","我的消息",

        assert titles != null;
        for (int i = 0; i < titles.length; i++) {
            MainDao mainDao = new MainDao();
            mainDao.setTitle(titles[i]);
            mainDao.setIcon(icons[i]);
            mainDao.setId(ids[i]);
            daos.add(mainDao);
        }
        return daos;
    }

    @Override
    public void showBanner(Context context, List<BannerDao.ImgBean> imgs, Banner braner) {


        List<String> imgUrl = new ArrayList<>();
        List<String> title = new ArrayList<>();

        for (int i = 0; i < (imgs.size()>5?5:imgs.size()); i++) {
            imgUrl.add(ApiService.IMG_URL + imgs.get(i).getImg());
            title.add(imgs.get(i).getTitle());
        }
        braner.setImageLoader(new GlideImageLoader());
        braner.setImages(imgUrl);
        braner.setBannerTitles(title);
        braner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        braner.isAutoPlay(true);
        braner.setDelayTime(2500);
        braner.setIndicatorGravity(BannerConfig.RIGHT);
        braner.start();
        braner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra(NewsActivity.NEWS_CONTENT,imgs.get(position).getContent());
                intent.putExtra(NewsActivity.NEWS_TIME,imgs.get(position).getAddtime());
                intent.putExtra(NewsActivity.NEWS_TITLE,imgs.get(position).getTitle());
//                intent.putExtra("url", imgs.get(position).ge);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void register(MainView view) {
        mView = view;
    }

    @Override
    public void unregister(MainView view) {
        if (mView != null) {
            mView = null;
        }
    }
}
