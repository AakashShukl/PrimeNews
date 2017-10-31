package pay4free.in.primenews.Common;

import android.graphics.drawable.Icon;

import pay4free.in.primenews.Interface.IconBetterIdeaService;
import pay4free.in.primenews.Interface.NewsService;
import pay4free.in.primenews.Model.IconBetterIdea;
import pay4free.in.primenews.Remote.IconBetterIdeaClient;
import pay4free.in.primenews.Remote.RetrofitClient;

/**
 * Created by AAKASH on 23-10-2017.
 */

public class Common {
    private static final String Base_URL="https://newsapi.org/";
    public static final  String API_KEY="f367190560d84b188a219417d32147cb";
    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(Base_URL).create(NewsService.class);
    }
    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);

    }

    //https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey={f367190560d84b188a219417d32147cb}
    public static String getAPIUrl(String source,String sortBy,String apiKEY)
    {
        StringBuilder apiUrl=new StringBuilder("https://newsapi.org/v1/articles?source=");
        return apiUrl.append(source).append("&sortBy=").append(sortBy).append("&apiKey=").append(apiKEY).toString();
    }
}
