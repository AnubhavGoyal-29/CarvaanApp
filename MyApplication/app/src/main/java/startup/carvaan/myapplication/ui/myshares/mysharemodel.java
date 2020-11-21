package startup.carvaan.myapplication.ui.myshares;

public class mysharemodel {
    private String holdings,lastBuyingPrice;

    public String getHoldings() {
        return holdings;
    }

    public void setHoldings(String holdings) {
        this.holdings = holdings;
    }

    public String getLastBuyingPrice() {
        return lastBuyingPrice;
    }

    public void setLastBuyingPrice(String lastBuyingPrice) {
        this.lastBuyingPrice = lastBuyingPrice;
    }

    public mysharemodel(String holdings, String lastBuyingPrice) {
        this.holdings = holdings;
        this.lastBuyingPrice = lastBuyingPrice;
    }

    public mysharemodel() {
    }
}
