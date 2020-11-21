package startup.carvaan.myapplication.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.mainActivity.MainActivity;

public class payouts extends AppCompatActivity {
    FirebaseFirestore ff=FirebaseFirestore.getInstance();
    private EditText coins,upi_id,bank_account,ifsc_code,account_holder_name;
    private TextView convertedMoney;
    private Button withdrawl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payouts);
        final String[] rci = new String[1];
        convertedMoney=findViewById(R.id.convertedMoney);
        coins=findViewById(R.id.coins);
        upi_id=findViewById(R.id.upi_id);
        bank_account=findViewById(R.id.bank_account);
        ifsc_code=findViewById(R.id.ifsc_code);
        account_holder_name=findViewById(R.id.account_holder_name);
        withdrawl=findViewById(R.id.withdrawButton);
        withdrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String , String >withDrawDetails=new HashMap<>();
                withDrawDetails.put("amount",convertedMoney.getText().toString());
                if(upi_id.getText().toString()==null){
                    withDrawDetails.put("upi_id",null);
                    withDrawDetails.put("bank_account",bank_account.getText().toString());
                    withDrawDetails.put("ifsc_code",ifsc_code.getText().toString());
                    withDrawDetails.put("account_holder_name",account_holder_name.getText().toString());
                }
                if(upi_id.getText().toString()!=null){
                    withDrawDetails.put("upi_id",upi_id.getText().toString());
                    withDrawDetails.put("bank_account",bank_account.getText().toString());
                    withDrawDetails.put("ifsc_code",ifsc_code.getText().toString());
                    withDrawDetails.put("account_holder_name",account_holder_name.getText().toString());
                }
                ff.collection("Withdrawls").document().set(withDrawDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(payouts.this,"withdrawl is successful, will be reflected in 2-3 days",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(payouts.this, MainActivity.class));
                    }
                });
            }
        });
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
                    convertedMoney.setText(String.valueOf(a*Double.valueOf(rci[0])));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}