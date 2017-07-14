
import React, { Component, PropTypes } from 'react';
import { StyleSheet, requireNativeComponent, NativeModules, View, Platform } from 'react-native';


const RCTVideo = requireNativeComponent('RCTVideoPlayer', {
  propTypes: {
    ...View.propTypes,
    src: PropTypes.string.isRequired,
    title: PropTypes.string,
    placeholderImage: PropTypes.string,
    onBack : PropTypes.func,
    onPrepared: PropTypes.func,
    onPlay : PropTypes.func,
    onPause : PropTypes.func,
    onEnd : PropTypes.func,
    onError : PropTypes.func,
  },
  nativeOnly: {
    
  }
});

export default RCTVideo;
