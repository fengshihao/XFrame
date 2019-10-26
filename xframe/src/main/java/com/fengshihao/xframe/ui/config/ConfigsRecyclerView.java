package com.fengshihao.xframe.ui.config;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.logic.IXModule;
import com.fengshihao.xframe.logic.XFrame;
import com.fengshihao.xframe.logic.config.ModuleConfig;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;

import java.util.LinkedList;
import java.util.List;

public class ConfigsRecyclerView extends RecyclerView {

  private static final String TAG = "ConfigsView";
  private CommonAdapter<ModuleConfigUIModel> mAdapter = new CommonAdapter<>();

  public ConfigsRecyclerView(Context context) {
    super(context);
    init();
  }

  public ConfigsRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ConfigsRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    mAdapter.setHolderCreator((v, layoutId) -> {
      if (layoutId == R.layout.module_config_boolean_item) {
        return new ConfigBooleanViewHolder(v);
      } else if (layoutId == R.layout.module_config_int_item) {
        return new ConfigIntViewHolder(v);
      } else if (layoutId == R.layout.module_config_title_item) {
        return new ConfigTitleViewHolder(v);
      }
      throw new RuntimeException("cant find view layout type");
    });

    setAdapter(mAdapter);

    List<IXModule> moduleList = XFrame.getInstance().getAllModules();
    List<ModuleConfigUIModel> items = new LinkedList<>();
    for (IXModule m : moduleList) {
      items.add(new ModuleConfigUIModel(m.getName(), null));
      List<ModuleConfig> configs = m.getConfigs();
      for (ModuleConfig config : configs) {
        Log.d(TAG, "onCreate: add config module=" + m.getName() + " " + config);
        items.add(new ModuleConfigUIModel(config.mName, config));
      }
    }
    mAdapter.getPageList().addAll(items);
  }
}
