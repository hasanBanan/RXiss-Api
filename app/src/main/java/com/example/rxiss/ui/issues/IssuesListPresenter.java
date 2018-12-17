package com.example.rxiss.ui.issues;

import android.util.Log;

import com.example.rxiss.data.IssueRepository;
import com.example.rxiss.domains.Issue;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class IssuesListPresenter implements IssuesContract.Presenter {

    private IssuesContract.View view;
    private IssueRepository repository;

    IssuesListPresenter(){
        repository = new IssueRepository(this);
    }

    @Override
    public void initView(IssuesContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {

        view.showProgressBar(true);

        repository.getIssues();
    }

    @Override
    public void updateData(List<Issue> list) {
        view.showProgressBar(false);
        view.showList(list);
    }


    @Override
    public void destroy() {
        repository.destroy();
    }
}
