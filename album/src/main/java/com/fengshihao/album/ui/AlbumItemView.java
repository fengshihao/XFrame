package com.fengshihao.album.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fengshihao.album.R;
import com.fengshihao.xframe.logic.ItemSelection;
import com.fengshihao.xframe.ui.util.FrescoUtil;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView;

final class AlbumItemView extends CommonRecyclerView.ItemView<AlbumItemUIModel> {
  private static final String TAG = "AlbumItemView";

  @Nullable
  public static ItemSelection<Integer> sSelection;

  public AlbumItemView(Context context) {
    super(context);
  }

  public AlbumItemView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public AlbumItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  private TextView mTextView;
  private SimpleDraweeView mImageView;

  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
    mImageView = findViewById(R.id.image_view);
    mTextView.setOnClickListener(v -> Log.d(TAG, "bindViews: click TextView"));
  }

  @Override
  public void updateView(@NonNull AlbumItemUIModel uiModel) {
    AlbumItemUIModel m = uiModel;
    mTextView.setText(m.mInfo);
    boolean selected = sSelection != null && sSelection.isSelected(getPosition());
    mTextView.setVisibility(selected ? INVISIBLE : VISIBLE);
    if (TextUtils.isEmpty(m.mImagePath)) {
      mImageView.setImageResource(R.drawable.placeholder);
    } else {
      FrescoUtil.imageBindLocalPath(mImageView, m.mImagePath, 100, 100);
    }
  }
}
