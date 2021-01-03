package startup.carvaan.myapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.mainActivity.MainActivity;
import startup.carvaan.myapplication.ui.user.User;

public class verificationactivity extends AppCompatActivity {
    Button verify;
    User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationactivity);
        verify=findViewById(R.id.verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getUser().isEmailVerified()){
                    startActivity(new Intent(verificationactivity.this, MainActivity.class));
                }
                else Toast.makeText(verificationactivity.this, "please verify your email first ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}