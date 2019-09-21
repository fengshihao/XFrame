package com.fengshihao.xframework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fengshihao.album.api.IAlbumAPI;
import com.fengshihao.xframe.logic.XFrame;

public class MainActivity extends AppCompatActivity {


  private static final String TAG = "MainActivity";
  private static final String[] ACTIVITIES = new String[]{
      "ALBUM ACTIVITY"
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    XFrame.getInstance().getModule(IAlbumAPI.class).startActivity(this);

  }

  @Override
  public void onStart() {
    super.onStart();
    ListView activities = findViewById(R.id.activity_list);
    activities.setAdapter(new ArrayAdapter<>(this,
        android.R.layout.simple_list_item_1, ACTIVITIES));
    activities.setOnItemClickListener((adapterView, view, row, l) -> {
      Log.d(TAG, "onItemClick: row=" + row);
      switch (row) {
        case 0:
          XFrame.getInstance().getModule(IAlbumAPI.class).startActivity(this);
          break;
        default:
          Log.e(TAG, "onItemClick: no implementation");
      }
    });
  }
}
