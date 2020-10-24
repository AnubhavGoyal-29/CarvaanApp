package startup.carvaan.myapplication.ui.user;

import androidx.annotation.Nullable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class User {
    private String Credits,Email;
    private FirebaseFirestore ff;
    private FirebaseAuth fauth;
    private FirebaseUser user;
    public User() {
        loginUser();
    }
    public void setCredits(String credits) {
        Credits = credits;
    }
    public String getCredits() {
        return Credits;
    }

    public void setEmail(String email) {
        Email = email;
    }
    public String getEmail() {
        return Email;
    }

    public FirebaseUser getUser() {
        return user;
    }
    public void setUser(FirebaseUser user) {
        this.user = user;
    }


    private void getUserDetails() {
        ff=FirebaseFirestore.getInstance();
        ff.collection("Users").document(user.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                DocumentSnapshot snapshot=value;
                Credits=snapshot.getString("Credits");
                Email=snapshot.getString("Email");
            }
        });
    }
    public void loginUser(){
        fauth=FirebaseAuth.getInstance();
        setUser(fauth.getCurrentUser());
        getUserDetails();
    }



    public void logoutUser(){
        fauth.signOut();
        user=null;
    }



    public void addCredits(float a){

    }
    public void removeCredits(float a){

    }

}

