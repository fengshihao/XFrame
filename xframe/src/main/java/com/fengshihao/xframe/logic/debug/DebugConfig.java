package com.fengshihao.xframe.logic.debug;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fengshihao.xframe.logic.listener.ListenerManager;

public class DebugConfig extends ListenerManager<IConfigListener> {

  private static final String TAG = "DebugConfig";

  @NonNull
  public final String mName;

  private Object mValue;


  public DebugConfig(@NonNull String name) {
    mName = name;
  }

  public void setValue(@NonNull Object v) {
    if (mValue == v) {
      return;
    }
    Log.d(TAG, "setValue: " + mName + " " + mValue);
    mValue = v;
    notifyListeners(l -> l.onChanged(mValue));
  }

}
