package startup.carvaan.myapplication.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Button;

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

import retrofit2.http.HEAD;
import startup.carvaan.myapplication.ProgressButton;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.mainActivity.MainActivity;

public class RegisterActivity extends AppCompatActivity {
//<<<<<<< HEAD
//    private TextInputEditText user_name, otp, display_name;
//
//||||||| 6f7395b
//    private TextInputEditText user_name,otp,display_name;

//=======
//    private TextInputEditText user_name,otp,display_name;
//    private LinearLayout beforeEmailVerification;
//>>>>>>> 084d461537eece261a812c3873e1c4bfed894f99
    private TextInputLayout passwordText;
    private TextInputLayout confirmPassword;
    private FirebaseAuth firebaseAuth;
    private View regis_ter;
    private TextView movetologin;
    FirebaseFirestore ff;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser firebaseUser;

    FirebaseAuth mAuth;
    EditText mail;
    EditText pass;
    private Button sendOtp, confirmOtp;
//||||||| 6f7395b
//    private Button sendOtp,confirmOtp;
//=======
//>>>>>>> 084d461537eece261a812c3873e1c4bfed894f99

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //beforeEmailVerification=findViewById(R.id.beforeEmailVerification);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        // do your code here

        mail = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
//        otp = findViewById(R.id.otp);
//        confirmOtp = findViewById(R.id.confirmOtp);
        sendOtp = findViewById(R.id.sendOtp);

        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // Show the message task.getException()
                        }
                        else
                        {
                            // successfully account created
                            // now the AuthStateListener runs the onAuthStateChanged callback
                        }

                        // ...
                    }
                });

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            // NOTE: this Activity should get open only when the user is not signed in, otherwise
                            // the user will receive another verification email.
                            sendVerificationEmail();
                        } else {
                            // User is signed out

                        }
                        // ...
                    }
                };

            }

            private void sendVerificationEmail() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                assert user != null;
                user.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // email sent


                                    // after email is sent just logout the user and finish this activity
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    // email not sent, so display message and restart the activity or do whatever you wish to do

                                    //restart this activity
                                    overridePendingTransition(0, 0);
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());

                                }
                            }
                        });

            }
        });
//        confirmOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//
//||||||| 6f7395b
//        otp=findViewById(R.id.otp);
//        confirmOtp=findViewById(R.id.confirmOtp);
//        sendOtp=findViewById(R.id.sendOtp);
//        sendOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // SEND OTP CODE HERE
//
//            }
//        });
//        confirmOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String Otp=otp.getText().toString();
//                Double otp=Double.valueOf(Otp);
//            }
//        });















//=======
//>>>>>>> 084d461537eece261a812c3873e1c4bfed894f99
//
//        ff = FirebaseFirestore.getInstance();
//        firebaseAuth = FirebaseAuth.getInstance();
//        user_name = findViewById(R.id.username);
//        display_name = findViewById(R.id.displayName);
//        passwordText = findViewById(R.id.password);
//        confirmPassword = findViewById(R.id.confirmPassword);
//        regis_ter = findViewById(R.id.register1);
//        movetologin = findViewById(R.id.gotologin);
//
//        confirmPassword.setErrorEnabled(false);
//        confirmPassword.setError(null);
//
//        passwordText.setErrorEnabled(false);
//        passwordText.setError(null);
//
//        ProgressButton progressButton = new ProgressButton(RegisterActivity.this, regis_ter);
//        progressButton.initialPhase("REGISTER", true);
//        regis_ter.setClickable(false);
//        regis_ter.setEnabled(false);
//
//
//        passwordText.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String confPassword = Objects.requireNonNull(confirmPassword.getEditText()).getText().toString();
//                if (s.length() > 0 && confPassword.length() > 0) {
//                    if (!passwordText.getEditText().getText().toString().equals(confPassword)) {
//                        if (passwordText.getEditText() != null && confirmPassword.getEditText() != null) {
//
//                            confirmPassword.setErrorEnabled(true);
//                            confirmPassword.setError("Passwords don't match");
//
//                            passwordText.setErrorEnabled(true);
//                            passwordText.setError("Passwords don't match");
//                            if (progressButton.isEnabled()) {
//                                progressButton.buttonsetEnabledFalse(false);
//
//                                regis_ter.setClickable(false);
//                                regis_ter.setEnabled(false);
//                            }
//
//                        }
//                    } else {
//                        confirmPassword.setErrorEnabled(false);
//                        confirmPassword.setError(null);
//
//                        passwordText.setErrorEnabled(false);
//                        passwordText.setError(null);
//                        if (!progressButton.isEnabled()) {
//                            progressButton.buttonsetEnabledTrue("REGISTER");
//                            regis_ter.setClickable(true);
//                            regis_ter.setEnabled(true);
//                        }
//                    }
//
//                }
//
//
//            }
//        });


//        confirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String password = Objects.requireNonNull(passwordText.getEditText()).getText().toString();
//                if (editable.length() > 0 && password.length() > 0) {
//                    if (!confirmPassword.getEditText().getText().toString().equals(password)) {
//                        // give an error that password and confirm password not match
//                        if (confirmPassword.getEditText() != null && passwordText.getEditText() != null) {
//                            confirmPassword.setErrorEnabled(true);
//                            confirmPassword.setError("Passwords don't match");
//
//                            passwordText.setErrorEnabled(true);
//                            passwordText.setError("Passwords don't match");
//                            if (progressButton.isEnabled()) {
//                                progressButton.buttonsetEnabledFalse(false);
//                                regis_ter.setClickable(false);
//                                regis_ter.setEnabled(false);
//                            }
//                        }
//                        Log.i("TAG", "SORRY, PASSWORDS DONT MATCH!!!");
//                    } else {
//                        Log.i("TAG", "HURRAY, PASSWORDS MATCH!!");
//                        confirmPassword.setErrorEnabled(false);
//                        confirmPassword.setError(null);
//
//                        passwordText.setErrorEnabled(false);
//                        passwordText.setError(null);
//                        if (!progressButton.isEnabled()) {
//                            progressButton.buttonsetEnabledTrue("REGISTER");
//                            regis_ter.setClickable(true);
//                            regis_ter.setEnabled(true);
//                        }
//
//                    }
//
//                }
//            }
//        });

//        regis_ter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressButton.buttonsetEnabledFalse(true);
//                regis_ter.setClickable(false);
//                regis_ter.setEnabled(false);
//
//                if (TextUtils.isEmpty(user_name.getText().toString())) {
//                    Toast.makeText(RegisterActivity.this, "Username is empty! Please enter a valid username.", Toast.LENGTH_SHORT).show();
//                } else {
//                    firebaseAuth.createUserWithEmailAndPassword(user_name.getText().toString(), passwordText.getEditText().getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            firebaseUser = firebaseAuth.getCurrentUser();
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("Email", firebaseUser.getEmail());
//                            map.put("DisplayName", display_name.getText().toString());
//                            map.put("PhoneNumber", "phone Number");
//                            map.put("ImageUrl", "imageURL");
//                            ff.collection("Users").document(firebaseUser.getUid()).set(map);
//                            Map<String, String> coins = new HashMap<>();
//                            coins.put("earned", String.valueOf(100));
//                            coins.put("winnings", String.valueOf(0));
//                            Map<String, String> cash = new HashMap<>();
//                            cash.put("added", String.valueOf(0));
//                            cash.put("redeemed", String.valueOf(0));
//                            ff.collection("Users").
//                                    document(firebaseUser.getUid()).
//                                    collection("CreditDetails").
//                                    document("coins").set(coins).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    ff.collection("Users").
//                                            document(firebaseUser.getUid()).
//                                            collection("CreditDetails").
//                                            document("cash").set(cash).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
//
//                                        }
//                                    });
//                                }
//                            });
//
//                            finish();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            String error = e.getMessage();
//                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
////                        regis_ter.setEnabled(true);
//                            progressButton.buttonsetEnabledTrue("REGISTER");
//                            regis_ter.setClickable(true);
//                            regis_ter.setEnabled(true);
//                        }
//                    });
//                }
//
//            }
//
//        });
//
//        movetologin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            }
//        });

    }
}