package com.fengshihao.xframework.ui;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fengshihao.xframework.R;
import com.fengshihao.xframework.logic.ITodoListProject;
import com.fengshihao.xframework.logic.ITodoListProjectListener;
import com.fengshihao.xframework.logic.TodoListProject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 列表的控制器
 */
public class TodoListViewController {
    private static final String TAG = "TodoListViewController";
    @NonNull
    public final List<TodoItemModel> mTodoList = new ArrayList<>();

    @NonNull
    private ITodoListProject mProject;
    public TodoListViewController(@NonNull RecyclerView recyclerView, @NonNull ITodoListProject project) {
        recyclerView.setAdapter(mTodoListAdapter);
        mProject = project;
    }

    @NonNull
    private final RecyclerView.Adapter<TodoListViewHolder> mTodoListAdapter = new RecyclerView.Adapter<TodoListViewHolder>() {
        @NonNull
        @Override
        public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                throw new IllegalArgumentException("viewType must be a layout resource");
            }
            Log.d(TAG, "onCreateViewHolder: " + viewType);
            View  v = LayoutInflater
                    .from(parent.getContext()).inflate(viewType, parent, false);
            return new TodoListViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
            holder.bind(position, null);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position, @NonNull List<Object> payloads) {
            holder.bind(position, payloads);
        }

        @Override
        public int getItemCount() {
            return mTodoList.size();
        }
        @Override
        public int getItemViewType(int position) {
            return R.layout.todo_item;
        }
    };

    public static class TodoItemModel {
        public final String mContent;
        public final String mCreatedTime;
        public final boolean mIsDone;
        public final long mId;

        public TodoItemModel(@NonNull TodoListProject.TodoItem item) {
            this(item.mId, item.mContent, stringToDate(item.mTime), item.isDone());
        }
        public TodoItemModel(long id, String mContent, String mCreatedTime, boolean mIsDone) {
            mId = id;
            this.mContent = mContent;
            this.mCreatedTime = mCreatedTime;
            this.mIsDone = mIsDone;
        }
    }

    public class TodoListViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextContent;
        private final TextView mTextTime;
        @Nullable
        private TodoItemModel mModel;
        private int mPosInUI = -1;

        public TodoListViewHolder(@NonNull View itemView) {
            super(itemView);
            Button mBtnDel = itemView.findViewById(R.id.button_del_todo);
            mBtnDel.setOnClickListener(view -> {
                if (mModel == null) return;
                mProject.deleteTodoById(mModel.mId);
            });
            itemView.setOnClickListener(view -> {
                if (mModel == null) return;
                mProject.toggle(mModel.mId);
            });

            mTextContent = itemView.findViewById(R.id.text_todo_content);
            mTextContent.getPaint().setAntiAlias(true);
            mTextTime = itemView.findViewById(R.id.text_todo_time);
        }

        protected void bind(int position, List<Object> payloads) {
            Log.d(TAG, "bind() called with: position = [" + position + "], payloads = [" + payloads + "]");
            mPosInUI = position;
            mModel = mTodoList.get(position);
            if (mModel == null) return;
            if (mModel.mIsDone) {
                mTextContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                mTextContent.getPaint().setFlags(~Paint.STRIKE_THRU_TEXT_FLAG);
            }

            mTextContent.setText(mModel.mContent);
            mTextTime.setText(mModel.mCreatedTime);
        }
    }


    public void addItem(int position, @NonNull TodoListProject.TodoItem item) {
        mTodoList.add(position, new TodoItemModel(item));
        mTodoListAdapter.notifyItemInserted(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(@NonNull List<TodoItemModel> list) {
        mTodoList.clear();
        mTodoList.addAll(list);
        mTodoListAdapter.notifyDataSetChanged();
    }

    public void delItemById(long id) {
        int pos = -1;
        for (int i = 0; i < mTodoList.size(); i++) {
            TodoItemModel m = mTodoList.get(i);
            if (m.mId == id) {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            return;
        }
        mTodoList.remove(pos);
        mTodoListAdapter.notifyItemRemoved(pos);
    }

    public void updateItem(@NonNull TodoListProject.TodoItem item) {
        for (int i = 0; i < mTodoList.size(); i++) {
            TodoItemModel m = mTodoList.get(i);
            if (m.mId == item.mId) {
                mTodoList.set(i, new TodoItemModel(item));
                mTodoListAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    public static String stringToDate(long time){
        Date date = new Date(time);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sd.format(date);
    }
}