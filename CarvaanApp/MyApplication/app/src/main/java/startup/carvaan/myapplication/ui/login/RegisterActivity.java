package startup.carvaan.myapplication.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import startup.carvaan.myapplication.ProgressButton;
import startup.carvaan.myapplication.R;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "bla";
    private TextInputEditText user_name, display_name;

    private TextInputLayout passwordText;
    private TextInputLayout confirmPassword;
    private FirebaseAuth firebaseAuth;
    private View regis_ter;
    private TextView movetologin;
    FirebaseFirestore ff;
    FirebaseUser firebaseUser;
    private LinearLayout bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        ff = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_name = findViewById(R.id.username);
        display_name = findViewById(R.id.displayName);
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
                        if (passwordText.getEditText().getText().toString().trim().length() >= 6 && passwordText.getEditText().getText().toString().trim().length() <= 15) {
                            confirmPassword.setErrorEnabled(false);
                            confirmPassword.setError(null);

                            passwordText.setErrorEnabled(false);
                            passwordText.setError(null);
                            if (!progressButton.isEnabled()) {
                                progressButton.buttonsetEnabledTrue("REGISTER");
                                regis_ter.setClickable(true);
                                regis_ter.setEnabled(true);
                            }
                        } else {
                            passwordText.setErrorEnabled(true);
                            passwordText.setError("Password should contain 6 to 15 characters");
                            confirmPassword.setErrorEnabled(true);
                            confirmPassword.setError("Password should contain 6 to 15 characters");
                            if (progressButton.isEnabled()) {
                                progressButton.buttonsetEnabledFalse(false);
                                regis_ter.setClickable(false);
                                regis_ter.setEnabled(false);

                            }

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
                        if (confirmPassword.getEditText().getText().toString().trim().length()>=6 && confirmPassword.getEditText().getText().toString().trim().length() <= 15) {
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
                         else {
                            passwordText.setErrorEnabled(true);
                            passwordText.setError("Password should contain 6-15 characters");
                            confirmPassword.setErrorEnabled(true);
                            confirmPassword.setError("Password should contain 6-15 characters");
                            if (progressButton.isEnabled()) {
                                progressButton.buttonsetEnabledFalse(false);
                                regis_ter.setClickable(false);
                                regis_ter.setEnabled(false);
                            }
//
                        }

                    }

//                    if (password.trim().length()>=6)

                }
            }
        });

        regis_ter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressButton.isEnabled()) {
                    progressButton.buttonsetEnabledFalse(true);
                    regis_ter.setClickable(false);
                    regis_ter.setEnabled(false);
                }

                if (TextUtils.isEmpty(user_name.getText().toString()) || TextUtils.isEmpty(display_name.getText().toString()) || TextUtils.isEmpty(passwordText.getEditText().getText().toString()) || TextUtils.isEmpty(confirmPassword.getEditText().getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Don't leave any fields empty.", Toast.LENGTH_SHORT).show();
                    if (!progressButton.isEnabled()) {
                        progressButton.buttonsetEnabledTrue("REGISTER");
                        regis_ter.setClickable(true);
                        regis_ter.setEnabled(true);
                    }
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(user_name.getText().toString(), passwordText.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = authResult.getUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "verification email successfully sent", Toast.LENGTH_LONG).show();
                                }
                            });
                            firebaseUser = firebaseAuth.getCurrentUser();
                            Map<String, Object> map = new HashMap<>();
                            map.put("Email", firebaseUser.getEmail());
                            map.put("DisplayName", display_name.getText().toString());
                            map.put("PhoneNumber", "phone Number");
                            map.put("ImageUrl", "imageURL");
                            ff.collection("Users").document(firebaseUser.getUid()).set(map);
                            Map<String, String> coins = new HashMap<>();
                            coins.put("earned", String.valueOf(100));
                            coins.put("winnings", String.valueOf(0));
                            Map<String, String> cash = new HashMap<>();
                            cash.put("added", String.valueOf(0));
                            cash.put("redeemed", String.valueOf(0));
                            ff.collection("Users").
                                    document(firebaseUser.getUid()).
                                    collection("CreditDetails").
                                    document("coins").set(coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    ff.collection("Users").
                                            document(firebaseUser.getUid()).
                                            collection("CreditDetails").
                                            document("cash").set(cash).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class).putExtra("email", user_name.getText().toString()).putExtra("password", passwordText.getEditText().getText().toString()));
                                            Toast.makeText(RegisterActivity.this, "First verify your email and then login", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            });

                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
                            progressButton.buttonsetEnabledTrue("REGISTER");
                            regis_ter.setClickable(true);
                            regis_ter.setEnabled(true);
                        }
                    });
                }
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