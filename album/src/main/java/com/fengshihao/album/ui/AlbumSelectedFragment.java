package com.fengshihao.album.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fengshihao.album.R;
import com.fengshihao.album.api.IAlbumProjectListener;
import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.album.logic.AlbumProject;
import com.fengshihao.xframe.ui.util.FrescoUtil;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonAdapter;
import com.fengshihao.xframe.ui.widget.CommonRecyclerView.CommonViewHolder;

import static android.view.View.VISIBLE;


/**
 */
public class AlbumSelectedFragment extends Fragment implements IAlbumProjectListener {
  private static final String TAG = "AlbumSelectedFragment";

  @NonNull
  private final CommonAdapter<AlbumSelectItemUIModel> mCommonAdapter = new CommonAdapter<>();

  @Nullable
  private RecyclerView mListView;

  public AlbumSelectedFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    AlbumProject.getsCurrentProject().addListener(this);
    return inflater.inflate(R.layout.fragment_album_selected, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onViewCreated: ");
    mListView = view.findViewById(R.id.list);
    mCommonAdapter.setHolderCreator((v, layoutId) -> new CommonViewHolder<AlbumSelectItemUIModel>(v) {

      private TextView mTextView;
      private SimpleDraweeView mImageView;
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
          int size = getResources().getDimensionPixelSize(R.dimen.album_media_thumbnail_size);
          FrescoUtil.imageBindLocalPath(mImageView, mModel.mImagePath, size, size);
        }
      }
    });
    mListView.setAdapter(mCommonAdapter);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    AlbumProject.getsCurrentProject().addListener(this);
  }

  @Override
  public void onSelect(@NonNull AlbumMediaItem item) {
    Log.d(TAG, "onSelect() called with: item = [" + item + "]");
    mCommonAdapter.addItem(new AlbumSelectItemUIModel(item.mId, item.mType, item.mPath));
  }

  @Override
  public void onUnSelect(@NonNull AlbumMediaItem item) {
    Log.d(TAG, "onUnSelect() called with: item = [" + item + "]");
    mCommonAdapter.removeById(item.mId);
  }
}
