package com.qlckh.chunlvv.fragment;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.activity.AddressActivity;
import com.qlckh.chunlvv.activity.EvaluationDetailActivity;
import com.qlckh.chunlvv.activity.ExchangeActivity;
import com.qlckh.chunlvv.activity.FeedBackActivity;
import com.qlckh.chunlvv.activity.GuideActivity;
import com.qlckh.chunlvv.activity.MessageActivity;
import com.qlckh.chunlvv.activity.MyRecycleRecordActivity;
import com.qlckh.chunlvv.activity.PointExchangeActivity;
import com.qlckh.chunlvv.activity.SettingActivity;
import com.qlckh.chunlvv.adapter.HomepageClassifyAdapter;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseMvpFragment;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.MainDao;
import com.qlckh.chunlvv.impl.MainPresenterImpl;
import com.qlckh.chunlvv.presenter.MainPresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.user.UserRespDao;
import com.qlckh.chunlvv.usercase.UserEvent;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.utils.ScreenUtils;
import com.qlckh.chunlvv.view.CustomGridView;
import com.qlckh.chunlvv.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;

/**
 * @author Andy
 * @date 2018/8/20 17:32
 * Desc:
 */
public class MyFragment extends BaseMvpFragment<MainPresenter> implements MainView {
    @BindView(R.id.headImg)
    ImageView headImg;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.userinfoClick)
    RelativeLayout userinfoClick;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.classify_gd)
    CustomGridView classifyGd;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    protected int getContentView() {

        return R.layout.fragment_me;
    }

    @Override
    public void onSuccess(BannerDao dao) {

    }

    @Override
    public void initView() {

        UserRespDao.UserResp userResp = UserConfig.getUserResp();
        if (userResp != null) {
            setView(userResp);
        }

        EventBus.getDefault().register(this);
    }

    private void setView(UserRespDao.UserResp userResp) {
        GlideUtil.displayCircleImg(mContext, ApiService.IMG_URL + userResp.getImg(), headImg);
        username.setText(userResp.getFullname());
        tvPhone.setText(userResp.getPhone());
        tvScore.setText(MessageFormat.format("积分: {0}", userResp.getJifen()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPersonInfo(UserConfig.getUserid());
    }

    @Override
    public void initDate() {

        List<MainDao> datas = mPresenter.getMeDatas();
        HomepageClassifyAdapter adapter = new HomepageClassifyAdapter(mContext, datas);
        classifyGd.setAdapter(adapter);
        classifyGd.setVerticalSpacing(ScreenUtils.dp2px(mContext, 28f));
        classifyGd.setOnItemClickListener((parent, view, position, id) -> {

            MainDao mainDao = datas.get(position);
            switch (mainDao.getId()) {
//                我的数据","兑换记录","意见反馈","收货地址","我的评价","设置","操作文档","消息
                case 0:
                    startActivity(new Intent(mActivity, MyRecycleRecordActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(mActivity, PointExchangeActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(mActivity, FeedBackActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(mActivity, AddressActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(mActivity, EvaluationDetailActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(mActivity, SettingActivity.class));
                    break;
                case 6:
                    startActivity(new Intent(mActivity, GuideActivity.class));
                    break;
                case 7:
                    startActivity(new Intent(mActivity, MessageActivity.class));
                    break;
                default:
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserSuccess(UserEvent event) {
        UserRespDao.UserResp userResp = UserConfig.getUserResp();
        setView(userResp);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

        EventBus.getDefault().unregister(this);
    }

}
