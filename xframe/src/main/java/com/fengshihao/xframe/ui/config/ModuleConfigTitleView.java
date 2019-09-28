package com.fengshihao.xframe.ui.config;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.ICommonItemModel;

final class ModuleConfigTitleView extends CommonItemView<ModuleConfigUIItem> {
  private static final String TAG = "ModuleConfigBooleanView";

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
  public void updateView(@Nullable ModuleConfigUIItem uiModel, int position) {
    ModuleConfigUIItem item = uiModel;
    if (item != null) {
      mTextView.setText(item.mTitle);
    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }

}
