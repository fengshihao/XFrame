package com.fengshihao.xframe.ui.util;

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


public final class FrescoUtil {
  private static final String TAG = "FrescoUtil";

  private FrescoUtil() {
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
}
