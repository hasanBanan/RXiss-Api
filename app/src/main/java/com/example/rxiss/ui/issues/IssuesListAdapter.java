package com.example.rxiss.ui.issues;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Bundle;

import com.example.rxiss.App;
import com.example.rxiss.R;
import com.example.rxiss.domains.Issue;
import com.example.rxiss.ui.issuedetails.IssueDetailActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class IssuesListAdapter extends RecyclerView.Adapter<IssuesListAdapter.MyViewHolder> {
    private List<Issue> list;
    public Context context;

    public IssuesListAdapter(List<Issue> list) {
        this.list = list;
    }

    @Override
    public IssuesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_issue, parent, false);
        return  new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.body.setText(list.get(position).getBody());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = null;
        try {
            date = dateFormat.parse(list.get(position).getDate());
            DateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
            String dateStr = formatter.format(date);
            holder.date.setText(dateStr);
        } catch (ParseException e) {
            holder.date.setText(list.get(position).getDate());
            e.printStackTrace();
        }

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IssueDetailActivity.class);
                Bundle b = new Bundle();
                b.putLong("id", list.get(position).id); //Your id
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

        if(list.get(position).getStatus().equals("open")){
            holder.status.setText(list.get(position).getStatus());
            holder.status.setTextColor(context.getResources().getColor(R.color.green));
            holder.status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_open_status,  0, 0, 0);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView body;
        TextView status;
        TextView date;
        RelativeLayout row;

        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            body = v.findViewById(R.id.body);
            status = v.findViewById(R.id.status);
            date = v.findViewById(R.id.date);
            row = v.findViewById(R.id.row_content);
        }
    }
}
