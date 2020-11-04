package startup.carvaan.myapplication.ui.about;

public class PostModal {
    String name,type,url;

    public PostModal(String name, String type, String url) {
        this.name = name;
        this.type = type;
        this.url = url;
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
