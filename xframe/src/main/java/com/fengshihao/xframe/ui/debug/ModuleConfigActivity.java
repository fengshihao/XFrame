package com.fengshihao.xframe.ui.debug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.logic.XFrame;
import com.fengshihao.xframe.logic.XModule;
import com.fengshihao.xframe.logic.debug.ModuleConfig;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;

import java.util.LinkedList;
import java.util.List;

public class ModuleConfigActivity extends AppCompatActivity {

  private RecyclerView mModuleConfigView;
  private CommonAdapter<ModuleConfigUIItem> mAdapter = new CommonAdapter<>(
      R.layout.module_config_title_item,
      R.layout.module_config_int_item,
      R.layout.module_config_boolean_item);
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_debug);
    mModuleConfigView = findViewById(R.id.module_list);
    mModuleConfigView.setAdapter(mAdapter);

    List<XModule> moduleList = XFrame.getInstance().getAllModules();
    List<ModuleConfigUIItem> items = new LinkedList<>();
    for (XModule m : moduleList) {
      items.add(new ModuleConfigUIItem(m.getName(), null));
      List<ModuleConfig> configs = m.getConfigs();
      for (ModuleConfig config: configs) {
        items.add(new ModuleConfigUIItem(config.mName, config));
      }
    }
    mAdapter.getPageList().addAll(items);
  }

  @Override
  public void onStart() {
    super.onStart();

  }
}
