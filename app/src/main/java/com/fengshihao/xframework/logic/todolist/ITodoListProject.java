package com.fengshihao.xframework.logic.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fengshihao.xframe.logic.listener.ListenerManager;

/**
 * TodoList 的接口
 */
public interface ITodoListProject {

    void addTodo(@NonNull String content);

    @Nullable
    TodoListProject.TodoItem deleteTodoById(long id);

    @NonNull
    ListenerManager<ITodoListProjectListener> getListeners();

    void loadAll();

    void toggle(long mId);
}
