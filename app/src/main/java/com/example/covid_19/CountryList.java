package com.example.covid_19;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.model.MyWebService;
import com.example.covid_19.model.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryList extends AppCompatActivity {

    RecyclerView recyclerView;
    CountryListAdapter countryListAdapter;
    private String TAG = "TAG";
    private LinearLayoutManager mLinearLayoutManager;
    private MyWebService mMyWebService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        recyclerView = findViewById(R.id.recyclerView);

        this.init();
        apiCall();

    }

    private void init() {
        mLinearLayoutManager = new LinearLayoutManager(CountryList.this);
        mMyWebService = MyWebService.retrofit.create(MyWebService.class);
    }

    private void apiCall() {

        try {

            Call<List<data>> call = mMyWebService.getCountryList();

            call.enqueue(new Callback<List<data>>() {
                private static final String TAG = "Response";

                @Override
                public void onResponse(Call<List<data>> call, Response<List<data>> response) {
                    if (response.isSuccessful()) {
                        countryListAdapter = new CountryListAdapter(CountryList.this, response.body());
                        recyclerView.setLayoutManager(mLinearLayoutManager);
                        recyclerView.setAdapter(countryListAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<data>> call, Throwable t) {
                    Toast.makeText(CountryList.this, "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: Error-" + t.getMessage().toString());

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "onFailure:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem stSearch = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) stSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countryListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countryListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
