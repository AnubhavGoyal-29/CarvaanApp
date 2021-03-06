package startup.carvaan.myapplication.ui.payment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import startup.carvaan.myapplication.ui.user.User;

public class payouts extends AppCompatActivity {
    FirebaseFirestore ff=FirebaseFirestore.getInstance();
    private EditText coins,upi_id,bank_account,ifsc_code,account_holder_name,phonenumber,upiname;
    private TextView convertedMoney;
    private Button withdrawl;
    User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payouts);
        getSupportActionBar().setTitle("Withdrawls");
        getSupportActionBar().setElevation(0);
        final String[] rci = new String[1];
        convertedMoney=findViewById(R.id.convertedMoney);
        coins=findViewById(R.id.coins);
        upi_id=findViewById(R.id.upi_id);
        upiname=findViewById(R.id.upiname);
        phonenumber=findViewById(R.id.phonenumber);
//        bank_account=findViewById(R.id.bank_account);
//        ifsc_code=findViewById(R.id.ifsc_code);
//        account_holder_name=findViewById(R.id.account_holder_name);
        withdrawl=findViewById(R.id.withdrawButton);
        withdrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.valueOf(user.getWinnings())<750){
                    Toast.makeText(payouts.this,"you should have minimum 750 coins before redeem",Toast.LENGTH_LONG).show();
                }
                ProgressDialog progressDialog = new ProgressDialog(payouts.this);
                progressDialog.setMessage("Processing.."); // Setting Message
                progressDialog.setTitle("please wait while we are checking your information"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCanceledOnTouchOutside(false);

                progressDialog.setCancelable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                        if(coins.getText().toString()==null||coins.getText().length()==0){
                            Toast.makeText(payouts.this,"input some coins to redeem",Toast.LENGTH_LONG).show();
                        }
                        else{
                            if(Double.valueOf(coins.getText().toString())<=Double.valueOf(user.getWinnings())) {
                                if((upi_id.getText().toString().length()==0)||phonenumber.getText().toString().length()==0||upiname.getText().toString().length()==0) {
                                    Toast.makeText(payouts.this,"please enter your details",Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Map<String, String> withDrawDetails = new HashMap<>();
                                    withDrawDetails.put("username",upiname.getText().toString());
                                    withDrawDetails.put("upi_id",upi_id.getText().toString());
                                    withDrawDetails.put("phoneNumber",phonenumber.getText().toString());
                                    ff.collection("Withdrawls").document().set(withDrawDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(payouts.this, "withdrawl is successful, will be reflected in 2-3 days", Toast.LENGTH_LONG).show();
                                            user.addRedeem(Double.valueOf(convertedMoney.getText().toString()));
                                            user.removeWinnings(Double.valueOf(coins.getText().toString()
                                            ));
                                            startActivity(new Intent(payouts.this, MainActivity.class));
                                        }
                                    });
                                }
                            }
                            else{
                                Toast.makeText(payouts.this,"you do not have enough coins to redeem",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                },2000);

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
                    convertedMoney.setText("Rs 0");

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