package startup.carvaan.myapplication.ui.about.dailogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import startup.carvaan.myapplication.ui.myshares.mysharemodel;
import startup.carvaan.myapplication.ui.user.User;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.shareDetails;
public class Buy extends DialogFragment {
    User user=new User();
    EditText nos;
    Button buy;
    private ImageView closefg;
    String totalShares,occupied,buyingprice;
    private TextView shareprice,totalavailable,yourpreviousholdings;
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle bundle= getArguments();
        final String shareId= bundle.getString("shareid");
        final View view = inflater.inflate(R.layout.fragment_buy,null,false);
        FirebaseFirestore ff=FirebaseFirestore.getInstance();
        shareDetails shareDetails=new shareDetails(shareId);

        shareprice=view.findViewById(R.id.shareprice);
        closefg=view.findViewById(R.id.close23);
        closefg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        totalavailable=view.findViewById(R.id.totalAvailable);
        ff.collection("shares")
                .document(shareId)
                .collection("Price")
                .document("price").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                totalShares=value.getString("totalShares");
                occupied=value.getString("occupied");
                buyingprice=value.getString("buyingPrice");

                shareprice.setText(buyingprice+"  RCI");

                totalavailable.setText("Available Shares "+String.valueOf(Integer.valueOf((int) (Double.valueOf(totalShares)-Double.valueOf(occupied)))));
            }
        });

        shareprice.setText("Rs"+shareDetails.getBuyingPrice());
        nos=view.findViewById(R.id.noofshares);
        buy=view.findViewById(R.id.btn_buy);
        buy.setVisibility(View.GONE);
        nos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    buy.setVisibility(View.GONE);
                }
                else buy.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double Nos = Double.valueOf(nos.getText().toString());
                if (Nos < Integer.valueOf((int) (Double.valueOf(totalShares)-Double.valueOf(occupied)))) {
                    Double totalPrice = Double.valueOf(Nos * Double.valueOf(shareDetails.getBuyingPrice()));
                    Double credits = Double.valueOf(user.getEarned()) + Double.valueOf(user.getWinnings());
                    if (credits >= totalPrice) {
                        final String[] priceHolding = {null};
                        final String[] holdings = {null};
                        ff.collection("Users")
                                .document(user.getUser().getUid())
                                .collection("myshares").document(shareId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                priceHolding[0] = documentSnapshot.getString("priceHoldings");
                                holdings[0] = documentSnapshot.getString("holdings");
                            }
                        });
                        Toast.makeText(getContext(), "Proceeding your buying.....", Toast.LENGTH_LONG).show();
                        ff.collection("Users")
                                .document(user.getUser().getUid())
                                .collection("myshares")
                                .document(shareId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                mysharemodel mysharemodel = task.getResult().toObject(mysharemodel.class);
                                if (task.getResult().getString("holdings") == null) {
                                    String lastPriceHolding = String.valueOf(shareDetails.getBuyingPrice());
                                    Map<Object, Object> map = new HashMap<>();
                                    map.put("priceHoldings", lastPriceHolding);
                                    map.put("holdings", String.valueOf(Nos));
                                    ff.collection("Users")
                                            .document(user.getUser().getUid())
                                            .collection("myshares")
                                            .document(shareId).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (totalPrice <= Double.valueOf(user.getEarned()))
                                                user.removeEarned(Double.valueOf(totalPrice));
                                            else {
                                                user.removeWinnings(Double.valueOf(totalPrice - Double.valueOf(user.getEarned())));
                                                user.removeEarned(Double.valueOf(Double.valueOf(user.getEarned())));
                                            }
                                            ff.collection("shares")
                                                    .document(shareId)
                                                    .collection("Price")
                                                    .document("price").update("occupied", String.valueOf(Double.valueOf(occupied) + Double.valueOf(nos.getText().toString())));
                                            ff.collection("shares").document(shareId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    String peopleinvested=task.getResult().getString("peopleinvested");
                                                    ff.collection("shares").document(shareId).update("peopleinvested",String.valueOf(Integer.valueOf(peopleinvested)+1));
                                                }
                                            });

                                            startup.carvaan.myapplication.ui.about.dailogFragments.dialog_buy_success dialog_buy_success = new dialog_buy_success();
                                            Bundle bundle1 = new Bundle();
                                            bundle1.putString("nos", String.valueOf(nos.getText().toString()));
                                            dialog_buy_success.setArguments(bundle1);
                                            dialog_buy_success.show(getChildFragmentManager(), "Dialog_Buy");
                                        }
                                    });
                                } else {
                                        String lastPriceHolding = String.valueOf(shareDetails.getBuyingPrice());
                                        ff.collection("Users")
                                                .document(user.getUser().getUid())
                                                .collection("myshares")
                                                .document(shareId)
                                                .update("holdings", String.valueOf(Double.valueOf(mysharemodel.getHoldings()) + Nos),
                                                        "priceHoldings", lastPriceHolding).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (totalPrice <= Double.valueOf(user.getEarned()))
                                                    user.removeEarned(Double.valueOf(totalPrice));
                                                else {
                                                    user.removeWinnings(Double.valueOf(totalPrice - Double.valueOf(user.getEarned())));
                                                    user.removeEarned(Double.valueOf(Double.valueOf(user.getEarned())));
                                                }
                                                ff.collection("shares")
                                                        .document(shareId)
                                                        .collection("Price")
                                                        .document("price").update("priceHoldings3",lastPriceHolding);
                                                ff.collection("Users").document(user.getUser().getUid()).collection("myshares").document(shareId).update("occupied", String.valueOf(Double.valueOf(occupied) + Double.valueOf(nos.getText().toString())));
                                                startup.carvaan.myapplication.ui.about.dailogFragments.dialog_buy_success dialog_buy_success = new dialog_buy_success();
                                                Bundle bundle1 = new Bundle();
                                                bundle1.putString("nos", String.valueOf(nos.getText().toString()));
                                                dialog_buy_success.setArguments(bundle1);
                                                dialog_buy_success.show(getChildFragmentManager(), "Dialog_Buy");

                                            }
                                        });


                                }
                            }
                        });

                    } else {
                        Toast.makeText(getContext(), "You do not have sufficient amount", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                        Toast.makeText(getContext(),String.valueOf(Nos)+" shares are not available \n"+totalavailable.getText().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });


        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onStop() {
        super.onStop();
        getDialog().dismiss();
    }
}