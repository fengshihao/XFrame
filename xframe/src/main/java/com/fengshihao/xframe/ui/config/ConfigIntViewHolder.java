package com.fengshihao.xframe.ui.config;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

import java.util.List;

class ConfigIntViewHolder extends CommonViewHolder<ModuleConfigUIModel> {

  private TextView mTextView;

  private EditText mValueView;

  ConfigIntViewHolder(View v) {
    super(v);
  }

  @Override
  protected void bindView(@NonNull View itemView) {
    mTextView = itemView.findViewById(R.id.text_view);
    mValueView = itemView.findViewById(R.id.value_edit_view);
    mValueView.setOnFocusChangeListener((v, b) -> {
      if (mModel != null && mModel.mConfig != null) {
        String val = mValueView.getText().toString();
        Integer ret = Integer.valueOf(val);
        mModel.mConfig.setValue(ret);
      }
    });
  }

  @Override
  protected void updateView(int position, @NonNull List<Object> payloads) {
    if (mModel != null && mModel.mConfig != null) {
      mTextView.setText(mModel.mTitle);
      String val = String.valueOf(mModel.mConfig.<Integer>get());
      mValueView.setText(val);
      mValueView.setSelection(val.length());
    } else {
      mTextView.setText(R.string.some_thing_wrong);
    }
  }
}
