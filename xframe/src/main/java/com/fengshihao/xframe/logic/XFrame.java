package com.fengshihao.xframe.logic;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.HashMap;
import java.util.Map;

/**
 * To Manage all modules
 */
public final class XFrame {

  private static final String TAG = "XFrame";
  private static XFrame instance = new XFrame();
  private Map<Class<? extends XModule>, XModule> mModuleMap = new HashMap<>();

  private XFrame() {}

  public static XFrame getInstance() {
    return instance;
  }

  public void register(Class<? extends XModule> moduleClass, @NonNull String className) {
    Log.d(TAG, "register() called with: moduleClass = [" 
        + moduleClass + "], className = [" + className + "]");
    try {
      Class cl = Class.forName(className);
      mModuleMap.put(moduleClass, (XModule) cl.newInstance());

    } catch (Exception e) {
      Log.e(TAG, "register: failed for className=" + className, e);
      throw new RuntimeException(e);
    }
  }

  @NonNull
  public <T extends XModule> T getModule(@NonNull Class<T> moduleClass) {
    XModule module = mModuleMap.get(moduleClass);
    if (module == null) {
      Log.e(TAG, "getModule: cant find module " + moduleClass);
      throw new RuntimeException("getModule: cant find module " + moduleClass);
    }
    return (T) module;
  }

  public void onApplicationStart(@NonNull Application app) {
    Fresco.initialize(app);
    for (XModule m: mModuleMap.values()) {
      m.onApplicationStart(app);
    }
  }
}
