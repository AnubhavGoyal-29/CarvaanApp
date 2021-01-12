package startup.carvaan.myapplication.ads;

public class ad_model {
    String addName;
    String addUrl;
    String addImage;
    String addDescription;
    String addPeopleWatched;
    String addReward;
    String addTime;
    String addAddress;

    public String getId() {
        return id;
    }

    String id;

    public ad_model() {
    }

    public String getAddName() {
        return addName;
    }

    public String getAddUrl() {
        return addUrl;
    }

    public String getAddImage() {
        return addImage;
    }

    public String getAddDescription() {
        return addDescription;
    }

    public String getAddPeopleWatched() {
        return addPeopleWatched;
    }

    public String getAddReward() {
        return addReward;
    }

    public String getAddTime() {
        return addTime;
    }

    public String getAddAddress() {
        return addAddress;
    }

    public ad_model(String id ,String addName, String addUrl, String addImage, String addDescription, String addPeopleWatched, String addReward, String addTime, String addAddress) {
        this.addName = addName;
        this.addUrl = addUrl;
        this.addImage = addImage;
        this.addDescription = addDescription;
        this.addPeopleWatched = addPeopleWatched;
        this.addReward = addReward;
        this.addTime = addTime;
        this.addAddress = addAddress;
        this.id=id;
    }
    void updateAddWatched(String a){

    }
}
