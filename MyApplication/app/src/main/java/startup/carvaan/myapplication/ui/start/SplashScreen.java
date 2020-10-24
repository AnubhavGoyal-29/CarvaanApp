package startup.carvaan.myapplication.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.login.LoginActivity;
import startup.carvaan.myapplication.ui.mainActivity.MainActivity;
import startup.carvaan.myapplication.ui.user.User;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ImageView app_logo;
    TextView app_motto, app_name;
    User user=new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        app_logo = findViewById(R.id.applogo);
        app_motto = findViewById(R.id.appmotto);
        app_name = findViewById(R.id.appname);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotonextpage();
            }
        }, 1000);

    }
    private void gotonextpage() {
        if(user.getUser()!=null){
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
        }
        else{
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
        }
    }
}