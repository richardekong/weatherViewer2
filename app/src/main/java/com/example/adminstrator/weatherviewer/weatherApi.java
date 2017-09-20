package com.example.adminstrator.weatherviewer;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.squareup.okhttp.ResponseBody;
/**
 * Created by Adminstrator on 9/18/2017.
 */
//http://samples.openweathermap.org/data/2.5/history/city?q=Moscow,RU&appid=28d840e167ecd3a805e9e32b285f0adb


public interface weatherApi
{
      String base_url="http://api.openweathermap.org";

    //making api call to openweathermap
    @GET("/data/2.5/weather") Call<Weather> getWeatherInfo(
            @Query("q") String city,
            @Query("APPID") String api_key);



   class factory {

        private static weatherApi service;

        public static weatherApi getInstance() {
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(base_url)
                        .build();

                service = retrofit.create(weatherApi.class);
                return service;
            } else {
                return service;
            }

        }

    }

}
