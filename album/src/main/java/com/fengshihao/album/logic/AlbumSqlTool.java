package com.fengshihao.album.logic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fengshihao.album.logic.AlbumAPI.getContext;
import static com.fengshihao.album.logic.AlbumLogicUtil.logFirstAndLast;

public final class AlbumSqlTool {
  private static final String TAG = "AlbumSqlTool";

  private AlbumSqlTool() {
  }

  @WorkerThread
  public static int getImagesNum() {
    ContentResolver mContentResolver = getContext().getContentResolver();

    String selectionClause = MediaStore.Images.Media.MIME_TYPE + "=? or "
        + MediaStore.Images.Media.MIME_TYPE + "=?";
    String[] args = new String[]{"image/jpeg", "image/png"};
    String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC";

    Cursor cursor = mContentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        new String[]{
            "COUNT(" + BaseColumns._ID + ") AS image_num  FROM (SELECT *",
        }, selectionClause, args, sortOrder + ")");

    if (cursor == null) {
      Log.e(TAG, "getImagesNum: cursor is null ");
      return 0;
    }

    while (cursor.moveToNext()) {
      int num = cursor.getInt(0);
      Log.d(TAG, "getImagesNum: get image num = " + num);
      return num;
    }
    cursor.close();

    return 0;
  }

  @WorkerThread
  @NonNull
  public static List<AlbumMediaItem> loadImages(int offset, int pageItemCount) {
    Log.d(TAG, "loadImages() offset = [" + offset
        + "], pageItemCount = [" + pageItemCount + "]");
    ContentResolver mContentResolver = getContext().getContentResolver();

    String[] columns = new String[]{
        BaseColumns._ID,
        MediaStore.MediaColumns.DATA,
        MediaStore.MediaColumns.DATE_ADDED,
        MediaStore.MediaColumns.DATE_MODIFIED,
        MediaStore.MediaColumns.WIDTH,
        MediaStore.MediaColumns.HEIGHT,
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns.ORIENTATION,
        MediaStore.Images.ImageColumns.LATITUDE,
        MediaStore.Images.ImageColumns.LONGITUDE

    };

    String selectionClause = MediaStore.Images.Media.MIME_TYPE + "=? or "
        + MediaStore.Images.Media.MIME_TYPE + "=?";
    String[] args = new String[]{"image/jpeg", "image/png"};
    String limitClause = " limit " + pageItemCount + " offset " + offset;
    String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC";

    Cursor cursor = mContentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, selectionClause, args,
        sortOrder + limitClause);

    if (cursor == null) {
      Log.e(TAG, "loadImages: cant create cursor");
      return Collections.emptyList();
    }

    ArrayList<AlbumMediaItem> ret = new ArrayList<>(pageItemCount);
    int position = offset;
    while (cursor.moveToNext()) {
      long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
      String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
      float latitude = cursor.getFloat(
          cursor.getColumnIndex(MediaStore.Images.ImageColumns.LATITUDE));
      float longitude = cursor.getFloat(
          cursor.getColumnIndex(MediaStore.Images.ImageColumns.LONGITUDE));
      AlbumMediaItem item = new AlbumMediaItem(id, position, AlbumMediaItem.IMAGE, path);
      item.mLatitude = latitude;
      item.mLongitude = longitude;
      Log.d(TAG, "loadImages: item=" + item);
      ret.add(item);
      position += 1;
    }

    cursor.close();
    logFirstAndLast(TAG, "loadImages", ret);
    return ret;
  }

  @WorkerThread
  @NonNull
  public static List<AlbumMediaItem> loadVideos(int offset, int pageItemCount) {
    Log.d(TAG, "loadVideos() offset = [" + offset + "], pageItemCount = ["
        + pageItemCount + "]");
    ContentResolver mContentResolver = getContext().getContentResolver();

    String[] columns = new String[]{
        BaseColumns._ID,
        MediaStore.MediaColumns.DATA,
        MediaStore.MediaColumns.DATE_ADDED,
        MediaStore.MediaColumns.DATE_MODIFIED,
        MediaStore.MediaColumns.WIDTH,
        MediaStore.MediaColumns.HEIGHT,
        MediaStore.Video.VideoColumns.MIME_TYPE,
        MediaStore.Video.VideoColumns.DATE_TAKEN,
        MediaStore.Video.VideoColumns.DURATION,
        MediaStore.Images.ImageColumns.LATITUDE,
        MediaStore.Images.ImageColumns.LONGITUDE
    };

    String selectionClause = MediaStore.Images.Media.MIME_TYPE + "=? or "
        + MediaStore.Images.Media.MIME_TYPE + "=?";
    String[] args = new String[]{"video/mp4"};

    String limitClause = "limit " + pageItemCount + " offset " + offset;
    String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED + " DESC " + limitClause;

    Cursor cursor = mContentResolver.query(
        MediaStore.Files.getContentUri("external"),
        columns,
        selectionClause,
        args,
        sortOrder);

    if (cursor == null) {
      Log.e(TAG, "loadVideos: cant create cursor");
      return Collections.emptyList();
    }

    ArrayList<AlbumMediaItem> ret = new ArrayList<>(pageItemCount);
    int position = offset;
    while (cursor.moveToNext()) {
      long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
      String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
      float latitude = cursor.getFloat(
          cursor.getColumnIndex(MediaStore.Images.ImageColumns.LATITUDE));
      float longitude = cursor.getFloat(
          cursor.getColumnIndex(MediaStore.Images.ImageColumns.LONGITUDE));
      AlbumMediaItem item = new AlbumMediaItem(id, position, AlbumMediaItem.VIDEO, path);
      item.mLatitude = latitude;
      item.mLongitude = longitude;
      ret.add(item);
      position += 1;
    }

    cursor.close();
    logFirstAndLast(TAG, "loadVideos", ret);
    return ret;
  }

  //  SELECT COUNT(parent) AS fileCount, _data
  //  FROM (SELECT * FROM files WHERE (media_type = 1) ORDER BY date_modified)
  //  GROUP BY (parent)
  //  ORDER BY fileCount DESC
  public static void loadAlbumInfo() {
    Cursor cursor = getContext().getContentResolver().query(
        MediaStore.Files.getContentUri("external"),
        new String[]{
            "COUNT(" + MediaStore.Files.FileColumns.PARENT + ") AS fileCount",
            MediaStore.Files.FileColumns.DATA + " FROM (SELECT *",
        },
        MediaStore.Files.FileColumns.MEDIA_TYPE + " = "
            + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " or " +
        MediaStore.Files.FileColumns.MEDIA_TYPE + " = "
            + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO + ")"
            + " ORDER BY " + MediaStore.Files.FileColumns.DATE_MODIFIED + " )"
            + " GROUP BY (" + MediaStore.Files.FileColumns.PARENT,
        null,
        "fileCount DESC"
    );
    if (cursor != null) {
      while (cursor.moveToNext()) {
        int imageFileCountInFolder = cursor.getInt(0);
        String latestImageFilePath = cursor.getString(1);
        File folderFile = new File(latestImageFilePath).getParentFile();

        Log.d(TAG, "parent path：" + folderFile.getAbsolutePath());
        Log.d(TAG, "image count：" + imageFileCountInFolder);
        Log.d(TAG, "latest image ：" + latestImageFilePath);
      }
      cursor.close();
    }
  }

  public static List<AlbumMediaItem> loadImageVideos(int offset, int pageItemCount) {
    Log.d(TAG, "loadImageVideos() called");
    ContentResolver mContentResolver = getContext().getContentResolver();

    String[] columns = new String[]{
        BaseColumns._ID,
        MediaStore.MediaColumns.DATA,
        MediaStore.MediaColumns.DATE_ADDED,
        MediaStore.MediaColumns.DATE_MODIFIED,
        MediaStore.MediaColumns.WIDTH,
        MediaStore.MediaColumns.HEIGHT,
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns.LATITUDE,
        MediaStore.Images.ImageColumns.LONGITUDE,
        MediaStore.Images.ImageColumns.SIZE
    };

    String selectionClause = MediaStore.Files.FileColumns.MEDIA_TYPE + "=? or " +
        MediaStore.Files.FileColumns.MEDIA_TYPE + "=? and (" +
        MediaStore.Images.Media.MIME_TYPE + "=? or "
        + MediaStore.Images.Media.MIME_TYPE + "=? or "
        + MediaStore.Images.Media.MIME_TYPE + "=?)";
    String[] args = new String[]{
        "" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
        "" + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,
        "image/jpeg", "image/png", "video/mp4"};
    String sortOrder = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC ";
    String limitClause = "limit " + pageItemCount + " offset " + offset;

    Cursor cursor = mContentResolver.query(
        MediaStore.Files.getContentUri("external"), columns, selectionClause, args,
        sortOrder + limitClause);

    if (cursor == null) {
      Log.e(TAG, "loadImageVideos: cant create cursor");
      return Collections.emptyList();
    }

    ArrayList<AlbumMediaItem> ret = new ArrayList<>();
    int position = offset;
    while (cursor.moveToNext()) {
      long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
      String path = cursor.getString(
          cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
      float latitude = cursor.getFloat(
          cursor.getColumnIndex(MediaStore.Images.ImageColumns.LATITUDE));
      float longitude = cursor.getFloat(
          cursor.getColumnIndex(MediaStore.Images.ImageColumns.LONGITUDE));
      int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.SIZE));
      Log.v(TAG, "loadImageVideos: latitude=" + latitude + " longitude="
          + longitude + " size=" + size + " " + path);
      ret.add(new AlbumMediaItem(id, position, AlbumMediaItem.IMAGE, path));
      position += 1;
    }

    cursor.close();
    logFirstAndLast(TAG, "loadImageVideos", ret);
    return ret;
  }


  @WorkerThread
  public static int getImagesAndVideosNum() {
    ContentResolver mContentResolver = getContext().getContentResolver();
    String selectionClause = MediaStore.Files.FileColumns.MEDIA_TYPE + "=? or " +
        MediaStore.Files.FileColumns.MEDIA_TYPE + "=? and (" +
        MediaStore.Images.Media.MIME_TYPE + "=? or "
        + MediaStore.Images.Media.MIME_TYPE + "=? or "
        + MediaStore.Images.Media.MIME_TYPE + "=?)";
    String[] args = new String[]{
        "" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
        "" + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,
        "image/jpeg", "image/png", "video/mp4"};

    Cursor cursor = mContentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        new String[]{
            "COUNT(" + BaseColumns._ID + ") AS image_num  FROM (SELECT *",
        }, selectionClause, args, ")");

    if (cursor == null) {
      Log.e(TAG, "getImagesAndVideosNum: cursor is null ");
      return 0;
    }

    int num = 0;
    if (cursor.moveToNext()) {
      num = cursor.getInt(0);
    }
    cursor.close();

    Log.d(TAG, "getImagesAndVideosNum: get image num = " + num);
    return num;
  }

}
