package com.fengshihao.xframe.logic.debug;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fengshihao.xframe.logic.listener.ListenerManager;

public class ModuleConfig extends ListenerManager<IConfigListener> {

  private static final String TAG = "ModuleConfig";

  @NonNull
  public final String mName;

  @Nullable
  private Object mValue;

  @Nullable
  private final Object mDefault;


  public <T> ModuleConfig(@NonNull String name, @Nullable T def) {
    mName = name;
    mDefault = def;
    mValue = mDefault;
  }

  public <T> void setValue(@Nullable T v) {
    if (mValue == v) {
      return;
    }
    Log.d(TAG, "setValue: " + mName + " v=" + mValue);
    mValue = v;
    notifyListeners(l -> l.onChanged(mValue));
  }

  public <T> T get() {
    if (mValue == null) {
      return (T) mDefault;
    }
    return (T) mValue;
  }

  public boolean isInt() {
    return mValue instanceof Integer || mDefault instanceof  Integer;
  }

  public boolean isBoolean() {
    return mValue instanceof Boolean || mDefault instanceof  Boolean;

  }
}
