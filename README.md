# XFrame的初衷

   初衷是传播一种客户端编程的理念. XFrame是这个这种理念的Android上的实现.
   
   为什么不给开发一个框架, 而是强调一个理念. 是因为框架是死的, 细节繁琐不适合人类记忆. 人类大脑适合思考,总结,抽象. 
   
   XFrame的代码就是用来表达这个理念的, 希望开发能从中能抽象出 *UI代码和Logic代码分开* 这么个思想.  如果只是口头介绍或者举例说明这个理念,仍然不够详细,让人不知道如何理解. 
         
   市面上已经有很多客户端框架, 比如 MVC, MVP, MVVM, React, Vue, Flux, Redux ... 还有很多种平台, 很多种系统. 在开发过手机游戏, web, windows, android, 后自己总结出一套客户端编程的理念.  避免开发人员学习各种框架的成本. 
   
   这种思想其实在各个框架都有体现, 只是没有人强调出来.  唯一看和此理念很相仿的是在一篇Android的blog上 (ViewModels and LiveData: Patterns + AntiPatterns) 这篇介绍了如何'正确'使用ViewModel LiveData.  
   * ❌ Don’t let ViewModels (and Presenters) know about Android framework classes
   * ✅ Keep the logic in Activities and Fragments to a minimum
   * ❌ Avoid references to Views in ViewModels.
   * ✅ Instead of pushing data to the UI, let the UI observe changes to it.
   
  ...
  
   如果更精简的说就是*UI代码和Logic代码分开*, *UI代码和Logic代码分开*, *UI代码和Logic代码分开* 
   
   这个理念的更详细细节就是:
   * 所有UI代码放入ui文件夹, 包括 显示,隐藏 ,动画, 字体...
   * 所有非UI代码放入logic文件夹. 包括io, 网络, 业务逻辑...
   
# XFrame 代码介绍
   xframe可以说是一个简单相册功能的app , 或者说以框架带有一个相册模块,主要用来展示如何分离ui和逻辑, 然后展示如何模块化. 而且大部分app需要一个相册功能.
   
   代码分几个模块
   * xframe 
       - 简单的模块化功能, 新开发一个功能就是添加一个模块, 并且非常简单的配置这个模块的可配置项,用于调试
       - 带有一个通用的recyclerView, 带有延迟加载, 分页加载等功能. 比如相册中1w张照片 从第3007张加载显示.
       - 包含了RxJava Retrofit 基本库.
   * album
       - 主要用来表述如何分离ui和逻辑代码. 所有的ui都在ui文件夹下, 非ui都在logic文件夹, 对外接口都在api文件夹.
       - 懒加载相册图片, 滚动到哪里就load哪里
       - 单选,多选照片
       - 可以快速配置一个可调式开关
   * app
       - 程序壳,可以包含很多模块,使用xframe模块管理各个模块
       
   * xframelint
       - 对*UI代码和Logic代码分开*这种理念的lint自动检查, 避免写出不规范的代码.  输出是一个jar
       - 比如防止在逻辑模块中import了ui的代码.
       - 比如防止import android.view.* 到逻辑代码中
       
   * xframe_lint_aar
       - 多上边xframelint jar的aar包装. 为了在AndroidStudio中使用. 不过现在还不work 不知道为啥

# 最后
   如果你想看之前XFrame的详细说明,请退回到36fe544 这个commit点.  git reset --hard 36fe544


