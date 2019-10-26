package com.fengshihao.album.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fengshihao.album.R;
import com.fengshihao.xframe.ui.util.FrescoUtil;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonItemView;

final class AlbumSelectedItemView extends CommonItemView<AlbumSelectItemUIModel> {
  private static final String TAG = "AlbumItemView";

  private TextView mTextView;
  private SimpleDraweeView mImageView;

  public AlbumSelectedItemView(Context context) {
    super(context);
  }

  public AlbumSelectedItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AlbumSelectedItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public void bindViews() {
    mTextView = findViewById(R.id.text_view);
    mImageView = findViewById(R.id.image_view);
  }

  @Override
  public void updateView(int position) {
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
      int size = getResources().getDimensionPixelSize(R.dimen.album_media_thumbnail_size);
      FrescoUtil.imageBindLocalPath(mImageView, mModel.mImagePath, size, size);
    }
  }
}
