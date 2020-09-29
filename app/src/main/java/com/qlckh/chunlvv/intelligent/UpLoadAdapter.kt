package com.qlckh.chunlvv.intelligent

import android.widget.EditText
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.dao.ScoreBean
import java.util.ArrayList

/**
 * @author Andy
 * @date   2020/8/29 13:57
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    UpLoadAdapter.kt
 */
class UpLoadAdapter(datas: ArrayList<ScoreBean>) :
        BaseQuickAdapter<ScoreBean, BaseViewHolder>(R.layout.upload_list_item, datas) {
    override fun convert(helper: BaseViewHolder?, item: ScoreBean?) {
        helper!!.setText(R.id.tvName, item!!.fullname)
        helper.setText(R.id.tvAddress, item.address)
        val etWeight = helper.getView<EditText>(R.id.etWeight)
        etWeight.setText(item.weight)
        helper.setText(R.id.tv_score, item.totalScore.toString())
        helper.addOnClickListener(R.id.tvSumbit)
    }
}
