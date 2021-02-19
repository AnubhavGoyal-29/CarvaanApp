package startup.carvaan.myapplication.ui.login;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.user.User;

public class verificationactivity extends AppCompatActivity {
    Button verify;
    User user=new User();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationactivity);
    }
}