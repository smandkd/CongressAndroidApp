package org.techtowm.congressAndroidApp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.techtowm.congressAndroidApp.Retrofit.APIClient;
import org.techtowm.congressAndroidApp.Retrofit.APIInterface;
import org.techtowm.congressAndroidApp.data.LegisResponse;
import org.techtowm.congressAndroidApp.adpater.LegisAdapter;
import org.techtowm.congressAndroidApp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LegislationActivity extends AppCompatActivity implements LegisAdapter.OnItemClickListener{
    List<LegisResponse> mData;
    LegisAdapter legisAdapter;
    RecyclerView recyclerView;
    APIInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislation);

        View v = findViewById(R.id.constraint);
        mData = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);

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
            actionBar.setTitle("입법 예고");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setData() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterface.getRegisData().enqueue(new Callback<List<LegisResponse>>() {
            @Override
            public void onResponse(Call<List<LegisResponse>> call, Response<List<LegisResponse>> response) {
                if( response.isSuccessful() ) {
                    progressDialog.dismiss();
                    mData = response.body();
                    prepareAdapter(mData);
                }
                else {
                    Log.d("Sang", "Legislation Activity failed");
                }
            }

            @Override
            public void onFailure(Call<List<LegisResponse>> call, Throwable t) {
                Log.d("Sang", "Retrofit faild " + t);
            }
        });
    }

    public void prepareRecyclerView() {
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void prepareAdapter(List<LegisResponse> list) {
        legisAdapter = new LegisAdapter(list, this::onItemClick);
        recyclerView.setAdapter(legisAdapter);
    }

    @Override
    public void onItemClick(View v, int position) {
        if( v.getVisibility() == View.VISIBLE) {
            v.setVisibility( View.GONE );
        }
        else {
            v.setVisibility( View.VISIBLE );
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.search_view ) {
            return true;
        }else if( id == R.id.home ) {
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
                legisAdapter.getFilter().filter(searchStr);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
