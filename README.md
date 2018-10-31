# XFrame

# 客户端程序的开发心得

##示例代码
https://github.com/fengshihao/XFrame

##有很多框架 MVC  MVP  MVVM  Flux cycle.js... Why?

> 每个框架在诞生时都看起来很好， 似乎问题解决了。

> 开发者之后会发现代码并非之前想象的那样在生长。
  
>   最后那些框架下的代码都长成了.....
 
> 看起来像是某种框架下的畸形儿。 发育的很奇怪。
  
> 各种开发者也许是对**框架概念不熟悉，理解错误，**
  
> **或者就是为了偷懒工期压力的缘故**，违反了各种规则。
  
> 最后写出来就是四不像的代码。
  
> 问题在于**框架只规定该怎么做， 无法禁止不对的做法**
  
##有没有一个终极框架解决这些问题？

> 有，但不是一个框架，而是一套规则，一套心法。
  
> 不是框架不好，是人的问题。 人都是倾向于简单，容易的事情。
  
> 框架越来越复杂，越来越多的概念需要理解， 越来越多的名词需要记忆。
  
> 最后cycle.js干脆到了要 纯函数式编程的地步。
  
> 可无论如何，也无法禁止人写出**非纯**的代码
  
> 之前框架规定的套路都是，都是针对代码的。
  
> 而**人是写代码的，像上帝一样可以为所欲为**。可以无视那些规则。
  
> 除非不计成本的去培训，去review，去防止人们写出坏代码。
  
> 指定一套开发者不能逾越的规则，一个中心思想（心法），才是根本。
  
  
##这套规则是什么样子的？ 
> 所有的框架都在做**解耦**的工作。在人的工作上解耦即可，也就是分工
> 这套规则应该有**很简单的执行性**, 即人们很容易遵守,并且很容易被reviewer发现
> 不遵守规则的地方
> 
  * 有人负责UI代码生成就是View， 有人负责逻辑，数据获取，存储，更新
  * UI只依赖逻辑， 但只知道接口。自己可以mock数据测试
  * 逻辑不需要具体UI可以运行，自己可以写挫UI测试
  * feature代码要切分，也就是不同的feature代码在不同的包里。
  


### 有人会担心几个问题。
1. 沟通成本太高怎么办？
   > 这个不是缺点，而是优点。UI和逻辑沟通需要哪些接口，正是一个思考的工程。无论是一个人在定接口，还是几个人在商量，都是对整个设计的思考。很有利于后续的开发。
2. bug互相扯皮怎么办？
  
  > 接口依赖功能分离，使得bug极易被定位，打印出request和response谁的锅自然很容易分清楚。 而且因为分离，可以各自写测试程序保证了程序的稳定健壮。


3. 开发人员不够怎么办？任务没法指定个多个人。
  > 因为前边规则UI依赖逻辑，逻辑不依赖UI。所以我们可以把UI的代码放在一个工程 ，逻辑代码放在另外的工程。UI工程依赖逻辑工程。gradle里的review可以很简单的保证这点
即便一个人在做这个开发，也必须用接口依赖的方式去做，无法越界。


## 举个栗子来看看吧

### 什么是UI代码？什么是逻辑代码？接口又是什么样子的？
假设我们要做一个计算器

##### 第一个版本 原始写法
```java
        mNumView1 = findViewById(R.id.edit_num1);
        mNumView2 = findViewById(R.id.edit_num2);
        mSumView = findViewById(R.id.view_sum);

        findViewById(R.id.btn_sum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float num1 = Float.valueOf(mNumView1.getText().toString());
                float num2 = Float.valueOf(mNumView2.getText().toString());
                mSumView.setText(String.valueOf(num1 + num2));
            }
        });
```
上边的代码只有 num1 + num2 是逻辑部分其他的都是UI部分代码.

UI代码包括:

* view是否显示, 显示效果
* 动画
* 字体,字符串,各种资源
* 布局

逻辑代码包括:

* 业务逻辑
* 存储
* 数据处理
* 网络
* 文件读写


#### 第二个版本 逻辑, UI分离

* 新建一个模块calculator 
* 分离出ICalculator接口.
```java
public interface ICalculator {
    float sum(float v1, float v2);
}
```
一个接口的实现
```java
// 非public的Calculator本身不被外部访问. 外部是通过 ICalculator 访问
// 这里只是为了说明方便声明为了public, 让外边能通过它访问 createCalculator. 
public class Calculator implements ICalculator {
    ...
    @Override
    public float sum(float v1, float v2) {
        // request的log很重要, 为以后定位问题在ui还是在逻辑模块很有帮助
        log(TAG, "sum v1=" + v1 + " v2=" + v2);
        float ret = v1 + v2;
        // response的log也很重要.
        log(TAG, "sum: result=" + ret);
        return ret;
    }

    //外界是无法访问Calculator具体实现的
    private Calculator() {}
    
    //这里正规的做法是模块化的方式比如用Dagger. 这不是重点.这里先简单实现
    public static ICalculator createCalculator() {
        return new Calculator();
    }
}
```
很多细节,但是主要是一个原则,外部依赖访问接口. 逻辑模块就像一个服务器一样.sum就是一个请求. 结果就是一个回复. ICalculator就是服务器的接口.

#### 第三个版本 一个异步API
ICalculator

```java
public interface ICalculator {
    float sum(float v1, float v2);
    //异步API 
    void asyncWork(String msg); 
    
    // 异步的结果返回callback 是由listener实现的.
    void setCalculatorListener(ICalculatorListener listener); 
}
```
ICalculatorListener

```java
public interface ICalculatorListener {
    //异步的返回结果
    void onGetAsyncWorkResult(int errorCode, String msg);
}
```

MainActivity

```java
        findViewById(R.id.btn_async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalculator.asyncWork("hello async work done!");
            }
        });

        mCalculator.setCalculatorListener(new ICalculatorListener() {
            @Override
            public void onGetAsyncWorkResult(int errorCode, final String msg) {
                Log.d(TAG, "onGetAsyncWorkResult: errorCode=" + errorCode + " msg=" + msg);
                //因为异步的回调是在非UI线程,所以到了这里需要做线程切换
                //切换线程后需要检查是否activity还存在,这里省略
                //所有的这些不便利. 线程切换, 检查activity, 多个listener管理. 
                //都可以用XListener这个工具来简化
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
```
对于异步处理,可以使用RX来做,但是为了简单,推荐用[XListener](https://github.com/fengshihao/xlistener) 模块内部的复杂逻辑可以用RX来做. 对外接口尽量的简单

#### 第四个版本 使用XListener
XListener是个用于代码生成的注解, 帮助生成一些样本代码

首先加入依赖. 
calculator/build.gradle

```java
//这个库包含两样东西, 一个是注解类@XListener 一个是注解处理类
//都是编译期用到, compileOnly 用来引用@XListener annotationProcessor是用来生成代码
    compileOnly 'com.fengshihao.xlistener:xlistener:1.0.1'
    annotationProcessor 'com.fengshihao.xlistener:xlistener:1.0.1'
```

使用@Xlistener
ICalculatorListener.java

```java
@XListener  
public interface ICalculatorListener {
    void onGetAsyncWorkResult(int errorCode, String msg);
}
//这样会生成一个叫做ICalculatorListenerList的类
//这个列主要是维护一个 List<ICalculatorListener>
```

使用生成的类 ICalculatorListenerList
Calculator.java

```java
    @NonNull
    private ICalculatorListenerList mListeners = new ICalculatorListenerList();

    //外界是无法访问Calculator具体实现的
    private Calculator() {
        // 这里会让回调发生在主线程.自动帮助在回调的时候完成线程切换
        mListeners.attachToCurrentThread();
    }
```

MainActivity.java

```java
mCalculator.getListeners().addListener(
    (errorCode, msg) -> {
        // 这里已经是主线程
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    });
```









