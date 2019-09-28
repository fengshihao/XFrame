package com.fengshihao.xframe.ui.config;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;

final class ModuleConfigBooleanView extends CommonItemView<ModuleConfigUIModel> {
  private static final String TAG = "ModuleConfigBooleanView";

  private TextView mTextView;

  private Switch mSwitchView;

  public ModuleConfigBooleanView(Context context) {
    super(context);
  }

  public ModuleConfigBooleanView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ModuleConfigBooleanView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
    mSwitchView = findViewById(R.id.value_switch);
    mSwitchView.setOnCheckedChangeListener((compoundButton, b) -> {
      if (mModel != null && mModel.mConfig != null) {
        Log.d(TAG, "bindViews: " + b);
        mModel.mConfig.setValue(b);
      }
    });
  }

  @Override
  public void updateView(int position) {
    if (mModel != null) {
      mTextView.setText(mModel.mTitle);
      mSwitchView.setChecked(mModel.mConfig.get());
    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }

}
