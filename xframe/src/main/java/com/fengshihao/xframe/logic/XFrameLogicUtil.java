package com.fengshihao.xframe.logic;


import java.lang.reflect.Proxy;

import androidx.annotation.NonNull;
import android.util.Log;

public class XFrameLogicUtil {
  private static final String TAG = "XFrameLogicUtil";

  private XFrameLogicUtil() {}

  public static Object createProxyObj(@NonNull Object target) {
    return Proxy.newProxyInstance(
        target.getClass().getClassLoader(),
        target.getClass().getInterfaces(),
        (proxy, method, args) -> {
          Log.d(TAG, "invoke() called with: method = [" + method + "]");
          Object returnValue = method.invoke(target, args);
          Log.d(TAG, "invoke() called over: method = [" + method + "] returnValue=" + returnValue);
          return returnValue;
        });
  }
}
