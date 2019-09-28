package com.fengshihao.xframe.ui.config;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;

final class ModuleConfigBooleanView extends CommonItemView<ModuleConfigUIItem> {
  private static final String TAG = "ModuleConfigBooleanView";

  private TextView mTextView;

  private Switch mSwitchView;
  private ModuleConfigUIItem mItem;

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
      if (mItem != null && mItem.mConfig != null) {
        Log.d(TAG, "bindViews: " + b);
        mItem.mConfig.setValue(b);
      }
    });
  }

  @Override
  public void updateView(@Nullable ModuleConfigUIItem uiModel, int position) {
    mItem = uiModel;
    if (mItem != null) {
      mTextView.setText(mItem.mTitle);
    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }

}
