package startup.carvaan.myapplication.ui.about;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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

public class


AboutShare extends AppCompatActivity {
    private String path=null;
    private Uri pdfUri=null;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView textView;
    private int a;
    private static final String LOG_TAG = AboutShare.class.getSimpleName();
    private FirebaseFirestore ff;
    private ImageView stonksimage;
    private Button add_button;
    private Intent myFileIntent;
    private List<PostModal> tech_list;
    private RecyclerView shareRecyclerView;
    private BottomNavigationView bottomNavigationView;
    private String shareid,name;
    private TextView coins;
    public static JCVideoPlayerStandard current_vv;
    private User user = new User();


    //firebase
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
            @Override
            protected void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i, @NonNull final PostModal postModal) {
                postViewHolder.title.setText(postModal.getTitle());
                postViewHolder.description.setText(postModal.getDescription());
                Map<String, Boolean> userLiking=new HashMap<>();
                userLiking.putAll(postModal.getUsersLiking());
                postViewHolder.nooflikes.setText(String.valueOf(userLiking.size()));
                if(userLiking.containsKey(user.getUser().getUid()))
                    postViewHolder.likebutton.setTextColor(R.color.red);
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
                if(pdfUri==null){
                    postViewHolder.attachfile.setVisibility(View.VISIBLE);
                    postViewHolder.uploadFile.setVisibility(View.GONE);
                    Map<String ,String > files=new HashMap<>();
                    files.putAll(postModal.getFiles());
                    postViewHolder.nooffiles.setText(String.valueOf(files.size()));
                    postViewHolder.attachfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ContextCompat.checkSelfPermission
                                    (AboutShare.this,Manifest.permission.READ_EXTERNAL_STORAGE)
                                    ==PackageManager.PERMISSION_GRANTED){
                                selectPdf();
                            }
                            else{
                                ActivityCompat.requestPermissions(AboutShare.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                            }
                        }
                    });
                }
                if(pdfUri!=null){
                    postViewHolder.attachfile.setVisibility(View.GONE);
                    postViewHolder.uploadFile.setVisibility(View.VISIBLE);
                    postViewHolder.uploadFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            path=uploadFile(shareid,postModal.getId(),pdfUri);
                            Map<String ,String > files=new HashMap<>();
                            files.putAll(postModal.getFiles());
                            files.put(user.getUser().getUid(),path);
                            if(path==null){
                                Toast.makeText(AboutShare.this,"files does not uploaded successfully please try again",Toast.LENGTH_LONG).show();
                            }
                            else{
                                ff.collection("shares")
                                        .document(shareid)
                                        .collection("Bloging")
                                        .document(postModal.getId())
                                        .update("files",files).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        pdfUri=null;
                                        postViewHolder.uploadFile.setVisibility(View.GONE);
                                        postViewHolder.attachfile.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        }
                    });
                }
                postViewHolder.videoPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = postModal.getUrl();
                        youTubePlayer.cueVideo(videoId,0);

                    }
                });
                postViewHolder.writeComment.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        bottomNavigationView.setVisibility(View.GONE);
                        return false;
                    }
                });
                postViewHolder.writeComment.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length()==0){
                            bottomNavigationView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.length()!=0)
                            bottomNavigationView.setVisibility(View.GONE);
                    }
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
        private BottomNavigationView bottomBar;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            comments=itemView.findViewById(R.id.comments);
            videoPlayer=itemView.findViewById(R.id.videoplayer);
            attachfile=itemView.findViewById(R.id.attachFile);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            likebutton=itemView.findViewById(R.id.likeButton);
            nooflikes=itemView.findViewById(R.id.number_of_likes);
            noofcomments=itemView.findViewById(R.id.number_of_comments);
            intern=itemView.findViewById(R.id.intern);
            freelancer=itemView.findViewById(R.id.freelancer);
            assistance=itemView.findViewById(R.id.assistance);
            uploadFile=itemView.findViewById(R.id.uploadFile);
            commentButton=itemView.findViewById(R.id.commentButton);
            writeComment=itemView.findViewById(R.id.writeComment);
            nooffiles=itemView.findViewById(R.id.number_of_files);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }
        else{
            Toast.makeText(AboutShare.this,"please provide permissions",Toast.LENGTH_LONG).show();
        }
    }

    private void selectPdf() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
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
    public String uploadFile(String shareid,String blogid,Uri uri){
        StorageReference storageReference=firebaseStorage.getReference();
        StorageReference finalPath=storageReference.child("shareFiles").child(shareid).child(blogid).child(user.getUser().getUid()+".pdf");
        finalPath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AboutShare.this,"file upload successfully",Toast.LENGTH_SHORT).show();
            }
        });
        return finalPath.toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK&&data!=null) {
                    // Get the Uri of the selected file
                    pdfUri = data.getData();

                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }
    @Override
    public void onBackPressed() {
        adapter.notifyDataSetChanged();
        super.onBackPressed();
    }
    private void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    10);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
}