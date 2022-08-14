package com.fengshihao.xframework.logic;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * TodoList 的接口
 */
public interface ITodoListProjectListener {

    void onAddTodo(int pos, @NonNull TodoListProject.TodoItem item);

    void onDelTodo(@NonNull TodoListProject.TodoItem item);

    void onLoadAll(@NonNull List<TodoListProject.TodoItem> items);

    void onTodoUpdate(@NonNull TodoListProject.TodoItem item);
}
