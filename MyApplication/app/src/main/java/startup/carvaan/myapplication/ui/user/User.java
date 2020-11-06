package startup.carvaan.myapplication.ui.user;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class User {
    private String Email,ImageUrl,PhoneNumber;
    private FirebaseFirestore ff;
    private FirebaseAuth fauth;
    private FirebaseUser user;
    public User() {
        user=null;
        loginUser();
    }
    private String earned,winnings,added,redeemed;

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public FirebaseFirestore getFf() {
        return ff;
    }

    public void setFf(FirebaseFirestore ff) {
        this.ff = ff;
    }

    public FirebaseAuth getFauth() {
        return fauth;
    }

    public void setFauth(FirebaseAuth fauth) {
        this.fauth = fauth;
    }

    public String getEarned() {
        return earned;
    }

    public void setEarned(String earned) {
        this.earned = earned;
    }

    public String getWinnings() {
        return winnings;
    }

    public void setWinnings(String winnings) {
        this.winnings = winnings;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(String redeemed) {
        this.redeemed = redeemed;
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
                Email=snapshot.getString("Email");
                ImageUrl=snapshot.getString("ImageUrl");
                PhoneNumber=snapshot.getString("PhoneNumber");
            }
        });
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                earned= value.getString("earned");
                winnings=value.getString("winnings");
            }
        });
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("cash").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                added= value.getString("added");
                redeemed=value.getString("redeemed");
            }
        });
    }
    public void loginUser(){
        fauth=FirebaseAuth.getInstance();
        setUser(fauth.getCurrentUser());
        if(user!=null)
            getUserDetails();
    }



    public void logoutUser(){
        fauth.signOut();
    }



    public void addCredits(float a){
        ff.collection("Users").document(user.getUid()).collection("CreditDetails").document("coins").update("earned",String.valueOf(Integer.valueOf(earned)+Integer.valueOf((int) a)));
    }
    public void removeCredits(float a){
        ff.collection("Users").document(user.getUid()).update("Credits",String.valueOf(Integer.valueOf(earned)-Integer.valueOf((int) a)));
    }

}

