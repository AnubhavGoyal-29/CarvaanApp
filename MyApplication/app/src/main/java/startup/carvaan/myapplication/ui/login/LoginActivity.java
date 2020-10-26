package startup.carvaan.myapplication.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import startup.carvaan.myapplication.ui.mainActivity.MainActivity;
import startup.carvaan.myapplication.R;


public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1 ;
    private EditText user_name;
    private EditText pass_word;
    private FirebaseAuth firebaseAuth;
    private Button lo_gin;
    private ImageView googlesign;
    private TextView movetoregister;
    private FirebaseFirestore ff;
    public GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        movetoregister = findViewById(R.id.gotoregister);
        ff = FirebaseFirestore.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        user_name = findViewById(R.id.username);
        pass_word = findViewById(R.id.password);
        lo_gin = findViewById(R.id.loginbutton);
        TextView forgotPass = findViewById(R.id.forgotPass);
        googlesign = findViewById(R.id.googlelogin);

        lo_gin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(user_name.getText().toString() + "@gmail.com", pass_word.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(newIntent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
        movetoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this,googleSignInOptions);
        googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsignin();
            }
        });

    }
    private void gsignin()
    {
        Intent gintent = googleSignInClient.getSignInIntent();
        startActivityForResult(gintent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            Log.i("TAG", "MATCHES AF");
            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"","Please Wait");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())

                        {
                            Log.i("TAG", "SUCCESSFUL 1");
                            Toast.makeText(LoginActivity.this,"LOGIN SUCCESSFUL",Toast.LENGTH_LONG).show();
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            ff.collection("Users").document(user.getUid()).collection("Credits").document("Credits").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Log.i("TAG", "INSIDE ON COMPLETE");
                                    DocumentSnapshot documentSnapshot=task.getResult();
                                    if(!documentSnapshot.exists()){
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("Email",user.getEmail());
                                        map.put("PhoneNumber","phone Number");
                                        map.put("ImageUrl","imageURL");
                                        ff.collection("Users").document(user.getUid())
                                                .collection("PersonalInformation")
                                                .document("personalInformation").set(map);
                                        Map<String,Object> credits=new HashMap<>();
                                        credits.put("credits","100");
                                        Log.i("TAG", "MAYBE TOWARDS ERROR IG");
                                        ff.collection("Users").document(user.getUid()).collection("Credits")
                                                .document("Credits").set(credits).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(LoginActivity.this, "signin successful", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "signin unsuccessful", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    }
                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    progressDialog.dismiss();
                                }
                            });



                        }
                        else
                        {
                            Log.i("TAG", "PEHLA HI UNSUCCESSFUL");
                            Toast.makeText(LoginActivity.this,"TASK UNSUCCESSFUL",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
            catch (Exception e)
            {
                //Toast.makeText(LoginActivity.this,"LOGIN UNSUCCESSFUL API EXCEPTION",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
               // Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("api",e.getMessage());
            }
        }
    }
}