package com.fengshihao.xframework.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fengshihao.album.api.IAlbumAPI;
import com.fengshihao.xframe.logic.XFrame;
import com.fengshihao.xframework.R;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private static final String[] ACTIVITIES = new String[] {
          "TodoList example",
      "相册组件",
      "调试页面"
  };

  private static final int DEFAULT_SELECT = 0;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ListView activities = findViewById(R.id.activity_list);
    activities.setAdapter(new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, ACTIVITIES));
    activities.setOnItemClickListener((adapterView, view, row, l) -> {
      Log.d(TAG, "onItemClick: row=" + row);
      select(row);
    });
    select(DEFAULT_SELECT);
  }

  private void select(int index) {
    Log.d(TAG, "select() called with: index = [" + index + "]");
    switch (index) {
      case 0:
        startActivity(new Intent(this, TodoListActivity.class));
        break;
      case 1:
        XFrame.getInstance().getModule(IAlbumAPI.class).startActivity(this);
        break;
      case 2:
        XFrame.getInstance().startDebugActivity(this);
        break;
      default:
        Log.e(TAG, "onItemClick: no implementation");
    }
  }
}
