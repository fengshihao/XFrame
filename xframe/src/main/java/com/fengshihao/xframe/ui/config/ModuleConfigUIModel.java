package com.fengshihao.xframe.ui.config;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.logic.config.ModuleConfig;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemModel;

public class ModuleConfigUIModel extends CommonItemModel {
  @NonNull
  public final String mTitle;

  @Nullable
  public final ModuleConfig mConfig;
  public ModuleConfigUIModel(@NonNull String title, @Nullable ModuleConfig config) {
    super(-1);
    mTitle = title;
    mConfig = config;
  }

  @LayoutRes
  public int getLayoutId() {
    if (mConfig == null) {
      return R.layout.module_config_title_item;
    }

    if (mConfig.isInt()) {
      return R.layout.module_config_int_item;
    }

    if (mConfig.isBoolean()) {
      return R.layout.module_config_boolean_item;
    }
    return R.layout.module_config_title_item;
  }

  @Override
  public String toString() {
    return "ModuleConfigUIModel name=" + mTitle + " " + mConfig;
  }
}
