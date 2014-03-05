package com.taximagicroshambo.roshambo.services;

import android.net.Uri;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by huttneab on 3/4/14.
 */
public class ThrowToWinAsyncTask extends AsyncTask<Uri, Void, Void> {

    boolean ioexception = false;

    @Override
    protected Void doInBackground(Uri... params) {

        OkHttpClient okHttpClient = new OkHttpClient();
        InputStream in = null;
        HttpURLConnection connection = null;

        try {
            connection = okHttpClient.open(new URL(params[0].toString()));
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "text/plain");
            in = connection.getInputStream();

            byte[] response = readInputStream(in);
            String body = new String(response, "UTF-8");
            // TODO: store to content provider once implemented

        } catch (IOException e) {
            ioexception = true;
        } finally {
            if(connection != null){
                connection.disconnect();
            }

            try {
                if (in != null) in.close();
            } catch (IOException e) {
                ioexception = true;
            }


        }

        return null;
    }

    byte[] readInputStream(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];

        int count;
        while((count = in.read(buff)) != -1 ) {
            out.write(buff, 0, count);
        }

        return out.toByteArray();
    }


//    @Override
//    protected void onPostExecute(Void a) {
//        super.onPostExecute(a);
//        optionally, check if an exception was thrown and display it to the user via a dialog
//        we would also have to have kept track of the calling activity via a weak reference.
//        A bit overkill for this HW assignment.
//    }
}
