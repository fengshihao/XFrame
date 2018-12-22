package com.fengshihao.xframe;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * To Manage all modules
 */
public class XFrame {

    private static final String TAG = "XFrame";
    private static XFrame instance = new XFrame();
    private XFrame() {}

    public static XFrame getInstance() {
        return instance;
    }

    @NonNull
    private Map<String, XModule> mModules = new HashMap<>();
    public boolean register(String name, XModule module) {
        Log.d(TAG, "register() called with: name = [" + name + "], module = [" + module + "]");
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name is empty");
        }

        if (module == null) {
            throw new IllegalArgumentException("module is null");
        }

        if (mModules.containsKey(name)) {
            throw new IllegalArgumentException("this name has been used name=" + name);
        }

        mModules.put(name, module);
        return false;
    }

    public <T> T getModule(String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name is empty");
        }
        XModule m = mModules.get(name);
        if (m == null) {
            return null;
        }
        return (T) m;
    }
}
