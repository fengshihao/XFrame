package com.fengshihao.xframe;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * To Manage all modules
 */
public final class XFrame {

  private static final String TAG = "XFrame";
  private static XFrame instance = new XFrame();
  private Map<Class<? extends XModule>, Object> mModuleMap = new HashMap<>();

  private XFrame() {}

  public static XFrame getInstance() {
    return instance;
  }

  public void register(Class<? extends XModule> moduleClass, @NonNull String className) {
    Log.d(TAG, "register() called with: moduleClass = [" 
        + moduleClass + "], className = [" + className + "]");
    try {
      Class cl = Class.forName(className);
      mModuleMap.put(moduleClass, cl.newInstance());

    } catch (Exception e) {
      Log.e(TAG, "register: failed for className=" + className, e);
      throw new RuntimeException(e);
    }
  }

  @NonNull
  public <T extends XModule> T getModule(@NonNull Class<T> moduleClass) {
    Object o = mModuleMap.get(moduleClass);
    if (o == null) {
      Log.e(TAG, "getModule: cant find module " + moduleClass);
      throw new RuntimeException("getModule: cant find module " + moduleClass);
    }
    return (T) o;
  }
}
