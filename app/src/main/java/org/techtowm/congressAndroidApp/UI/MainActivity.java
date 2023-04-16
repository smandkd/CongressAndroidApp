package org.techtowm.congressAndroidApp.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.techtowm.congressAndroidApp.Retrofit.APIClient;
import org.techtowm.congressAndroidApp.Retrofit.APIInterface;
import org.techtowm.congressAndroidApp.data.ArticleResponse;
import org.techtowm.congressAndroidApp.data.DiplomacyResponse;
import org.techtowm.congressAndroidApp.data.LegisResponse;
import org.techtowm.congressAndroidApp.R;
import org.techtowm.congressAndroidApp.data.PetResponse;
import org.techtowm.congressAndroidApp.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    TextView legisTV;
    TextView legisTV2;
    TextView legisTV3;
    TextView legisTV4;
    Button legisBT;

    TextView petTV;
    TextView petTV2;
    TextView petTV3;
    TextView petTV4;
    Button petBT;

    TextView dipTV;
    TextView dipTV1;
    TextView dipTV2;
    TextView dipTV3;
    Button dipBT;

    TextView artTV;
    TextView artTV1;
    TextView artTV2;
    TextView artTV3;
    Button artBT;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(android.R.style.Widget_Material_ProgressBar);
        progressDialog.show();

        // Regislation
        ConstraintLayout rootLayout = binding.root;
        legisTV = binding.legisTvTitle1;
        legisTV2 = binding.legisTvTitle2;
        legisTV3 = binding.legisTvTitle3;
        legisTV4 = binding.legisTvTitle4;
        legisBT = binding.legisButton;
        legisBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LegislationActivity.class);
                startActivity(i);
            }
        });
        getLegislationList();

        // Petition
        petTV = binding.petitionTvTitle1;
        petTV2 = binding.petitionTvTitle2;
        petTV3 = binding.petitionTvTitle3;
        petTV4 = binding.petitionTvTitle4;
        petBT = binding.petitionButton;
        petBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PetActivity.class);
                startActivity(i);
            }
        });
        getPetitionList();

        dipTV = binding.diplomacyTvTitle1;
        dipTV1 = binding.diplomacyTvTitle2;
        dipTV2 = binding.diplomacyTvTitle3;
        dipTV3 = binding.diplomacyTvTitle4;
        dipBT = binding.diplomacyButton;
        dipBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DiplomacyActivity.class);
                startActivity(i);
            }
        });
        getDiplomacyList();

        artTV = binding.articleTvTitle1;
        artTV1 = binding.articleTvTitle2;
        artTV2 = binding.articleTvTitle3;
        artTV3 = binding.articleTvTitle4;
        artBT = binding.articleButton;
        artBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ArticleActivity.class);
                startActivity(i);
            }
        });
        getArticleList();

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null ) {
            actionBar.hide();
        }

    }

    private void getLegislationList() {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        apiInterface.getRegisData().enqueue(new Callback<List<LegisResponse>>() {
            @Override
            public void onResponse(Call<List<LegisResponse>> call, Response<List<LegisResponse>> response) {
                Log.d("Sang", "MainActivity Regislation list" + response.body().toString() + "\n");
                List<LegisResponse> list = response.body();

                if( response.isSuccessful() ) {
                    /*
                    for( LegisResponse bang : list ) {
                        Log.d("Sang", "MainActivity : "
                                + " " + bang.getBillName()
                                + " " + bang.getAge()
                                + " " + bang.getCurrCommitee()
                                + " " + bang.getLinkUrl()
                                + " " + bang.getLegConRe()
                        );
                    }
                     */
                    progressDialog.dismiss();

                    legisTV.setText(list.get(0).getBillName());
                    legisTV2.setText(list.get(1).getBillName());
                    legisTV3.setText(list.get(2).getBillName());
                    legisTV4.setText(list.get(3).getBillName());
                }
            }

            @Override
            public void onFailure(Call<List<LegisResponse>> call, Throwable t) {
                Log.d("Sang", "Retrofit faild " + t);
            }
        });
    }

    private void getPetitionList() {
        APIInterface api = APIClient.getClient().create(APIInterface.class);
        api.getPetData().enqueue(new Callback<List<PetResponse>>() {
            @Override
            public void onResponse(Call<List<PetResponse>> call, Response<List<PetResponse>> response) {
                List<PetResponse> body = response.body();
                if(response.isSuccessful()) {
                    /*
                    for( PetResponse list : body ) {
                        Log.d("SangPet",
                                list.getBillName() + " " +
                                     list.getApprover() + " " +
                                     list.getUrl() + " "
                                );
                    }
                    */

                    petTV.setText(body.get(0).getBillName());
                    petTV2.setText(body.get(1).getBillName());
                    petTV3.setText(body.get(2).getBillName());
                    petTV4.setText(body.get(3).getBillName());
                }
                else {
                    Log.d("SangPet", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<PetResponse>> call, Throwable t) {
                Log.d("Sang", t.toString());
            }
        });
    }

    private void getDiplomacyList() {
        APIInterface api = APIClient.getClient().create(APIInterface.class);
        api.getDipData().enqueue(new Callback<List<DiplomacyResponse>>() {
            @Override
            public void onResponse(Call<List<DiplomacyResponse>> call, Response<List<DiplomacyResponse>> response) {
                if( response.isSuccessful() ) {
                    List<DiplomacyResponse> list = response.body();

                    for( DiplomacyResponse data : list ) {
                        Log.d("Sang" , "Diplomacy " + data.getArticleTitle() + " "
                                                             + data.getCategoryNM() + " "
                                                             + data.getUrl() + " "
                                                             + data.getMasterNM() + " "
                                                             + data.getUpdateDT() + " "
                                                             );
                    }

                    dipTV.setText(list.get(0).getArticleTitle());
                    dipTV1.setText(list.get(1).getArticleTitle());
                    dipTV2.setText(list.get(2).getArticleTitle());
                    dipTV3.setText(list.get(3).getArticleTitle());
                }
                else {
                    Log.d("Sang", "MainActivity Diplomacy failed " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DiplomacyResponse>> call, Throwable t) {
                Log.d("Sang", "MainActivity Diplomacy failed " + t.toString() );
            }
        });
    }

    private void getArticleList() {
        APIInterface api = APIClient.getClient().create(APIInterface.class);
        api.getArtData().enqueue(new Callback<List<ArticleResponse>>() {
            @Override
            public void onResponse(Call<List<ArticleResponse>> call, Response<List<ArticleResponse>> response) {
                if( response.isSuccessful() ) {
                    List<ArticleResponse> list = response.body();

                    artTV.setText(list.get(0).getTitle());
                    artTV1.setText(list.get(1).getTitle());
                    artTV2.setText(list.get(2).getTitle());
                    artTV3.setText(list.get(3).getTitle());
                }
                else {
                    Log.d("Sang", "Article response failed " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<List<ArticleResponse>> call, Throwable t) {
                Log.d("Sang", "Article response successful" + t);
            }
        });
    }
}