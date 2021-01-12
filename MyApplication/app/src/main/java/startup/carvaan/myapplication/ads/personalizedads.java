package startup.carvaan.myapplication.ads;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.user.User;

public class personalizedads extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView personalized;
    private FirebaseFirestore ff=FirebaseFirestore.getInstance();
    YouTubePlayerView youTubePlayerView;

    User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizedads);
        personalized=findViewById(R.id.personalized);
        Query query= firebaseFirestore.collection("personalized_ads");
        FirestoreRecyclerOptions<ad_model> options=new FirestoreRecyclerOptions.Builder<ad_model>().setQuery(query,ad_model.class).build();
        adapter=new FirestoreRecyclerAdapter<ad_model,PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull ad_model model) {
                holder.addTime.setText(model.getAddTime());
                holder.progressTimer.setMax(Integer.valueOf(model.getAddTime()));
                holder.progressTimer.setProgress(0);
                holder.progressTimer.setMax(Integer.valueOf(model.getAddTime()));
                holder.addName.setText(model.getAddName());
                holder.addDescription.setText(model.getAddDescription());
                holder.addAddress.setText(model.getAddAddress());
                holder.addPeopleWatched.setText(model.getAddPeopleWatched());
                holder.addReward.setText(model.getAddReward()+" Rci");
                StorageReference firebaseStorage=FirebaseStorage.getInstance().getReference();
                StorageReference storageReference=firebaseStorage.child(model.getAddImage());
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Glide.with(personalizedads.this)
                                .load(task.getResult())
                                .into(holder.addImage);
                    }
                });
                holder.addUrl.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                        super.onReady(youTubePlayer);
                        youTubePlayer.cueVideo(model.getAddUrl(),0);
                    }

                    @Override
                    public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float second) {
                        super.onCurrentSecond(youTubePlayer, second);
                        if(Integer.valueOf((int)second)==Integer.valueOf(model.getAddTime())){

                            int a=Integer.valueOf(model.addPeopleWatched)+1;
                            ff.collection("personalized_ads").document(model.getId()).update("addPeopleWatched",String.valueOf(a));
                        }
                        holder.addTime.setText(String.valueOf((int)second));
                        holder.progressTimer.setProgress(Integer.valueOf((int)second));
//                        user.addEarned(Double.valueOf(model.getAddReward()));
                    }
                });
                final float[] remaining = {0};
            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_add_file,parent,false);
                return new PostViewHolder(view);
            }
        };
        personalized.setAdapter(adapter);
        personalized.setLayoutManager(new LinearLayoutManager(this));
    }
    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView addTime,addName,addDescription,addAddress,addPeopleWatched,addReward;
        private ProgressBar progressTimer;
        private YouTubePlayerView addUrl;
        private CircleImageView addImage;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            addImage=itemView.findViewById(R.id.addImage);
            addTime=itemView.findViewById(R.id.addTime);
            addName=itemView.findViewById(R.id.addName);
            addDescription=itemView.findViewById(R.id.addDescription);
            addAddress=itemView.findViewById(R.id.addAddress);
            addPeopleWatched=itemView.findViewById(R.id.addPeopleWatched);
            addReward=itemView.findViewById(R.id.addReward);
            progressTimer=itemView.findViewById(R.id.progressTimer);
            addUrl=itemView.findViewById(R.id.addUrl);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}