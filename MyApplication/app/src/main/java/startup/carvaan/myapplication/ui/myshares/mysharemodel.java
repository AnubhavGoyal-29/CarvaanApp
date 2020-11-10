package startup.carvaan.myapplication.ui.myshares;

import java.util.Map;

public class mysharemodel {
    private String holdings;
    Map<Object,Object>priceHoldings;

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public Map<Object, Object> getPriceHoldings() {
        return priceHoldings;
    }

    public void setPriceHoldings(Map<Object, Object> priceHoldings) {
        this.priceHoldings = priceHoldings;
    }

    public mysharemodel(String holdings, Map<Object, Object> priceHoldings) {
        this.holdings = holdings;
        this.priceHoldings = priceHoldings;
    }

    public mysharemodel() {
    }
}
