
# react-native-video-player

## Getting started

`$ npm install react-native-video-player --save`

### Mostly automatic installation

`$ react-native link react-native-video-player`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-video-player` and add `RNVideoPlayer.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNVideoPlayer.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.video.player.RNVideoPlayerPackage;` to the imports at the top of the file
  - Add `new RNVideoPlayerPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-video-player'
  	project(':react-native-video-player').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-video-player/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-video-player')
  	```


## Usage
```javascript
import RNVideoPlayer from 'react-native-video-player';

// TODO: What to do with the module?
RNVideoPlayer;
```
  