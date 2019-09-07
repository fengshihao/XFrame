package com.fengshihao.xframeui.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A Common RecyclerView to avoiding define a Adapter and a ViewHolder
 */
public class CommonRecyclerView extends RecyclerView {

  private static final String TAG = "CommonRecyclerView";

  private Listener mListener;

  @NonNull
  private final RadioAdapter mRadioAdapter = new RadioAdapter();

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
    setAdapter(mRadioAdapter);
  }

  public void setItemLayoutId(int id) {
    mRadioAdapter.setItemLayoutId(id);
  }

  public <T extends RadioModel> void setModels(@NonNull List<T> list1) {
    mRadioAdapter.clear();
    for (T m : list1) {
      mRadioAdapter.add(m);
    }
    Log.d(TAG, "setModels: size=" + list1.size());
    mRadioAdapter.notifyDataSetChanged();
  }

  public void select(int pos) {
    mRadioAdapter.select(pos);
  }

  public void setListener(Listener listener) {
    mListener = listener;
  }

  public interface RadioModel {
  }

  public interface Listener {
    void onClickItem(int pos);
  }


  public class RadioAdapter extends Adapter<CommonViewHolder> {
    private static final String TAG = "RadioAdapter";

    private int mSelectPos = NO_POSITION;

    @NonNull
    private final List<RadioModel> mList = new ArrayList<>();

    @LayoutRes
    private int mItemLayoutId;

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      if (mItemLayoutId == 0) {
        throw new IllegalStateException("must call RadioAdapter.setItemLayoutId first!!");
      }
      ItemView v = (ItemView) LayoutInflater
          .from(parent.getContext()).inflate(mItemLayoutId, parent, false);
      v.bindViews();
      return new CommonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
      Log.d(TAG, "onBindViewHolder: position=" + position);
      final int pos = position;
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (mListener != null) {
            mListener.onClickItem(pos);
          }
        }
      });
      holder.updateView(mList.get(position), position, position == mSelectPos);
    }

    @Override
    public int getItemCount() {
      return mList.size();
    }

    void setItemLayoutId(int mItemLayoutId) {
      this.mItemLayoutId = mItemLayoutId;
    }

    void clear() {
      mList.clear();
    }

    void add(RadioModel m) {
      mList.add(m);
    }

    void select(int pos) {
      if (pos == mSelectPos) {
        return;
      }
      Log.d(TAG, "select: pos=" + pos);
      if (pos > mList.size()) {
        Log.e(TAG, "select: wrong pos=" + pos);
        return;
      }
      int old = mSelectPos;
      mSelectPos = pos;
      if (mSelectPos >= 0) {
        notifyItemChanged(mSelectPos);
      }
      if (old >= 0) {
        notifyItemChanged(old);
      }
    }
  }

  static class CommonViewHolder extends ViewHolder {

    CommonViewHolder(ItemView itemView) {
      super(itemView);
    }

    void updateView(@NonNull RadioModel model, int position, boolean selected) {
      ItemView v = (ItemView) itemView;
      v.updateView(model, selected);
      v.setPosition(position);
      v.setSelected(selected);
    }
  }

  public static abstract class ItemView extends FrameLayout {
    protected int mPosition = NO_POSITION;
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

    abstract public void updateView(@NonNull RadioModel uiModel, boolean selected);
  }
}
