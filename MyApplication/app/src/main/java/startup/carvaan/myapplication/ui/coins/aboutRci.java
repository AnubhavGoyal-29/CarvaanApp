package startup.carvaan.myapplication.ui.coins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private TextView coinvalue,seetext;
    private Button redeem,buy;

    private LinearLayout butsee,rcimore;
    FirebaseFirestore ff=FirebaseFirestore.getInstance();
    coinModal coinModal=new coinModal();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rci);
        coinvalue=findViewById(R.id.coinvalue);
        butsee=findViewById(R.id.butsee);
        rcimore=findViewById(R.id.rcimore);
        seetext=findViewById(R.id.seetext);

        seetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcimore.setVisibility(View.VISIBLE);
                butsee.setVisibility(View.GONE);
            }
        });
        ff.collection("Coins").document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                coinvalue.setText(value.getString("value"));
            }
        });
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
    }
}