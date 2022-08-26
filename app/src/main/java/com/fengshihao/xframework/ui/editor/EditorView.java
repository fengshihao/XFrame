package com.fengshihao.xframework.ui.editor;

import java.util.function.Consumer;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.translation.ViewTranslationRequest;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;


public class EditorView extends FrameLayout {

  private static final String TAG = "EditorView";
  public EditorView(Context context) {
    super(context);
    init(null, 0);
  }

  public EditorView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0);
  }

  public EditorView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs, defStyle);
  }

  private void init(AttributeSet attrs, int defStyle) {
    Log.d(TAG, "init() called with: attrs = [" + attrs + "], defStyle = [" + defStyle + "]");
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec +
        "], heightMeasureSpec = [" + heightMeasureSpec + "]");
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    Log.d(TAG,
        "onLayout() called with: changed = [" + changed + "], left = [" + left + "], top = [" +
            top + "], right = [" + right + "], bottom = [" + bottom + "]");
  }

  @Override
  public void onViewAdded(View child) {
    super.onViewAdded(child);
    Log.d(TAG, "onViewAdded() called with: child = [" + child + "]");
  }

  @Override
  public void onViewRemoved(View child) {
    super.onViewRemoved(child);
    Log.d(TAG, "onViewRemoved() called with: child = [" + child + "]");
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    Log.d(TAG, "onAttachedToWindow() called");
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    Log.d(TAG, "onFinishInflate() called");
  }

}