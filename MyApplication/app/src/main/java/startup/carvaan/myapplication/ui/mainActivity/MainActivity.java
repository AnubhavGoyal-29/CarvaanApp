package startup.carvaan.myapplication.ui.mainActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import startup.carvaan.myapplication.BuyCoin;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.AboutShare;
import startup.carvaan.myapplication.ui.allshares.allsharemodel;
import startup.carvaan.myapplication.ui.coins.aboutRci;
import startup.carvaan.myapplication.ui.login.LoginActivity;
import startup.carvaan.myapplication.ui.myshares.myshares;
import startup.carvaan.myapplication.ui.navbar.Helppage;
import startup.carvaan.myapplication.ui.payment.payouts;
import startup.carvaan.myapplication.ui.profile.Profile;
import startup.carvaan.myapplication.ui.user.User;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

import static com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "carvaan";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DrawerLayout drawerLayout;
    FirebaseFirestore ff;
    private StorageReference firebaseStorage= FirebaseStorage.getInstance().getReference();
    GestureDetector gestureDetector;
    private ActionBarDrawerToggle toggle;
    private FirestoreRecyclerAdapter adapter;
    private float mYVal;
    private RelativeLayout mlinId;
    PathModel outline;
    private ActionBar actionBar;
    TextView coins;
    User user=new User();
    private RecyclerView allShareRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppUpdateManager appUpdateManager= AppUpdateManagerFactory.create(MainActivity.this);
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask=appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if(result.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE && result.isUpdateTypeAllowed(FLEXIBLE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE,MainActivity.this,01);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        Paper.init(MainActivity.this);
        Paper.init(this);
        ff=FirebaseFirestore.getInstance();
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("StartupCarvaan");
        NavigationView navigationView =findViewById(R.id.n1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Are you sure you want to logout")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        user.logoutUser();
                                        gotoLoginActivity();
                                    }
                                }).setNegativeButton("No",null);
                        AlertDialog alert=builder.create();
                        alert.show();
                        break;
                    case R.id.howtoplay:
                        startActivity(new Intent(MainActivity.this, Helppage.class));
                        break;
                    case R.id.buyc:
                        startActivity(new Intent(MainActivity.this, BuyCoin.class));
                        break;
                    case R.id.aboutRci:
                        startActivity(new Intent(MainActivity.this, aboutRci.class));
                        break;
                    case R.id.coin:
                        startActivity(new Intent(MainActivity.this, payouts.class));
                        break;
                }
                return false;
            }
        });
        View view=getSupportActionBar().getCustomView();

        CircleImageView profile=view.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Profile.class));
            }
        });

        CircleImageView myshare=view.findViewById(R.id.myshare);
        myshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, myshares.class));
            }
        });
        View header=navigationView.getHeaderView(0);
        TextView headerCoins=header.findViewById(R.id.headerCoins);

        TextView headerWinnings=header.findViewById(R.id.headerWinnings);

        TextView headerCash=header.findViewById(R.id.headerCash);

        FirebaseFirestore.getInstance().collection("Users")
                .document(user.getUser().getUid())
                .collection("CreditDetails")
                .document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                headerCoins.setText(value.getString("earned"));
                headerWinnings.setText(value.getString("winnings"));
            }
        });
        FirebaseFirestore.getInstance().collection("Users")
                .document(user.getUser().getUid())
                .collection("CreditDetails")
                .document("cash").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                headerCash.setText(" Your cash :"+value.getString("added"));

            }
        });
        TextView textView=view.findViewById(R.id.title);
        textView.setText("StartupCarvaan");
        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // database



        allShareRecyclerView=findViewById(R.id.allShareRecyclerView);
        Query query=ff.collection("shares").orderBy("peopleinvested", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<allsharemodel> options = new FirestoreRecyclerOptions.Builder<allsharemodel>().setQuery(query, allsharemodel.class).build();
        adapter= new FirestoreRecyclerAdapter<allsharemodel,PostViewHolder>(options) {

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_allshare,parent,false);
                return new PostViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(PostViewHolder postViewHolder, int i, allsharemodel allsharemodel) {

                postViewHolder.companyName.setText(allsharemodel.getCompanyname());
                postViewHolder.description.setText("Description :  "+allsharemodel.getDescription());
                postViewHolder.text_view_progress.setText(allsharemodel.getGrowth());
                postViewHolder.peopleinvested.setText(allsharemodel.getPeopleinvested());
                postViewHolder.growth.setMax(100) ;//dummy max Val
                postViewHolder.tag.setText(allsharemodel.getTag());
                postViewHolder.growth.setProgress(Integer.valueOf(allsharemodel.getGrowth()));
                postViewHolder.videoPlayer.addYouTubePlayerListener(new YouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                        String videoId = allsharemodel.getIntrovideourl();
                        youTubePlayer.cueVideo(videoId,0);
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

                StorageReference storageReference=firebaseStorage.child(allsharemodel.getLogoUrl());
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Glide.with(MainActivity.this)
                                .load(task.getResult())
                                .into(postViewHolder.circleImageView);
                    }
                });

                postViewHolder.aboutShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AboutShare.class).putExtra("shareid",allsharemodel.getId()).putExtra("name",allsharemodel.getCompanyname()));
                    }
                });
            }
        };
        allShareRecyclerView.setAdapter(adapter);
        allShareRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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


    private void gotoLoginActivity() {
        Intent logIntent = new Intent(MainActivity.this, LoginActivity.class);
        logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logIntent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        if(!user.getUser().isEmailVerified()){
            Toast.makeText(MainActivity.this, "please verify your mail first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else{
            if(!Paper.book().contains("isFirst")) {
                AppDemo();
                Paper.book().write("isFirst", "true");
            }
            else{
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("dialogueMessage"); // Setting Message
                progressDialog.setTitle("dialogueTitle"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();

                    }
                },0000);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
        public void AppDemo() {
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(MainActivity.this)
                        .setTarget(findViewById(R.id.n1))
                        .setPrimaryText("Hii Carvan User ")
                        .setSecondaryText("Welcome to this app ,If you want to know all things about this app anf how to operate it then go to the how to play page in side drawer")
                        .create(), 4000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(MainActivity.this)
                        .setTarget(findViewById(R.id.allShareRecyclerView))
                        .setPrimaryText("All shares page ")
                        .setSecondaryText("Here you can see all the startups that are listed in this app in which you invest.Invest in it by clicking on invest in me button.")
                        .create(), 4000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(MainActivity.this)
                        .setTarget(findViewById(R.id.myshare))
                        .setPrimaryText("My share icon")
                        .setSecondaryText("Here you can see all the shares in which you invested ")
                        .setIcon(R.drawable.ic_dashboard_black_24dp)
                        .create(), 4000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(MainActivity.this)
                        .setTarget(findViewById(R.id.profile))
                        .setPrimaryText("Profile button")
                        .setSecondaryText("Here you can see  how much coins you have.You can redeem them here or earn coins here to invest more. ")
                        .create(), 4000)
                .show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==01 && resultCode==RESULT_OK){
            Toast.makeText(MainActivity.this,"start download...",Toast.LENGTH_LONG).show();
        }
    }
}
