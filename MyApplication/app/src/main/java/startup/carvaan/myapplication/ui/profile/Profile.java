package startup.carvaan.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import startup.carvaan.myapplication.BuyCoin;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.earn.Addactivity;
import startup.carvaan.myapplication.ui.payment.paymentActivity;
import startup.carvaan.myapplication.ui.payment.payouts;
import startup.carvaan.myapplication.ui.user.User;

public class Profile extends AppCompatActivity {
    private TextView earned,winnins,added,redeemed;
    private Button coinEarn,redeemCoin,addCash,buyCoins;
    private User user;
    private FirebaseFirestore ff;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_profile);
       ff=FirebaseFirestore.getInstance();

        user=new User();
        buyCoins=findViewById(R.id.buyCoinsButton);
        buyCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, BuyCoin.class));
            }
        });
        earned=findViewById(R.id.coinsEarnedTextView);
        earned.setText(user.getEarned());
        winnins=findViewById(R.id.winningsTextView);
        winnins.setText(user.getWinnings());
        added=findViewById(R.id.cashAddedTextView);
        added.setText(user.getAdded());
        redeemed=findViewById(R.id.redeemCashTextView);
        redeemed.setText(user.getRedeemed());
        redeemCoin=findViewById(R.id.redeemButton);
//        redeemCoin.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), payouts.class));
//            }
//        });
       redeemCoin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(Profile.this, payouts.class));
           }
       });
        buyCoins=findViewById(R.id.buyCoinsButton);
        addCash=findViewById(R.id.addCashButton);
        addCash.setVisibility(View.GONE);
        addCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, paymentActivity.class));
            }
        });
        redeemCoin=findViewById(R.id.redeemButton);
        coinEarn=findViewById(R.id.earnCoinsButton);
        coinEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Addactivity.class));
            }
        });
        ff.collection("Users").document(user.getUser().getUid()).collection("CreditDetails")
                .document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                earned.setText("Coins earned   "+value.getString("earned"));
                winnins.setText("Winnings   "+value.getString("winnings"));
            }
        });
        ff.collection("Users").document(user.getUser().getUid()).collection("CreditDetails")
                .document("cash").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                added.setText("Cash added   "+value.getString("added"));
                redeemed.setText(" "+value.getString("redeemed"));
            }
        });
    }
}