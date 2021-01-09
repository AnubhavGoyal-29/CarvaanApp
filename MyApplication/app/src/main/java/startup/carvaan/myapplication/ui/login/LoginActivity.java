package startup.carvaan.myapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import startup.carvaan.myapplication.ProgressButton;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.mainActivity.MainActivity;


public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1 ;
    private EditText user_name;
    private EditText pass_word;
    private FirebaseAuth firebaseAuth;
    private View lo_gin;
    private ImageView googlesign;
    private TextView movetoregister;
    private FirebaseFirestore ff;
    public GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        movetoregister = findViewById(R.id.gotoregister);
        ff = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_name = findViewById(R.id.username);
        pass_word = findViewById(R.id.password);
        lo_gin = findViewById(R.id.loginbutton);
        TextView forgotPass = findViewById(R.id.forgotPass);
        ProgressButton progressButton = new ProgressButton(LoginActivity.this,lo_gin);
        progressButton.initialPhase("LOGIN",false);
        lo_gin.setClickable(true);
        lo_gin.setEnabled(true);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPassActivity.class));
            }
        });

        lo_gin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressButton.buttonsetEnabledFalse(true);
                lo_gin.setClickable(false);
                lo_gin.setEnabled(false);
                if(!TextUtils.isEmpty(user_name.getText().toString())&&!TextUtils.isEmpty(pass_word.getText().toString())) {
                    firebaseAuth.signInWithEmailAndPassword(user_name.getText().toString(), pass_word.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
                            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(newIntent);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressButton.buttonsetEnabledTrue("LOGIN");
                            lo_gin.setClickable(true);
                            lo_gin.setEnabled(true);

                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Empty credentials! Please make sure you don't leave username or/and password empty.", Toast.LENGTH_SHORT).show();
                    progressButton.buttonsetEnabledTrue("LOGIN");
                    lo_gin.setClickable(true);
                    lo_gin.setEnabled(true);

                }
            }

        });
        movetoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}