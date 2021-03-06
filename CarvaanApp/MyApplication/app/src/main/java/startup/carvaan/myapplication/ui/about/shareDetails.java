package startup.carvaan.myapplication.ui.about;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class shareDetails {
    String buyingPrice,sellingPrice,shareId,name,totalShares,occupied;
    private FirebaseFirestore ff;

    public String getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(String totalShares) {
        this.totalShares = totalShares;
    }

    public String getOccupied() {
        return occupied;
    }

    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public shareDetails(String id) {
        this.shareId=id;
        getDetails(shareId);
    }

    private void getDetails(String shareId) {
        ff=FirebaseFirestore.getInstance();
        ff.collection("shares")
                .document(shareId)
                .collection("Price")
                .document("price").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                setBuyingPrice(value.getString("buyingPrice"));
                setSellingPrice(value.getString("sellingPrice"));
                setTotalShares(value.getString("totalShares"));
                setOccupied(value.getString("occupied"));
            }
        });
        ff.collection("shares")
                .document(shareId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setName(task.getResult().getString("name"));
            }
        });
    }

    public shareDetails(String buyingPrice, String sellingPrice) {
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }
    void addOccupied(Double d){
        ff.collection("shares")
                .document(shareId)
                .collection("Price")
                .document("price").update("occupied",String.valueOf(Double.valueOf(getOccupied())+d));
    }
    void removeOccupied(Double d){
        ff.collection("shares")
                .document(shareId)
                .collection("Price")
                .document("price").update("occupied",String.valueOf(Double.valueOf(getOccupied())-d));
    }



}
