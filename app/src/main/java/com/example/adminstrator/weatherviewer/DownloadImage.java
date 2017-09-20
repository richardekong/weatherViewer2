package com.example.adminstrator.weatherviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Adminstrator on 9/19/2017.
 */

public class DownloadImage extends AsyncTask<String,Void,Bitmap> {
    private ImageView imageView; // displays the thumbnail
    private Map<String,Bitmap> bitmaps=new HashMap<>();
    // store ImageView on which to set the downloaded Bitmap
    public DownloadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    // load image; params[0] is the String URL representing the image
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(params[0]); // create URL for image

            // open an HttpURLConnection, get its InputStream
            // and download the image
            connection = (HttpURLConnection) url.openConnection();

            try (InputStream inputStream = connection.getInputStream()) {
                bitmap = BitmapFactory.decodeStream(inputStream);
                bitmaps.put(params[0], bitmap); // cache for later use
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect(); // close the HttpURLConnection
        }

        return bitmap;
    }

    // set weather condition image in list item
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}


