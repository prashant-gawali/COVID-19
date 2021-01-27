package com.example.covid_19;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid_19.model.ImageSliderModel;
import com.example.covid_19.model.IndiaModel;
import com.example.covid_19.model.MyWebService;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    Button btnGlobalData;
    SliderView sliderView;
    List<ImageSliderModel> imageSliderModels;
    TextView cases;
    TextView todayCases;
    TextView deaths;
    TextView todayDeaths;
    TextView recovered;
    TextView active;
    TextView critical;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v=findViewById(android.R.id.content);

        sliderView = findViewById(R.id.imageSlider);
        imageSliderModels = new ArrayList<>();
        btnGlobalData = findViewById(R.id.btnGlobalData);
        btnGlobalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CountryList.class);
                startActivity(intent);
            }
        });

        imageSliderModels.add(new ImageSliderModel(R.mipmap.three));
        imageSliderModels.add(new ImageSliderModel(R.mipmap.two));
        imageSliderModels.add(new ImageSliderModel(R.mipmap.keep_distance));
        imageSliderModels.add(new ImageSliderModel(R.mipmap.four));
        imageSliderModels.add(new ImageSliderModel(R.mipmap.five));


        sliderView.setSliderAdapter(new ImageSliderAdapter(this, imageSliderModels));
        initViews();

    }

    private void initViews() {
        cases = findViewById(R.id.cases);
        todayCases = findViewById(R.id.todayCases);
        deaths = findViewById(R.id.deaths);
        todayDeaths = findViewById(R.id.todayDeaths);
        recovered = findViewById(R.id.recovered);
        active = findViewById(R.id.active);
        critical = findViewById(R.id.critical);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if ((networkInfo == null) || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Snackbar.make(v, "No Internet", Snackbar.LENGTH_SHORT).show();
        }else {

            getObject();

        }
    }

    private void getObject() {
        MyWebService myWebService = MyWebService.retrofit.create(MyWebService.class);
        Call<IndiaModel> call = myWebService.getObject();
        call.enqueue(new Callback<IndiaModel>() {
            @Override
            public void onResponse(Call<IndiaModel> call, Response<IndiaModel> response) {
                IndiaModel data = response.body();

                cases.setText(data.getCases().toString());
                todayCases.setText(data.getTodayCases().toString());
                deaths.setText(data.getDeaths().toString());
                todayDeaths.setText(data.getTodayDeaths().toString());
                recovered.setText(data.getDeaths().toString());
                active.setText(data.getDeaths().toString());
                critical.setText(data.getCritical().toString());

                Log.d(TAG, "onResponse: "+data.getTodayCases());
                Log.d(TAG, "onResponse: "+data.getTodayDeaths());

            }

            @Override
            public void onFailure(Call<IndiaModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}