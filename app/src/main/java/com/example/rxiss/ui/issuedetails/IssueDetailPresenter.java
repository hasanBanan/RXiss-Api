package com.example.rxiss.ui.issuedetails;

import com.example.rxiss.data.IssueRepository;
import com.example.rxiss.domains.Issue;

import java.util.List;

public class IssueDetailPresenter implements IssueDetailContract.Presenter {

    private IssueDetailContract.View view;
    private IssueRepository repository;

    IssueDetailPresenter(){
        repository = new IssueRepository(this);
    }

    @Override
    public void initView(IssueDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(long id) {
        view.showProgressBar(true);
        repository.getIssue(id);
    }

    @Override
    public void updateData(Issue issue) {
        view.showProgressBar(false);
        view.showIssue(issue);
    }

    @Override
    public void destroy() {
        repository.destroy();
    }
}
