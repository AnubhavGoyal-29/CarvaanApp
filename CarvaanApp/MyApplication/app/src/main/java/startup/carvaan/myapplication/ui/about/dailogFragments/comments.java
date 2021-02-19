package startup.carvaan.myapplication.ui.about.dailogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.PostModal;
import startup.carvaan.myapplication.ui.user.User;

public class comments extends DialogFragment {
    FirebaseFirestore ff=FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    User user=new User();
    EditText nos;
    Button sell;
    PostModal postModal;
    Map<String ,String >commentsMap;
    ArrayList<commentsModal> arrayList = new ArrayList<>();
    EditText comment;
    Button sendcomment;
    TextView nocomment;

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.comments,null,false);
        comment=view.findViewById(R.id.writeComment);
        sendcomment=view.findViewById(R.id.commentButton);
        Bundle bundle= getArguments();
        final String shareId= bundle.getString("shareid");
        final String blogid=bundle.getString("blogid");
        recyclerView = view.findViewById(R.id.commentRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        nocomment=view.findViewById(R.id.nocomment);
        ff.collection("shares")
                .document(shareId)
                .collection("Bloging")
                .document(blogid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                postModal=documentSnapshot.toObject(PostModal.class);
                commentsMap=new HashMap<>();
                commentsMap.putAll(postModal.getComments());
                for (Map.Entry<String,String> entry : commentsMap.entrySet()){
                    arrayList.add(new commentsModal(entry.getKey(),entry.getValue()));
                }
                if(arrayList.size()!=0){
                    nocomment.setVisibility(View.GONE);
                }
                mAdapter=new commentsAdapter(arrayList,getContext());
                recyclerView.setAdapter(mAdapter);

            }
        });
        sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r= UUID.randomUUID().toString();
                Map<String,String>comments=new HashMap<>();
                comments.putAll(commentsMap);
                comments.put(user.getDisplayName()+"//"+r,comment.getText().toString());
                ff.collection("shares")
                        .document(shareId)
                        .collection("Bloging")
                        .document(postModal.getId())
                        .update("comments",comments).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getDialog().dismiss();
                    }
                });
            }
        });
        builder.setView(view);
        return builder.create();
    }
}