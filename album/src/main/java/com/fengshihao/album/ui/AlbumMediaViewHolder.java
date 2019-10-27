package com.fengshihao.album.ui;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fengshihao.album.R;
import com.fengshihao.album.api.IAlbumProject;
import com.fengshihao.xframe.ui.util.FrescoUtil;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

class AlbumMediaViewHolder extends CommonViewHolder<AlbumItemUIModel> {
  private static final String TAG = "AlbumItemViewHolder";

  private TextView mTextView;
  private SimpleDraweeView mImageView;

  @NonNull
  private final IAlbumProject mProject;

  AlbumMediaViewHolder(@NonNull View v, @NonNull IAlbumProject project) {
    super(v);
    mProject = project;
  }

  @Override
  protected void bindView(@NonNull View itemView) {
    mTextView = itemView.findViewById(R.id.text_view);
    mImageView = itemView.findViewById(R.id.image_view);
    itemView.setOnClickListener(view -> {
      if (mModel == null) {
        return;
      }

      mProject.toggleSelect(mModel.mId);
    });
  }

  @Override
  protected void updateView(int position) {
    if (mModel == null) {
      mTextView.setVisibility(View.VISIBLE);
      mTextView.setText("empty");
      mImageView.setImageResource(R.drawable.placeholder);
      return;
    }
    mTextView.setText(mModel.mInfo);
    mTextView.setVisibility(mProject.isSelected(mModel.mId)
        ? View.INVISIBLE : View.VISIBLE);
    if (TextUtils.isEmpty(mModel.mImagePath)) {
      Log.e(TAG, "updateView: get a wrong data " + mModel);
      mImageView.setImageResource(R.drawable.placeholder);
    } else {
      int size = mImageView.getResources().getDimensionPixelSize(R.dimen.album_media_thumbnail_size);
      FrescoUtil.imageBindLocalPath(mImageView, mModel.mImagePath, size, size);
    }
  }
}
