package com.fengshihao.xframe.ui.config;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.logic.IXModule;
import com.fengshihao.xframe.logic.XFrame;
import com.fengshihao.xframe.logic.config.ModuleConfig;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

import java.util.LinkedList;
import java.util.List;

public class ModuleConfigActivity extends AppCompatActivity {

  private RecyclerView mModuleConfigView;
  private CommonAdapter<ModuleConfigUIModel> mAdapter = new CommonAdapter<ModuleConfigUIModel>() {
    @Override
    protected CommonViewHolder<ModuleConfigUIModel> createItemViewHolder(
        @NonNull View v, @LayoutRes int layoutId) {
      if (layoutId == R.layout.module_config_int_item) {
        return new ModuleConfigIntViewHolder(v);
      }
      return  null;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_debug);
    mModuleConfigView = findViewById(R.id.module_list);
    mModuleConfigView.setAdapter(mAdapter);

    List<IXModule> moduleList = XFrame.getInstance().getAllModules();
    List<ModuleConfigUIModel> items = new LinkedList<>();
    for (IXModule m : moduleList) {
      items.add(new ModuleConfigUIModel(m.getName(), null));
      List<ModuleConfig> configs = m.getConfigs();
      for (ModuleConfig config: configs) {
        items.add(new ModuleConfigUIModel(config.mName, config));
      }
    }
    mAdapter.getPageList().addAll(items);
  }

  @Override
  public void onStart() {
    super.onStart();

  }
}
