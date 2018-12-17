package com.example.rxiss.ui.issues;

import com.example.rxiss.domains.Issue;

import java.util.List;

interface IssuesContract {
    interface View{
        void showList(List<Issue> list);
        void showProgressBar(Boolean state);
    }

    interface Presenter{
        void initView(View view);

        void loadData();

        void updateData(List<Issue> list);

        void destroy();
    }
}
