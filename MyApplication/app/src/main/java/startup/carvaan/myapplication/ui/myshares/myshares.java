package startup.carvaan.myapplication.ui.myshares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.user.User;


public class myshares extends Fragment {

    private RecyclerView allShareRecyclerView;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore ff;
    private User user=new User();
    public myshares() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_myshares, container, false);
        User user=new User();
        allShareRecyclerView=view.findViewById(R.id.myShareRecyclerView);
        ff=FirebaseFirestore.getInstance();
        Query query=ff.collection("Users").document(user.getUser().getUid()).collection("myshares");
        FirestoreRecyclerOptions<mysharemodel> options = new FirestoreRecyclerOptions.Builder<mysharemodel>().setQuery(query, mysharemodel.class).build();
        adapter= new FirestoreRecyclerAdapter<mysharemodel, PostViewHolder>(options) {
            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_myshare,parent,false);
                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder postViewHolder, int i, mysharemodel mysharemodel) {
                postViewHolder.shareName.setText(user.getEmail());
                List<String> list = new ArrayList<String>();
                list.add(mysharemodel.getHoldings());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                postViewHolder.spinner.setAdapter(arrayAdapter);
                postViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (parent.getItemAtPosition(position).equals("Select problem")) {

                        } else {
//                            item = parent.getItemAtPosition(position).toString();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });

            }
        };
        allShareRecyclerView.setAdapter(adapter);
        allShareRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView shareName;
        private Spinner spinner;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            shareName=itemView.findViewById(R.id.sharename);

            
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
