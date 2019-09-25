package com.fengshihao.album.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fengshihao.album.R;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.util.FrescoUtil;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.ICommonItemModel;

final class AlbumItemView extends CommonItemView {
  private static final String TAG = "AlbumItemView";

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

  private AlbumItemUIModel mModel;

  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
    mImageView = findViewById(R.id.image_view);
    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mModel == null) {
          return;
        }
        AlbumProject.getActiveProject().select(mModel.mData);
      }
    });
  }

  @Override
  public void updateView(@Nullable ICommonItemModel uiModel) {
    mModel = (AlbumItemUIModel) uiModel;
    if (uiModel == null) {
      mTextView.setVisibility(VISIBLE);
      mTextView.setText("empty");
      mImageView.setImageResource(R.drawable.placeholder);
      return;
    }
    mTextView.setText(mModel.mInfo);
    mTextView.setVisibility(mModel.isSelected() ? INVISIBLE : VISIBLE);
    if (TextUtils.isEmpty(mModel.mData.mPath)) {
      mImageView.setImageResource(R.drawable.placeholder);
    } else {
      int size = getResources().getDimensionPixelSize(R.dimen.album_media_thumbnail_size);
      FrescoUtil.imageBindLocalPath(mImageView, mModel.mData.mPath, size, size);
    }
  }
}
