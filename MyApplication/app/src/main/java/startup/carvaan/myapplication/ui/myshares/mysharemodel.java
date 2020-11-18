package startup.carvaan.myapplication.ui.myshares;

import java.util.Map;

public class mysharemodel {
    private String holdings;
    Map<String,String>priceHoldings;

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public Map<String, String> getPriceHoldings() {
        return priceHoldings;
    }

    public void setPriceHoldings(Map<String, String> priceHoldings) {
        this.priceHoldings = priceHoldings;
    }

    public mysharemodel(String holdings, Map<String, String> priceHoldings) {
        this.holdings = holdings;
        this.priceHoldings = priceHoldings;
    }

    public mysharemodel() {
    }
}
