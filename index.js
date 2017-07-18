import React, { Component, PropTypes } from 'react';
import ReactNative, {
  StyleSheet,
  requireNativeComponent,
  NativeModules,
  View,
  Platform,
  UIManager,
  BackAndroid
} from 'react-native';

class VideoPlayer extends Component {
  constructor(...args) {
    super(...args);
    this.isFullscreen = false;
  }

  static propTypes = {
    ...View.propTypes,
    src: PropTypes.string.isRequired,
    title: PropTypes.string,
    placeholderImage: PropTypes.string,
    onBack: PropTypes.func,
    onPrepared: PropTypes.func,
    onPlay: PropTypes.func,
    onPause: PropTypes.func,
    onEnd: PropTypes.func,
    onError: PropTypes.func,
    onFullscreen: PropTypes.func
  };

  static nativeOnly = {
    onFullscreen: true
  };

  componentDidMount() {
    if (Platform.OS == 'android') {
      BackAndroid.addEventListener('hardwareBackPress', this.handleAndroidBack);
    }
  }

  componentWillUnmount() {
    if (Platform.OS == 'android') {
      BackAndroid.removeEventListener('hardwareBackPress', this.handleAndroidBack);
    }
  }

  handleAndroidBack = () => {
    if (this.isFullscreen) {
      this.backFromFull();
      return true;
    }
    return false;
  };

  onFullscreen = event => {
    this.isFullscreen = event.nativeEvent.isFullscreen;
    this.props.onFullscreen && this.props.onFullscreen(event.nativeEvent.isFullscreen);
  };

  play = () => {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.RCTVideoPlayer.Commands.play,
      []
    );
  };

  backFromFull = () => {
    UIManager.dispatchViewManagerCommand(
      ReactNative.findNodeHandle(this),
      UIManager.RCTVideoPlayer.Commands.backFromFull,
      []
    );
  };

  render() {
    return <RCTVideo ref="native" {...this.props} onFullscreen={this.onFullscreen} />;
  }
}

const RCTVideo = requireNativeComponent('RCTVideoPlayer', VideoPlayer);

export default VideoPlayer;
