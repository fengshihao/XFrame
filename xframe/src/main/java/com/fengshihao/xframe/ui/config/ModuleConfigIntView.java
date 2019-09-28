package com.fengshihao.xframe.ui.config;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.fengshihao.xframe.R;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;

final class ModuleConfigIntView extends CommonItemView<ModuleConfigUIModel>  {
  private static final String TAG = "ModuleConfigIntView";


  private TextView mTextView;

  private EditText mValueView;

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
      if (mModel != null) {
        String val = mValueView.getText().toString();
        Log.d(TAG, "onFocusChange: val =" + val);
        Integer ret = Integer.valueOf(val);
        mModel.mConfig.setValue(ret);
      }
    });
  }

  @Override
  public void updateView(int position) {
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
