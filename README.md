# MvvmAndroidFrame

**用途**<br/>
快速搭建Mvvm架构的安卓(Android)项目<br/>

**说明**<br/>
基于jetpack包下的 lifecycle+room+ktcoroutines搭建<br/>

*如下是内部集成的库*
```
// 协程(它的Flow用来替换Rxjava，轻量而又强大；协程替换线程，清爽了许多)
api "org.jetbrains.kotlinx:kotlinx-coroutines-android:xxx"

// Android兼容库
api 'androidx.multidex:multidex:xxx' // 解决最大方法数问题
api "androidx.startup:startup-runtime:xxx" // 负责App启动需做的初始化工作，方便使用者扩展无需继承RootApp
api 'androidx.appcompat:appcompat:xxx' // 官方兼容库，原生的已基本没人用，所以这里当然要引入了
api 'androidx.constraintlayout:constraintlayout:xxx' // 约束布局，考虑到官方极力推荐，AS相关布局都做了很多努力，也引入吧
api 'androidx.recyclerview:recyclerview:xxx' // 官方推荐的大多数的ListView和GridView的使用场景替代方案
api 'androidx.cardview:cardview:xxx' // 圆角矩形卡片UI，现在挺常见的场景，一并引入吧

// ARouter 路由解耦(Dagger需要时自己引用)
api 'com.alibaba:arouter-api:xxx' // 极力推荐要会用的一个库，直接强引进来，好处多大，不会的同学快去了解下

// 下拉刷新，上拉加载
api 'com.scwang.smartrefresh:SmartRefreshLayout:xxx'

// recycler adapter 快速搭建
api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:xxx'

// Android动态权限
api "org.permissionsdispatcher:permissionsdispatcher:xxx"

// 为了解耦已注销，按需自己引入：jetpack生命周期管理及数据库
# api 'androidx.lifecycle:lifecycle-extensions:xxx'
# api 'androidx.room:room-runtime:xxx'
```

**扩展库**<br/>
*网络请求* <br/>
URL：https://jitpack.io/#Dazhi528/RootHttp <br/>
导入：implementation 'com.github.Dazhi528:RootHttp:Tag' <br/>
作用：基于kotlin协程和流(coroutines+flow+okhttp+Retrofit)封装的替代之前的RxJava网络请求方案<br/>

*一维码二维码扫描* <br/>
URL：https://jitpack.io/#Dazhi528/ZxingScan <br/>
导入：implementation 'com.github.Dazhi528:ZxingScan:Tag' <br/>
作用：扫描识别一维码或二维码 <br/>

*图片选择库* <br/>
URL：https://jitpack.io/#Dazhi528/PictureSelector  <br/>
导入：implementation 'com.github.Dazhi528:PictureSelector:Tag' <br/>
作用：从相册选择图片或拍照选择图片，用于上传 <br/>

*常用视图库* <br/>
URL：https://jitpack.io/#Dazhi528/RootView <br/>
导入：implementation 'com.github.Dazhi528:RootView:Tag' <br/>
作用：常用的自定义view <br/>
* 带删除内容的EditText: ViewClearEditText<br/>
* 带图片的TextView：ViewDrawableText <br/>
* 带图片的EditText：ViewDrawableEdit <br/>
* 带图片的Button：ViewDrawableButton <br/>
* 带图片的RadioButton：ViewDrawableRadioBt <br/>

*由RxJava实现的事件总线库* <br/>
URL：https://jitpack.io/#Dazhi528/RootRxbus <br/>
导入：implementation 'com.github.Dazhi528:RootRxbus:1.0.1' <br/>
作用： <br/>
替代一些广播接收器或消息接收处理场景，同EventBus功能，比其轻量，不过前提是你打算引入Rxjava库，<br/>
此库作者之前用rxjava封装网络请求时，用的比较多，由于作者现在更推协程，网络请求正在打算用协程构思，<br/>
因此，此库用的比较少了，正在寻找协程的替代方案 <br/>


### 引入方式 
[![Release](https://img.shields.io/github/release/Dazhi528/MvvmAndroidFrame?style=flat)](https://jitpack.io/#Dazhi528/MvvmAndroidFrame)
[![API](https://img.shields.io/badge/API-19%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=19)

**Gradle** <br/>
Step 1. Add the JitPack repository to your build file

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```
dependencies {
    implementation 'com.github.Dazhi528:MvvmAndroidFrame:Tag'
}
```


### 本库功能描述

####  目录util下存放了常用工具类
***考虑到挺火的第三方库blankj:utilcode不能部分引入，冗余太多，这里封装了几个常用工具类*** <br/>
```
RtCmn：常用方法类
RtSp：偏好存储类（copy第三方工具库：blankj:utilcode）
RtLog：统一管理日志
RtFile: 文件相关工具类（copy第三方工具库：blankj:utilcode）
RtStack：管理Acitvity堆栈
RtStatusBar：状态条控制类（沉浸式、状态条颜色修改等）
RtConfig:生命周期扩展引擎加载配置类
RtCode：常用加密及编码工具方法
RtCatch：全局捕获异常，防止崩溃，可处理重启等操作
RtThread：线程池封装，替换某些new Thread场景
RtView：封装点击防抖动方法(全局方法，可直接使用，见实例)
```

#### 屏幕适配
美工喜欢用720*1280开发安卓，在这个分辨率下，2px=1dp <br/>
我们开发就根据美工给的这个原型图像素除以2就可以了，尺寸都 <br/>
放到values/dimens.xml下，开发完成copy项目的main目录下的 <br/>
screenMatchDP.jar文件到你项目的对应位置，然后在终端执行 <br/>
第一个必须是360，即标准的720 X 1280，后面的是插件没生成，自己需要的<br/>
如下命令：<br/>
***java -jar screenMatchDP.jar 360 120 160 240 320***
<br/>
美工开发IOS用iPhone6为标准； 屏幕宽度为375px，共有750个物理像素

#### 安卓动态权限管理
二次封装的第三方permissionsdispatcher库，使用此库的框架，已集成动态权限，<br/>
需要申请什么权限直接调用permissionXXX()方法，例：permissionDhCcXj()，<br/>
详情跳入源码看已有权限

#### 友盟统计等需要在BaseActivity生命周期方法中添加内容的引擎加载方式 <br/>
类似友盟统计等的一些库，需要写入BaseAcitvity生命周期方法中时，我们提供了 <br/>
InteRootEngineLifecycle引擎加载接口，只有实现此接口，然后App类里调用 <br/>
UtConfig.self().initEngineLifecycle(你的引擎)即可，实例中有加载实例 <br/>

#### 上拉刷新、下拉加载
二次封装的第三方SmartRefreshLayout库

#### 快速搭建Recycler适配器，去除冗余代码
引入了第三方BaseRecyclerViewAdapterHelper库，可直接用

#### 适配器Item间隔
DecorationSpaceGridLinear

#### 通用适配器
RootAdapter


### 修改说明
* V 1.0.5
* 20-11-30 官方viewBinding方案替换过时的kotlin-android-extensions方案
```
// 注释掉：apply plugin: 'kotlin-android-extensions'
// 添加如下：
android {
   ...
   buildFeatures {
       viewBinding = true
   }
}
```