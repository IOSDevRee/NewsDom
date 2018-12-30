package geoacircle.info.newsdom;

public class GetterSetter {

    String title;
    String link;
    String newsImageUrl;
    String description;
    String published;

    public GetterSetter(String title, String newsImageUrl, String description, String link, String published) {
        this.title = title;
        this.link = link;
        this.newsImageUrl = newsImageUrl;
        this.description = description;
        this.published = published;
    }

    public GetterSetter(String title, String newsImageUrl) {
        this.title = title;
        this.newsImageUrl = newsImageUrl;
    }


    public GetterSetter(String title, String description, String link, String published) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }


}
