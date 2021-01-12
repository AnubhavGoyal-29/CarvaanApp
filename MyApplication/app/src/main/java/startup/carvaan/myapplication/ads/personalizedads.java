package startup.carvaan.myapplication.ads;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import startup.carvaan.myapplication.R;

public class personalizedads extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    private FirestoreRecyclerAdapter adapter;
    private RecyclerView personalized;
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
                holder.timer.setText(model.getTimer());
                holder.progressTimer.setProgress(0);
                holder.progressTimer.setMax(Integer.valueOf(model.getTimer()));
                final float[] remaining = {0};
                holder.videoPlayer.addYouTubePlayerListener(new YouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                            youTubePlayer.cueVideo(model.getUrl(),0);
                    }

                    @Override
                    public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {

                    }

                    @Override
                    public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

                    }

                    @Override
                    public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

                    }

                    @Override
                    public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

                    }

                    @Override
                    public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {
                        if(Integer.valueOf(holder.timer.getText().toString())==Integer.valueOf(model.getTimer())-2)
                            Toast.makeText(personalizedads.this,"YOU GOT SOME REWARDS",Toast.LENGTH_SHORT).show();
                        else{

                        }
                        holder.timer.setText(String.valueOf((int)v));
                        holder.progressTimer.setProgress((int) v);
                    }

                    @Override
                    public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {
                    }

                    @Override
                    public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                    }

                    @Override
                    public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                    }

                    @Override
                    public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                    }
                });
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
        private TextView timer;
        private ProgressBar progressTimer;
        private YouTubePlayerView videoPlayer;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            timer=itemView.findViewById(R.id.timer);
            progressTimer=itemView.findViewById(R.id.progressTimer);
            videoPlayer=itemView.findViewById(R.id.adplayer);
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