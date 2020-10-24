package startup.carvaan.myapplication.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gocashfree.cashfreesdk.CFPaymentService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import startup.carvaan.myapplication.R;

public class paymentActivity extends AppCompatActivity {

    private static final String TAG = "Anubhav";
    private EditText orderAmount;
    private TextView orderid;
    private Button payAmount;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ICloudFunction iCloudFunction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        iCloudFunction=RetrofitClient.getInstance().create(ICloudFunction.class);
        orderAmount=findViewById(R.id.amount);
        orderid=findViewById(R.id.order);
        payAmount=findViewById(R.id.pay);
        payAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePaymentRequest();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle  bundle = data.getExtras();
            if (bundle != null)
                for (String  key  :  bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                    }
                }
        }
    }

    private void makePaymentRequest() {
        String orderId= UUID.randomUUID().toString();
        Map<String ,String > map=new HashMap<>();
        map.put("orderId", String.valueOf(orderId));
        map.put("orderAmount", String.valueOf(orderAmount.getText()));
        compositeDisposable.add(iCloudFunction.getToken(orderId,orderAmount.getText().toString())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CashFreeToken>() {
                    @Override
                    public void accept(CashFreeToken cashFreeToken) throws Throwable {
                        if(cashFreeToken.getStatus().equals("OK")){
                            CFPaymentService.getCFPaymentServiceInstance().doPayment(paymentActivity.this,map,cashFreeToken.getCftoken(),"TEST");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(paymentActivity.this,""+throwable.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
        );
    }
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}