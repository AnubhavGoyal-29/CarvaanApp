package startup.carvaan.myapplication.ui.about;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.dailogFragments.Buy;
import startup.carvaan.myapplication.ui.about.dailogFragments.Sell;
import startup.carvaan.myapplication.ui.about.dailogFragments.comments;
import startup.carvaan.myapplication.ui.user.User;
public class AboutShare extends AppCompatActivity {
    private int a;
    private static final String LOG_TAG = AboutShare.class.getSimpleName();
    private FirebaseFirestore ff;
    private Button add_button;
    private Intent myFileIntent;
    private List<PostModal> tech_list;
    private RecyclerView shareRecyclerView;
    private BottomNavigationView bottomNavigationView;
    private String shareid,name;
    private TextView coins;
    public static JCVideoPlayerStandard current_vv;
    private User user = new User();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseStorage firebaseStorage;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_share);
        firebaseStorage=FirebaseStorage.getInstance();
        getSupportActionBar().setElevation(0);
        ff = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        shareRecyclerView = findViewById(R.id.shareRecyclerView);
        final Bundle bundle = getIntent().getExtras();
        shareid = bundle.getString("shareid");
        name=bundle.getString("name");
        getSupportActionBar().setTitle(name);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
        ff = FirebaseFirestore.getInstance();
        tech_list = new ArrayList<>();


        //firebase recycler adapter
        Query query = ff.collection("shares").document(shareid).collection("Bloging");
        FirestoreRecyclerOptions<PostModal> options = new FirestoreRecyclerOptions.Builder<PostModal>().setQuery(query, PostModal.class).build();
        adapter = new FirestoreRecyclerAdapter<PostModal, PostViewHolder>(options) {


            // bind holder all work is working from here
            @SuppressLint("ResourceAsColor")
            @Override
            protected void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i, @NonNull final PostModal postModal) {
                postViewHolder.title.setText(postModal.getTitle());
                postViewHolder.description.setText(postModal.getDescription());
                Map<String, Boolean> userLiking=new HashMap<>();
                userLiking.putAll(postModal.getUsersLiking());
                postViewHolder.nooflikes.setText(String.valueOf(userLiking.size()));
                if(userLiking.containsKey(user.getUser().getUid()))
                    postViewHolder.likebutton.setTextColor(R.color.progress_color);
                postViewHolder.likebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AboutShare.this,"you liked this post",Toast.LENGTH_LONG).show();
                        userLiking.put(user.getUser().getUid(),Boolean.TRUE);
                        ff.collection("shares")
                                .document(shareid)
                                .collection("Bloging")
                                .document(postModal.getId())
                                .update("UsersLiking",userLiking).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
                postViewHolder.likeimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(AboutShare.this,"you liked this post",Toast.LENGTH_LONG).show();
                        userLiking.put(user.getUser().getUid(),Boolean.TRUE);
                        ff.collection("shares")
                                .document(shareid)
                                .collection("Bloging")
                                .document(postModal.getId())
                                .update("UsersLiking",userLiking).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
                Map<String,String>comments=new HashMap<>();
                comments.putAll(postModal.getComments());
                postViewHolder.noofcomments.setText(String.valueOf(comments.size()));
                if(postModal.getNeedAsistance())
                    postViewHolder.assistance.setVisibility(View.VISIBLE);
                if(postModal.getNeedIntern())
                    postViewHolder.intern.setVisibility(View.VISIBLE);
                if(postModal.getNeedFreelancer())
                    postViewHolder.freelancer.setVisibility(View.VISIBLE);

                postViewHolder.comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comments comments=new comments();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("shareid", shareid);
                        bundle1.putString("blogid",postModal.getId());
                        comments.setArguments(bundle1);
                        comments.show(getSupportFragmentManager(),"comments");
                    }
                });
                postViewHolder.commentimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comments comments=new comments();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("shareid", shareid);
                        bundle1.putString("blogid",postModal.getId());
                        comments.setArguments(bundle1);
                        comments.show(getSupportFragmentManager(),"comments");
                    }
                });
                postViewHolder.videoPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = postModal.getUrl();
                        youTubePlayer.cueVideo(videoId,0);

                    }
                });
                postViewHolder.writeComment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        if(s.length()==0){
//                            bottomNavigationView.setVisibility(View.VISIBLE);
//                        }
//                        else bottomNavigationView.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        if(s.length()!=0)
//                            bottomNavigationView.setVisibility(View.GONE);
//                        else bottomNavigationView.setVisibility(View.VISIBLE);
//                    }
                });
                postViewHolder.commentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        String r=UUID.randomUUID().toString();
                        Map<String,String>comments=new HashMap<>();
                        comments.putAll(postModal.getComments());
                        comments.put(user.getDisplayName()+"//"+r,postViewHolder.writeComment.getText().toString());
                        ff.collection("shares")
                                .document(shareid)
                                .collection("Bloging")
                                .document(postModal.getId())
                                .update("comments",comments).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
            }


            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneforall, parent, false);
                return new PostViewHolder(view);
            }

        };
        shareRecyclerView.setLayoutManager(new LinearLayoutManager(AboutShare.this));
        shareRecyclerView.setAdapter(adapter);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView attachfile,title,description,likebutton,nooflikes,noofcomments,intern,freelancer,assistance,uploadFile,nooffiles;
        private YouTubePlayerView videoPlayer;
        private TextView comments;
        private Button commentButton;
        private EditText writeComment;
        private ImageView likeimage,commentimage;
        private BottomNavigationView bottomBar;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            comments=itemView.findViewById(R.id.comments);
            videoPlayer=itemView.findViewById(R.id.videoplayer);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            likebutton=itemView.findViewById(R.id.likeButton);
            nooflikes=itemView.findViewById(R.id.number_of_likes);
            noofcomments=itemView.findViewById(R.id.number_of_comments);
            intern=itemView.findViewById(R.id.intern);
            freelancer=itemView.findViewById(R.id.freelancer);
            assistance=itemView.findViewById(R.id.assistance);
            likeimage=itemView.findViewById(R.id.likeimage);
            commentimage=itemView.findViewById(R.id.commentimage);
            commentButton=itemView.findViewById(R.id.commentButton);
            writeComment=itemView.findViewById(R.id.writeComment);

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

    private BottomNavigationView.OnNavigationItemSelectedListener navlistner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.sharebuy:
                    Buy dialog_buy = new Buy();
                    Bundle bundle = new Bundle();
                    bundle.putString("shareid", shareid);
                    dialog_buy.setArguments(bundle);
                    dialog_buy.show(getSupportFragmentManager(), "Dialog_Buy");
                    break;
                case R.id.sharesell:
                    ff.collection("Users")
                            .document(user.getUser().getUid())
                            .collection("myshares")
                            .document(shareid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if(documentSnapshot.exists()){
                                Sell dialog_sell = new Sell();
                                Bundle bundle1 = new Bundle();
                                bundle1.putString("shareid", shareid);
                                dialog_sell.setArguments(bundle1);
                                dialog_sell.show(getSupportFragmentManager(), "Dialog_Buy");
                            }
                            else{
                                Toast.makeText(AboutShare.this,"you have do not have any share",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    break;

            }
            return false;
        }
    };
    @Override
    public void onBackPressed() {
        adapter.notifyDataSetChanged();
        super.onBackPressed();
    }
}