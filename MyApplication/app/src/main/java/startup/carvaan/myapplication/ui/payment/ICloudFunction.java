package startup.carvaan.myapplication.ui.payment;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICloudFunction {
    @GET("token")
    Observable<CashFreeToken> getToken(@Query("orderId") String orderId, @Query("orderAmount") String orderAmount);
}
