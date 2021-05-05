package com.fengshihao.xframe.lint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;


public class XFrameLintRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        Issue[] issues = new Issue[] {
            com.fengshihao.xframe.lint.XFrameLint.ISSUE_LOGIC_IMPORT_UI
        };

        return Arrays.asList(issues);
    }

    @Override
    public int getApi() {
        return ApiKt.CURRENT_API;
    }
}

