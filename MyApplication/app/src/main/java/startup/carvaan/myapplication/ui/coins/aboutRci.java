package startup.carvaan.myapplication.ui.coins;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import startup.carvaan.myapplication.BuyCoin;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.payment.payouts;

public class
aboutRci extends AppCompatActivity {
    private TextView coinvalue,coinText,rsText;
    private EditText coins,amount;
    private Button redeem,buy;
    FirebaseFirestore ff=FirebaseFirestore.getInstance();
    coinModal coinModal=new coinModal();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rci);
        coinText=findViewById(R.id.coinsText);
        rsText=findViewById(R.id.rsText);
        coinvalue=findViewById(R.id.coinvalue);
        ff.collection("Coins").document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                coinvalue.setText(value.getString("value"));
            }
        });
        coins=findViewById(R.id.conversioncoin);
        amount=findViewById(R.id.conversionrs);
        redeem=findViewById(R.id.redeem);
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(aboutRci.this, payouts.class));
            }
        });
        buy=findViewById(R.id.buycoin);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(aboutRci.this, BuyCoin.class));
            }
        });
        coins.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0){
                    rsText.setText(String.valueOf("Rs"+Double.valueOf(coins.getText().toString())*Double.valueOf(coinModal.getValue())));
                }
                else{
                    rsText.setText("Rs 0");
                }
            }
        });
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()!=0){
                    coinText.setText(String.valueOf(Double.valueOf(amount.getText().toString())/Double.valueOf(coinModal.getValue()))+"coins");
                }
                else{
                    coinText.setText("0 coins");
                }
            }
        });
    }
}