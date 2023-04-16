package org.techtowm.congressAndroidApp.Retrofit;

import org.techtowm.congressAndroidApp.data.ArticleResponse;
import org.techtowm.congressAndroidApp.data.DiplomacyResponse;
import org.techtowm.congressAndroidApp.data.PetResponse;
import org.techtowm.congressAndroidApp.data.LegisResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("regis_html.html")
    Call<List<LegisResponse>> getRegisData();

    @GET("pet_html.html")
    Call<List<PetResponse>> getPetData();

    @GET("dip_html.html")
    Call<List<DiplomacyResponse>> getDipData();

    @GET("art_html.html")
    Call<List<ArticleResponse>> getArtData();
}
