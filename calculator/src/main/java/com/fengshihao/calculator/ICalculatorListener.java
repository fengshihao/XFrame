package com.fengshihao.calculator;

import com.fengshihao.xlistener.XListener;

/**
 * Created by fengshihao on 18-10-28.
 */

@XListener
public interface ICalculatorListener {
    void onGetAsyncWorkResult(int errorCode, String msg);
}
