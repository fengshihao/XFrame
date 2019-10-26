package com.fengshihao.xframe.ui.config;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.logic.IXModule;
import com.fengshihao.xframe.logic.XFrame;
import com.fengshihao.xframe.logic.config.ModuleConfig;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;

import java.util.LinkedList;
import java.util.List;

public class ModuleConfigActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_debug);
  }
}
