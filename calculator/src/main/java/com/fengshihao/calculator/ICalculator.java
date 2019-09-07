package com.fengshihao.calculator;

import com.fengshihao.xframe.XModule;

/**
 * Created by fengshihao on 18-10-28.
 */

public interface ICalculator extends XModule {
    float sum(float v1, float v2);
    void asyncWork(String msg); //异步API
    ICalculatorListenerList getListeners();
}
