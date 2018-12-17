package com.example.rxiss.ui.issuedetails;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rxiss.R;
import com.example.rxiss.domains.Issue;
import com.example.rxiss.domains.Label;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IssueDetailActivity extends AppCompatActivity implements IssueDetailContract.View {

    TextView status;
    TextView date;
    TextView title;
    ImageView userIcon;
    TextView username;
    LinearLayout labelsList;
    TextView body;
    ProgressBar mProgressBar;

    IssueDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);

        Bundle b = getIntent().getExtras();
        long issueId = -1; // or other values
        if(b != null)
            issueId = b.getLong("id");

        mProgressBar = findViewById(R.id.progress_bar_detail);
        status = findViewById(R.id.status);
        date = findViewById(R.id.date);
        title = findViewById(R.id.title);
        userIcon = findViewById(R.id.user_icon);
        username = findViewById(R.id.username);
        labelsList = findViewById(R.id.labels_list);
        body = findViewById(R.id.body);

        mPresenter = new IssueDetailPresenter();
        mPresenter.initView(this);
        if(issueId != -1)
            mPresenter.loadData(issueId);
    }

    @Override
    public void showIssue(Issue issue) {
        title.setText(issue.getTitle());
        body.setText(issue.getBody());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date dateTemp = null;
        try {
            dateTemp = dateFormat.parse(issue.getDate());
            DateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(dateTemp);
            date.setText(dateStr);
        } catch (ParseException e) {
            date.setText(issue.getDate());
            e.printStackTrace();
        }

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(this).load(issue.getUser().getAvatar_url())
                .apply(options)
                .into(userIcon);

        username.setText(issue.getUser().getLogin());

        TextView labelView;
        for(Label label : issue.getLables()){
            labelView = new TextView(this);
            labelView.setText(label.name);
            labelView.setTextColor(Color.parseColor("#ffffff"));
            labelView.setBackgroundColor(Color.parseColor("#" + label.color));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,3,5,3);
            labelView.setLayoutParams(params);
            labelsList.addView(labelView);
        }

        if(issue.getStatus().equals("open")){
            status.setText(issue.getStatus());
            status.setTextColor(this.getResources().getColor(R.color.green));
            status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_open_status,  0, 0, 0);
        }
    }

    @Override
    public void showProgressBar(Boolean state) {
        if(state){
            mProgressBar.setVisibility(View.VISIBLE);
            findViewById(R.id.issue_detail_content).setVisibility(View.INVISIBLE);
        }else{
            mProgressBar.setVisibility(View.INVISIBLE);
            findViewById(R.id.issue_detail_content).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }
}
