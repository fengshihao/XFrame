package com.fengshihao.xframe.ui.config;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;

final class ModuleConfigTitleView extends CommonItemView<ModuleConfigUIModel> {
  private static final String TAG = "ModuleConfigTitleView";

  private TextView mTextView;

  public ModuleConfigTitleView(Context context) {
    super(context);
  }

  public ModuleConfigTitleView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ModuleConfigTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }


  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
  }

  @Override
  public void updateView(int position) {
    if (mModel != null) {
      mTextView.setText(mModel.mTitle);
    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }

}
