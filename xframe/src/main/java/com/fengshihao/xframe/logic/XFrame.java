package com.fengshihao.xframe.logic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.fengshihao.xframe.ui.config.ModuleConfigActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * To Manage all modules
 */
public final class XFrame {

  private static final String TAG = "XFrame";
  private static Application context;
  private static final XFrame instance = new XFrame();
  private final Map<Class<? extends IXModule>, IXModule> mModuleMap = new HashMap<>();

  private XFrame() {
  }

  public static XFrame getInstance() {
    return instance;
  }

  @NonNull
  public static Context getContext() {
    return context;
  }

  public <T extends IXModule> void register(@NonNull Class<T> moduleClass, @NonNull T module) {
    if (mModuleMap.containsKey(moduleClass)) {
      Log.e(TAG, "register: already exist " + moduleClass);
      return;
    }
    Log.d(TAG, "register: " + module);
    mModuleMap.put(moduleClass, module);
  }

  public <T extends IXModule> void register(@NonNull Class<T> moduleClass, @NonNull String className) {
    Log.d(TAG, "register() called with: moduleClass = ["
        + moduleClass + "], className = [" + className + "]");
    if (mModuleMap.containsKey(moduleClass)) {
      Log.e(TAG, "register: already exist " + moduleClass);
      return;
    }
    try {
      Class cl = Class.forName(className);
      mModuleMap.put(moduleClass, (IXModule) cl.newInstance());

    } catch (Exception e) {
      Log.e(TAG, "register: failed for className=" + className, e);
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public <T extends IXModule> T getModule(@NonNull Class<T> moduleClass) {
    IXModule module = mModuleMap.get(moduleClass);
    if (module == null) {
      Log.e(TAG, "getModule: cant find module " + moduleClass);
      throw new RuntimeException("getModule: cant find module " + moduleClass);
    }
    return (T) module;
  }

  public void onApplicationStart(@NonNull Application app) {
    context = app;
    Fresco.initialize(app);
    for (IXModule m : mModuleMap.values()) {
      m.onApplicationStart(app);
    }
  }

  public void startDebugActivity(@NonNull Activity fromActivity) {
    fromActivity.startActivity(new Intent(fromActivity, ModuleConfigActivity.class));
  }

  @NonNull
  public List<IXModule> getAllModules() {
    return new ArrayList<>(mModuleMap.values());
  }
}
