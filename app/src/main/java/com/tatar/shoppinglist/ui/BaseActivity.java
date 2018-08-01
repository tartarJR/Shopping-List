package com.tatar.shoppinglist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tatar.shoppinglist.R;
import com.tatar.shoppinglist.utils.ui.recyclerview.RecyclerViewDividerDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public static final String INCOMING_SHOPPING_LIST = "shopping_list";
    public static final String INCOMING_TITLE = "title";
    public static final String INCOMING_SHOPPING_LIST_ID = "id";

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.noDataTv)
    public TextView noDataTv;

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setTitle(getActivityTitle());
        bindViews();
        prepareRecyclerView();
        provideDependencies();
        setUpViews();
        makeInitialCalls();
    }

    protected abstract int getLayoutId();

    private void bindViews() {
        ButterKnife.bind(this);
    }

    protected abstract String getActivityTitle();

    protected abstract void provideDependencies();

    protected abstract void setUpViews();

    protected abstract void makeInitialCalls();

    private void prepareRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerViewDividerDecoration(this, LinearLayoutManager.VERTICAL, 16));
    }

    protected void switchNoDataTvVisibility(int size) {
        if (size > 0) {
            noDataTv.setVisibility(View.GONE);
        } else {
            noDataTv.setVisibility(View.VISIBLE);
        }
    }


    public void displayErrorMessage() {
        displayToast(getString(R.string.error_msg));
    }

    protected void displayToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
