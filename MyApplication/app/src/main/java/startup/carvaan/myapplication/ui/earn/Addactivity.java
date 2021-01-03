package startup.carvaan.myapplication.ui.earn;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    private RewardedAd rewardedAd1,rewardedAd2,rewardedAd3,rewardedAd4,rewardedAd5,rewardedAd6;
    ListView listView;
    private CardView addl1,addl2,addl3,addl4,addl5,addl6;
    Button add1,add2,add3,add4,add5,add6;
    User user=new User();
    String[] id={
            "ca-app-pub-1372656325166770/4738696917",
            "ca-app-pub-1372656325166770/5044052317",
            "ca-app-pub-1372656325166770/8517211705",
            "ca-app-pub-1372656325166770/2417888971",
            "ca-app-pub-1372656325166770/8565313162",
            "ca-app-pub-1372656325166770/6302305422"
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        addl1=findViewById(R.id.addl1);
        addl2=findViewById(R.id.addl2);
        addl3=findViewById(R.id.addl3);
        addl4=findViewById(R.id.addl4);
        addl5=findViewById(R.id.addl5);
        addl6=findViewById(R.id.addl6);
        add1=findViewById(R.id.add1watch);
        add2=findViewById(R.id.add2watch);
        add3=findViewById(R.id.add3watch);
        add4=findViewById(R.id.add4watch);
        add5=findViewById(R.id.add5watch);
        add6=findViewById(R.id.add6watch);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                rewardedAd1 = new RewardedAd(Addactivity.this,
                        id[0]);
                rewardedAd2 = new RewardedAd(Addactivity.this,
                        id[1]);
                rewardedAd3 = new RewardedAd(Addactivity.this,
                        id[2]);
                rewardedAd4 = new RewardedAd(Addactivity.this,
                        id[3]);
                rewardedAd5 = new RewardedAd(Addactivity.this,
                        id[4]);
                rewardedAd6 = new RewardedAd(Addactivity.this,
                        id[5]);
                RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
                    @Override
                    public void onRewardedAdLoaded() {

                    }

                    @Override
                    public void onRewardedAdFailedToLoad(LoadAdError adError) {
                        // Ad failed to load.
                    }
                };
                rewardedAd1.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                rewardedAd2.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                rewardedAd3.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                rewardedAd4.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                rewardedAd5.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                rewardedAd6.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                rewardedAd1 = createAndLoadRewardedAd(id[0]);
                rewardedAd2 = createAndLoadRewardedAd(id[1]);
                rewardedAd3 = createAndLoadRewardedAd(id[2]);
                rewardedAd4 = createAndLoadRewardedAd(id[3]);
                rewardedAd5 = createAndLoadRewardedAd(id[4]);
                rewardedAd6 = createAndLoadRewardedAd(id[5]);
//                if(rewardedAd1.isLoaded()){
//                    addl1.setVisibility(View.VISIBLE);
//                }
//                else addl1.setVisibility(View.GONE);
//                if(rewardedAd2.isLoaded()){
//                    addl2.setVisibility(View.VISIBLE);
//                }
//                else addl2.setVisibility(View.GONE);
//                if(rewardedAd3.isLoaded()){
//                    addl3.setVisibility(View.VISIBLE);
//                }
//                else addl3.setVisibility(View.GONE);
//                if(rewardedAd4.isLoaded()){
//                    addl4.setVisibility(View.VISIBLE);
//                }
//                else addl4.setVisibility(View.GONE);
//                if(rewardedAd5.isLoaded()){
//                    addl5.setVisibility(View.VISIBLE);
//                }
//                else addl5.setVisibility(View.GONE);
//                if(rewardedAd6.isLoaded()){
//                    addl6.setVisibility(View.VISIBLE);
//                }
//                else addl6.setVisibility(View.GONE);
                add1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd1.isLoaded()) {
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
                                    Toast.makeText(Addactivity.this,adError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    rewardedAd1 = createAndLoadRewardedAd(id[0]);
                                }

                            };
                            rewardedAd1.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
                add2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd2.isLoaded()) {
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
                                    Toast.makeText(Addactivity.this,adError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    rewardedAd2 = createAndLoadRewardedAd(id[1]);
                                }

                            };
                            rewardedAd2.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
                add3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd3.isLoaded()) {
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
                                    Toast.makeText(Addactivity.this,adError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    rewardedAd3 = createAndLoadRewardedAd(id[2]);
                                }

                            };
                            rewardedAd3.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
                add4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd4.isLoaded()) {
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
                                    Toast.makeText(Addactivity.this,adError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    rewardedAd4 = createAndLoadRewardedAd(id[3]);
                                }

                            };
                            rewardedAd4.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
                add5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd5.isLoaded()) {
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
                                    Toast.makeText(Addactivity.this,adError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    rewardedAd5 = createAndLoadRewardedAd(id[4]);
                                }

                            };
                            rewardedAd5.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
                add6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rewardedAd6.isLoaded()) {
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
                                    Toast.makeText(Addactivity.this,adError.getMessage(),Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onRewardedAdClosed() {
                                    rewardedAd6 = createAndLoadRewardedAd(id[5]);
                                }

                            };
                            rewardedAd6.show(activityContext, adCallback);
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                        }
                    }
                });
            }
        });
    }
    public RewardedAd createAndLoadRewardedAd(String id) {
        RewardedAd rewardedAd = new RewardedAd(this,
                id);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                createAndLoadRewardedAd(id);
            }
            @Override
            public void onRewardedAdFailedToLoad(LoadAdError error) {
                    createAndLoadRewardedAd(id);
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
                    Log.d("Ads", error.getMessage());
                Toast.makeText(Addactivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
}

