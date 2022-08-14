package com.fengshihao.xframework.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fengshihao.xframework.R;
import com.fengshihao.xframework.logic.ITodoListProject;
import com.fengshihao.xframework.logic.ITodoListProjectListener;
import com.fengshihao.xframework.logic.TodoListProject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private Button mBtnAdd;
    private RadioButton mBtnShowAll;
    private RadioButton mBtnShowComplete;
    private RadioButton mBtnShowActive;
    private EditText mEditTextTodo;
    private TodoListViewController mListViewController;

    @Nullable
    private RadioButton mFilter;

    private ITodoListProject mTodoListProject = new TodoListProject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        initView();
    }

    private void initView() {
        mEditTextTodo = findViewById(R.id.edit_text_todo);
        mBtnAdd = findViewById(R.id.button_add_todo);
        mBtnAdd.setOnClickListener(view -> {
           String input = mEditTextTodo.getText().toString();
           mTodoListProject.addTodo(input);
           mEditTextTodo.setText("");
        });
        mBtnShowActive = findViewById(R.id.button_show_active);
        mBtnShowActive.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mFilter = mBtnShowActive;
                mTodoListProject.loadAll();
            }
        });
        mBtnShowAll = findViewById(R.id.button_show_all);
        mFilter = mBtnShowAll;
        mBtnShowAll.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mFilter = mBtnShowAll;
                mTodoListProject.loadAll();
            }
        });
        mBtnShowComplete = findViewById(R.id.button_show_complete);
        mBtnShowComplete.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mFilter = mBtnShowComplete;
                mTodoListProject.loadAll();
            }
        });
        mListViewController = new TodoListViewController(findViewById(R.id.todo_list_view), mTodoListProject);
        mTodoListProject.getListeners().addListener(new ITodoListProjectListener() {
            @Override
            public void onAddTodo(int pos, @NonNull TodoListProject.TodoItem item) {
                if (mFilter == mBtnShowComplete) {
                    return;
                }
                mListViewController.addItem(pos, item);
            }

            @Override
            public void onDelTodo(@NonNull TodoListProject.TodoItem item) {
                mListViewController.delItemById(item.mId);
            }

            @Override
            public void onLoadAll(@NonNull List<TodoListProject.TodoItem> items) {
                List<TodoListViewController.TodoItemModel> models = new LinkedList<>();
                for (TodoListProject.TodoItem item : items) {
                    if (mBtnShowAll == mFilter) {
                        models.add(new TodoListViewController.TodoItemModel(item));
                    } else if (mBtnShowActive == mFilter && !item.isDone()) {
                        models.add(new TodoListViewController.TodoItemModel(item));
                    } else if (mBtnShowComplete == mFilter && item.isDone()) {
                        models.add(new TodoListViewController.TodoItemModel(item));
                    }
                }
                mListViewController.addItems(models);
            }

            @Override
            public void onTodoUpdate(@NonNull TodoListProject.TodoItem item) {
                mListViewController.updateItem(item);
            }
        });
    }
}