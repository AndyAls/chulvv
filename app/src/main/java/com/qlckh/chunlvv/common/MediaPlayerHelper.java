package com.qlckh.chunlvv.common;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RawRes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableEmitter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Andy
 * @date 2019/8/2 10:20
 * Desc:raw文件夹下的音频播放
 */
public class MediaPlayerHelper {

    private Context mContext;
    private final AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private final AudioManager.OnAudioFocusChangeListener mListener;
    private boolean isFouce;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable subscribe;
    private static volatile MediaPlayerHelper playerHelper = null;

    private MediaPlayerHelper(Context context) {
        this.mContext = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(false);
        //音频焦点监听,录音时应用获取焦点,暂停系统音乐播放
        mListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {

                    case AudioManager.AUDIOFOCUS_GAIN:
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                        isFouce = true;
                        requestAudioFocus();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        onPause();
                        isFouce = false;
                        abandonAudioFocus();
                        break;
                    default:
                }
            }
        };
    }


    public static MediaPlayerHelper getInstance(Context context) {
        if (playerHelper == null) {
            synchronized (MediaPlayerHelper.class) {
                if (playerHelper == null) {
                    playerHelper = new MediaPlayerHelper(context);
                }
            }
        }
        return playerHelper;
    }

    public interface MediaPaylerLisener {
        void onPlayCompletion();

    }

    public interface MediaPaylerUrlLisener {
        void onMusicPlayCompletion();

        void onMusicPlaying();

        void onMusicProgress(int current, int duration);

        void onMusicPause();

        void onMusicStop();
    }

    public void startPlay(@RawRes final int rawId, MediaPaylerLisener lisener) {
        requestAudioFocus();
        try {
            preparePlay(null, rawId, lisener);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        subscribe = Completable.create(new CompletableOnSubscribe() {
//            @Override
//            public void subscribe(final CompletableEmitter emitter) throws Exception {
//                preparePlay(emitter, rawId);
//            }
//        })
//                .subscribeOn(Schedulers.single())
//                .subscribe(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        stopPlay();
//                    }
//                });


    }

    public void startPlay(@RawRes final int rawId) {
        startPlay(rawId, null);
    }

    public void setProgress(int progress) {
        if (mediaPlayer.isPlaying()) {
//            mediaPlayer.reset();
//            mediaPlayer.start();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mediaPlayer.seekTo(progress, MediaPlayer.SEEK_CLOSEST);
            } else {
                mediaPlayer.seekTo(progress);
            }
        }
    }


    public void startPlay(String path, MediaPaylerUrlLisener lisener) throws Exception {
        if (mediaPlayer == null || mediaPlayer.isPlaying()) {
            return;
        }


        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.reset();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                if (lisener != null) {
                    lisener.onMusicPlaying();
                    compositeDisposable.add(Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(o -> lisener.onMusicProgress(mp.getCurrentPosition(), mp.getDuration())));
                }

            }

        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
                lisener.onMusicPlayCompletion();
            }

        });
        mediaPlayer.setDataSource(path);
        mediaPlayer.prepareAsync();
        if (lisener != null) {
            compositeDisposable.add(Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> lisener.onMusicProgress(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration())));
        }
//        mediaPlayer.start();
//        Thread.sleep(1000);

    }

    private MusicInfo musicInfo;

    public MusicInfo getMusicInfo() {
        return musicInfo;
    }

    public void setMusicInfo(MusicInfo musicInfo) {
        this.musicInfo = musicInfo;
    }


    public List<MediaPaylerUrlLisener> mediaPaylerUrlLisenerList = new ArrayList<>();

    public void addMediaPaylerUrlLisener(MediaPaylerUrlLisener lisener) {
        mediaPaylerUrlLisenerList.add(lisener);
    }

    public void removeMediaPaylerUrlLisener(MediaPaylerUrlLisener lisener) {
        mediaPaylerUrlLisenerList.remove(lisener);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private void notifyAllListenerStop() {
        for (MediaPaylerUrlLisener lisener : mediaPaylerUrlLisenerList) {
            lisener.onMusicStop();
        }
    }

    private void notifyAllListenerPlaying() {
        for (MediaPaylerUrlLisener lisener : mediaPaylerUrlLisenerList) {
            lisener.onMusicPlaying();
        }
    }

    private void notifyAllListenerPause() {
        for (MediaPaylerUrlLisener lisener : mediaPaylerUrlLisenerList) {
            lisener.onMusicPause();
        }
    }

    private void notifyAllListenerProgress() {
        if (mediaPlayer.isPlaying()) {
            for (MediaPaylerUrlLisener lisener : mediaPaylerUrlLisenerList) {
                lisener.onMusicProgress(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
            }
        }
    }

    private void notifyAllListenerPlayCompletion() {
        for (MediaPaylerUrlLisener lisener : mediaPaylerUrlLisenerList) {
            lisener.onMusicPlayCompletion();
        }
    }


    public void startPlay(MusicInfo musicInfo, MediaPaylerUrlLisener lisener) throws Exception {
        if (this.musicInfo != musicInfo) {
            //重新播放新的
            stopPlay();
        }
        this.musicInfo = musicInfo;
        if (mediaPlayer == null || mediaPlayer.isPlaying()) {
            return;
        }


        addMediaPaylerUrlLisener(lisener);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.reset();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();


                notifyAllListenerPlaying();
                compositeDisposable.add(Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                            //如果是正在播放的话 那就回调方法
                            notifyAllListenerProgress();
                        }));
            }

        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
                notifyAllListenerPlayCompletion();
            }

        });
        mediaPlayer.setDataSource(musicInfo.musicUrl);
        mediaPlayer.prepare();
        compositeDisposable.add(Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                            //如果是正在播放的话 那就回调方法
                            notifyAllListenerProgress();
                        }
                ));
//        mediaPlayer.start();
//        Thread.sleep(1000);

    }

    private void preparePlay(final CompletableEmitter emitter, @RawRes int rawId, MediaPaylerLisener lisener) throws Exception {
        if (mediaPlayer==null||mediaPlayer.isPlaying()){
            return;
        }
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (lisener != null) {
                    lisener.onPlayCompletion();
                }
                stopPlay();
            }

        });
        AssetFileDescriptor file = mContext.getResources().openRawResourceFd(
                rawId);
        mediaPlayer.setDataSource(file.getFileDescriptor(),
                file.getStartOffset(), file.getLength());
        file.close();
        mediaPlayer.prepareAsync();
//        mediaPlayer.start();
//        Thread.sleep(1000);
    }

    public void onResume() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            notifyAllListenerPlaying();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void onTogle() {
        if (mediaPlayer.isPlaying()) {
            onPause();
        } else {
            onResume();
        }
    }

    public void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            notifyAllListenerPause();
        }
    }

    public void release() {
        abandonAudioFocus();
        notifyAllListenerStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (playerHelper != null) {
            playerHelper = null;
        }
        if (mContext != null) {
            mContext = null;
        }
        compositeDisposable.clear();
        /*if (!subscribe.isDisposed()) {
            subscribe.dispose();
        }*/


    }

    private void requestAudioFocus() {
        if (!isFouce) {
            int result = audioManager.requestAudioFocus(mListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                isFouce = true;
            }
        }
    }

    private void abandonAudioFocus() {
        if (isFouce) {
            audioManager.abandonAudioFocus(mListener);
            isFouce = false;
        }
    }

    private void stopPlay() {
        mediaPlayer.reset();
        abandonAudioFocus();
        notifyAllListenerStop();
    }


    public static class MusicInfo {
        private String musicUrl;
        private String musicPic;
        private String author;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMusicUrl() {
            return musicUrl;
        }

        public void setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
        }

        public String getMusicPic() {
            return musicPic;
        }

        public void setMusicPic(String musicPic) {
            this.musicPic = musicPic;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
