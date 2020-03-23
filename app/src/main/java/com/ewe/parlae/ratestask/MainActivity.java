package com.ewe.parlae.ratestask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private MainActivityViewModel mMainActivityViewModel;
    private RecyclerView mRates_rv;
    private DataRepository mDataRepository;
    private RatesAdapter mAdapter;
    private Handler mHandler;

    public void useHandler() {
        mHandler = new Handler();
        mHandler.post(mRunnable);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            Log.d("Handlers", "Call asynctask");
            /** Call your AsyncTask here **/
            Log.d(TAG, "run: base curr: " + mDataRepository.getBaseRate().getValue());
            mMainActivityViewModel.getRatesFor(mRates_rv, mAdapter);
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mRates_rv = findViewById(R.id.currency_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRates_rv.setHasFixedSize(true);
        mRates_rv.setLayoutManager(llm);
        mRates_rv.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RatesAdapter(MainActivity.this, new RatesAdapter.OnMoveClickListener() {
            @Override
            public void moveOnItemClickFrom(int position, String value) {
                mAdapter.moveItemToStartFrom(position, value);
                llm.scrollToPosition(0);
                RatesAdapter.CurrencyViewHolder cvh = (RatesAdapter.CurrencyViewHolder) mRates_rv.findViewHolderForAdapterPosition(0);
                cvh.curr_value.requestFocus();
            }
        });
        //preventing of item Flickering in RecyclerView on update
        try {
            mRates_rv.getItemAnimator().setChangeDuration(0);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        mRates_rv.setAdapter(mAdapter);
        mDataRepository = DataRepository.get();

        //displaying initial set of data
        mMainActivityViewModel.getRatesFor(mRates_rv, mAdapter, true);
        //updating data periodically
        useHandler();


    }



}
