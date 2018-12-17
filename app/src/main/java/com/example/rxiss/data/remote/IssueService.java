package com.example.rxiss.data.remote;

import com.example.rxiss.domains.Issue;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IssueService {
    String API_PATH = "https://api.github.com/";

    @GET("repos/ReactiveX/RxJava/issues?state=all")
    Observable<List<Issue>> getIssues();

    class Instance{
        IssueService instance = null;

        IssueService getService(){
            if(instance == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_PATH)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                instance = retrofit.create(IssueService.class);
            }
            return instance;
        }
    }
}
