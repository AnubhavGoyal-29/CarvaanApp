package startup.carvaan.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

/**

 */
public class Profile extends Fragment {
    private TextView earned,winnins,added,redeemed;
    private Button coinEarn,redeemCoin,addCash,buyCoins;
    private User user;
    private FirebaseFirestore ff;
    public Profile()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ff=FirebaseFirestore.getInstance();

        user=new User();
        buyCoins=view.findViewById(R.id.buyCoinsButton);
        buyCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BuyCoin.class));
            }
        });
        earned=view.findViewById(R.id.coinsEarnedTextView);
        earned.setText(user.getEarned());
        winnins=view.findViewById(R.id.winningsTextView);
        winnins.setText(user.getWinnings());
        added=view.findViewById(R.id.cashAddedTextView);
        added.setText(user.getAdded());
        redeemed=view.findViewById(R.id.redeemCashTextView);
        redeemed.setText(user.getRedeemed());
        redeemCoin=view.findViewById(R.id.redeemButton);
        redeemCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), payouts.class));
            }
        });
        buyCoins=view.findViewById(R.id.buyCoinsButton);
        addCash=view.findViewById(R.id.addCashButton);
        addCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), paymentActivity.class));
            }
        });
        redeemCoin=view.findViewById(R.id.redeemButton);
        coinEarn=view.findViewById(R.id.earnCoinsButton);
        coinEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Addactivity.class));
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
        return view;
    }
}