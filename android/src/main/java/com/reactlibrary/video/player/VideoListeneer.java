package com.reactlibrary.video.player;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;

/**
 * Created by lzw on 2017/6/18.
 */

public class VideoListeneer implements StandardVideoAllCallBack {
    private EventDispatcher eventDispatcher;
    private RCTVideoPlayer player;

    public VideoListeneer(EventDispatcher eventDispatcher, RCTVideoPlayer player) {
        this.eventDispatcher = eventDispatcher;
        this.player = player;
    }

    @Override
    public void onClickStartThumb(String url, Object... objects) {

    }

    @Override
    public void onClickBlank(String url, Object... objects) {

    }

    @Override
    public void onClickBlankFullscreen(String url, Object... objects) {

    }

    @Override
    public void onPrepared(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topPrepared"));
    }

    @Override
    public void onClickStartIcon(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topPlay"));
    }

    @Override
    public void onClickStartError(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topPlay"));
    }

    @Override
    public void onClickStop(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topPause"));
    }

    @Override
    public void onClickStopFullscreen(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topPause"));
    }

    @Override
    public void onClickResume(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topPlay"));
    }

    @Override
    public void onClickResumeFullscreen(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topPlay"));
    }

    @Override
    public void onClickSeekbar(String url, Object... objects) {

    }

    @Override
    public void onClickSeekbarFullscreen(String url, Object... objects) {

    }

    @Override
    public void onAutoComplete(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topEnd"));
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        WritableMap map = new WritableNativeMap();
        map.putBoolean("isFullscreen", true);
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topFullscreen", map));
    }

    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        WritableMap map = new WritableNativeMap();
        map.putBoolean("isFullscreen", false);
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topFullscreen", map));
    }

    @Override
    public void onQuitSmallWidget(String url, Object... objects) {

    }

    @Override
    public void onEnterSmallWidget(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekVolume(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekPosition(String url, Object... objects) {

    }

    @Override
    public void onTouchScreenSeekLight(String url, Object... objects) {

    }

    @Override
    public void onPlayError(String url, Object... objects) {
        eventDispatcher.dispatchEvent(new VideoPlayerEvent(player.getId(), "topError"));
    }
}
