package com.qlckh.chunlvv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlckh.chunlvv.R;
import com.qlckh.chunlvv.api.ApiService;
import com.qlckh.chunlvv.base.BaseMvpActivity;
import com.qlckh.chunlvv.dao.AddressDao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.MallDao;
import com.qlckh.chunlvv.http.RxHttpUtils;
import com.qlckh.chunlvv.impl.ExchangePresenterImpl;
import com.qlckh.chunlvv.presenter.ExchangePresenter;
import com.qlckh.chunlvv.user.UserConfig;
import com.qlckh.chunlvv.utils.GlideUtil;
import com.qlckh.chunlvv.utils.OnDialogClickListener;
import com.qlckh.chunlvv.view.HintDialog;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Andy
 * @date 2018/9/5 20:40
 * Desc:
 */
public class ExchangeActivity extends BaseMvpActivity<ExchangePresenter> implements ExchangePresenter.ExChangeView {

    public static final String MALL_BEAN = "#30d0af";
    @BindView(R.id.ll_no_address)
    LinearLayout llNoAddress;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address_phone)
    TextView tvAddressPhone;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.goods_img)
    ImageView goodsImg;
    @BindView(R.id.goods_name_tv)
    TextView goodsNameTv;
    @BindView(R.id.integral_tv)
    TextView integralTv;
    @BindView(R.id.tv_goods_subtract)
    TextView tvGoodsSubtract;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_goods_add)
    TextView tvGoodsAdd;
    @BindView(R.id.ll_do_shopping)
    LinearLayout llDoShopping;
    @BindView(R.id.tv_xiaohao)
    TextView tvXiaohao;
    @BindView(R.id.ll_xiao)
    LinearLayout llXiao;
    @BindView(R.id.tv_zong)
    TextView tvZong;
    @BindView(R.id.tv_kucun)
    TextView tvKuCun;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private MallDao.MallBean mallBean;

    @Override
    protected ExchangePresenter initPresenter() {
        return new ExchangePresenterImpl();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_exchange;
    }

    @Override
    protected boolean isSetFondSize() {
        return false;
    }

    private AddressDao.AddressBean bean;
    @Override
    public void onAddressSuccess(AddressDao dao) {
        if (dao!=null&&dao.getRow()!=null){
            List<AddressDao.AddressBean> addressBeans = dao.getRow();
            for (int i = 0; i < addressBeans.size(); i++) {
                if ("1".equals(addressBeans.get(i).getIsdefault())){
                    bean=addressBeans.get(i);
                    break;
                }
            }
         setAddress(bean);
        }else {
            llAddress.setVisibility(View.GONE);
            llNoAddress.setVisibility(View.VISIBLE);
        }

    }

    private void setAddress(AddressDao.AddressBean bean) {
        if (bean==null){
            llAddress.setVisibility(View.GONE);
            llNoAddress.setVisibility(View.VISIBLE);
        }else {
            llAddress.setVisibility(View.VISIBLE);
            llNoAddress.setVisibility(View.GONE);
            tvAddressName.setText(bean.getUsername());
            tvAddressPhone.setText(bean.getPhone());
            tvAddressDetail.setText(MessageFormat.format("{0}{1}", bean.getShippingaddress(), bean.getAddress()));
        }

    }

    @Override
    public void onSuccess(CommonDao dao) {

        showShort("订单提交成功");
        finish();
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

        setTitle("确认订单");
        goBack();
    }

    @Override
    public void initDate() {
        mPresenter.getAddress();
        Intent intent = getIntent();
        mallBean = intent.getParcelableExtra(MALL_BEAN);
        setGoods();


    }

    private void setGoods() {
        GlideUtil.displayRoundConnerImg(this, ApiService.IMG_URL+mallBean.getImgpath(),goodsImg);
        goodsNameTv.setText(mallBean.getPrdname());
        integralTv.setText(MessageFormat.format("积分: {0}", mallBean.getJifen()));
        tvZong.setText(UserConfig.getUserResp().getJifen());
        tvKuCun.setText(MessageFormat.format("库存 {0}", mallBean.getPrdnum()));
        tvXiaohao.setText(mallBean.getJifen());
    }

    @Override
    public void showError(String msg) {
        showShort(msg);
    }

    @Override
    public void release() {

        RxHttpUtils.cancelAllRequest();
    }

    private int count=1;
    @OnClick({R.id.tv_goods_subtract, R.id.tv_goods_add, R.id.bt_confirm,R.id.ll_no_address,R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_subtract:
                count--;
                setCount();
                break;
            case R.id.tv_goods_add:
                count++;
                setCount();
                break;
            case R.id.bt_confirm:
                confirm();
                break;
            case R.id.ll_no_address:
                Intent intent=new Intent(this,AddressActivity.class);
                intent.setAction(AddressActivity.CHOOSE_ADDRESS_ACTION);
                startActivityForResult(intent,10000);
                break;
            case R.id.ll_address:
                Intent intent1=new Intent(this,AddressActivity.class);
                intent1.setAction(AddressActivity.CHOOSE_ADDRESS_ACTION);
                startActivityForResult(intent1,10000);
                break;
                default:
        }
    }

    private void confirm() {
        if (bean==null){
            showShort("请添加收货地址");
            return;
        }
        if (Double.parseDouble(UserConfig.getUserResp().getJifen())<Double.parseDouble(tvXiaohao.getText().toString())){
            showShort("积分不足,快去赚取积分吧");
            return;
        }
        if (count>Integer.parseInt(mallBean.getPrdnum())){
            showShort("库存不足");
            return;
        }
        HintDialog.showHintDialog(this, "温馨提示", "购买商品将消耗" + tvXiaohao.getText().toString() + "积分是否确认订单",
                "确定", "取消", true, new OnDialogClickListener() {
                    @Override
                    public void onSureClick() {
                        mPresenter.order(bean.getUsername(),mallBean.getPrdname(),tvXiaohao.getText().toString().trim(),count+"",bean.getId(),
                                bean.getShippingaddress()+bean.getAddress(),bean.getPhone(), UserConfig.getUserid());
                    }

                    @Override
                    public void onCancleClick() {

                    }
                }
        );


    }

    private void setCount() {
        if (count<1){
            showShort("不能再小啦!!");
            return;
        }
        double jifen = Double.parseDouble(mallBean.getJifen()) * count;
        tvGoodsNum.setText(MessageFormat.format("{0}", count));
        tvXiaohao.setText(String.format("%s",jifen));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null&&resultCode==RESULT_OK&&requestCode==10000){
            bean=data.getParcelableExtra("bean");
            setAddress(bean);
        }
    }
}
