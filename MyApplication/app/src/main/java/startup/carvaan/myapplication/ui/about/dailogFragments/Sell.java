package startup.carvaan.myapplication.ui.about.dailogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.shareDetails;
import startup.carvaan.myapplication.ui.myshares.mysharemodel;
import startup.carvaan.myapplication.ui.user.User;

public class Sell extends DialogFragment {
    User user=new User();
    EditText nos;
    Button sell;
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_sell,null,false);
        FirebaseFirestore ff=FirebaseFirestore.getInstance();
        Bundle bundle= getArguments();
        final String shareId= bundle.getString("shareid");
        nos=view.findViewById(R.id.noofshares);

        sell=view.findViewById(R.id.btn_sell);
        shareDetails shareDetails=new shareDetails(shareId);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double Nos=Double.valueOf(nos.getText().toString());
                ff.collection("Users")
                        .document(user.getUser().getUid())
                        .collection("myshares")
                        .document(shareId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> value) {
                        mysharemodel mysharemodel=value.getResult().toObject(startup.carvaan.myapplication.ui.myshares.mysharemodel.class);
                        if(value.getResult().getString("holdings")==null){
                            Toast.makeText(getContext(),"you cannot sell as u dont have any share",Toast.LENGTH_LONG).show();
                        }
                        else if(Nos>Double.valueOf(mysharemodel.getHoldings())){
                            Toast.makeText(getContext(),"dont have enough",Toast.LENGTH_LONG).show();
                        }
                        else{
                            ff.collection("Users")
                                    .document(user.getUser().getUid())
                                    .collection("myshares")
                                    .document(shareId).update("holdings",String.valueOf(Double.valueOf(Double.valueOf(mysharemodel.getHoldings()))-Nos)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    user.addWinnings(Double.valueOf(shareDetails.getSellingPrice())*Nos);
                                    sell_success sell_success=new sell_success();
                                    sell_success.show(getChildFragmentManager(), "Dialog_Buy");
                                }
                            });

                        }
                    }
                });
            }
        });
        builder.setView(view);
        return builder.create();
    }
}