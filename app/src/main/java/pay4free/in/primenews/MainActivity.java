package pay4free.in.primenews;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import pay4free.in.primenews.Adapter.ListSourceAdapter;
import pay4free.in.primenews.Common.Common;
import pay4free.in.primenews.Interface.NewsService;
import pay4free.in.primenews.Model.Articles;
import pay4free.in.primenews.Model.IconBetterIdea;
import pay4free.in.primenews.Model.WebSite;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
RecyclerView listWebsite;
    WebView webView;
    KenBurnsView kenBurnsView;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    SpotsDialog Dialog;
    Articles articles;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        mService= Common.getNewsService();
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });
        listWebsite=(RecyclerView)findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(false);
        layoutManager=new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        Dialog=new SpotsDialog(this);
        loadWebsiteSource(false);

       kenBurnsView=(KenBurnsView)findViewById(R.id.webView);
        Picasso.with(getApplicationContext()).load("https://i0.wp.com/dubaicompanieslist.ae/wp-content/uploads/2017/01/newspaper-Distributor-Companies-dubai.png?resize=1170%2C610").into(kenBurnsView);


    }


    private void loadWebsiteSource(boolean isRefreshed) {
    if(!isRefreshed)
    {
    String cache =Paper.book().read("cache");
    if(cache!=null&&!cache.isEmpty())
    {
        WebSite webSite=new Gson().fromJson(cache,WebSite.class);
        adapter=new ListSourceAdapter(getBaseContext(),webSite);
        adapter.notifyDataSetChanged();
        listWebsite.setAdapter(adapter);
    }
    else
    {
        Dialog.show();
        mService.getSources().enqueue(new Callback<WebSite>() {
            @Override
            public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                adapter=new ListSourceAdapter(getBaseContext(),response.body());
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
                Paper.book().write("cache",new Gson().toJson(response.body()));
                Dialog.dismiss();
            }

            @Override
            public void onFailure(Call<WebSite> call, Throwable t) {

            }
        });
    }
}
else
{
    Dialog.show();
    mService.getSources().enqueue(new Callback<WebSite>() {
        @Override
        public void onResponse(Call<WebSite> call, Response<WebSite> response) {
            adapter=new ListSourceAdapter(getBaseContext(),response.body());
            adapter.notifyDataSetChanged();
            listWebsite.setAdapter(adapter);

            Paper.book().write("cache",new Gson().toJson(response.body()));
            swipeRefreshLayout.setRefreshing(false);
            Dialog.dismiss();
        }

        @Override
        public void onFailure(Call<WebSite> call, Throwable t) {

        }
    });
}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
