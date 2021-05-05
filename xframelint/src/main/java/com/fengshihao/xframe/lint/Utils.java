package com.fengshihao.xframe.lint;

import org.jetbrains.annotations.NotNull;

public final class Utils {
  private Utils() {}


  private static final String [] LOGIC_PATH = {
      ".logic"
  };

  private static final String [] LOGIC_API_PATH = {
      ".logic.api"
  };

  private static final String [] UI_PATH = {
      ".ui", ".view", ".fragment", ".activity", ".widget"
  };

  public static boolean isInPath(@NotNull String importStatement, @NotNull String[] paths) {
    for (String p: paths) {
      if (importStatement.contains(p)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isInUIPath(@NotNull String importStatement) {
    return isInPath(importStatement, UI_PATH);
  }

  public static boolean isInLogicPath(@NotNull String importStatement) {
    return isInPath(importStatement, LOGIC_PATH);
  }

  public static boolean isInLogicApiPath(@NotNull String importStatement) {
    return isInPath(importStatement, LOGIC_API_PATH);
  }
}
