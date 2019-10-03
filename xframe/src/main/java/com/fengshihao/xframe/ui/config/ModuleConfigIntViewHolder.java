package com.fengshihao.xframe.ui.config;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

final class ModuleConfigIntViewHolder extends CommonViewHolder<ModuleConfigUIModel> {
  private static final String TAG = "ConfigIntViewHolder";

  private TextView mTextView;

  private EditText mValueView;

  private ModuleConfigUIModel mModel;

  ModuleConfigIntViewHolder(View itemView) {
    super(itemView);
    mTextView = itemView.findViewById(R.id.text_view);
    mValueView = itemView.findViewById(R.id.value_edit_view);
    mValueView.setOnFocusChangeListener((v, b) -> {
      if (mModel != null) {
        String val = mValueView.getText().toString();
        Log.d(TAG, "onFocusChange: val =" + val);
        Integer ret = Integer.valueOf(val);
        mModel.mConfig.setValue(ret);
      }
    });
  }

  @Override
  protected void updateView(@Nullable ModuleConfigUIModel data, int position) {
    mModel = data;
    if (mModel != null) {
      mTextView.setText(mModel.mTitle);
      String val = String.valueOf(mModel.mConfig.<Integer>get());
      mValueView.setText(val);
      mValueView.setSelection(val.length());

    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }
}
