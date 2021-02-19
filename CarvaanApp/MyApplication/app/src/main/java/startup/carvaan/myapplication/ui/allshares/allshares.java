package startup.carvaan.myapplication.ui.allshares;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.user.User;


public class allshares extends Fragment {
    ProgressBar progressBar;

    private RecyclerView allShareRecyclerView;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore ff;

    public allshares() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_allshares, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        },2000);
        User user=new User();

        ff=FirebaseFirestore.getInstance();

        return view;
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        private Button aboutShare;
        private YouTubePlayerView videoPlayer;
        private TextView companyName,description,peopleinvested,text_view_progress,tag;
        private ProgressBar growth;
        private CircleImageView circleImageView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.logo);
            text_view_progress=itemView.findViewById(R.id.text_view_progress);
            growth=itemView.findViewById(R.id.progress_bar);
            peopleinvested=itemView.findViewById(R.id.peopleinvested);
            description=itemView.findViewById(R.id.description);
            companyName=itemView.findViewById(R.id.companyName);
            videoPlayer=itemView.findViewById(R.id.videoplayer);
            aboutShare=itemView.findViewById(R.id.gotoshare);
            tag=itemView.findViewById(R.id.tag);
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