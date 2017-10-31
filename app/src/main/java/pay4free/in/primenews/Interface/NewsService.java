package pay4free.in.primenews.Interface;

import android.support.constraint.solver.Cache;

import pay4free.in.primenews.Model.News;
import pay4free.in.primenews.Model.WebSite;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by AAKASH on 23-10-2017.
 */

public interface NewsService {
    @GET("v1/sources?language=en")
    Call<WebSite> getSources();
    @GET
    Call<News> getNewestArticles(@Url String url);
}
