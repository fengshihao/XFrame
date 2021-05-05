package com.fengshihao.xframe.lint;

import static com.fengshihao.xframe.lint.Utils.isInLogicApiPath;
import static com.fengshihao.xframe.lint.Utils.isInLogicPath;
import static com.fengshihao.xframe.lint.Utils.isInUIPath;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UFile;
import org.jetbrains.uast.UImportStatement;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Detector.UastScanner;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

public class XFrameLint extends Detector implements UastScanner {
  private static final String TAG = "SampleCodeDetector";

  public static final Issue ISSUE_LOGIC_IMPORT_UI = Issue.create(
      "DoNotUseViewInLogic",
      "不要在逻辑代码中使用UI相关的类",
      "逻辑代码要和UI代码分离开, 可以独立存在, 所以ui包下的类,\n" +
          "或者Activity Fragment View Layout 等",
      Category.CORRECTNESS,
      6,
      Severity.ERROR,
      new Implementation(
          com.fengshihao.xframe.lint.XFrameLint.class,
          Scope.JAVA_FILE_SCOPE));

  public static final Issue ISSUE_UI_IMPORT_LOGIC = Issue.create(
      "DoNotUseLogicInUIClass",
      "不要UI代码中使用xxx.logic.api.xxx 包之外的的类",
      "logic.api包中的类只允许有interface, 或者数据类, 或者final 修饰的工具类",
      Category.CORRECTNESS,
      6,
      Severity.ERROR,
      new Implementation(
          com.fengshihao.xframe.lint.XFrameLint.class,
          Scope.JAVA_FILE_SCOPE));

  @Override
  public List<Class<? extends UElement>> getApplicableUastTypes() {
    return Arrays.asList(UImportStatement.class, UFile.class);
  }

  @Override
  public UElementHandler createUastHandler(@NotNull JavaContext context) {
    return new XFrameLintUastHandler(context);
  }

  private static class XFrameLintUastHandler extends UElementHandler {
    private boolean mIsLogicPackage;

    @NotNull
    private final JavaContext mContext;
    private boolean mIsUIPackage;

    XFrameLintUastHandler(@NotNull JavaContext context) {
      mContext = context;
    }

    @Override
    public void visitImportStatement(@NotNull UImportStatement importStatement) {
      String importPackage = importStatement.toString();
      if (mIsLogicPackage) {
        if (isInUIPath(importPackage)) {
          mContext.report(ISSUE_LOGIC_IMPORT_UI, mContext.getLocation(importStatement),
              "一个logic类中不能引用ui相关的类");
        }
      } else if (mIsUIPackage) {

      }
    }

    @Override
    public void visitFile(@NotNull UFile file) {
      mIsLogicPackage = isInLogicPath(file.getPackageName());
      if (!mIsLogicPackage) {
        mIsUIPackage = isInUIPath(file.getPackageName());
      }
    }

  }
}

