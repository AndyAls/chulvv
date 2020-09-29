package com.qlckh.chunlvv.intelligent

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.ScoreDB
import com.qlckh.chunlvv.api.ApiService
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.common.MediaPlayerHelper
import com.qlckh.chunlvv.dao.ScoreBean
import com.qlckh.chunlvv.http.RxHttpUtils
import com.qlckh.chunlvv.http.interceptor.Transformer
import com.qlckh.chunlvv.http.observer.CommonObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_upload_list.*

/**
 * @author Andy
 * @date   2020/8/29 11:07
 * Desc:
 */
class UpLoadListActivity : BaseActivity() {
    private lateinit var adapter: UpLoadAdapter
    private lateinit var mediaPlayerHelper: MediaPlayerHelper
    private var disposable = CompositeDisposable()
    override fun getContentView(): Int {

        return R.layout.activity_upload_list
    }

    override fun isSetFondSize(): Boolean {
        return false
    }

    override fun initDate() {

        val subscribe = ScoreDB.getInstance().scoreDao.queryListObserver()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.replaceData(it)
                    adapter.setOnItemChildClickListener { adapter1, view, position ->
                        val scoreBean = adapter1.data[position] as ScoreBean
                        when (view.id) {
                            R.id.tvSumbit -> {
                                postData(-1, scoreBean, null)
                            }
                        }
                    }
                    if (it == null || it.size == 0) {
                        finish()
                    }
                }
        disposable.add(subscribe)
        val data = adapter.data
        tvUpLoad.setOnClickListener {
            data.forEachIndexed { index, scoreBean ->
                postData(index, scoreBean,data)
            }

        }


    }

    private fun postData(index: Int, scoreBean: ScoreBean?, data: MutableList<ScoreBean>?) {

        if (scoreBean == null) return
        var imagesId = "0"
        if (scoreBean.imgs.isNotEmpty()) {
            imagesId = scoreBean.imgs[0] ?: "0"
        }
        loading()
        RxHttpUtils.createApi(ApiService::class.java)
                .mark1(imagesId, scoreBean.whatType, scoreBean.bucketScore, scoreBean.userId, imagesId,
                        scoreBean.totalScore.toString(), scoreBean.weight,
                        scoreBean.userName, scoreBean.fullItems, scoreBean.fullPhone, scoreBean.fullId)
                .compose(Transformer.switchSchedulers())
                .subscribe(object : CommonObserver<Any>() {
                    override fun onError(errorMsg: String) {
                        cancelLoading()
                        showLong("上传失败")
                    }

                    override fun onSuccess(homeInfo: Any) {
                        cancelLoading()
                        if (index == -1) {
                            mediaPlayerHelper.startPlay(R.raw.hege)
                        }else{
                            if (index==data!!.size-1){
                                mediaPlayerHelper.startPlay(R.raw.hege)
                            }
                        }
                        try {
                            val queryById = ScoreDB.getInstance().scoreDao.queryById(scoreBean.id)
                            if (queryById != null) {
                                ScoreDB.getInstance().scoreDao.delect(scoreBean)
                            }
                        } catch (e: Exception) {

                        }
                    }
                })
    }

    override fun initView() {
        mediaPlayerHelper = MediaPlayerHelper.getInstance(this)
        setTitle("待上传")
        goBack()
        val manager = LinearLayoutManager(this)
        upRv.layoutManager = manager
        upRv.setHasFixedSize(true)
        upRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = UpLoadAdapter(arrayListOf<ScoreBean>())
        upRv.adapter = adapter
    }

    override fun showError(msg: String?) {
    }

    override fun release() {
        disposable.clear()
    }
}