package startup.carvaan.myapplication.ui.earn;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.user.User;


public class Addactivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RewardedAd rewardedAd;
    ListView listView;
    Button adds;
    User user=new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        adds=findViewById(R.id.add);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                rewardedAd = new RewardedAd(Addactivity.this,
                        "ca-app-pub-1372656325166770/4738696917");
                RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                    @Override
                    public void onRewardedAdLoaded() {
                        Toast.makeText(Addactivity.this,"loaded",Toast.LENGTH_LONG).show();
                        createAndLoadRewardedAd();
                    }

                    @Override
                    public void onRewardedAdFailedToLoad(LoadAdError adError) {
                        // Ad failed to load.
                    }
                };
                rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

                adds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd.isLoaded()) {
                            Activity activityContext = Addactivity.this;
                            RewardedAdCallback adCallback = new RewardedAdCallback() {
                                @Override
                                public void onRewardedAdOpened() {
                                    // Ad opened
                                }

                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem reward) {
                                    user.addEarned(Double.valueOf(reward.getAmount()));
                                    // User earned reward.
                                }

                                @Override
                                public void onRewardedAdFailedToShow(AdError adError) {
                                    // Ad failed to display.
                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    rewardedAd = createAndLoadRewardedAd();
                                }

                            };
                            rewardedAd.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
            }
        });




    }
    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                "ca-app-pub-1372656325166770/4738696917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError error) {
                // Ad failed to load.
                    // Gets the domain from which the error came.
                    String errorDomain = error.getDomain();
                    // Gets the error code. See
                    // https://developers.google.com/android/reference/com/google/android/gms/ads/AdRequest#constant-summary
                    // for a list of possible codes.
                    int errorCode = error.getCode();
                    // Gets an error message.
                    // For example "Account not approved yet". See
                    // https://support.google.com/admob/answer/9905175 for explanations of
                    // common errors.
                    String errorMessage = error.getMessage();
                    // Gets additional response information about the request. See
                    // https://developers.google.com/admob/android/response-info for more
                    // information.
                    ResponseInfo responseInfo = error.getResponseInfo();
                    // Gets the cause of the error, if available.
                    AdError cause = error.getCause();
                    // All of this information is available via the error's toString() method.
                    Log.d("Ads", error.toString());


            }

        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }

    // ...
}

