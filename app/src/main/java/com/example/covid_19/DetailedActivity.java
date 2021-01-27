package com.example.covid_19;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.covid_19.model.IndiaModel1;
import com.example.covid_19.model.MyWebService;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedActivity extends AppCompatActivity {
    TextView cases, todayCases, todayDeaths, deaths, recovered, active, critical, detailedCountryName;
    ImageView detailedImageVIew;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initViews();

        String CountryName = getIntent().getStringExtra("CountryName");
        String CountryFlag1 = getIntent().getStringExtra("CountryFlag");
        getStats(CountryName);
        detailedCountryName.setText(CountryName);
        Glide.with(DetailedActivity.this).load(CountryFlag1).into(detailedImageVIew);
    }

    private void getStats(String CountryName) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if ((networkInfo == null) || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Snackbar.make(v, "No Internet", Snackbar.LENGTH_SHORT).show();
        } else {

            MyWebService myWebService = MyWebService.retrofit.create(MyWebService.class);
            Call<IndiaModel1> call = myWebService.getStats(CountryName);
            call.enqueue(new Callback<IndiaModel1>() {
                @Override
                public void onResponse(Call<IndiaModel1> call, Response<IndiaModel1> response) {
                    if (response.isSuccessful()) {
                        IndiaModel1 mdata = response.body();
                        cases.setText(mdata.getCases().toString());
                        todayCases.setText(mdata.getTodayCases().toString());
                        deaths.setText(mdata.getDeaths().toString());
                        todayDeaths.setText(mdata.getTodayDeaths().toString());
                        recovered.setText(mdata.getRecovered().toString());
                        active.setText(mdata.getActive().toString());
                        critical.setText(mdata.getCritical().toString());


                    } else {
                        Toast.makeText(DetailedActivity.this, "Error:", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<IndiaModel1> call, Throwable t) {

                }
            });
        }

    }


    private void initViews() {

        cases = findViewById(R.id.cases);
        v = findViewById(R.id.content);
        todayCases = findViewById(R.id.todayCases);
        deaths = findViewById(R.id.deaths);
        todayDeaths = findViewById(R.id.todayDeaths);
        recovered = findViewById(R.id.recovered);
        active = findViewById(R.id.active);
        critical = findViewById(R.id.critical);
        detailedImageVIew = findViewById(R.id.detailedImageVIew);
        detailedCountryName = findViewById(R.id.detailedCountryName);
    }


}