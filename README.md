# SmallFish 小鱼视频开发文档（更新中...）

## 说明

## 功能截图

## 技术框架

## 提交记录
**第一次提交 2020.10.15**
- 初始化工程文件

**第二次提交 2020.10.15**
- 自创建定义注解处理器

**第三次提交 2020.10.15**
- 构建页面导航栏

**第四次提交 2020.10.16**
- 定制app页面底部导航**

**第五次提交 2020.10.16**
- 使用okhttp封装网络请求

**第六次提交 2020.10.26**
- Room数据库创建
- Room实现数据缓存

**第七次提交 2020.10.27**
- 创建各种的ben对象
- 文章作者信息布局文件编写 使用dataBinding
- 文章标签信息布局文件编写 使用dataBinding MaterialButton
  - 注意：使用MaterialButton要将主题修改(在values/styles)parent="Theme.MaterialComponents.Light.NoActionBar"
  - 复写样式，否则不会生效：<!--复写样式--><style name="materialButton" parent="Widget.MaterialComponents.Button">
- 文章评论区域布局编写 使用dataBinding
- 互动区域布局文件编写 使用dataBinding
- 图片动态布局编写，不使用dataBinding,自己写一个类来设置图片大小
- 创建像素工具类PixUtils,dp2px,获取屏幕宽度、获取屏幕高度

**第八次提交 2020.10.27**
- 编写视频类型布局资源文件:layout_player_view.xml
- 视频数据绑定:ListPlayerView.java
  - 视频布局、高斯模糊背景、封面等布局参数

**第九次提交 2020.10.27**
- 首页列表布局:layout_refresh_view.xml
  - 使用SmartRefreshLayout下拉刷新框架
  - 添加错误布局、空布局：com.example.libcommon.EmptyView--->layout_empty_view.xml
  - 提供几个接口给外部设置：设置空图片、提示、按钮事件
- 复写了dao类的equals方法

**第十次提交 2020.10.27**
- 发起网络请求
- 展示首页数据
- 已知bug：评论区域数据不展示、点赞等互动区域不显示
- 页面顶部导航没有消除，消除出现空指针异常
