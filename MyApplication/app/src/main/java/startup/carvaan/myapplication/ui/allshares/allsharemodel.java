package startup.carvaan.myapplication.ui.allshares;

public class allsharemodel {
    private String companyname,description,growth,id,introvideourl,peopleinvested;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getIntrovideourl() {
        return introvideourl;
    }

    public void setIntrovideourl(String introvideourl) {
        this.introvideourl = introvideourl;
    }

    public String getPeopleinvested() {
        return peopleinvested;
    }

    public void setPeopleinvested(String peopleinvested) {
        this.peopleinvested = peopleinvested;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }


    public allsharemodel(String companyname, String description, String growth, String id, String introvideourl, String peopleinvested) {
        this.companyname = companyname;
        this.description = description;
        this.growth = growth;
        this.id = id;
        this.introvideourl = introvideourl;
        this.peopleinvested = peopleinvested;
    }

    public allsharemodel() {
    }
}
