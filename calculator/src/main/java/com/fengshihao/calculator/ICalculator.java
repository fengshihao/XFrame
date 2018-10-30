package com.fengshihao.calculator;

/**
 * Created by fengshihao on 18-10-28.
 */

public interface ICalculator {
    float sum(float v1, float v2);
    void asyncWork(String msg); //异步API 
    void setCalculatorListener(ICalculatorListener listener); // 异步的结果返回callback 是由listener实现的.
}
