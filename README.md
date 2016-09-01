# ViewfinderCamera
摄像头取景框 支持前后置摄像头拍照
目前流传的比较广泛的选取最佳分辨率的方法 getOptimalPreviewSize 存在一个问题
就是在同时有1280*960 1280*720/768 的情况下 会优先选择960 实际上应该选择720/768，这样会导致预览拉伸的比较严重。
![image](https://github.com/PengsongAndroid/SrcollTextView/blob/master/GIF.gif)
