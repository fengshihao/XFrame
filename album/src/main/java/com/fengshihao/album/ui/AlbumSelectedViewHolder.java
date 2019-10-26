package com.fengshihao.album.ui;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fengshihao.album.R;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.util.FrescoUtil;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

import static android.view.View.VISIBLE;

class AlbumSelectedViewHolder extends CommonViewHolder<AlbumSelectItemUIModel> {

  private static final String TAG = "AlbumMediaSelectedViewH";
  private AlbumSelectedFragment albumSelectedFragment;
  private TextView mTextView;
  private SimpleDraweeView mImageView;

  public AlbumSelectedViewHolder(AlbumSelectedFragment albumSelectedFragment, View v) {
    super(v);
    this.albumSelectedFragment = albumSelectedFragment;
  }

  @Override
  protected void bindView(@NonNull View itemView) {
    mTextView = itemView.findViewById(R.id.text_view);
    mImageView = itemView.findViewById(R.id.image_view);
    itemView.findViewById(R.id.remove_button).setOnClickListener(view1 -> {
      if (mModel != null) {
        Log.d(TAG, "bindView: click remove " + mModel);
        AlbumProject.getsCurrentProject().unSelect(mModel.mId);
      }
    });
  }

  @Override
  protected void updateView(int position) {
    if (mModel == null) {
      mTextView.setVisibility(VISIBLE);
      mTextView.setText("empty");
      mImageView.setImageResource(R.drawable.placeholder);
      return;
    }
    mTextView.setText(mModel.mInfo);
    if (TextUtils.isEmpty(mModel.mImagePath)) {
      Log.e(TAG, "updateView: get a wrong data " + mModel);
      mImageView.setImageResource(R.drawable.placeholder);
    } else {
      int size = albumSelectedFragment.getResources().getDimensionPixelSize(R.dimen.album_media_thumbnail_size);
      FrescoUtil.imageBindLocalPath(mImageView, mModel.mImagePath, size, size);
    }
  }
}
