package com.qlckh.chunlvv.api;

import com.qlckh.chunlvv.dao.AddressDao;
import com.qlckh.chunlvv.dao.BannerDao;
import com.qlckh.chunlvv.dao.BaojieDao;
import com.qlckh.chunlvv.dao.CategoryDao;
import com.qlckh.chunlvv.dao.Comm2Dao;
import com.qlckh.chunlvv.dao.CommonDao;
import com.qlckh.chunlvv.dao.CunGuanDao;
import com.qlckh.chunlvv.dao.CunListDao;
import com.qlckh.chunlvv.dao.CuntryDao;
import com.qlckh.chunlvv.dao.DeviceDao;
import com.qlckh.chunlvv.dao.EvaluationDao;
import com.qlckh.chunlvv.dao.EventListDao;
import com.qlckh.chunlvv.dao.GuanDao;
import com.qlckh.chunlvv.dao.GuideDao;
import com.qlckh.chunlvv.dao.HandDao;
import com.qlckh.chunlvv.dao.HomeInfo;
import com.qlckh.chunlvv.dao.InMsgDao;
import com.qlckh.chunlvv.dao.MallCatgrayDao;
import com.qlckh.chunlvv.dao.MallDao;
import com.qlckh.chunlvv.dao.OrderDao;
import com.qlckh.chunlvv.dao.OutMessageDao;
import com.qlckh.chunlvv.dao.PointDao;
import com.qlckh.chunlvv.dao.ProcuteDao;
import com.qlckh.chunlvv.dao.RankDao;
import com.qlckh.chunlvv.dao.RecordDao;
import com.qlckh.chunlvv.dao.RecycleDao;
import com.qlckh.chunlvv.dao.ScanCount;
import com.qlckh.chunlvv.dao.ScanListDao;
import com.qlckh.chunlvv.dao.SignDao;
import com.qlckh.chunlvv.dao.TitleDao;
import com.qlckh.chunlvv.qidian.StoreDao;
import com.qlckh.chunlvv.user.UseDo;
import com.qlckh.chunlvv.user.UserRespDao;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author Andy
 * @date 2018/5/15 19:20
 * Desc:
 */
public interface ApiService {

    //    String BASE_URL = "http://chunlv.hanziyi.cn/";
    String BASE_URL = "http://qidian.365igc.cn/";
    long DEFAULT_TIME = 30;
    String IMG_URL = "http://chunlv.hanziyi.cn/Uploads/Public/";

    /**
     * 登录接口
     */
    @FormUrlEncoded
    @POST("index.php/Api/index/login")
    Observable<UseDo> login(@Field("username") String name,
                            @Field("pwd") String pwd, @Field("status") int type);

    /**
     * banner图
     */
    @GET("index.php/api/anyu/news")
    Observable<BannerDao> getBanner(@Query("status") int status, @Query("uid") String userid);

    /**
     * 签到
     */
    @FormUrlEncoded
    @POST("index.php/Api/suggest/dack")
    Observable<CommonDao> signin(@Field("id") int userId, @Field("status") int state, @Field("address") String address);

    /**
     * 综合打分
     */
    @FormUrlEncoded
    @POST("index.php/api/suggest/huanjing")
    Observable<CommonDao> compositeSubmit(@Field("username") String username, @Field("usercode") String usercode,
                                          @Field("adduser") String adduser, @Field("adduserid") String adduserid,
                                          @Field("ji_class") int ji_class,
                                          @Field("ji_she") int ji_she, @Field("ji_qing") int ji_qing,
                                          @Field("address") String address, @Field("zong") int zong,
                                          @Field("whattype") String whattype,
                                          @Field("tel") String tel, @Field("imgpath") String imgpath, @Field("ji_duinum") String weight);


    /**
     * 环境打分
     */
    @FormUrlEncoded
    @POST("index.php/api/suggest/huanjing")
    Observable<CommonDao> sanitationSubmit(@Field("username") String username, @Field("usercode") String usercode,
                                           @Field("adduser") String adduser, @Field("adduserid") String adduserid,
                                           @Field("ji_class") int ji_class,
                                           @Field("address") String address,
                                           @Field("zong") int zong, @Field("whattype") String whattype,
                                           @Field("tel") String tel, @Field("imgpath") String imgpath, @Field("ji_duinum") String weight);


    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("index.php/Api/suggest/index")
    Observable<CommonDao> feedSubmit(@Field("id") String userId, @Field("status") int status, @Field("content") String content,
                                     @Field("img") String img);


    /**
     * 桶报废
     */
    @FormUrlEncoded
    @POST("index.php/Api/suggest/scrap")
    Observable<CommonDao> scrapSubmit(@Field("id") String userid, @Field("fullname") String fullname,
                                      @Field("img") String img,
                                      @Field("suggest") String suggest,
                                      @Field("address") String address

    );


    /**
     * 账户消息
     */
    @GET("index.php/Api/suggest/message")
    Observable<InMsgDao> getMsgList(@Query("id") String id, @Query("status") int status);


    /**
     * 平台消息
     */

    @GET("index.php/Api/suggest/platform")
    Observable<OutMessageDao> getMsgOutList(@Query("id") String id, @Query("status") int status);

    /**
     * 账户消息已读
     */
    @GET("index.php/Api/suggest/read")
    Observable<CommonDao> updateMessageState(@Query("id") String messageid);

    /**
     * 平台消息已读
     */
    @GET("index.php/Api/suggest/isread")
    Observable<CommonDao> updateMessageState2(@Query("id") String messageid);

    /**
     * 获取村
     *
     * @return
     */
    @GET("index.php/api/suggest/guan")
    Observable<CunGuanDao> getCuntryList(@Query("id") String id, @Query("status") int status);

    @GET("index/index/list_cgs")
    Observable<GuanDao> getGuanList(@Query("address") String address);

    /**
     * 获取保洁员
     */
    @GET("index.php/Api/suggest/coll")
    Observable<BaojieDao> getBaojieList(@Query("id") String id);


    /**
     * 下发任务
     */
    @FormUrlEncoded
    @POST("index.php/Api/suggest/task")
    Observable<CommonDao> order(@Field("userid") String userid, @Field("id") String id, @Field("taskid") String taskid,
                                @Field("title") String title, @Field("content") String content, @Field("img") String img);


    /**
     * 事件列表
     */
    @FormUrlEncoded
    @POST("index.php/Api/suggest/event")
    Observable<EventListDao> getEventList(@Field("id") String id, @Field("status") int status);

    /**
     * 审核
     */
    @GET("index.php/api/anyu/shenhe")
    Observable<CommonDao> sure(@Query("id") String id, @Query("status") int status);

    @FormUrlEncoded
    @POST("index.php/api/suggest/add")
    Observable<CommonDao> handEvent(@Field("titles") String titles,
                                    @Field("id") String id, @Field("imgpath") String imgpath);


    @FormUrlEncoded
    @POST("index.php/Api/suggest/txun")
    Observable<CommonDao> sendSumbit(@Field("id") String fromid, @Field("userid") String gotoid,
                                     @Field("title") String title, @Field("content") String content);


    @GET("index.php/Api/suggest/scan")
    Observable<ScanCount> getScanCount(@Query("id") String id, @Query("time") String time, @Query("status") int status);

    @GET("index.php/Api/suggest/addscan")
    Observable<CommonDao> addScan(@Query("id") String id, @Query("caijiid") String caijiid);

    /**
     * 保洁员管理
     */
    @GET("index.php/Api/suggest/scan")
    Observable<ScanListDao> getScanList(@Query("time") String starttime,
                                        @Query("id") String id, @Query("status") int status);


    @FormUrlEncoded
    @POST("index/index/cun")
    Observable<CunListDao> getCunList(@Field("id") String id);

    @GET("index.php/api/suggest/seldack")
    Observable<SignDao> getSigin(@Query("id") String id, @Query("addtime") String addtime);

    /**
     * 商品分类
     */
    @GET("index.php/Api/goods/fenlei")
    Observable<MallCatgrayDao> getMallCatgray(@Query("id") String userId);

    /**
     * 分类商品
     */
    @GET("index.php/Api/goods/index")
    Observable<MallDao> getMall(@Query("id") String id);

    /**
     * 获取个人信息
     */
    @GET("index.php/Api/goods/user")
    Observable<UserRespDao> personInfo(@Query("id") String id, @Query("status") String status);

    @GET("index.php/Api/anyu/indexs")
    Observable<RecycleDao> getRecycle(@Query("status") int status, @Query("id") String id,
                                      @Query("starttime") String starttime, @Query("endtime") String endtime,
                                      @Query("Page") int page, @Query("PageSize") int PageSize);

    /**
     * 呼叫等待物品分类
     */
    @GET("index.php/Api/anyu/hfl")
    Observable<CategoryDao> getCategory();

    /**
     * 呼叫等待物品
     */
    @GET("index.php/Api/anyu/fclass")
    Observable<ProcuteDao> getProcute(@Query("id") String id);

    /**
     * 提交回收订单
     */
    @FormUrlEncoded
    @POST("index.php/api/anyu/htjioa")
    Observable<CommonDao> recycleSumbit(@FieldMap Map<String, String> parmas);


    /**
     * 获取地址
     */
    @GET("index.php/Api/goods/address")
    Observable<AddressDao> getAddress(@Query("id") String id, @Query("status") int status);

    /**
     * 删除地址
     */
    @GET("index.php/api/goods/del")
    Observable<CommonDao> delAddress(@Query("id") String id);

    /**
     * 默认地址
     */
    @GET("index.php/Api/goods/edit")
    Observable<CommonDao> selAddress(@Query("id") String id);

    /**
     * 新增地址
     */
    @FormUrlEncoded
    @POST("index.php/Api/goods/add")
    Observable<CommonDao> addAddress(@FieldMap Map<String, String> map);

    /**
     * 修改地址
     */
    @FormUrlEncoded
    @POST("index.php/Api/goods/edits")
    Observable<CommonDao> editAddress(@FieldMap Map<String, String> map);

    /**
     * 积分订单提交
     */
    @GET("index.php/Api/goods/goods")
    Observable<CommonDao> goodsOrder(@Query("username") String username, @Query("prdname") String prdname,
                                     @Query("jifen") String jifen, @Query("num") String num,
                                     @Query("addressid") String addressid, @Query("address") String address,
                                     @Query("phone") String phone, @Query("id") String id);

    /**
     * 回收订单列表
     */
    @GET("index.php/Api/order/index")
    Observable<OrderDao> getOrders(@Query("id") String id, @Query("flag") int flag, @Query("status") int status);

    @FormUrlEncoded
    @POST("index.php/api/order/ping")
    Observable<CommonDao> evaluation(@Field("userid") String userid, @Field("p_xing") String p_xing, @Field("user") String user,
                                     @Field("content") String content, @Field("orderid") String orderid, @Field("img") String img);

    @GET("index.php/api/user/wping")
    Observable<EvaluationDao> getEvaDetail(@Query("id") String id, @Query("status") String status, @Query("name") String name);

    /**
     * 操作指南
     */
    @GET("index.php/api/anyu/guide")
    Observable<GuideDao> getGuide();

    /**
     * 设备展示
     */
    @GET("index.php/api/anyu/pment")
    Observable<DeviceDao> getDevice();

    /**
     * 历史排行
     */
    @GET("index.php/api/anyu/honor")
    Observable<RankDao> getRank(@Query("items") String items, @Query("status") String status, @Query("start") String start);

    /**
     * 我的数据记录
     */
    @GET("index.php/api/anyu/shuju")
    Observable<RecordDao> getRecord(@Query("id") String id, @Query("start") String start, @Query("end") String end);

    /**
     * 修改手机号码
     */
    @FormUrlEncoded
    @POST("index.php/api/user/phone")
    Observable<CommonDao> motifyPhone(@Field("id") String id, @Field("status") String status, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("index.php/api/user/mima")
    Observable<CommonDao> motifyPwd(@Field("id") String id, @Field("status") String status, @Field("pass") String pass
            , @Field("xpass") String xpass, @Field("qpass") String qpass);


    /**
     * 修改头像
     */
    @FormUrlEncoded
    @POST("index.php/api/user/head")
    Observable<CommonDao> motifyHead(@Field("id") String id, @Field("img") String img);

    /**
     * 积分订单
     */
    @GET("index.php/api/user/book")
    Observable<PointDao> getPoints(@Query("id") String id, @Query("flag") String flag);

    /**
     * 采集员回收订单
     */
    @GET("index.php/api/order/caiji")
    Observable<HandDao> getHand(@Query("id") String id);

    /**
     * 采集员确定订单
     */
    @GET("index.php/api/order/qren")
    Observable<CommonDao> toHand(@Query("id") String id);


    /**
     * 采集员首页头
     */
    @GET("index.php/api/suggest/tt")
    Observable<TitleDao> getTitle(@Query("id") String id, @Query("status") int type);

    @FormUrlEncoded
    @POST("index.php/Api/order/order_cl")
    Observable<CommonDao> handRecy(@FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST("api/jqi/ruk")
    Observable<StoreDao> putStore(@Field("code") String code, @Field("n_code") String n_code);


    @FormUrlEncoded
    @POST("api/jqi/yhu")
    Observable<HomeInfo> queryInfo(@Field("n_code") String code);


    @FormUrlEncoded
    @POST("api/jqi/pfen_news")
    Observable<Object> mark(@Field("n_code") String userId, @Field("status") int status, @Field("uid") String uid,
                            @Field("img") String img, @Field("jifen") String jifen, @Field("weight") String wegit,
                            @Field("flag_status") String flag_status,
                            @Field("flag_status1") String flag_status1,
                            @Field("flag_status2") String flag_status2,
                            @Field("flag_status3") String flag_status3);


}
