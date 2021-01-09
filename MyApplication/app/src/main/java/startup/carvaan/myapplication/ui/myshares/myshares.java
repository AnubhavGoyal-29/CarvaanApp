package startup.carvaan.myapplication.ui.myshares;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.AboutShare;
import startup.carvaan.myapplication.ui.user.User;


public class myshares extends AppCompatActivity {

    private RecyclerView allShareRecyclerView;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore ff=FirebaseFirestore.getInstance();
    private User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_myshares);
        User user=new User();
        allShareRecyclerView=findViewById(R.id.myShareRecyclerView);
        ff=FirebaseFirestore.getInstance();
        Query query=ff.collection("Users")
                .document(user.getUser().getUid())
                .collection("myshares")
                .orderBy("holdings", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<mysharemodel> options = new FirestoreRecyclerOptions.Builder<mysharemodel>().setQuery(query, mysharemodel.class).build();
        adapter= new FirestoreRecyclerAdapter<mysharemodel, PostViewHolder>(options) {
            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_myshare,parent,false);
                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder postViewHolder, int position, mysharemodel mysharemodel) {
                String docId= getSnapshots().getSnapshot(position).getId();
                ff.collection("shares")
                        .document(docId)
                        .collection("Price")
                        .document("price").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        postViewHolder.sellingPrice.setText(value.getString("sellingPrice"));
                        postViewHolder.buyingPrice.setText(value.getString("buyingPrice"));
                    }
                });
                ff.collection("shares")
                        .document(docId)
                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        postViewHolder.shareName.setText(value.getString("companyname"));
                    }
                });
                if(Double.valueOf(mysharemodel.getHoldings())==0){
                    ff.collection("Users").document(user.getUser().getUid()).collection("myshares").document(docId).delete();
                }
                postViewHolder.holdings.setText(mysharemodel.getHoldings());
                postViewHolder.price.setText("at price  "+mysharemodel.getPriceHoldings());
                postViewHolder.trade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(myshares.this, AboutShare.class).putExtra("shareid",docId));
                    }
                });
            }
        };
        allShareRecyclerView.setAdapter(adapter);
        allShareRecyclerView.setLayoutManager(new LinearLayoutManager(myshares.this));

    }
    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView shareName;
        private TextView holdings,price,buyingPrice,sellingPrice;
        private Button trade;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            trade=itemView.findViewById(R.id.trade);
            shareName=itemView.findViewById(R.id.sharename);
            holdings=itemView.findViewById(R.id.holdings);
            price=itemView.findViewById(R.id.price);
            buyingPrice=itemView.findViewById(R.id.buyingPrice);
            sellingPrice=itemView.findViewById(R.id.sellingPrice);
            
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }
}
