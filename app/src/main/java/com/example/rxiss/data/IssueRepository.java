package com.example.rxiss.data;

import android.util.Log;

import com.example.rxiss.App;
import com.example.rxiss.data.remote.IssueService;
import com.example.rxiss.domains.Issue;
import com.example.rxiss.domains.Label;
import com.example.rxiss.domains.User;
import com.example.rxiss.ui.issuedetails.IssueDetailContract;
import com.example.rxiss.ui.issuedetails.IssueDetailPresenter;
import com.example.rxiss.ui.issues.IssuesListPresenter;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IssueRepository{

    private Retrofit retrofit = null;
    private IssuesListPresenter presenterList;
    private IssueDetailPresenter presenterDetail;
    private CompositeDisposable cp = new CompositeDisposable();

    public IssueRepository(IssuesListPresenter presenter) {
        this.presenterList = presenter;
    }

    public IssueRepository(IssueDetailPresenter presenter) {
        this.presenterDetail = presenter;
    }

    public void getIssues() {

        final List issues = new ArrayList<Issue>();

        getRetrofit().create(IssueService.class).getIssues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Issue>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(List<Issue> list) {
                        presenterList.updateData(list);

                        List<Label> labels = new ArrayList<>();
                        List<User> users = new ArrayList<>();

                        for(Issue iss: list){
                            for(Label label: iss.labels){
                                label.iss_id = iss.id;
                            }
                            labels.addAll(iss.labels);
                            iss.user.iss_id = iss.id;
                            users.add(iss.user);
                        }

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                App.getInstance().getDatabase().labelDao().deleteAll();
                                App.getInstance().getDatabase().userDao().deleteAll();
                                App.getInstance().getDatabase().issueDao().deleteAll();
                                App.getInstance().getDatabase().labelDao().insert(labels);
                                App.getInstance().getDatabase().userDao().insert(users);
                                App.getInstance().getDatabase().issueDao().insert(list);
                            }
                        });

                        //new CompositeDisposable().add(App.getInstance().getDatabase().issueDao().insert(list));
                    }

                    @Override
                    public void onError(Throwable e) {

                        cp.add(App.getInstance().getDatabase().issueDao().getAll()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        list -> presenterList.updateData(list),
                                        error -> Log.d("GetLocalData", error.getLocalizedMessage())));
                    }

                    @Override
                    public void onComplete() { }
                });
    }

    public void getIssue(long id){
        cp.add(App.getInstance().getDatabase().issueDao().getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        iss ->{
                            cp.add(App.getInstance().getDatabase().userDao().getById(id)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            user ->{
                                                cp.add(App.getInstance().getDatabase().labelDao().getById(id)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(
                                                                labels ->{
                                                                    iss.setUser(user);
                                                                    iss.setLables(labels);
                                                                    presenterDetail.updateData(iss);
                                                                },
                                                                error ->{
                                                                    iss.setUser(user);
                                                                    presenterDetail.updateData(iss);
                                                                    Log.d("GetLocalData", "labels is empty");
                                                                }));
                                                                
                                            },
                                            error ->
                                                    Log.d("GetLocalData", error.getLocalizedMessage())));
                        },
                        error -> Log.d("GetLocalData", error.getLocalizedMessage())));
    }

    private Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(IssueService.API_PATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void destroy(){
        cp.dispose();
    }
}
