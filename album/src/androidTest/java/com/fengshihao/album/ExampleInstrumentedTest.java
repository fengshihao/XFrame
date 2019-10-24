package com.fengshihao.album;

import android.Manifest;
import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.fengshihao.album.logic.AlbumMediaItem;
import com.fengshihao.album.logic.AlbumSqlTool;
import com.fengshihao.xframe.logic.XFrame;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

  @Rule
  public GrantPermissionRule mRuntimePermissionRule =
      GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE);

  @BeforeClass
  public static void useAppContext() {
    XFrame.getInstance().onApplicationStart((Application)
        InstrumentationRegistry.getContext().getApplicationContext());

  }

  @Test
  public void testLoadImages() {
    int loadNum = 100;
    List<AlbumMediaItem> imgs = AlbumSqlTool.loadImages(0, loadNum);
    Assert.assertEquals("test AlbumSqlTool.loadImages", loadNum, imgs.size());
  }

  @Test
  public void testLoadAlbumInfo() {
    AlbumSqlTool.loadAlbumInfo();
  }
}
