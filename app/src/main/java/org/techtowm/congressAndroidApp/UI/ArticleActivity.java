package org.techtowm.congressAndroidApp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.techtowm.congressAndroidApp.R;
import org.techtowm.congressAndroidApp.Retrofit.APIClient;
import org.techtowm.congressAndroidApp.Retrofit.APIInterface;
import org.techtowm.congressAndroidApp.adpater.ArtAdapter;
import org.techtowm.congressAndroidApp.data.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleActivity extends AppCompatActivity implements ArtAdapter.OnItemClickListener{
    ArtAdapter artAdapter;
    List<ArticleResponse> list = new ArrayList<>();
    RecyclerView recyclerView;
    APIInterface api;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        View v = findViewById(R.id.article_constraints_layout);
        recyclerView = findViewById(R.id.article_recycler_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_Material_ProgressBar);
        progressDialog.show();

        setData();
        prepareRecyclerView();

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.setTitle("국회 뉴스");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setData() {
        api = APIClient.getClient().create(APIInterface.class);
        api.getArtData().enqueue(new Callback<List<ArticleResponse>>() {
            @Override
            public void onResponse(Call<List<ArticleResponse>> call, Response<List<ArticleResponse>> response) {
                if( response.isSuccessful() ) {
                    progressDialog.dismiss();
                    list = response.body();
                    prepareAdapter(list);
                }else {
                    Log.d("Sang", "Article Activity failed");
                }
            }

            @Override
            public void onFailure(Call<List<ArticleResponse>> call, Throwable t) {
                Log.d("Sang", "Article Activity failed");
            }
        });
    }

    public void prepareRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void prepareAdapter(List<ArticleResponse> list) {
        artAdapter = new ArtAdapter(list, this::onItemClick);
        recyclerView.setAdapter(artAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.search_view ) {
            return true;
        } else if( id == R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchStr = newText;
                artAdapter.getFilter().filter(searchStr);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(TextView v, int position) {
        Log.d("Sang", "Article clicked get Text" + v.getText() );
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(v.getText().toString()));
        startActivity(i);
    }
}