package startup.carvaan.myapplication.ui.mainActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import io.paperdb.Paper;
import startup.carvaan.myapplication.BuyCoin;
import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.allshares.allshares;
import startup.carvaan.myapplication.ui.coins.aboutRci;
import startup.carvaan.myapplication.ui.login.LoginActivity;
import startup.carvaan.myapplication.ui.myshares.myshares;
import startup.carvaan.myapplication.ui.navbar.Helppage;
import startup.carvaan.myapplication.ui.payment.payouts;
import startup.carvaan.myapplication.ui.profile.Profile;
import startup.carvaan.myapplication.ui.user.User;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "carvaan";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DrawerLayout drawerLayout;
    FirebaseFirestore ff;
    GestureDetector gestureDetector;
    private ActionBarDrawerToggle toggle;
    private CurvedNavigationBottomView mView;
    private VectorMasterView heartVector;
    private VectorMasterView heartVector1;
    private VectorMasterView heartVector2;
    private float mYVal;
    private RelativeLayout mlinId;
    PathModel outline;
    private ActionBar actionBar;
    TextView coins;
    User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
        new MaterialTapTargetPrompt.Builder(MainActivity.this)
                .setTarget(R.id.n1)
                .setPrimaryText("Hii Carvaan User")
                .setSecondaryText("Go to the How to play page in the navigation drawer by clicking in this icon to know everything about this app")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                               AppDemo();
                        }
                    }
                })
                .show();
        ff=FirebaseFirestore.getInstance();
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("StartupCarvaan");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new allshares()).commit();
        mView= findViewById(R.id.aboutsharebottomnavview);
        heartVector = findViewById(R.id.fab);
        heartVector1 = findViewById(R.id.fab1);
        heartVector2 = findViewById(R.id.fab2);
        mlinId = findViewById(R.id.lin_id);
        mView.inflateMenu(R.menu.bottom_nav_menu);
        mView.setSelectedItemId(R.id.allshares);

        mView.setOnNavigationItemSelectedListener(MainActivity.this);
        NavigationView navigationView =findViewById(R.id.n1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        user.logoutUser();
                        gotoLoginActivity();
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
        TextView textView=view.findViewById(R.id.title);
        textView.setText("StartupCarvaan");
        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.myshares:
                selectedFragment = new myshares();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


                tet(6);
                // find the correct path using name
                mlinId.setX(mView.mFirstCurveControlPoint1.x );
                heartVector.setVisibility(View.VISIBLE);
                heartVector1.setVisibility(View.GONE);
                heartVector2.setVisibility(View.GONE);


                break;
            case R.id.allshares:
                selectedFragment = new allshares();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                tet(2);
                mlinId.setX(mView.mFirstCurveControlPoint1.x );
                heartVector.setVisibility(View.GONE);
                heartVector1.setVisibility(View.VISIBLE);
                heartVector2.setVisibility(View.GONE);



                break;
            case R.id.profile:
                selectedFragment = new Profile();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                tet();
                mlinId.setX(mView.mFirstCurveControlPoint1.x ) ;
                heartVector.setVisibility(View.GONE);
                heartVector1.setVisibility(View.GONE);
                heartVector2.setVisibility(View.VISIBLE);


               
                break;
        }

        return true;
    }
    private void selectAnimation(final VectorMasterView heartVector) {

        outline = heartVector.getPathModelByName("outline");
        outline.setStrokeColor(Color.parseColor("#ffffff"));
        outline.setTrimPathEnd(0.0f);
        // initialise valueAnimator and pass start and end float values
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimator.setDuration(1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // set trim end value and update view
                outline.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
                heartVector.update();
            }
        });
        valueAnimator.start();
    }

    private void tet(int i) {

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        //mNavigationBarHeight = getHeight();
        //mNavigationBarWidth = getWidth();
        // the coordinates (x,y) of the start point before curve
        mView.mFirstCurveStartPoint.set((mView.mNavigationBarWidth / i) - (mView.CURVE_CIRCLE_RADIUS * 2) - (mView.CURVE_CIRCLE_RADIUS / 3), 0);
        // the coordinates (x,y) of the end point after curve
        mView.mFirstCurveEndPoint.set(mView.mNavigationBarWidth / i, mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mView.mSecondCurveStartPoint = mView.mFirstCurveEndPoint;
        mView.mSecondCurveEndPoint.set((mView.mNavigationBarWidth / i) + (mView.CURVE_CIRCLE_RADIUS * 2) + (mView.CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mView.mFirstCurveControlPoint1.set(mView.mFirstCurveStartPoint.x + mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4), mView.mFirstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mView.mFirstCurveControlPoint2.set(mView.mFirstCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS * 2) + mView.CURVE_CIRCLE_RADIUS, mView.mFirstCurveEndPoint.y);

        mView.mSecondCurveControlPoint1.set(mView.mSecondCurveStartPoint.x + (mView.CURVE_CIRCLE_RADIUS * 2) - mView.CURVE_CIRCLE_RADIUS, mView.mSecondCurveStartPoint.y);
        mView.mSecondCurveControlPoint2.set(mView.mSecondCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4)), mView.mSecondCurveEndPoint.y);



    }

    private void tet() {

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        //mNavigationBarHeight = getHeight();
        //mNavigationBarWidth = getWidth();
        // the coordinates (x,y) of the start point before curve
        mView.mFirstCurveStartPoint.set((mView.mNavigationBarWidth * 10/12) - (mView.CURVE_CIRCLE_RADIUS * 2) - (mView.CURVE_CIRCLE_RADIUS / 3), 0);
        // the coordinates (x,y) of the end point after curve

        mView.mFirstCurveEndPoint.set(mView.mNavigationBarWidth  * 10/12, mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mView.mSecondCurveStartPoint = mView.mFirstCurveEndPoint;
        mView.mSecondCurveEndPoint.set((mView.mNavigationBarWidth  * 10/12) + (mView.CURVE_CIRCLE_RADIUS * 2) + (mView.CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mView.mFirstCurveControlPoint1.set(mView.mFirstCurveStartPoint.x + mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4), mView.mFirstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mView.mFirstCurveControlPoint2.set(mView.mFirstCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS * 2) + mView.CURVE_CIRCLE_RADIUS, mView.mFirstCurveEndPoint.y);

        mView.mSecondCurveControlPoint1.set(mView.mSecondCurveStartPoint.x + (mView.CURVE_CIRCLE_RADIUS * 2) - mView.CURVE_CIRCLE_RADIUS, mView.mSecondCurveStartPoint.y);
        mView.mSecondCurveControlPoint2.set(mView.mSecondCurveEndPoint.x - (mView.CURVE_CIRCLE_RADIUS + (mView.CURVE_CIRCLE_RADIUS / 4)), mView.mSecondCurveEndPoint.y);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(!(Paper.book().contains("isFirst"))){
            Paper.book().write("isFirst",true);
        }
    }
    public void AppDemo() {
        new MaterialTapTargetSequence()
                .addPrompt(new MaterialTapTargetPrompt.Builder(MainActivity.this)
                        .setTarget(findViewById(R.id.allshares))
                        .setPrimaryText("All shares page ")
                        .setSecondaryText("Here you can see all the startups that are listed in this app to invest.Invest in it by clicking on invest in me button. ")
                        .create(), 4000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(MainActivity.this)
                        .setTarget(findViewById(R.id.myshares))
                        .setPrimaryText("My share button")
                        .setSecondaryText("Here you can see all the shares in which you invest ")
                        .create(), 4000)
                .addPrompt(new MaterialTapTargetPrompt.Builder(MainActivity.this)
                        .setTarget(findViewById(R.id.profile))
                        .setPrimaryText("Profile")
                        .setSecondaryText("Here you can see  how much coins you have.You can redeem them here or buy coins here to invest more. ")
                        .create(), 4000)
                .show();
    }
}
