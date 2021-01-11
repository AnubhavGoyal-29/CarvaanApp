package startup.carvaan.myapplication.ads;

public class ad_model {
    String timer,url;

    public ad_model() {
    }

    public ad_model(String timer, String url) {
        this.timer = timer;
        this.url = url;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
