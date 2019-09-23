package com.qlckh.chunlvv.activity

import android.app.AlertDialog
import android.content.Intent
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.dao.HomeDao
import com.qlckh.chunlvv.utils.JsonUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_text.*
import uhf.MultiLableCallBack
import uhf.Reader

/**
 * @author Andy
 * @date   2019/9/9 11:37
 * Desc:
 */
class TextActivity : BaseActivity(){
    val json="{\n" +
            "    \"address\": \"浙江省新昌县羽林街道孟家塘\",\n" +
            "    \"addtime\": \"1533892131\",\n" +
            "    \"adduser\": \"admin\",\n" +
            "    \"cardid\": \"445454564545\",\n" +
            "    \"company\": \"3-105\",\n" +
            "    \"dangid\": \"0\",\n" +
            "    \"erimg\": \"Uploads/QRcode/3-105.jpg\",\n" +
            "    \"fullname\": \"杨打定\",\n" +
            "    \"huiid\": \"3\",\n" +
            "    \"id\": \"5\",\n" +
            "    \"img\": \"2018-08-10/5b6d562318d6d.png\",\n" +
            "    \"items\": \"11323337\",\n" +
            "    \"jicode\": \"0\",\n" +
            "    \"jifen\": \"88\",\n" +
            "    \"name\": \"--\",\n" +
            "    \"names\": \"124\",\n" +
            "    \"phone\": \"13130724171\",\n" +
            "    \"pwd\": \"e10adc3949ba59abbe56e057f20f883e\",\n" +
            "    \"sex\": \"男\",\n" +
            "    \"topflag\": \"4\"\n" +
            "}"
    override fun initView() {

        tvSplit.setOnClickListener {
            val homeDao = JsonUtil.json2Object2(json, HomeDao::class.java)
//        if (!homeDao.getCaiid().equals(UserConfig.getUserid()+"")){
//            showShort("您没有权限评分");
//            return;
//        }
            val intent = Intent(this, CompositeActivity::class.java)
            intent.putExtra("HOME_DAO", homeDao)
            overridePendingTransition(0, 0)
            startActivity(intent)

        }
    }

    override fun showError(msg: String?) {
    }

    override fun getContentView(): Int {
        return R.layout.activity_text
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initDate() {
    }

    override fun release() {
    }
}