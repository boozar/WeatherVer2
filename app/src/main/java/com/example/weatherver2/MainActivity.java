package com.example.weatherver2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    ImageView imageToday, imageTomorrow, imageAfterTomorrow;
    ImageView imageArrowToday, imageArrowTomorrow, imageArrowAfterTomorrow;
    ImageView imagePrecipitationToday, imagePrecipitationTomorrow, imagePrecipitationAfterTomorrow;
    TextView textViewToday, textViewTomorrow, textViewAfterTomorrow;
    Sinoptic sinoptic;
    boolean isDataLoaded, isConnected;
    String currWeatherURL;
    WeatherGetter wg;
    String jsonIn, text, massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.layoutHome);
        constraintLayout.setBackgroundColor(getColor(R.color.blue_clods));

        imageToday = findViewById(R.id.imageWeatherToday);
        imageTomorrow = findViewById(R.id.imageWeatherTomorrow);
        imageAfterTomorrow = findViewById(R.id.imageWeatherAfterTomorrow);
        imageArrowToday = findViewById(R.id.imageArrowToday);
        imageArrowTomorrow = findViewById(R.id.imageArrowTomorrow);
        imageArrowAfterTomorrow = findViewById(R.id.imageArrowAfterTomorrow);
        textViewToday = findViewById(R.id.textToday);
        textViewTomorrow = findViewById(R.id.textTomorrow);
        textViewAfterTomorrow = findViewById(R.id.textAfterTomorrow);
        jsonIn = "";
        text = "";
        isDataLoaded = false;
        isConnected = true;
        massage = "";
     //   currWeatherURL = "http://api.openweathermap.org/data/2.5/weather?id=698740&appid=dac392b2d2745b3adf08ca26054d78c4&lang=ru";
        currWeatherURL = "http://api.openweathermap.org/data/2.5/forecast?id=698740&APPID=7660f94b28c31ef1964c060932e29505&lang=ru";
        wg = new WeatherGetter();
        wg.execute();
    }

    public void parseWeather(){
        boolean cont = false;
        JSONObject json = null;
 //       jsonIn = "{\"cod\":\"200\",\"message\":0.0176,\"cnt\":40,\"list\":[{\"dt\":1551376800,\"main\":{\"temp\":282.65,\"temp_min\":280.634,\"temp_max\":282.65,\"pressure\":999.29,\"sea_level\":999.29,\"grnd_level\":997.78,\"humidity\":74,\"temp_kf\":2.02},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":8.31,\"deg\":261},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-02-28 18:00:00\"},{\"dt\":1551387600,\"main\":{\"temp\":281.26,\"temp_min\":279.747,\"temp_max\":281.26,\"pressure\":999,\"sea_level\":999,\"grnd_level\":997.51,\"humidity\":78,\"temp_kf\":1.51},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":8.41,\"deg\":285.503},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-02-28 21:00:00\"},{\"dt\":1551398400,\"main\":{\"temp\":280.11,\"temp_min\":279.105,\"temp_max\":280.11,\"pressure\":999.41,\"sea_level\":999.41,\"grnd_level\":997.93,\"humidity\":82,\"temp_kf\":1.01},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":8.41,\"deg\":295.002},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-01 00:00:00\"},{\"dt\":1551409200,\"main\":{\"temp\":278.87,\"temp_min\":278.361,\"temp_max\":278.87,\"pressure\":1000.14,\"sea_level\":1000.14,\"grnd_level\":998.62,\"humidity\":86,\"temp_kf\":0.5},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":7.62,\"deg\":298.5},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-01 03:00:00\"},{\"dt\":1551420000,\"main\":{\"temp\":277.867,\"temp_min\":277.867,\"temp_max\":277.867,\"pressure\":1002.57,\"sea_level\":1002.57,\"grnd_level\":1001.17,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":7.97,\"deg\":303},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-01 06:00:00\"},{\"dt\":1551430800,\"main\":{\"temp\":278.873,\"temp_min\":278.873,\"temp_max\":278.873,\"pressure\":1003.91,\"sea_level\":1003.91,\"grnd_level\":1002.5,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":8.87,\"deg\":307.502},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-01 09:00:00\"},{\"dt\":1551441600,\"main\":{\"temp\":279.29,\"temp_min\":279.29,\"temp_max\":279.29,\"pressure\":1004.23,\"sea_level\":1004.23,\"grnd_level\":1002.77,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":9.26,\"deg\":305},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-01 12:00:00\"},{\"dt\":1551452400,\"main\":{\"temp\":278.831,\"temp_min\":278.831,\"temp_max\":278.831,\"pressure\":1005.34,\"sea_level\":1005.34,\"grnd_level\":1003.95,\"humidity\":84,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"облачно\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":8.56,\"deg\":308.001},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-01 15:00:00\"},{\"dt\":1551463200,\"main\":{\"temp\":277.389,\"temp_min\":277.389,\"temp_max\":277.389,\"pressure\":1006.95,\"sea_level\":1006.95,\"grnd_level\":1005.46,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":7.06,\"deg\":314.506},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-01 18:00:00\"},{\"dt\":1551474000,\"main\":{\"temp\":276.427,\"temp_min\":276.427,\"temp_max\":276.427,\"pressure\":1007.37,\"sea_level\":1007.37,\"grnd_level\":1005.88,\"humidity\":98,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":6.06,\"deg\":321.002},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-01 21:00:00\"},{\"dt\":1551484800,\"main\":{\"temp\":275.448,\"temp_min\":275.448,\"temp_max\":275.448,\"pressure\":1007.79,\"sea_level\":1007.79,\"grnd_level\":1006.31,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":56},\"wind\":{\"speed\":4.67,\"deg\":300.501},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-02 00:00:00\"},{\"dt\":1551495600,\"main\":{\"temp\":274.568,\"temp_min\":274.568,\"temp_max\":274.568,\"pressure\":1008.13,\"sea_level\":1008.13,\"grnd_level\":1006.61,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":5.06,\"deg\":305.503},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-02 03:00:00\"},{\"dt\":1551506400,\"main\":{\"temp\":273.778,\"temp_min\":273.778,\"temp_max\":273.778,\"pressure\":1009.77,\"sea_level\":1009.77,\"grnd_level\":1008.28,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":7.37,\"deg\":321.003},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-02 06:00:00\"},{\"dt\":1551517200,\"main\":{\"temp\":274.162,\"temp_min\":274.162,\"temp_max\":274.162,\"pressure\":1011.84,\"sea_level\":1011.84,\"grnd_level\":1010.36,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"облачно\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":8.62,\"deg\":354.001},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-02 09:00:00\"},{\"dt\":1551528000,\"main\":{\"temp\":274.127,\"temp_min\":274.127,\"temp_max\":274.127,\"pressure\":1013.22,\"sea_level\":1013.22,\"grnd_level\":1011.69,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"облачно\",\"icon\":\"02d\"}],\"clouds\":{\"all\":24},\"wind\":{\"speed\":8.57,\"deg\":339.002},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-02 12:00:00\"},{\"dt\":1551538800,\"main\":{\"temp\":274.23,\"temp_min\":274.23,\"temp_max\":274.23,\"pressure\":1014.9,\"sea_level\":1014.9,\"grnd_level\":1013.39,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":8.06,\"deg\":343.002},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-02 15:00:00\"},{\"dt\":1551549600,\"main\":{\"temp\":273.407,\"temp_min\":273.407,\"temp_max\":273.407,\"pressure\":1017.54,\"sea_level\":1017.54,\"grnd_level\":1016.06,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":6.2,\"deg\":349.5},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-02 18:00:00\"},{\"dt\":1551560400,\"main\":{\"temp\":272.094,\"temp_min\":272.094,\"temp_max\":272.094,\"pressure\":1019.34,\"sea_level\":1019.34,\"grnd_level\":1017.76,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.67,\"deg\":356.501},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-02 21:00:00\"},{\"dt\":1551571200,\"main\":{\"temp\":271.2,\"temp_min\":271.2,\"temp_max\":271.2,\"pressure\":1021.18,\"sea_level\":1021.18,\"grnd_level\":1019.65,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.71,\"deg\":348.501},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-03 00:00:00\"},{\"dt\":1551582000,\"main\":{\"temp\":270.504,\"temp_min\":270.504,\"temp_max\":270.504,\"pressure\":1022.31,\"sea_level\":1022.31,\"grnd_level\":1020.75,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":4.05,\"deg\":339.502},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-03 03:00:00\"},{\"dt\":1551592800,\"main\":{\"temp\":270.768,\"temp_min\":270.768,\"temp_max\":270.768,\"pressure\":1023.59,\"sea_level\":1023.59,\"grnd_level\":1022.05,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":2.88,\"deg\":348.003},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-03 06:00:00\"},{\"dt\":1551603600,\"main\":{\"temp\":272.624,\"temp_min\":272.624,\"temp_max\":272.624,\"pressure\":1024.8,\"sea_level\":1024.8,\"grnd_level\":1023.35,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.73,\"deg\":8.00455},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-03 09:00:00\"},{\"dt\":1551614400,\"main\":{\"temp\":273.625,\"temp_min\":273.625,\"temp_max\":273.625,\"pressure\":1024.21,\"sea_level\":1024.21,\"grnd_level\":1022.83,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":1.81,\"deg\":196.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-03 12:00:00\"},{\"dt\":1551625200,\"main\":{\"temp\":273.996,\"temp_min\":273.996,\"temp_max\":273.996,\"pressure\":1023.3,\"sea_level\":1023.3,\"grnd_level\":1021.81,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":3.86,\"deg\":197.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-03 15:00:00\"},{\"dt\":1551636000,\"main\":{\"temp\":273.196,\"temp_min\":273.196,\"temp_max\":273.196,\"pressure\":1022.93,\"sea_level\":1022.93,\"grnd_level\":1021.39,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":5.49,\"deg\":201.001},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-03 18:00:00\"},{\"dt\":1551646800,\"main\":{\"temp\":273.902,\"temp_min\":273.902,\"temp_max\":273.902,\"pressure\":1022.1,\"sea_level\":1022.1,\"grnd_level\":1020.63,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":6.16,\"deg\":207.002},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-03 21:00:00\"},{\"dt\":1551657600,\"main\":{\"temp\":274.471,\"temp_min\":274.471,\"temp_max\":274.471,\"pressure\":1021.03,\"sea_level\":1021.03,\"grnd_level\":1019.47,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":7.26,\"deg\":211.502},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-04 00:00:00\"},{\"dt\":1551668400,\"main\":{\"temp\":274.945,\"temp_min\":274.945,\"temp_max\":274.945,\"pressure\":1019.26,\"sea_level\":1019.26,\"grnd_level\":1017.74,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":7.98,\"deg\":214.503},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-04 03:00:00\"},{\"dt\":1551679200,\"main\":{\"temp\":275.806,\"temp_min\":275.806,\"temp_max\":275.806,\"pressure\":1018.7,\"sea_level\":1018.7,\"grnd_level\":1017.14,\"humidity\":100,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":8.53,\"deg\":217.004},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-04 06:00:00\"},{\"dt\":1551690000,\"main\":{\"temp\":277.947,\"temp_min\":277.947,\"temp_max\":277.947,\"pressure\":1017.92,\"sea_level\":1017.92,\"grnd_level\":1016.38,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":8.73,\"deg\":214.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-04 09:00:00\"},{\"dt\":1551700800,\"main\":{\"temp\":278.855,\"temp_min\":278.855,\"temp_max\":278.855,\"pressure\":1016.28,\"sea_level\":1016.28,\"grnd_level\":1014.82,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"облачно\",\"icon\":\"02d\"}],\"clouds\":{\"all\":12},\"wind\":{\"speed\":8.81,\"deg\":212.503},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-04 12:00:00\"},{\"dt\":1551711600,\"main\":{\"temp\":278.643,\"temp_min\":278.643,\"temp_max\":278.643,\"pressure\":1014.84,\"sea_level\":1014.84,\"grnd_level\":1013.34,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04d\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":8.9,\"deg\":207.501},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-04 15:00:00\"},{\"dt\":1551722400,\"main\":{\"temp\":277.807,\"temp_min\":277.807,\"temp_max\":277.807,\"pressure\":1014.3,\"sea_level\":1014.3,\"grnd_level\":1012.89,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"пасмурно\",\"icon\":\"04n\"}],\"clouds\":{\"all\":64},\"wind\":{\"speed\":8.88,\"deg\":207.502},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-04 18:00:00\"},{\"dt\":1551733200,\"main\":{\"temp\":277.58,\"temp_min\":277.58,\"temp_max\":277.58,\"pressure\":1013.07,\"sea_level\":1013.07,\"grnd_level\":1011.5,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":9.1,\"deg\":206.002},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-04 21:00:00\"},{\"dt\":1551744000,\"main\":{\"temp\":277.595,\"temp_min\":277.595,\"temp_max\":277.595,\"pressure\":1012.07,\"sea_level\":1012.07,\"grnd_level\":1010.5,\"humidity\":92,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":9.42,\"deg\":207.006},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-05 00:00:00\"},{\"dt\":1551754800,\"main\":{\"temp\":277.694,\"temp_min\":277.694,\"temp_max\":277.694,\"pressure\":1010.54,\"sea_level\":1010.54,\"grnd_level\":1009.02,\"humidity\":91,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01n\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":9.42,\"deg\":207},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2019-03-05 03:00:00\"},{\"dt\":1551765600,\"main\":{\"temp\":278.337,\"temp_min\":278.337,\"temp_max\":278.337,\"pressure\":1009.39,\"sea_level\":1009.39,\"grnd_level\":1007.92,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":9.92,\"deg\":201.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-05 06:00:00\"},{\"dt\":1551776400,\"main\":{\"temp\":280.019,\"temp_min\":280.019,\"temp_max\":280.019,\"pressure\":1008.59,\"sea_level\":1008.59,\"grnd_level\":1007.04,\"humidity\":78,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":10.46,\"deg\":210.001},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-05 09:00:00\"},{\"dt\":1551787200,\"main\":{\"temp\":281.481,\"temp_min\":281.481,\"temp_max\":281.481,\"pressure\":1007.11,\"sea_level\":1007.11,\"grnd_level\":1005.71,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":8.84,\"deg\":211.5},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-05 12:00:00\"},{\"dt\":1551798000,\"main\":{\"temp\":281.954,\"temp_min\":281.954,\"temp_max\":281.954,\"pressure\":1007.18,\"sea_level\":1007.18,\"grnd_level\":1005.75,\"humidity\":69,\"temp_kf\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"clouds\":{\"all\":0},\"wind\":{\"speed\":6.86,\"deg\":218},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2019-03-05 15:00:00\"}],\"city\":{\"id\":698740,\"name\":\"Odessa\",\"coord\":{\"lat\":46.4775,\"lon\":30.7326},\"country\":\"UA\"}}";
        try {
            json = new JSONObject(jsonIn);
            cont = true;
        }catch (JSONException e){
            Log.e("log_tag", "Error parsing data" + e.toString());
        }
        if (cont)
            try {
                JSONArray jsonList =  json.getJSONArray("list");
                String name = json.getJSONObject("city").getString("name");
                SimpleDateFormat sm = new SimpleDateFormat("d.M.Y H:mm");
                sm.setTimeZone(TimeZone.getTimeZone("GTM+2"));
                Date[]      dates      = new Date[40];
                int[]       deg        = new int[40];
                int[]       clouds     = new int[40];
                double[]    speeds     = new double[40];
                double[]    temps      = new double[40];
                double[]    pressures  = new double[40];
                double[]    humiditys  = new double[40];
                String[]    weathers   = new String[40];
                for (int i = 0 ; i <= 39; i++){
                    dates[i]     = new Date(jsonList.getJSONObject(i).getLong("dt")*1000);
                    temps[i]     = (jsonList.getJSONObject(i).getJSONObject("main").getDouble("temp")- 273.12);
                    pressures[i] = jsonList.getJSONObject(i).getJSONObject("main").getDouble("pressure");
                    humiditys[i] = jsonList.getJSONObject(i).getJSONObject("main").getInt("humidity");
                    weathers[i]  = jsonList.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                    deg[i]       = jsonList.getJSONObject(i).getJSONObject("wind").getInt("deg");
                    clouds[i]    = jsonList.getJSONObject(i).getJSONObject("clouds").getInt("all");
                    speeds[i]    = jsonList.getJSONObject(i).getJSONObject("wind").getDouble("speed");
                }
                int index = 0;
                String[] textViews = new String[]{"","",""};
                textViews[0] = name+ "\n";
                String  [] days      = new String[]{"Сегодня: ", "Завтра: ", "Послезавтра: "};
                String  [] texts     = new String[]{"\nтемпература: ", "\nдавление: ", "\nвлажность: ", "\nветер м/с: "};

                for (int i = 0 ; i <= 2 ; i++){
                    textViews[i] += ( days[i] + sm.format(dates[index]) + texts[0] + String.format("%.1f",temps[index]) + texts[1] + pressures[index] + texts[2] + humiditys[index] + texts[3] + speeds[index] + "\n"+ weathers[index] );
                    index +=8;
                }
                isDataLoaded = true;
                sinoptic = new Sinoptic(deg,clouds);
                textViewToday.setText(textViews[0]);
                textViewTomorrow.setText(textViews[1]);
                textViewAfterTomorrow.setText(textViews[2]);

            }catch (JSONException e){
                e.printStackTrace();
            }
            drawWeather();
    }
    public void btnLoadData(View view){
        currWeatherURL = "http://api.openweathermap.org/data/2.5/forecast?id=698740&APPID=7660f94b28c31ef1964c060932e29505";
        if (wg.getStatus() == AsyncTask.Status.RUNNING)
            wg.cancel(true);
        wg = new WeatherGetter();
        wg.execute();
        parseWeather();
    }
    public void setBackgroundSun(ImageView image){
        image.setBackgroundResource(R.drawable.sun);
    }
    public void drawCompact(ImageView image, int i){
        if (sinoptic.getCloudsId(i)<5 ){
            image.setImageResource(R.drawable.null1);
        }else if(sinoptic.getCloudsId(i)<25){
            image.setImageResource(R.drawable.cloud1);
        }else if(sinoptic.getCloudsId(i)<50){
            image.setImageResource(R.drawable.cloud2);
        }else if(sinoptic.getCloudsId(i)<75){
            image.setImageResource(R.drawable.cloud3);
        }else if(sinoptic.getCloudsId(i)<95){
            image.setImageResource(R.drawable.cloud4);
        } else
            image.setImageResource(R.drawable.null1);
    }
    public void arrowRotation(ImageView image, int i){
        image.setImageResource(R.drawable.arrow);
        image.setRotation(sinoptic.getDegId(i));
    }
    public void drawWeather(){
        if (isConnected){
            setBackgroundSun(imageToday);
            setBackgroundSun(imageTomorrow);
            setBackgroundSun(imageAfterTomorrow);
            drawCompact(imageToday, 0);
            drawCompact(imageTomorrow,8);
            drawCompact(imageAfterTomorrow,16);
            arrowRotation(imageArrowToday, 0);
            arrowRotation(imageArrowTomorrow, 8);
            arrowRotation(imageArrowAfterTomorrow, 16);

        }else imageToday.setImageResource(R.drawable.null1);
    }

    class WeatherGetter extends AsyncTask<Void, Void, Void> {

        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1){
                sb.append((char) cp);
            }
            return sb.toString();
        }

        public void ConnectAndGetData(String url){
            InputStream is = null;
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo.isConnected()){
                try {

                    is = new URL(url).openStream();
                    try {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                        try {
                            jsonIn = readAll(rd);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } finally
                            {
                            try {
                                 is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                isConnected = false;
            }
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ConnectAndGetData(currWeatherURL);
            return null;
        }
        protected void onPostExecute(Void result){
            parseWeather();
        }

        @Override
        protected void onCancelled(){
            super.onCancelled();
            Log.d("", "Process cancelling");
        }
    }
}