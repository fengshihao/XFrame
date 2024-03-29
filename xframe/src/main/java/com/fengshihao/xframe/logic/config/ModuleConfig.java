package com.fengshihao.xframe.logic.config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    Log.d(TAG, "setValue: " + mName + " v=" + v);
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

  @Override
  public String toString() {
    return TAG + " " + mName + " " + mValue;
  }
}
