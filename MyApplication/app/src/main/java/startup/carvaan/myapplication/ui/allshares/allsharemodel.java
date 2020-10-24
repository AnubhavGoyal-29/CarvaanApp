package startup.carvaan.myapplication.ui.allshares;

import java.lang.reflect.Array;

public class allsharemodel {
    private String description,id,growth,nameintrovideourl;
    private Array arr;

    public allsharemodel() {
    }
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

    public String getNameintrovideourl() {
        return nameintrovideourl;
    }

    public void setNameintrovideourl(String nameintrovideourl) {
        this.nameintrovideourl = nameintrovideourl;
    }

    public Array getArr() {
        return arr;
    }

    public void setArr(Array arr) {
        this.arr = arr;
    }

    public allsharemodel(String description, String id, String growth, String nameintrovideourl, Array arr) {
        this.description = description;
        this.id = id;
        this.growth = growth;
        this.nameintrovideourl = nameintrovideourl;
        this.arr = arr;
    }
}
