package startup.carvaan.myapplication.ui.myshares;

public class mysharemodel {
    private String holdings,priceHoldings;

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public String getPriceHoldings() {
        return priceHoldings;
    }

    public void setPriceHoldings(String priceHoldings) {
        this.priceHoldings = priceHoldings;
    }

    public mysharemodel(String holdings, String priceHoldings) {
        this.holdings = holdings;
        this.priceHoldings = priceHoldings;
    }

    public mysharemodel() {
    }
}
