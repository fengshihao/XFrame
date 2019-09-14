package com.fengshihao.xframe.ui.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.fengshihao.xframe.logic.layzlist.PageList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Common RecyclerView to avoiding define a Adapter and a ViewHolder
 */
public class CommonRecyclerView extends RecyclerView {

  private static final String TAG = "CommonRecyclerView";

  private Listener mListener;

  @NonNull
  private final CommonAdapter mCommonAdapter = new CommonAdapter();

  public CommonRecyclerView(Context context) {
    super(context);
    init(null, 0);
  }

  public CommonRecyclerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0);
  }

  public CommonRecyclerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs, defStyle);
  }

  private void init(AttributeSet attrs, int defStyle) {
    setAdapter(mCommonAdapter);
  }

  public void setItemLayoutId(@LayoutRes int... layoutIds) {
    mCommonAdapter.setItemLayoutId(layoutIds);
  }

  public <T extends ItemModel> void setModels(@NonNull List<T> list1) {
    mCommonAdapter.clear();
    mCommonAdapter.addAll(list1);
    Log.d(TAG, "setModels: size=" + list1.size());
    mCommonAdapter.notifyDataSetChanged();
  }

  public void select(int... pos) {
    mCommonAdapter.select(pos);
  }

  public void setListener(Listener listener) {
    mListener = listener;
  }

  public interface ItemModel {

    default int getViewType() {
      return 0;
    }
  }

  public interface Listener {
    void onClickItem(int pos);
  }


  public class CommonAdapter extends Adapter<CommonViewHolder> {
    private static final String TAG = "CommonAdapter";

    @NonNull
    private final Set<Integer> mSelects = new HashSet<>();

    @NonNull
    private final PageList<ItemModel> mList = new PageList<>();

    @LayoutRes
    private int[] mItemLayoutIds;

    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (mItemLayoutIds == null) {
        throw new IllegalStateException("must call CommonAdapter.setItemLayoutId first!!");
      }

      if (mItemLayoutIds.length <= viewType) {
        throw new IllegalStateException("must call CommonAdapter.setItemLayoutId lack of viewType="
            + viewType + " now mItemLayoutIds=" + Arrays.toString(mItemLayoutIds));
      }

      ItemView v = (ItemView) LayoutInflater
          .from(parent.getContext()).inflate(mItemLayoutIds[viewType], parent, false);
      v.bindViews();
      return new CommonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
      Log.d(TAG, "onBindViewHolder: position=" + position);
      final int pos = position;
      holder.itemView.setOnClickListener(view -> {
        if (mListener != null) {
          mListener.onClickItem(pos);
        }
      });
      holder.updateView(mList.get(position), position, mSelects.contains(pos));
    }

    @Override
    public int getItemCount() {
      return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
      return mList.get(position).getViewType();
    }

    void setItemLayoutId(@LayoutRes int... layoutIds) {
      Log.d(TAG, "setItemLayoutId() called with: layoutIds = ["
          + Arrays.toString(layoutIds) + "]");
      mItemLayoutIds = layoutIds;
    }

    void clear() {
      mSelects.clear();
      mList.clear();
    }

    <T extends ItemModel> void addAll(@NonNull List<T> list) {
      for (T m : list) {
        mList.add(m);
      }
    }

    void add(ItemModel m) {
      mList.add(m);
    }

    void select(@NonNull int... pos) {
      Set<Integer> oldSelect = new HashSet<>(mSelects);

      mSelects.clear();
      for (Integer idx : pos) {
        if (idx < 0 || idx >= mList.size()) {
          throw new IllegalArgumentException("wrong idx=" + idx);
        }

        mSelects.add(idx);
      }

      for (Integer idx : mSelects) {
        if (oldSelect.contains(idx)) {
          continue;
        }
        Log.d(TAG, "select: new select =" + idx);
        notifyItemChanged(idx);
      }
      oldSelect.removeAll(mSelects);
      for (Integer idx : oldSelect) {
        Log.d(TAG, "select: un select =" + idx);
        notifyItemChanged(idx);
      }
    }
  }

  static class CommonViewHolder extends ViewHolder {

    CommonViewHolder(ItemView itemView) {
      super(itemView);
    }

    void updateView(@NonNull ItemModel model, int position, boolean selected) {
      ItemView v = (ItemView) itemView;
      v.updateView(model, selected);
      v.setPosition(position);
      v.setSelected(selected);
    }
  }

  public static abstract class ItemView extends FrameLayout {
    protected int mPosition = RecyclerView.NO_POSITION;

    public ItemView(Context context) {
      super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
      super(context, attrs);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
    }

    private void setPosition(int pos) {
      mPosition = pos;
    }

    abstract public void bindViews();

    abstract public void updateView(@NonNull ItemModel uiModel, boolean selected);
  }
}
