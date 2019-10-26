package com.fengshihao.xframe.ui.config;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

class ConfigTitleViewHolder extends CommonViewHolder<ModuleConfigUIModel> {
  private TextView mTextView;

  public ConfigTitleViewHolder(View v) {
    super(v);
  }

  @Override
  protected void bindView(@Nullable View itemView) {
    mTextView = itemView.findViewById(R.id.text_view);
  }

  @Override
  protected void updateView(int position) {
    if (mModel != null) {
      mTextView.setText(mModel.mTitle);
    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }
}
