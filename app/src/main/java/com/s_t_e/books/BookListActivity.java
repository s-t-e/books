package com.s_t_e.books;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class BookListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl =  urls[0];
            String result = null;
            try {
                result = ApiUtil.getJson(searchUrl);
            } catch (IOException e) {
                Log.e("error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tvResult = (TextView)findViewById(R.id.tvResponse);
            tvResult.setText(result);
        }
    }
}
