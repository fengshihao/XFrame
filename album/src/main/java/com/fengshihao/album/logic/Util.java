package com.fengshihao.album.logic;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public final class Util {
  private static final String TAG = "Utils";

  private Util() {
  }

  public static Context sContext;

  @NonNull
  public static Context getContext() {
    return sContext;
  }

  public static void logFirstAndLast(@NonNull String tag,
                                     @NonNull String function,
                                     @NonNull ArrayList<AlbumItem> list) {
    if (list.isEmpty()) {
      Log.d(tag, function + " empty list");
      return;
    }
    Log.d(tag, function + " list size=" + list.size()
        + " first=" + list.get(0) + " last="
        + list.get(list.size() - 1));
  }

  public static void imageBindLocalPath(@NonNull SimpleDraweeView image,
                                        String path, int w, int h) {
    if (TextUtils.isEmpty(path)) {
      image.setImageURI("");
      return;
    }
    if (w <= 0 || h <= 0) {
      Log.e(TAG, "imageBindLocalPath: w=" + w + " h=" + h);
      image.setImageURI("");
      return;
    }
    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(
        Uri.fromFile(new File(path)))
        .setResizeOptions(new ResizeOptions(w, h))
        .build();
    image.setController(
        Fresco.newDraweeControllerBuilder()
            .setOldController(image.getController())
            .setImageRequest(request)
            .build());
  }

  private static final float[] DisRet = new float[]{0};

  public static boolean isSameAreaInEarth(double lat1, double lat2, double lon1,
                                          double lon2, double distanceInKMeter) {
//
//    double dis = latLongDistance(lat1, lat2, lon1, lon2, 'K');
//    return dis < distanceInKMeter;
    DisRet[0] = 0;
    Location.distanceBetween(lat1, lat2, lon1, lon2, DisRet);
    return DisRet[0] < distanceInKMeter * 1000;
  }

  public static double latLongDistance(double lat1, double lon1,
                                       double lat2, double lon2, char unit) {
    double theta = lon1 - lon2;
    double dist = Math.sin(deg2rad(lat1))
        * Math.sin(deg2rad(lat2))
        + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
    if (unit == 'K') {
      dist = dist * 1.609344;
    } else if (unit == 'N') {
      dist = dist * 0.8684;
    }
    return (dist);
  }

  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  /*::  This function converts decimal degrees to radians             :*/
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  private static double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
  }

  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  /*::  This function converts radians to decimal degrees             :*/
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  private static double rad2deg(double rad) {
    return (rad * 180.0 / Math.PI);
  }

  public static List<Address> getAddress(float latitude, float longitude) {
    List<Address> result = null;
    Log.d(TAG, "getAddress: start ");
    try {
      Geocoder gc = new Geocoder(getContext(), Locale.getDefault());
      result = gc.getFromLocation(latitude, longitude, 1);
      Log.d(TAG, "getAddress: start result=" + result + " ispresenter=" + Geocoder.isPresent());

      for (Address address : result) {
        Log.d(TAG, "getAddress: " + address + " " + address.getCountryName() + " " + address.getPostalCode());
      }
    } catch (Exception e) {
      Log.e(TAG, "getAddress: ", e);
    }
    return result;
  }
}
