# MvvmAndroidLib

**用途**<br/>
快速搭建Mvvm架构的安卓(Android)项目<br/>

**说明**<br/>
基于jetpack包下的 lifecycle+room+rxjava2+retrofit2搭建

#### 安卓开发必备常用支持库已引入
constraint、appcompat-v7、recyclerview-v7、design、cardview、
multidex：集成至BaseRootApp类里，继承此类可解决最大方法数问题

####  目录util下存放了常用工具类
***考虑到挺火的第三方库blankj:utilcode不能部分引入，冗余太多，这里封装了几个常用工具类*** <br/>
UtRoot：常用方法类<br/>
UtSp：偏好存储类<br/>（copy第三方工具库：blankj:utilcode）
UtLog：统一管理日志
UtFile: 文件相关工具类（copy第三方工具库：blankj:utilcode）
UtStack：管理Acitvity堆栈
UtStatusBar：状态条控制类（沉浸式、状态条颜色修改等）

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

#### 友盟统计
引入了友盟统计第三方库，用于统计项目崩溃日志，自己注册友盟账号
并创建统计项目，拿到appkey配置到自己的项目中，自己项目不用再
引友盟统计sdk，本库已集成

#### 上拉刷新、下拉加载
二次封装的第三方SmartRefreshLayout库

#### 快速搭建Recycler适配器，去除冗余代码
引入了第三方BaseRecyclerViewAdapterHelper库，可直接用

#### 自定义view
带删除内容的EditText: ViewClearEditText<br/>
带图片的TextView：ViewDrawableText <br/>
带图片的EditText：ViewDrawableEdit <br/>
带图片的Button：ViewDrawableButton <br/>
带图片的RadioButton：ViewDrawableRadioBt <br/>

#### 适配器Item间隔
DecorationIntervalGridLinear <br/>
DecorationIntervalSimple

#### 通用适配器
RootAdapter