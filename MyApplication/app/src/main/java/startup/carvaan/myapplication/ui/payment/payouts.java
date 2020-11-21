package startup.carvaan.myapplication.ui.payment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import startup.carvaan.myapplication.R;

public class payouts extends AppCompatActivity {
    FirebaseFirestore ff=FirebaseFirestore.getInstance();
    private EditText coins;
    private TextView convertedMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payouts);
        final String[] rci = new String[1];
        coins=findViewById(R.id.coins);
        convertedMoney=findViewById(R.id.convertedMoney);
        ff.collection("Coins").document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                rci[0] =value.getString("value");
            }
        });
        coins.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    convertedMoney.setText("0");

                }
                else{
                    int a=Integer.valueOf(coins.getText().toString());
                    convertedMoney.setText("Rs"+String.valueOf(a*Double.valueOf(rci[0])));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}