package com.fengshihao.xframe.ui.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fengshihao.xframe.logic.config.ModuleConfig;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.ICommonItemModel;

public class ModuleConfigUIModel implements ICommonItemModel {
  @NonNull
  public final String mTitle;

  @Nullable
  public final ModuleConfig mConfig;
  public ModuleConfigUIModel(@NonNull String title, @Nullable ModuleConfig config) {
    mTitle = title;
    mConfig = config;
  }

  @Override
  public int getViewType() {
    if (mConfig == null) {
      return 0;
    }

    if (mConfig.isInt()) {
      return 1;
    }

    if (mConfig.isBoolean()) {
      return 2;
    }
    return 0;
  }
}
