package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseActivity;
import com.qlckh.chunlvv.dao.GuideDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.http.interceptor.Transformer;
import com.qlckh.chunlvv.http.observer.CommonObserver;
import com.qlckh.chunlvv.http.utils.ScreenUtil;
import com.qlckh.chunlvv.http.utils.TimeUtil;
import com.qlckh.chunlvv.preview.ImgInfo;
import com.qlckh.chunlvv.preview.PrePictureActivity;
import com.qlckh.chunlvv.view.richtextview.XRichText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andy
 * @date 2018/9/7 16:25
 * Desc:
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_rich_content)
    XRichText tvRichContent;
    private ArrayList<ImgInfo> imgInfos=new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {

        setTitle("操作指南");
        goBack();

    }

    @Override
    public void initDate() {
        loading();
        RxHttpUtils.createApi(ApiService.class)
                .getGuide()
                .compose(Transformer.switchSchedulers())
                .subscribe(new CommonObserver<GuideDao>() {
                    @Override
                    protected void onError(String errorMsg) {
                       cancelLoading();
                       showError(errorMsg);
                    }

                    @Override
                    protected void onSuccess(GuideDao guideDao) {

                        cancelLoading();
                        if (guideDao!=null&&guideDao.getRow()!=null&&guideDao.getRow().size()>0) {
                            setView(guideDao.getRow().get(0));
                        }
                    }
                });

    }

    private void setView(GuideDao.GuideBean guideBean) {
        tvTitle.setText(guideBean.getTitle());
        tvRichContent.callback(new XRichText.BaseClickCallback() {
            @Override
            public void onImageClick(List<String> urlList, int position) {
                imgInfos.clear();
                for (int i = 0; i < urlList.size(); i++) {
                    ImgInfo info = new ImgInfo();
                    info.setBounds(new Rect(ScreenUtil.getScreenWidth(GuideActivity.this)/2-225,ScreenUtil.getScreenHeight(GuideActivity.this)/2-200,
                            ScreenUtil.getScreenWidth(GuideActivity.this)/2+225,ScreenUtil.getScreenHeight(GuideActivity.this)/2+200));
                    info.setUrl(urlList.get(i));
                    imgInfos.add(info);
                }
                PrePictureActivity.start(GuideActivity.this,imgInfos,position);
            }

            @Override
            public boolean onLinkClick(String url) {
                Intent intent = new Intent(GuideActivity.this, WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                return true;
            }

            @Override
            public void onFix(XRichText.ImageHolder holder) {
                super.onFix(holder);
                holder.setStyle(XRichText.Style.CENTER);
                //设置宽高
                holder.setWidth(550);
                holder.setHeight(400);

            }
        }).text(guideBean.getContent());
        tvTime.setText(TimeUtil.formatTime(guideBean.getAddtime(), TimeUtil.Y_M_D_H_M_S_24));


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
