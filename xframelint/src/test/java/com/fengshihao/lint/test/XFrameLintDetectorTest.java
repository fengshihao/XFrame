package com.fengshihao.lint.test;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.checks.infrastructure.TestLintTask;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.fengshihao.xframe.lint.XFrameLint;

import java.util.Collections;
import java.util.List;

public class XFrameLintDetectorTest extends LintDetectorTest {
    public void testBasic() {
      TestLintTask task = lint();
      task.files(
                java("" +
                        "package com.fengshihao.logic.pkg;\n" +
                    "import com.java.ui.cc; \n" +
                    "import android.view.xx; \n" +
                        "public class TestClass1 {\n" +
                        "    // In a comment, mentioning \"lint\" has no effect\n" +
                        "    private static String s1 = \"Ignore non-word usages: linting\";\n" +
                        "    private static String s2 = \"Let's say it: lint\";\n" +
                        "}"))
                .run()
                .expect("src/com/fengshihao/logic/pkg/TestClass1.java:2: Error: 一个logic类中不能引用ui相关的类 [DoNotUseViewInLogic]\n" +
                    "import com.java.ui.cc; \n" +
                    "~~~~~~~~~~~~~~~~~~~~~~\n" +
                    "src/com/fengshihao/logic/pkg/TestClass1.java:3: Error: 一个logic类中不能引用ui相关的类 [DoNotUseViewInLogic]\n" +
                    "import android.view.xx; \n" +
                    "~~~~~~~~~~~~~~~~~~~~~~~\n" +
                    "2 errors, 0 warnings");
    }

    @Override
    protected Detector getDetector() {
        return new XFrameLint();
    }

    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(XFrameLint.ISSUE);
    }
}