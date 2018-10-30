package com.fengshihao.calculator;

public class Calculator implements ICalculator {


    private static final String TAG = "Calculator";

    @Override
    public float sum(float v1, float v2) {
        log(TAG, "sum v1=" + v1 + " v2=" + v2);
        float ret = v1 + v2;
        log(TAG, "sum: result=" + ret);
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


    private Calculator() {}

    public static ICalculator createCalculator() {
        return new Calculator();
    }

    private void log(String tag, String msg) {
        System.out.println(tag + ": " + msg);
    }
}
