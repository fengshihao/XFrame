package com.fengshihao.album.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fengshihao.album.R;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.util.FrescoUtil;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;

final class AlbumItemView extends CommonItemView<AlbumItemUIModel> {
  private static final String TAG = "AlbumItemView";

  private TextView mTextView;
  private SimpleDraweeView mImageView;

  private AlbumItemUIModel mModel;

  public AlbumItemView(Context context) {
    super(context);
  }

  public AlbumItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AlbumItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
    mImageView = findViewById(R.id.image_view);
    setOnClickListener(view -> {
      if (mModel == null) {
        return;
      }
      AlbumProject project = AlbumProject.getActiveProject();
      project.toggleSelect(mModel.mItemId);
    });
  }

  @Override
  public void updateView(@Nullable AlbumItemUIModel uiModel, int position) {
    mModel = uiModel;
    if (uiModel == null) {
      mTextView.setVisibility(VISIBLE);
      mTextView.setText("empty");
      mImageView.setImageResource(R.drawable.placeholder);
      return;
    }
    mTextView.setText(mModel.mInfo);
    mTextView.setVisibility(mModel.isSelected() ? INVISIBLE : VISIBLE);
    if (TextUtils.isEmpty(mModel.mImagePath)) {
      Log.e(TAG, "updateView: get a wrong data " + mModel);
      mImageView.setImageResource(R.drawable.placeholder);
    } else {
      int size = getResources().getDimensionPixelSize(R.dimen.album_media_thumbnail_size);
      FrescoUtil.imageBindLocalPath(mImageView, mModel.mImagePath, size, size);
    }
  }
}
