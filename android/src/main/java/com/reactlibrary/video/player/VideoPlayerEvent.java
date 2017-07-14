package com.reactlibrary.video.player;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by lzw on 2017/7/6.
 */

public class VideoPlayerEvent extends Event {
    private String eventName;

    public VideoPlayerEvent(int viewTag, String eventName) {
        super(viewTag);
        this.eventName = eventName;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), Arguments.createMap());
    }
}
