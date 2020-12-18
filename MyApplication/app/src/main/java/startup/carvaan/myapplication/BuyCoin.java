package startup.carvaan.myapplication;

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

import androidx.appcompat.app.AppCompatActivity;

import startup.carvaan.myapplication.ui.coins.coinModal;
import startup.carvaan.myapplication.ui.mainActivity.MainActivity;
import startup.carvaan.myapplication.ui.user.User;

public class BuyCoin extends AppCompatActivity {
    coinModal coinModal=new coinModal();
    User user = new User();
    private TextView convertedCoins;
    private EditText rupees;
    private Button buyNow,buy120,buy260,buy500,buy775,buy1000,buy1400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_coin);
        getSupportActionBar().setTitle("Buy Some Coins");
        getSupportActionBar().setElevation(0);
        buy120=findViewById(R.id.BuyNow120);
        buy260=findViewById(R.id.BuyNow260);
        buy500=findViewById(R.id.BuyNow500);
        buy775=findViewById(R.id.BuyNow775);
        buy1000=findViewById(R.id.BuyNow1000);
        buy1400=findViewById(R.id.BuyNow1400);
        buy120.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(BuyCoin.this);
                ProgDialogue progDialogue = new ProgDialogue("message","title",false,Buy.this,progressDialog);
                progDialogue.showDialogue(progressDialog);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                        if(Double.valueOf(user.getAdded())>=Double.valueOf(120)){
                            user.addEarned(Double.valueOf(200));
                            user.removeCash(Double.valueOf(120));
                            startActivity(new Intent(BuyCoin.this, MainActivity.class));
                            Toast.makeText(BuyCoin.this,"Transaction successful",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(BuyCoin.this,"You do not have 120 rupees add now",Toast.LENGTH_LONG).show();
                        }

                    }
                },2000);

            }
        });
        buy260.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(BuyCoin.this);
                ProgDialogue progDialogue = new ProgDialogue("message","title",false,BuyCoin.this,progressDialog);
                progDialogue.showDialogue(progressDialog);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();
                        if(Double.valueOf(user.getAdded())>=Double.valueOf(260)){
                            user.addEarned(Double.valueOf(500));
                            user.removeCash(Double.valueOf(260));
                            startActivity(new Intent(BuyCoin.this, MainActivity.class));
                            Toast.makeText(BuyCoin.this,"Transaction successful",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(BuyCoin.this,"You do not have 260 rupees add now",Toast.LENGTH_LONG).show();
                        }

                    }
                },2000);

            }
        });
        buy500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(BuyCoin.this);
                ProgDialogue progDialogue = new ProgDialogue("message","title",false,BuyCoin.this,progressDialog);
                progDialogue.showDialogue(progressDialog);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();

                        if(Double.valueOf(user.getAdded())>=Double.valueOf(500)){
                            user.addEarned(Double.valueOf(1000));
                            user.removeCash(Double.valueOf(500));
                            startActivity(new Intent(BuyCoin.this, MainActivity.class));
                            Toast.makeText(BuyCoin.this,"Transaction successful",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(BuyCoin.this,"You do not have 500 rupees add now",Toast.LENGTH_LONG).show();
                        }

                    }
                },2000);

            }
        });
        buy775.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(BuyCoin.this);
                ProgDialogue progDialogue = new ProgDialogue("message","title",false,BuyCoin.this,progressDialog);
                progDialogue.showDialogue(progressDialog);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();

                        if(Double.valueOf(user.getAdded())>=Double.valueOf(775)){
                            user.addEarned(Double.valueOf(1000));
                            user.removeCash(Double.valueOf(775));
                            startActivity(new Intent(BuyCoin.this, MainActivity.class));
                            Toast.makeText(BuyCoin.this,"Transaction successful",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(BuyCoin.this,"You do not have 775 rupees add now",Toast.LENGTH_LONG).show();
                        }

                    }
                },2000);


            }
        });
        buy1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(BuyCoin.this);
                ProgDialogue progDialogue = new ProgDialogue("message","title",false,BuyCoin.this,progressDialog);
                progDialogue.showDialogue(progressDialog);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();


                        if(Double.valueOf(user.getAdded())>=Double.valueOf(1000)){
                            user.addEarned(Double.valueOf(2100));
                            user.removeCash(Double.valueOf(1000));
                            startActivity(new Intent(BuyCoin.this, MainActivity.class));
                            Toast.makeText(BuyCoin.this,"Transaction successful",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(BuyCoin.this,"You do not have 1000 rupees add now",Toast.LENGTH_LONG).show();
                        }

                    }
                },2000);

            }
        });
        buy1400.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(BuyCoin.this);
                ProgDialogue progDialogue = new ProgDialogue("message","title",false,BuyCoin.this,progressDialog);
                progDialogue.showDialogue(progressDialog);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog.dismiss();


                        if(Double.valueOf(user.getAdded())>=Double.valueOf(1400)){
                            user.addEarned(Double.valueOf(3000));
                            user.removeCash(Double.valueOf(1400));
                            startActivity(new Intent(BuyCoin.this, MainActivity.class));
                            Toast.makeText(BuyCoin.this,"Transaction successful",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(BuyCoin.this,"You do not have 1400 rupees add now",Toast.LENGTH_LONG).show();
                        }

                    }
                },2000);


            }
        });
        rupees=findViewById(R.id.rupees);
        convertedCoins=findViewById(R.id.conversioncoin);
        buyNow=findViewById(R.id.buyNow);
        rupees.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){

                }
                else{
                    convertedCoins.setText(String.valueOf(Integer.valueOf(Integer.valueOf(rupees.getText().toString()))/Double.valueOf(coinModal.getValue())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.valueOf(convertedCoins.getText().toString())!=0){
                    if(Double.valueOf(user.getAdded())>=Double.valueOf(rupees.getText().toString())) {
                        user.removeCash(Double.valueOf(rupees.getText().toString()));
                        user.addEarned((Double) (Integer.valueOf(Integer.valueOf(rupees.getText().toString())) / Double.valueOf(coinModal.getValue())));
                        Toast.makeText(BuyCoin.this,"done buying",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(BuyCoin.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(BuyCoin.this,"you do not have enough cash",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}