package startup.carvaan.myapplication.ui.about;

import java.util.HashMap;
import java.util.Map;

public class PostModal {
    String name,type,url,likes;
    Map<String,String>comments=new HashMap<>();

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public Map<String, String> getComments() {
        return comments;
    }

    public void setComments(Map<String, String> comments) {
        this.comments = comments;
    }

    public PostModal(String name, String type, String url, String likes, Map<String, String> comments) {
        this.name = name;
        this.type = type;
        this.url = url;
        this.likes = likes;
        this.comments = comments;
    }

    public PostModal() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
