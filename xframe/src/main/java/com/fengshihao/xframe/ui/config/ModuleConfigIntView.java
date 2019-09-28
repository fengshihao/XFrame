package com.fengshihao.xframe.ui.config;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.ICommonItemModel;

final class ModuleConfigIntView extends CommonItemView<ModuleConfigUIItem>  {
  private static final String TAG = "ModuleConfigIntView";


  private TextView mTextView;

  private EditText mValueView;

  private ModuleConfigUIItem mItem;

  public ModuleConfigIntView(Context context) {
    super(context);
  }

  public ModuleConfigIntView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ModuleConfigIntView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
    mValueView = findViewById(R.id.value_edit_view);
    mValueView.setOnFocusChangeListener((view, b) -> {
      if (mItem != null) {
        String val = mValueView.getText().toString();
        Log.d(TAG, "onFocusChange: val =" + val);
        Integer ret = Integer.valueOf(val);
        mItem.mConfig.setValue(ret);
      }
    });
  }

  @Override
  public void updateView(@Nullable ModuleConfigUIItem uiModel, int position) {
    mItem= uiModel;
    if (mItem != null) {
      mTextView.setText(mItem.mTitle);
      String val = String.valueOf(mItem.mConfig.<Integer>get());
      mValueView.setText(val);
      mValueView.setSelection(val.length());

    } else {
      mTextView.setText("some thing wrong !!!");
    }
  }

}
