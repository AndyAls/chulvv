package com.qlckh.chunlvv.http.upload;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * @author Andy
 * @date   2018/5/15 18:48
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    UploadFileApi.java
 */

public interface UploadFileApi {

    /**
     * 上传
     *
     * @param uploadUrl 地址
     * @param file      文件
     * @return ResponseBody
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadImg(@Url String uploadUrl,
                                       @Part MultipartBody.Part file);


    /**
     * 上传多个文件
     *
     * @param uploadUrl 地址
     * @param files      文件
     * @return ResponseBody
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadImgs(@Url String uploadUrl,
                                        @Part List<MultipartBody.Part> files);
}
