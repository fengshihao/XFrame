package com.fengshihao.xframelint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

public class XFrameLint extends Detector implements Detector.ClassScanner {
  public static final Issue ISSUE = Issue.create(
      "LogUse",
      "避免使用Log/System.out.println",
      "使用Ln，防止在正式包打印log",
      Category.SECURITY, 5, Severity.ERROR,
      new Implementation(XFrameLint.class, Scope.JAVA_FILE_SCOPE));

}
