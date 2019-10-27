package com.fengshihao.xframe.ui.config;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

import java.util.List;

class ConfigBooleanViewHolder extends CommonViewHolder<ModuleConfigUIModel> {


  private TextView mTextView;

  private Switch mSwitchView;

  public ConfigBooleanViewHolder(View v) {
    super(v);
  }

  @Override
  protected void bindView(@Nullable View itemView) {
    mTextView = itemView.findViewById(R.id.text_view);
    mSwitchView = itemView.findViewById(R.id.value_switch);
    mSwitchView.setOnCheckedChangeListener((compoundButton, b) -> {
      if (mModel != null && mModel.mConfig != null) {
        mModel.mConfig.setValue(b);
      }
    });
  }

  @Override
  protected void updateView(int position, List<Object> payloads) {
    if (mModel != null) {
      mTextView.setText(mModel.mTitle);
      mSwitchView.setChecked(mModel.mConfig.get());
    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }
}
