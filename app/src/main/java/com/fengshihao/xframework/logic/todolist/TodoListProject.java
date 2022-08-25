package com.fengshihao.xframework.logic.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fengshihao.xframe.logic.listener.ListenerManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 逻辑部分
 */
public class TodoListProject
        implements ITodoListProject {

    private long mLastId = 0;
    private final List<TodoItem> mTodos = new ArrayList<>();
    private final ListenerManager<ITodoListProjectListener> mListeners = new ListenerManager<>();
    @Override
    public void addTodo(@NonNull String content) {
        TodoItem item = new TodoItem(mLastId, System.currentTimeMillis(), content);
        mLastId += 1;
        mTodos.add(0, item);
        mListeners.notifyListeners(l -> l.onAddTodo(0, item));
    }

    @Nullable
    @Override
    public TodoItem deleteTodoById(long id) {
        Iterator<TodoItem> it = mTodos.iterator();
        while (it.hasNext()) {
            TodoItem item = it.next();
            if (item.mId == id) {
                it.remove();
                mListeners.notifyListeners(l -> l.onDelTodo(item));
                return item;
            }
        }
        return null;
    }

    @NonNull
    @Override
    public ListenerManager<ITodoListProjectListener> getListeners() {
        return mListeners;
    }

    @Override
    public void loadAll() {
        mListeners.notifyListeners(l -> l.onLoadAll(mTodos));
    }

    @Override
    public void toggle(long id) {
        Iterator<TodoItem> it = mTodos.iterator();
        while (it.hasNext()) {
            TodoItem item = it.next();
            if (item.mId == id) {
                item.setIsDone(!item.isDone());
                mListeners.notifyListeners(l -> l.onTodoUpdate(item));
            }
        }
    }


    public static class TodoItem {
        public final long mId;
        public final long mTime;
        @NonNull
        public final String mContent;
        private boolean mIsDone = false;

        public TodoItem(long mId, long mTime, @NonNull String mContent) {
            this.mId = mId;
            this.mTime = mTime;
            this.mContent = mContent;
        }

        public void setIsDone(boolean done) {
            mIsDone = done;
        }

        public boolean isDone() {
            return mIsDone;
        }
    }
}
