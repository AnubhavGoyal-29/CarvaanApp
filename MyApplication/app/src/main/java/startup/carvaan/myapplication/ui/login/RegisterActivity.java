package startup.carvaan.myapplication.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.ProgressButton;
import startup.carvaan.myapplication.ui.mainActivity.MainActivity;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText user_name;

    private TextInputLayout passwordText;
    private TextInputLayout confirmPassword;
    private FirebaseAuth firebaseAuth;
    private View regis_ter;
    private TextView movetologin;

    FirebaseFirestore ff;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setElevation(0);
        ff = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_name = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        regis_ter = findViewById(R.id.register1);
        movetologin = findViewById(R.id.gotologin);

        confirmPassword.setErrorEnabled(false);
        confirmPassword.setError(null);

        passwordText.setErrorEnabled(false);
        passwordText.setError(null);

        ProgressButton progressButton = new ProgressButton(RegisterActivity.this, regis_ter);
        progressButton.initialPhase("REGISTER", true);
        regis_ter.setClickable(false);
        regis_ter.setEnabled(false);


        passwordText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String confPassword = Objects.requireNonNull(confirmPassword.getEditText()).getText().toString();
                if (s.length() > 0 && confPassword.length() > 0) {
                    if (!passwordText.getEditText().getText().toString().equals(confPassword)) {
                        if (passwordText.getEditText() != null && confirmPassword.getEditText() != null) {

                            confirmPassword.setErrorEnabled(true);
                            confirmPassword.setError("Passwords don't match");

                            passwordText.setErrorEnabled(true);
                            passwordText.setError("Passwords don't match");
                            if (progressButton.isEnabled()) {
                                progressButton.buttonsetEnabledFalse(false);

                                regis_ter.setClickable(false);
                                regis_ter.setEnabled(false);
                            }

                        }
                    } else {
                        confirmPassword.setErrorEnabled(false);
                        confirmPassword.setError(null);

                        passwordText.setErrorEnabled(false);
                        passwordText.setError(null);
                        if (!progressButton.isEnabled()) {
                            progressButton.buttonsetEnabledTrue("REGISTER");
                            regis_ter.setClickable(true);
                            regis_ter.setEnabled(true);
                        }
                    }

                }


            }
        });


        confirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String password = Objects.requireNonNull(passwordText.getEditText()).getText().toString();
                if (editable.length() > 0 && password.length() > 0) {
                    if (!confirmPassword.getEditText().getText().toString().equals(password)) {
                        // give an error that password and confirm password not match
                        if (confirmPassword.getEditText() != null && passwordText.getEditText() != null) {
                            confirmPassword.setErrorEnabled(true);
                            confirmPassword.setError("Passwords don't match");

                            passwordText.setErrorEnabled(true);
                            passwordText.setError("Passwords don't match");
                            if (progressButton.isEnabled()) {
                                progressButton.buttonsetEnabledFalse(false);
                                regis_ter.setClickable(false);
                                regis_ter.setEnabled(false);
                            }
                        }
                        Log.i("TAG", "SORRY, PASSWORDS DONT MATCH!!!");
                    } else {
                        Log.i("TAG", "HURRAY, PASSWORDS MATCH!!");
                        confirmPassword.setErrorEnabled(false);
                        confirmPassword.setError(null);

                        passwordText.setErrorEnabled(false);
                        passwordText.setError(null);
                        if (!progressButton.isEnabled()) {
                            progressButton.buttonsetEnabledTrue("REGISTER");
                            regis_ter.setClickable(true);
                            regis_ter.setEnabled(true);
                        }

                    }

                }
            }
        });

        regis_ter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressButton.buttonsetEnabledFalse(true);
                regis_ter.setClickable(false);
                regis_ter.setEnabled(false);

                if (TextUtils.isEmpty(user_name.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Username is empty! Please enter a valid username.", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(user_name.getText().toString(), passwordText.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            Map<String, Object> map = new HashMap<>();
                            map.put("Email", firebaseUser.getEmail());
                            map.put("PhoneNumber", "phone Number");
                            map.put("ImageUrl", "imageURL");
                            map.put("Credits", "100");
                            ff.collection("Users").document(firebaseUser.getUid()).set(map);
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
//                        regis_ter.setEnabled(true);
                            progressButton.buttonsetEnabledTrue("REGISTER");
                            regis_ter.setClickable(true);
                            regis_ter.setEnabled(true);
                        }
                    });
                }

                /////////////////////////////////////////////////////////////////////////
            }

        });

        movetologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }
}

