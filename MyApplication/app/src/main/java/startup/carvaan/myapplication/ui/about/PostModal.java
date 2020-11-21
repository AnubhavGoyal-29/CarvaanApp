package startup.carvaan.myapplication.ui.about;

import java.util.HashMap;
import java.util.Map;

public class PostModal {
    Map<String, Boolean> UsersLiking=new HashMap<>();
    Map<String,String> comments=new HashMap<>();
    String description;
    Map<String,String> files=new HashMap<>();
    String id;
    Boolean needAsistance,needFreelancer,needIntern;
    String title,type,url;

    public Map<String, Boolean> getUsersLiking() {
        return UsersLiking;
    }

    public void setUsersLiking(Map<String, Boolean> usersLiking) {
        UsersLiking = usersLiking;
    }

    public Map<String, String> getComments() {
        return comments;
    }

    public void setComments(Map<String, String> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getNeedAsistance() {
        return needAsistance;
    }

    public void setNeedAsistance(Boolean needAsistance) {
        this.needAsistance = needAsistance;
    }

    public Boolean getNeedFreelancer() {
        return needFreelancer;
    }

    public void setNeedFreelancer(Boolean needFreelancer) {
        this.needFreelancer = needFreelancer;
    }

    public Boolean getNeedIntern() {
        return needIntern;
    }

    public void setNeedIntern(Boolean needIntern) {
        this.needIntern = needIntern;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public PostModal() {
    }

    public PostModal(Map<String, Boolean> usersLiking, Map<String, String> comments, String description, Map<String, String> files, String id, Boolean needAsistance, Boolean needFreelancer, Boolean needIntern, String title, String type, String url) {
        UsersLiking = usersLiking;
        this.comments = comments;
        this.description = description;
        this.files = files;
        this.id = id;
        this.needAsistance = needAsistance;
        this.needFreelancer = needFreelancer;
        this.needIntern = needIntern;
        this.title = title;
        this.type = type;
        this.url = url;
    }
}
