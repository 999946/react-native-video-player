package com.reactlibrary.video.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.Map;

/**
 * Created by lzw on 2017/6/8.
 */

public class RNVideoPlayerViewMannager extends SimpleViewManager<RCTVideoPlayer> implements LifecycleEventListener {

    private static final int COMMAND_PLAY = 0;
    private static final int BACK_FROM_FULL = 1;

    private final ReactApplicationContext mReactApplicationContext;
    private RCTVideoPlayer player;

    EventDispatcher eventDispatcher;

    public RNVideoPlayerViewMannager(ReactApplicationContext reactApplicationContext) {
        super();
        mReactApplicationContext = reactApplicationContext;
    }

    private String src;
    private boolean isPlay;
    private boolean isPause;

    private OrientationUtils orientationUtils;

    private BroadcastReceiver receiver;


    @Override
    public String getName() {
        return "RCTVideoPlayer";
    }

    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, RCTVideoPlayer view) {
        super.addEventEmitters(reactContext, view);
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    protected RCTVideoPlayer createViewInstance(final ThemedReactContext reactContext) {
        eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        player = new RCTVideoPlayer(mReactApplicationContext.getCurrentActivity());

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(mReactApplicationContext.getCurrentActivity(), player);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        //是否可以滑动界面改变进度，声音等
        //player.setIsTouchWiget(true);
        //全屏是否可以滑动界面改变进度，声音等
        //player.setIsTouchWigetFull(false);
        //关闭自动旋转
        player.setRotateViewAuto(false);
        player.setLockLand(false);
        player.setShowFullAnimation(false);
        player.setNeedLockFull(false);
        //player.setOpenPreView(false);

        player.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topBack"));
            }
        });

        player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                player.startWindowFullscreen(reactContext.getCurrentActivity(), true, true);
            }
        });

        player.setStandardVideoAllCallBack(new VideoListeneer(eventDispatcher, player) {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                if (orientationUtils != null) {
                    orientationUtils.backToProtVideo();
                }
            }
        });

        player.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
        return player;
    }

    private void addOrientationReceiver(ReactContext reactContext) {

        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Configuration newConfig = intent.getParcelableExtra("newConfig");
                Log.d("receiver", String.valueOf(newConfig.orientation));

                String orientationValue = newConfig.orientation == 1 ? "PORTRAIT" : "LANDSCAPE";
                //如果旋转了就全屏
                if (isPlay && !isPause) {
                    if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                        if (!player.isIfCurrentIsFullscreen()) {
                            player.startWindowFullscreen(mReactApplicationContext.getCurrentActivity(), true, true);
                        }
                    } else {
                        //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                        if (player.isIfCurrentIsFullscreen()) {
                            StandardGSYVideoPlayer.backFromWindowFull(mReactApplicationContext.getCurrentActivity());
                        }
                        if (orientationUtils != null) {
                            orientationUtils.setEnable(true);
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onHostResume() {
        isPause = false;
    }

    @Override
    public void onHostPause() {
        isPause = true;
    }

    @Override
    public void onHostDestroy() {
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        this.src = null;
    }

    @Override
    public void onDropViewInstance(RCTVideoPlayer view) {
        super.onDropViewInstance(view);
        GSYVideoPlayer.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
        this.src = null;
    }

    // props
    @ReactProp(name = "src")
    public void setSrc(RCTVideoPlayer player, @Nullable String src) {
        Log.i("RCTVideoPlayer", "set src " + src);

        player.setUp(src, false);
        // 如果切换了播放的文件路径，触发开始播放
        if(src.equals(this.src)){
            player.startPlayLogic();
        }
        this.src = src;
    }

    @ReactProp(name = "title")
    public void setTitle(RCTVideoPlayer player, String title) {
        Log.i("RCTVideoPlayer", "set title "+ title);
        player.getTitleTextView().setText(title);
    }

    @ReactProp(name = "placeholderImage")
    public void setPlaceholderImage(RCTVideoPlayer player, String placeholderImage) {
        Log.i("RCTVideoPlayer", "set placeholderImage " + placeholderImage);
        Uri uri = Uri.parse(placeholderImage);
        SimpleDraweeView draweeView = new SimpleDraweeView(mReactApplicationContext.getCurrentActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        draweeView.setLayoutParams(params);
        draweeView.setImageURI(uri);
        player.setThumbImageView(draweeView);
    }


    @Override
    protected void onAfterUpdateTransaction(RCTVideoPlayer view) {
        super.onAfterUpdateTransaction(view);

        player.getTitleTextView().setVisibility(View.GONE);
        player.getBackButton().setVisibility(View.VISIBLE);
    }

    @Override
    public @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                "topBack", MapBuilder.of("registrationName", "onBack"),
                "topPrepared", MapBuilder.of("registrationName", "onPrepared"),
                "topPlay", MapBuilder.of("registrationName", "onPlay"),
                "topPause", MapBuilder.of("registrationName", "onPause"),
                "topEnd", MapBuilder.of("registrationName", "onEnd"),
                "topError", MapBuilder.of("registrationName", "onError"),
                "topFullscreen", MapBuilder.of("registrationName", "onFullscreen")
                );
    }

    @javax.annotation.Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "play", COMMAND_PLAY,
                "backFromFull", BACK_FROM_FULL
                );
    }

    @Override
    public void receiveCommand(RCTVideoPlayer root, int commandId, @javax.annotation.Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);
        switch (commandId){
            case COMMAND_PLAY: {
                player.startPlayLogic();
                break;
            }
            case BACK_FROM_FULL: {
                break;
            }
        }
    }
}
