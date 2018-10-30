package com.fengshihao.calculator;

// 非public的Calculator本身不被外部访问. 外部是通过 ICalculator 访问
// 这里只是为了说明方便声明为了public, 让外边能通过它访问 createCalculator.
public class Calculator implements ICalculator {


    private static final String TAG = "Calculator";

    @Override
    public float sum(float v1, float v2) {
        log(TAG, "sum v1=" + v1 + " v2=" + v2); // request的log很重要, 为以后定位问题在ui还是在逻辑模块很有帮助
        float ret = v1 + v2;
        log(TAG, "sum: result=" + ret); // response的log也很重要.
        return ret;
    }

    @Override
    public void asyncWork(final String msg) {
        log(TAG, "asyncWork msg=" + msg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {

                }
                if (mListener != null) {
                    mListener.onGetAsyncWorkResult(0, msg);
                }
            }
        }).start();
    }

    private ICalculatorListener mListener;
    @Override
    public void setCalculatorListener(ICalculatorListener listener) {
        mListener = listener;
    }

    //外界是无法访问Calculator具体实现的
    private Calculator() {}

    //这里正规的做法是模块化的方式比如用Dagger. 这不是重点.这里先简单实现
    public static ICalculator createCalculator() {
        return new Calculator();
    }

    private void log(String tag, String msg) {
        System.out.println(tag + ": " + msg);
    }
}
