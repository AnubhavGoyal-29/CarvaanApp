package startup.carvaan.myapplication.ui.myshares;

import java.util.HashMap;
import java.util.Map;

public class mysharemodel {
    private String id,name,holdings;
    Map<String ,String> holdingsatprice=new HashMap<>();

    public mysharemodel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public Map<String, String> getHoldingsatprice() {
        return holdingsatprice;
    }

    public void setHoldingsatprice(Map<String, String> holdingsatprice) {
        this.holdingsatprice = holdingsatprice;
    }

    public mysharemodel(String id, String name, String holdings, Map<String, String> holdingsatprice) {
        this.id = id;
        this.name = name;
        this.holdings = holdings;
        this.holdingsatprice = holdingsatprice;
    }
}
