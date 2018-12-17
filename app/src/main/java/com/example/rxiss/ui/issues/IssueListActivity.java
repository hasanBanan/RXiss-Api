package com.example.rxiss.ui.issues;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.rxiss.R;
import com.example.rxiss.domains.Issue;

import java.util.List;

public class IssueListActivity extends AppCompatActivity implements IssuesContract.View{

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private IssuesListAdapter mAdapter;
    private IssuesContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_list);

        mProgressBar = findViewById(R.id.progress_bar_list);

        mRecyclerView = findViewById(R.id.issue_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter = new IssuesListPresenter();
        mPresenter.initView(this);

        mPresenter.loadData();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void showList(List<Issue> list) {
        mAdapter = new IssuesListAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showProgressBar(Boolean state) {
        if(state){
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }else{
            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
