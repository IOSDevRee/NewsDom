package geoacircle.info.newsdom;

public class GetterSetter {

    String title;
    String description;
    String link;
    String published;

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
