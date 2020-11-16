# MvvmAndroidLib

**用途**<br/>
快速搭建Mvvm架构的安卓(Android)项目<br/>

**说明**<br/>
基于jetpack包下的 lifecycle+room+ktcoroutines搭建<br/>

*如下是内部集成的库*
```
// 协程
api "org.jetbrains.kotlinx:kotlinx-coroutines-android:xxx"
// Android兼容库
api 'androidx.multidex:multidex:xxx'
api "androidx.startup:startup-runtime:xxx"
api 'androidx.appcompat:appcompat:xxx'
api 'androidx.constraintlayout:constraintlayout:xxx'
api 'androidx.recyclerview:recyclerview:xxx'
api 'androidx.cardview:cardview:xxx'
// jetpack 生命周期管理及数据库
api 'androidx.lifecycle:lifecycle-extensions:xxx'
api 'androidx.room:room-runtime:xxx' 
// ARouter 路由解耦(Dagger需要时自己引用)
api 'com.alibaba:arouter-api:xxx'
// 下拉刷新，上拉加载
api 'com.scwang.smartrefresh:SmartRefreshLayout:xxx'
// recycler adapter 快速搭建
api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:xxx'
// Android动态权限
api "org.permissionsdispatcher:permissionsdispatcher:xxx"
```

**扩展库**<br/>
TODO 待加入 <br/>
*网络请求库需引入扩展库，或自己写*
URL：

*一维码二维码扫描*
URL：

*图片选择库*
URL：https://jitpack.io/#Dazhi528/PictureSelector
导入：implementation 'com.github.Dazhi528:PictureSelector:1.0.1'

*常用视图库*
URL：https://jitpack.io/#Dazhi528/RootView
导入：implementation 'com.github.Dazhi528:RootView:1.0.1'
#### 自定义view
带删除内容的EditText: ViewClearEditText<br/>
带图片的TextView：ViewDrawableText <br/>
带图片的EditText：ViewDrawableEdit <br/>
带图片的Button：ViewDrawableButton <br/>
带图片的RadioButton：ViewDrawableRadioBt <br/>

*由RxJava实现的事件总线库*
URL：https://jitpack.io/#Dazhi528/RootRxbus
导入：implementation 'com.github.Dazhi528:RootRxbus:1.0.1'

### 引入方式 
[![Release](https://img.shields.io/github/release/Dazhi528/MvvmAndroidLib?style=flat)](https://jitpack.io/#Dazhi528/MvvmAndroidLib)
[![API](https://img.shields.io/badge/API-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=16)


### 引用本库
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
    implementation 'com.github.Dazhi528:MvvmAndroidLib:Tag'
}
```

####  目录util下存放了常用工具类
***考虑到挺火的第三方库blankj:utilcode不能部分引入，冗余太多，这里封装了几个常用工具类*** <br/>
RtCmn：常用方法类<br/>
RtSp：偏好存储类<br/>（copy第三方工具库：blankj:utilcode）
RtLog：统一管理日志
RtFile: 文件相关工具类（copy第三方工具库：blankj:utilcode）
RtStack：管理Acitvity堆栈
RtStatusBar：状态条控制类（沉浸式、状态条颜色修改等）
RtConfig:生命周期扩展引擎加载配置类
RtCode：常用加密及编码工具方法
RtCatch：全局捕获异常，防止崩溃，可处理重启等操作
RtThread：线程池封装，替换某些new Thread场景
Rtview：封装点击防抖动方法


#### 屏幕适配
美工喜欢用720*1280开发安卓，在这个分辨率下，2px=1dp <br/>
我们开发就根据美工给的这个原型图像素除以2就可以了，尺寸都
放到values/dimens.xml下，开发完成copy项目的main目录下的
screenMatchDP.jar文件到你项目的对应位置，然后在终端执行
（第一个必须是360，即标准的720*1280，后面的是插件没生成，自己需要的）
<br/>
如下命令：<br/>
***java -jar screenMatchDP.jar 360 120 160 240 320***
<br/>
美工开发IOS用iPhone6为标准； 屏幕宽度为375px，共有750个物理像素

#### 安卓动态权限管理
二次封装的第三方permissionsdispatcher库

#### 友盟统计等需要在BaseActivity生命周期方法中添加内容的引擎加载方式
类似友盟统计等的一些库，需要写入BaseAcitvity生命周期方法中时，我们提供了
InteRootEngineLifecycle引擎加载接口，只有实现此接口，然后App类里调用
UtConfig.self().initEngineLifecycle(你的引擎)即可

#### 上拉刷新、下拉加载
二次封装的第三方SmartRefreshLayout库

#### 快速搭建Recycler适配器，去除冗余代码
引入了第三方BaseRecyclerViewAdapterHelper库，可直接用

#### 适配器Item间隔
DecorationSpaceGridLinear

#### 通用适配器
RootAdapter