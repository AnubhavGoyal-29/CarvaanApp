package startup.carvaan.myapplication.ui.allshares;

import android.content.Intent;
import android.net.Uri;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.AboutShare;
import startup.carvaan.myapplication.ui.user.User;


public class allshares extends Fragment {
    ProgressBar progressBar;
    private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference();
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
        allShareRecyclerView=view.findViewById(R.id.allShareRecyclerView);
        ff=FirebaseFirestore.getInstance();
        Query query=ff.collection("shares");
        FirestoreRecyclerOptions<allsharemodel> options = new FirestoreRecyclerOptions.Builder<allsharemodel>().setQuery(query, allsharemodel.class).build();
        adapter= new FirestoreRecyclerAdapter<allsharemodel, PostViewHolder>(options) {
            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_allshare,parent,false);
                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder postViewHolder, int i, allsharemodel allsharemodel) {
                postViewHolder.companyName.setText(allsharemodel.getCompanyname());
                postViewHolder.description.setText(allsharemodel.getDescription());
                postViewHolder.text_view_progress.setText(allsharemodel.getGrowth());
                postViewHolder.peopleinvested.setText(allsharemodel.getPeopleinvested());
                postViewHolder.growth.setMax(100) ;//dummy max Val
                postViewHolder.tag.setText(allsharemodel.getTag());
                postViewHolder.growth.setProgress(Integer.valueOf(allsharemodel.getGrowth()));

                postViewHolder.videoPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = allsharemodel.getIntrovideourl();
                        youTubePlayer.cueVideo(videoId,0);

                    }
                });
                StorageReference storageReference=firebaseStorage.child(allsharemodel.getLogoUrl());
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Glide.with(getContext())
                                .load(task.getResult())
                                .into(postViewHolder.circleImageView);
                    }
                });

                postViewHolder.aboutShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getContext().startActivity(new Intent(getContext(), AboutShare.class).putExtra("shareid",allsharemodel.getId()).putExtra("name",allsharemodel.getCompanyname()));
                    }
                });
            }
        };
        allShareRecyclerView.setAdapter(adapter);
        allShareRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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