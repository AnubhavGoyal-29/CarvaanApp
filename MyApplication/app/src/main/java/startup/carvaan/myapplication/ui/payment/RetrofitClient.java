package startup.carvaan.myapplication.ui.payment;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    public static Retrofit getInstance(){
        return instance==null?instance=new Retrofit.Builder()
                .baseUrl("https://us-central1-payment-e03e8.cloudfunctions.net/widgets/")
                .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build():instance;
    }
}
