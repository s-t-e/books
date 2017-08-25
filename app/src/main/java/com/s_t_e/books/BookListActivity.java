package com.s_t_e.books;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private ProgressBar loadingProgress;
    private RecyclerView rvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        loadingProgress = (ProgressBar)findViewById(R.id.pb_loading);
        rvBooks = (RecyclerView)findViewById(R.id.rv_books);
        LinearLayoutManager booksLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvBooks.setLayoutManager(booksLayoutManager);
        try {
            URL bookUrl = ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            loadingProgress.setVisibility(View.VISIBLE);
            rvBooks.setVisibility(View.INVISIBLE);
        }

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
            loadingProgress.setVisibility(View.INVISIBLE);
            rvBooks.setVisibility(View.VISIBLE);
            ArrayList<Book> books = ApiUtil.getBooksFromJson(result);
            BooksAdapter adapter = new BooksAdapter(books);
            rvBooks.setAdapter(adapter);
        }
    }
}
