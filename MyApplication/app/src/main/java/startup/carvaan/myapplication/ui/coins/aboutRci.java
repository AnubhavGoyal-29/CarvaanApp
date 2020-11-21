package startup.carvaan.myapplication.ui.coins;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import startup.carvaan.myapplication.R;

public class aboutRci extends AppCompatActivity {
    private TextView coinsexchnge;
    private EditText coins,amount;
    private Button redeem,buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rci);
        coinsexchnge=findViewById(R.id.coinexchnge);
        coins=findViewById(R.id.conversioncoin);
        amount=findViewById(R.id.conversionrs);
        redeem=findViewById(R.id.redeem);
        buy=findViewById(R.id.buycoin);
    }
}