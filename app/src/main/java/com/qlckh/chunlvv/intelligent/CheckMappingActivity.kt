package com.qlckh.chunlvv.intelligent

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.media.MediaActionSound
import android.os.Handler
import android.view.ViewTreeObserver
import com.qlckh.chunlvv.R
import com.qlckh.chunlvv.base.BaseActivity
import com.qlckh.chunlvv.common.CaptureBean
import com.qlckh.chunlvv.http.utils.ToastUtils.showToast
import com.qlckh.chunlvv.preview.PicSavaUtil
import com.qlckh.chunlvv.qidian.HomeMarkActivity
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.result.WhenDoneListener
import io.fotoapparat.selector.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_check_mapping.*
import java.io.File
import java.lang.ref.WeakReference

/**
 * @author Andy
 * @date   2019/8/2 16:55
 * @link   {http://blog.csdn.net/andy_l1}
 * Desc:    人脸识别测试
 */
class CheckMappingActivity : BaseActivity(), ViewTreeObserver.OnGlobalLayoutListener {


    override fun onGlobalLayout() {
        cameraView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        cameraView.getGlobalVisibleRect(cameraRect)

    }

    private var cameraRect = Rect()
    private var activeCamera: Camera = Camera.Front
    private lateinit var fotoapparat: Fotoapparat
    val dispos: CompositeDisposable = CompositeDisposable()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        Camera.captureing = false
        Camera.isPlayingSuit = false
    }

    override fun getContentView(): Int {
        return R.layout.activity_check_mapping
    }

    override fun isSetFondSize(): Boolean {
        return false
    }


    override fun onBackPressed() {
        if (Camera.captureing) {
            return
        }
        super.onBackPressed()
    }


    override fun initView() {
        when (CaptureBean.camera) {
            "font" -> {
                activeCamera = Camera.Front
            }
            "back" -> {
                activeCamera = Camera.Back
            }
            else -> {
                activeCamera = Camera.Front
            }
        }
        cameraView.viewTreeObserver.addOnGlobalLayoutListener(this)
        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,
                focusView = focusView,
                logger = io.fotoapparat.log.none(),
                lensPosition = activeCamera.lensPosition,
                cameraConfiguration = activeCamera.configuration,
                cameraErrorCallback = {
                    showToast(it.message)
                }
        )

    }

    override fun showError(msg: String?) {
    }


    /**
     * 拍照
     */
    private fun capturePhoto() {
        Camera.capturePhoto()
    }

    /**
     * 转换摄像头
     */
    private fun toggleCamera() {
        activeCamera = when (activeCamera) {
            Camera.Front -> {
//                ivFace.setViewVisible(false)
                CaptureBean.camera = "back"
                Camera.Back
            }
            Camera.Back -> {
//                ivFace.setViewVisible(true)
                CaptureBean.camera = "font"
                Camera.Front
            }
        }
        fotoapparat.switchTo(
                lensPosition = activeCamera.lensPosition,
                cameraConfiguration = activeCamera.configuration
        )
        Camera.updataFoto(fotoapparat)
    }

    companion object {
        private const val TAG = "CheckMappingActivity"
        var disposables = CompositeDisposable()
        var isPause = false

    }

    override fun onStart() {
        super.onStart()
        fotoapparat.start()

    }

    override fun onStop() {
        super.onStop()
        fotoapparat.stop()
    }

    override fun initDate() {
        Camera.init(this, cameraRect, fotoapparat, intent)
        Camera.captureing = false
        Camera.isPlayingSuit = false

        Handler().postDelayed({
            capturePhoto()
        }, 50)
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun release() {
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        dispos.clear()
        Camera.handDisposables.clear()
        Camera.captureing = false
        Camera.startFrame = false
    }

    private sealed class Camera(
            val lensPosition: LensPositionSelector,
            val configuration: CameraConfiguration
    ) {

        object Back : Camera(
                lensPosition = back(),
                configuration = CameraConfiguration(

                        previewResolution = firstAvailable(
                                standardRatio(highestResolution()),
                                wideRatio(highestResolution())
                        ),
                        previewFpsRange = highestFps(),
                        flashMode = off(),
                        focusMode = firstAvailable(
                                autoFocus(),
                                continuousFocusPicture()

                        ),
                        frameProcessor = {
//                            handFrame(it)
                        }
                ))

        object Front : Camera(
                lensPosition = front(),
                configuration = CameraConfiguration(
                        previewResolution = firstAvailable(
//                                aspectRatio(cameraRect.width().toFloat() / cameraRect.height().toFloat(), highestResolution(), 0.0),
                                wideRatio(highestResolution()),
                                standardRatio(highestResolution())
                        ),
                        previewFpsRange = highestFps(),
                        flashMode = off(),
                        focusMode = firstAvailable(
                                fixed(),
                                autoFocus()
                        ),

                        frameProcessor = {
//                            handFrame(it)
                        }
                )
        )

        companion object {
            var handDisposables = CompositeDisposable()
            lateinit var weakReference: WeakReference<Activity>
            lateinit var cameraRect: Rect
            lateinit var fotoapparat: Fotoapparat

            /**
             * 是否已经开始检测人脸
             */
            var startFrame = false

            /**
             * 是否在拍照中
             */
            var captureing = false
            var isPlayingSuit = false

            fun init(
                    context: Activity,
                    rect: Rect,
                    foto: Fotoapparat,
                    intent: Intent

            ) {
                weakReference = WeakReference(context)
                cameraRect = rect
                fotoapparat = foto
            }

            fun updataFoto(foto: Fotoapparat) {
                fotoapparat = foto
            }


            /**
             * 拍照
             */
            fun capturePhoto() {
                val photoResult = fotoapparat
                        .takePicture()
                MediaActionSound().play(MediaActionSound.SHUTTER_CLICK);
                val savaPath = PicSavaUtil.getSavaPath(weakReference.get())
                val file = File(savaPath, "car_play_" + System.currentTimeMillis() + ".jpg")
                photoResult.saveToFile(file).transform {
                    file.toString()
                }.whenAvailable {
                    weakReference.get()?.let { context ->
                        captureing = false

                        /*   val intent = Intent(context, HomeMarkActivity::class.java)
                                   .apply {
                                       //传递bitmap过大 会崩溃 使用静态变量来保存
                                       CaptureBean.bitmap = bitmap
                                       CaptureBean.file = file
                                       putExtra("filePath", file.path)
                                   }
                           context.startActivity(intent)*/


                        val intent = context.intent
                        intent.putExtra("filePath", it)
                        context.setResult(Activity.RESULT_OK, intent)
                        context.finish()
                    }
                }
                return
                val disposable = Observable
                        .create<Bitmap> { emitter ->
                            photoResult
                                    .saveToFile(file)

                            photoResult
                                    .toBitmap()
                                    .whenAvailable { photo ->
                                        photo?.also {
                                            val bitmap = it.bitmap
                                            val rotationDegrees = it.rotationDegrees

                                            val matrix = Matrix()
                                            matrix.postRotate(-90f)
                                            val createBitmap = Bitmap.createBitmap(
                                                    bitmap,
                                                    0,
                                                    0,
                                                    bitmap.getWidth(),
                                                    bitmap.getHeight(),
                                                    matrix,
                                                    true
                                            )
                                            emitter.onNext(createBitmap)
                                        }
                                                ?: weakReference.get()?.runOnUiThread {
                                                    captureing = false
                                                    showToast("拍照失败")
                                                }

                                    }

                        }
                        .filter {
                            if (it == null) {
                                captureing = false
                            }
                            it != null
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { bitmap ->
                            weakReference.get()?.let { context ->
                                captureing = false

                                /*   val intent = Intent(context, HomeMarkActivity::class.java)
                                           .apply {
                                               //传递bitmap过大 会崩溃 使用静态变量来保存
                                               CaptureBean.bitmap = bitmap
                                               CaptureBean.file = file
                                               putExtra("filePath", file.path)
                                           }
                                   context.startActivity(intent)*/


                                val intent = context.intent
                                intent.putExtra("filePath", file.path)
                                context.setResult(Activity.RESULT_OK, intent)
                                context.finish()
                            }

                        }
                disposables.add(disposable)
            }
        }
    }

}
