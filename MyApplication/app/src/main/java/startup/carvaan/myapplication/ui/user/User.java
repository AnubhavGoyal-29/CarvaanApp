package startup.carvaan.myapplication.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class User {
    private String Email,ImageUrl,PhoneNumber,DisplayName;
    private FirebaseFirestore ff;
    private FirebaseAuth fauth;
    private FirebaseUser user;

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

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
        ff.collection("Users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();
                Email=snapshot.getString("Email");
                ImageUrl=snapshot.getString("ImageUrl");
                PhoneNumber=snapshot.getString("PhoneNumber");
                DisplayName=snapshot.getString("DisplayName");
            }
        });
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                setEarned(value.getString("earned"));
                setWinnings(value.getString("winnings"));
            }
        });
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("cash").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                setAdded(value.getString("added"));
                setRedeemed(value.getString("redeemed"));
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

    public void addEarned(float a){
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("coins").update("earned",String.valueOf(Integer.valueOf(earned)+Integer.valueOf((int) a)));
    }
     public void addWinnings(float a){
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("coins").update("winnings",String.valueOf(Integer.valueOf(winnings)+Integer.valueOf((int) a)));
    }
    public void removeEarned(float a){
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("coins").update("earned",String.valueOf(Integer.valueOf(earned)-Integer.valueOf((int) a)));
    }
    public void addCash(float a){
        ff.collection("Users").document(user.getUid()).collection("CreditDetails")
                .document("cash").update("added",String.valueOf(Integer.valueOf(getAdded())+a));
    }


}

