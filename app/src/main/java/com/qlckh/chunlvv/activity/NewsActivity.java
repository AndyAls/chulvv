package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.base.BaseActivity;
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
 * @date 2018/8/13 11:32
 * Desc: 新闻详情
 */
public class NewsActivity extends BaseActivity {
    public static final String NEWS_TITLE = "NEWS_TITLE";
    public static final String NEWS_TIME = "NEWS_TIME";
    public static final String NEWS_CONTENT = "NEWS_CONTENT";
    private ArrayList<ImgInfo> imgInfos=new ArrayList<>();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_rich_content)
    XRichText tvRichContent;

    @Override
    protected int getContentView() {
        return R.layout.activitity_news;
    }

    @Override
    protected boolean isSetFondSize() {
        return true;
    }

    @Override
    public void initView() {
        setTitle("新闻阅读");
        goBack();

    }

    @Override
    public void initDate() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(NEWS_TITLE);
        String time = intent.getStringExtra(NEWS_TIME);
        String content = intent.getStringExtra(NEWS_CONTENT);
        tvTitle.setText(title);
        tvRichContent.callback(new XRichText.BaseClickCallback() {
            @Override
            public void onImageClick(List<String> urlList, int position) {
                imgInfos.clear();
                for (int i = 0; i < urlList.size(); i++) {
                    ImgInfo info = new ImgInfo();
                    info.setBounds(new Rect(ScreenUtil.getScreenWidth(NewsActivity.this)/2-225,ScreenUtil.getScreenHeight(NewsActivity.this)/2-200,
                            ScreenUtil.getScreenWidth(NewsActivity.this)/2+225,ScreenUtil.getScreenHeight(NewsActivity.this)/2+200));
                    info.setUrl(urlList.get(i));
                    imgInfos.add(info);
                }
                PrePictureActivity.start(NewsActivity.this,imgInfos,position);
            }

            @Override
            public boolean onLinkClick(String url) {
                Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
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
        }).text(content);
        tvTime.setText(TimeUtil.formatTime(time, TimeUtil.Y_M_D_H_M_S_24));


    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void release() {

    }
}
