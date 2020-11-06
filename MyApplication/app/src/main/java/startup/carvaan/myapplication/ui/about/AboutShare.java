package startup.carvaan.myapplication.ui.about;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.about.dailogFragments.Buy;
import startup.carvaan.myapplication.ui.about.dailogFragments.Sell;
import startup.carvaan.myapplication.ui.user.User;

public class AboutShare extends AppCompatActivity {
    Uri pdfUri;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView textView;
    private int a;
    private static final String LOG_TAG = AboutShare.class.getSimpleName();
    private FirebaseFirestore ff;
    private ImageView stonksimage;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirestoreRecyclerAdapter adapter;
    private List<PostModal> tech_list;
    private RecyclerView shareRecyclerView;
    private BottomNavigationView bottomNavigationView;
    String shareid;
    private TextView coins;
    public static JCVideoPlayerStandard current_vv;
    User user = new User(); 
    Button add_button;
    Intent myFileIntent;
    private FirebaseStorage firebaseStorage;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_share);
        firebaseStorage.getInstance();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        View view = getSupportActionBar().getCustomView();
        coins = view.findViewById(R.id.coins);
        ff = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        shareRecyclerView = findViewById(R.id.shareRecyclerView);
        final Bundle bundle = getIntent().getExtras();
        shareid = bundle.getString("shareid");
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistner);
        ff = FirebaseFirestore.getInstance();
        tech_list = new ArrayList<>();
        Query query = ff.collection("shares").document(shareid).collection("Bloging");
        FirestoreRecyclerOptions<PostModal> options = new FirestoreRecyclerOptions.Builder<PostModal>().setQuery(query, PostModal.class).build();
        adapter = new FirestoreRecyclerAdapter<PostModal, PostViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, int i, @NonNull final PostModal postModal) {
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


            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneforall, parent, false);
                return new PostViewHolder(view);
            }

        };
        shareRecyclerView.setLayoutManager(new LinearLayoutManager(AboutShare.this));
        shareRecyclerView.setAdapter(adapter);

//        View bottomsheet = findViewById(R.id.bottomsheet);
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
//        //bottomSheetBehavior.setHideable(false);
//        stonksimage = bottomsheet.findViewById(R.id.stonksimage);
//        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                    stonksimage.setRotation(180f);
//                    bottomNavigationView.setVisibility(View.GONE);
//                } else {
//                    stonksimage.setRotation(0f);
//                    bottomNavigationView.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });

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

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView attachfile;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            attachfile=itemView.findViewById(R.id.attachfile);

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
                    Sell dialog_sell = new Sell();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("shareid", shareid);
                    dialog_sell.setArguments(bundle1);
                    dialog_sell.show(getSupportFragmentManager(), "Dialog_Buy");
                    break;

            }
            return false;
        }
    };
    void uploadFile(Uri uri){
        StorageReference storageReference=firebaseStorage.getReference();
        storageReference.child(shareid).child("this blog").putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK&&data!=null) {

                    // Get the Uri of the selected file
                    pdfUri = data.getData();
                    uploadFile(pdfUri);

                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coin_menu, menu);
        return true;
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