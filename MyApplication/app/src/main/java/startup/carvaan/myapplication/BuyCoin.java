package startup.carvaan.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import startup.carvaan.myapplication.ui.coins.coinModal;
import startup.carvaan.myapplication.ui.user.User;

public class BuyCoin extends AppCompatActivity {
    startup.carvaan.myapplication.ui.coins.coinModal coinModal = new coinModal();
    User user = new User();
    private TextView convertedCoins;
    private EditText rupees;
    private Button buyNow, buy120, buy260, buy500, buy775, buy1000, buy1400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_coin);
    }
}