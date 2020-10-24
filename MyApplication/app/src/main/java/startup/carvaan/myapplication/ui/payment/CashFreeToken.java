package startup.carvaan.myapplication.ui.payment;

public class CashFreeToken {
    private String cftoken,message,status;

    public String getCftoken() {
        return cftoken;
    }

    public void setCftoken(String cftoken) {
        this.cftoken = cftoken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CashFreeToken() {
    }

    public CashFreeToken(String cftoken, String message, String status) {
        this.cftoken = cftoken;
        this.message = message;
        this.status = status;
    }
}
