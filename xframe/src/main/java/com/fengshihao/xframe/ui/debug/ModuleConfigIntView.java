package com.fengshihao.xframe.ui.debug;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.ICommonItemModel;

final class ModuleConfigIntView extends CommonItemView {
  private static final String TAG = "ModuleConfigIntView";

  public ModuleConfigIntView(Context context) {
    super(context);
  }

  public ModuleConfigIntView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public ModuleConfigIntView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private TextView mTextView;


  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
  }

  @Override
  public void updateView(@Nullable ICommonItemModel uiModel, int position) {
    ModuleConfigUIItem item = (ModuleConfigUIItem) uiModel;
    if (item != null) {
      mTextView.setText(item.mTitle);
    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }

}
