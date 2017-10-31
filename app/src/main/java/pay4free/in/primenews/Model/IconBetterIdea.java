package pay4free.in.primenews.Model;



import java.util.List;

/**
 * Created by AAKASH on 23-10-2017.
 */

public class IconBetterIdea {
    private String url;
    private List<Icon> icons;
private List<Articles> articles;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public List<Icon> getIcons() {
        return icons;
    }
    public List<Articles> getArticles() {
        return articles;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
