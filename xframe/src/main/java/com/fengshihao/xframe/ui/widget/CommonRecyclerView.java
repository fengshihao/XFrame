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
import java.util.List;

/**
 * A Common RecyclerView to avoiding define a Adapter and a ViewHolder
 */
public class CommonRecyclerView extends RecyclerView {

  private static final String TAG = "CommonRecyclerView";

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

  public void setItemLayoutIds(@LayoutRes int... layoutIds) {
    mCommonAdapter.setItemLayoutIds(layoutIds);
  }

  public <T extends ItemModel> void setModels(@NonNull List<T> list1) {
    mCommonAdapter.clear();
    mCommonAdapter.addAll(list1);
  }

  public interface ItemModel {

    default int getViewType() {
      return 0;
    }
  }

  public static class CommonAdapter extends Adapter<CommonViewHolder> {
    private static final String TAG = "CommonAdapter";

    @NonNull
    private final PageList<ItemModel> mList = new PageList<>();

    @LayoutRes
    private int[] mItemLayoutIds;


    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (mItemLayoutIds == null) {
        throw new IllegalStateException("must call CommonAdapter.setItemLayoutIds first!!");
      }

      if (mItemLayoutIds.length <= viewType) {
        throw new IllegalStateException("must call CommonAdapter.setItemLayoutIds lack of viewType="
            + viewType + " now mItemLayoutIds=" + Arrays.toString(mItemLayoutIds));
      }

      ItemView v = (ItemView) LayoutInflater
          .from(parent.getContext()).inflate(mItemLayoutIds[viewType], parent, false);
      v.bindViews();
      return new CommonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
      ItemModel item = mList.get(position);
      mList.updateCurrentPage(position);
      if (item == null) {
        return;
      }
      holder.updateView(item, position);
    }

    @Override
    public int getItemCount() {
      return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
      ItemModel item = mList.get(position);
      if (item == null) {
        return 0;
      }
      return item.getViewType();
    }

    void setItemLayoutIds(@LayoutRes int... layoutIds) {
      Log.d(TAG, "setItemLayoutIds() called with: layoutIds = ["
          + Arrays.toString(layoutIds) + "]");
      mItemLayoutIds = layoutIds;
    }

    void clear() {
      mList.clear();
    }

    <T extends ItemModel> void addAll(@NonNull List<T> list) {
      mList.addAll(list);
      Log.d(TAG, "addAll: size=" + list.size());
      notifyDataSetChanged();
    }

//
//    void select(@NonNull Integer... pos) {
//      Set<Integer> oldSelect = new HashSet<>(mSelects);
//
//      mSelects.clear();
//      for (Integer idx : pos) {
//        if (idx < 0 || idx >= mList.size()) {
//          throw new IllegalArgumentException("wrong idx=" + idx);
//        }
//
//        mSelects.add(idx);
//      }
//
//      for (Integer idx : mSelects) {
//        if (oldSelect.isSelected(idx)) {
//          continue;
//        }
//        Log.d(TAG, "select: new select =" + idx);
//        notifyItemChanged(idx);
//      }
//      oldSelect.removeAll(mSelects);
//      for (Integer idx : oldSelect) {
//        Log.d(TAG, "select: un select =" + idx);
//        notifyItemChanged(idx);
//      }
//    }
  }

  static class CommonViewHolder extends ViewHolder {

    CommonViewHolder(ItemView itemView) {
      super(itemView);
    }

    void updateView(@NonNull ItemModel model, int position) {
      ItemView v = (ItemView) itemView;
      v.setPosition(position);
      v.updateView(model);
    }
  }

  public static abstract class ItemView<T extends ItemModel> extends FrameLayout {
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

    public int getPosition() {
      return mPosition;
    }

    abstract public void bindViews();

    abstract public void updateView(@NonNull T uiModel);
  }
}
