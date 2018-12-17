package com.example.rxiss.ui.issuedetails;

import com.example.rxiss.domains.Issue;

import java.util.List;

public interface IssueDetailContract {
    interface View{
        void showIssue(Issue iss);
        void showProgressBar(Boolean state);
    }

    interface Presenter{
        void initView(IssueDetailContract.View view);

        void loadData(long id);

        void updateData(Issue issue);

        void destroy();
    }
}
