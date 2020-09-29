package com.qlckh.chunlvv.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.telephony.TelephonyManager
import com.qlckh.chunlvv.http.utils.ToastUtils
import java.io.File


/**
 * @author Andy
 * @date 2018/5/15 11:40
 * Desc: 判断是手机还是其他
 */
object PhoneUtil {
    fun isPhone(activity: Activity): Boolean {
        val telephony = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephony != null && telephony.phoneType != TelephonyManager.PHONE_TYPE_NONE
    }

    @JvmStatic
    val isN5s: Boolean
        get() = "N5S" == Build.MODEL


    /**
     * 获取手机IMEI唯一标识
     *
     * @return
     */
    @JvmStatic
    fun getIMEI(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager?.deviceId ?:""
    }

    fun hasCamera(context: Context): Boolean {
        var hasCamera = false
        val pm: PackageManager = context.getPackageManager()
        hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) || Camera.getNumberOfCameras() > 0
        return hasCamera
    }

    /**
     * 检测是否有前置摄像头
     * @return
     */
    fun hasFrontFacingCamera(): Boolean {
        val CAMERA_FACING_BACK = 1
        return checkCameraFacing(CAMERA_FACING_BACK)
    }

    fun startCamera(context: Context):Boolean{

        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val f: File = File("")
            val u = Uri.fromFile(f)
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u)
            context.startActivity(intent)
            return true
        } catch (e: Exception) {
        } finally {
        }
        return false
    }
    /**
     * 检测是否有后置摄像头
     * @return
     */
    fun hasBackFacingCamera(): Boolean {
        val CAMERA_FACING_BACK = 0
        return checkCameraFacing(CAMERA_FACING_BACK)
    }

    private fun checkCameraFacing(facing: Int): Boolean {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false
        }
        val cameraCount = Camera.getNumberOfCameras()
        val info = CameraInfo()
        var open: Camera? = null
        for (i in 0 until cameraCount) {
            Camera.getCameraInfo(i, info)
            if (facing == info.facing) {
                try {
                    open = Camera.open(facing)
                    open.startPreview()
                    open.parameters
                    return true
                } catch (e: Exception) {
                    ToastUtils.showToast(e.message)
                } finally {
                    if (open != null) {
                        open.stopPreview()
                        open.release()
                        open = null
                    }
                }
            }
        }

        return false
    }

    fun getSdkVersion(): Int {
        return Build.VERSION.SDK_INT
    }

    fun isCameraCanUse(): Boolean {
        var canUse = false
        var mCamera: Camera? = null
        try {
            mCamera = Camera.open(0)
            val mParameters = mCamera.parameters
            mCamera.parameters = mParameters
        } catch (e: java.lang.Exception) {
            canUse = false
            ToastUtils.showToast(e.message)
        }
        if (mCamera != null) {
            mCamera.release()
            mCamera = null
            canUse = true
        }
        return canUse
    }

    fun checkCameraEnable(context: Context): Boolean {
        var result: Boolean
        var camera: Camera? = null
        try {
            camera = Camera.open()
            if (camera == null) {
                var connected = false
                for (camIdx in 0 until Camera.getNumberOfCameras()) {
                    try {
                        camera = Camera.open(camIdx)
                        connected = true
                    } catch (e: RuntimeException) {
                        return false
                    }
                    if (connected) {
                        break
                    }
                }
            }
            if (camera == null) {
                return false
            }
            val supportedPreviewSizes: List<Camera.Size> = camera.getParameters().getSupportedPreviewSizes()
            result = supportedPreviewSizes != null && hasCamera(context)
            camera.startPreview()
        } catch (e: Exception) {
            result = false
        } finally {
            if (camera != null) {
                camera.release()
            }
        }
        return result
    }

}