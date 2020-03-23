package com.ewe.parlae.ratestask;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    DataRepository mDataRepository;

    public MainActivityViewModel() {
        this.mDataRepository = DataRepository.get();
    }

    public void getRatesFor(RecyclerView rates_rv, RatesAdapter adapter, boolean initial) {
        mDataRepository.getRatesFor(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Failed response with code: " + response.code());
                }

                try {
                    Log.d(TAG, "onResponseSR: " + response.body());
                    JSONObject jsonObject = new JSONObject(response.body());
                    String base_currency = jsonObject.getString("baseCurrency");
                    JSONObject rates_jo = jsonObject.getJSONObject("rates");
                    Iterator<String> keysItr = rates_jo.keys();

                    ArrayList<CurrencyModel> currencyModelArrayList = new ArrayList<>();
                    try {
                        CurrencyModel baseOne = new CurrencyModel(0, base_currency, mDataRepository.getCurrencyDescription(base_currency), String.valueOf(mDataRepository.getBaseValue().getValue()), 1.0);
                        currencyModelArrayList.add(baseOne);
                        int j = 1;
                        while (keysItr.hasNext()) {
                            String rate_name = keysItr.next();
                            double rate = (Double) rates_jo.get(rate_name);
                            String description = mDataRepository.getCurrencyDescription(rate_name);
                            double cv = 0.0 ;
                            try {
                                cv = rate * mDataRepository.getBaseValue().getValue();
                            } catch (NullPointerException e){
                                e.printStackTrace();
                            }
                            currencyModelArrayList.add(new CurrencyModel(j, rate_name, description, String.valueOf(cv), rate));
                            Log.d(TAG, "onResponse: added currency id is: " + j);
                            j++;
                        }

                        // used to prevent editTextFrom getting random values. Not good solution as it dismisses recycling
                        rates_rv.setItemViewCacheSize(currencyModelArrayList.size());

                        adapter.setData(currencyModelArrayList);
                        if (initial) {
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.notifyItemRangeChanged(1, currencyModelArrayList.size() - 1);
                        }

                    } catch (NullPointerException ne) {
                        ne.printStackTrace();
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getRatesFor(RecyclerView rates_rv, RatesAdapter adapter) {
        getRatesFor(rates_rv, adapter, false);
    }
}
