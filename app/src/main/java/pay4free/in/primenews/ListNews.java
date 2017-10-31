package pay4free.in.primenews;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import pay4free.in.primenews.Adapter.ListNewAdapter;
import pay4free.in.primenews.Common.Common;
import pay4free.in.primenews.Interface.NewsService;
import pay4free.in.primenews.Model.Articles;
import pay4free.in.primenews.Model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {

    KenBurnsView kenBurnsView;
    DiagonalLayout diagonallayout;
    SpotsDialog dialog;
    NewsService mService;
    TextView top_author,top_title;
    SwipeRefreshLayout swipeRefreshLayout;
    String source="";
    String sortBy="";
    String webHotUrl="";

    ListNewAdapter adapter;
    RecyclerView listnews;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        mService= Common.getNewsService();
        dialog=new SpotsDialog(this);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source,true);
            }
        });
        diagonallayout=(DiagonalLayout)findViewById(R.id.diagonal);
        kenBurnsView=(KenBurnsView)findViewById(R.id.top_image);
        diagonallayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),DetailActivity.class);
                intent.putExtra("webURL",webHotUrl);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        top_author=(TextView)findViewById(R.id.topAuthor);
        top_title=(TextView)findViewById(R.id.toptitle);
        listnews=(RecyclerView)findViewById(R.id.listnews);
        listnews.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        listnews.setLayoutManager(layoutManager);

        if(getIntent()!=null)
        {
            source=getIntent().getStringExtra("source");
            sortBy=getIntent().getStringExtra("sortBy");
            if(!source.isEmpty()&&!sortBy.isEmpty())
            {
                loadNews(source,false);
            }
        }

    }

    private void loadNews(String source, boolean isRefreshed) {

        if(!isRefreshed)
        {
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source,sortBy, Common.API_KEY)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();
                    Picasso.with(getBaseContext()).load(response.body().getArticles().get(0).getUrlToimage()).into(kenBurnsView);
                    top_title.setText(response.body().getArticles().get(0).getTitle());
                    top_author.setText(response.body().getArticles().get(0).getAuthor());

                    webHotUrl=response.body().getArticles().get(0).getUrl();

                    List<Articles> removeFirstItem=response.body().getArticles();
                    removeFirstItem.remove(0);
                    adapter=new ListNewAdapter(removeFirstItem,getBaseContext());
                    adapter.notifyDataSetChanged();
                    listnews.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
        }
        else
        {
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source,sortBy, Common.API_KEY)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();
                    Picasso.with(getBaseContext()).load(response.body().getArticles().get(0).getUrlToimage()).into(kenBurnsView);
                    top_title.setText(response.body().getArticles().get(0).getTitle());
                    top_author.setText(response.body().getArticles().get(0).getAuthor());

                    webHotUrl=response.body().getArticles().get(0).getUrl();

                    List<Articles> removeFirstItem=response.body().getArticles();
                    removeFirstItem.remove(0);
                    adapter=new ListNewAdapter(removeFirstItem,getBaseContext());
                    adapter.notifyDataSetChanged();
                    listnews.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
            swipeRefreshLayout.setRefreshing(false);
        }


    }
}
