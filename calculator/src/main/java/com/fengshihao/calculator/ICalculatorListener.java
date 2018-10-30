package com.fengshihao.calculator;

/**
 * Created by fengshihao on 18-10-28.
 */

public interface ICalculatorListener {
    void onGetAsyncWorkResult(int errorCode, String msg);
}
