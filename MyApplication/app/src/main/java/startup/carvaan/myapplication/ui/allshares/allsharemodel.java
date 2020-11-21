package startup.carvaan.myapplication.ui.allshares;

public class allsharemodel {
    private String companyname,description,growth,id,introvideourl,peopleinvested,logoUrl,tag;

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public allsharemodel(String companyname, String description, String growth, String id, String introvideourl, String peopleinvested, String logoUrl, String tag) {
        this.companyname = companyname;
        this.description = description;
        this.growth = growth;
        this.id = id;
        this.introvideourl = introvideourl;
        this.peopleinvested = peopleinvested;
        this.logoUrl = logoUrl;
        this.tag = tag;
    }

    public allsharemodel() {
    }
}
