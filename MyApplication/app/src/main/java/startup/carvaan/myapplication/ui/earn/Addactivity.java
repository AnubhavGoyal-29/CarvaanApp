package startup.carvaan.myapplication.ui.earn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Random;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ads.personalizedads;
import startup.carvaan.myapplication.ui.user.User;

import static java.lang.Math.abs;

public class Addactivity extends AppCompatActivity {
    User user=new User();
    private Button rewareded,rewarded2,rewarded1;
    private RewardedAd rewardedAd;
    private Button personalized_ads;
    private InterstitialAd interstitialAd;
    String[] id={"ca-app-pub-1372656325166770/4738696917","ca-app-pub-1372656325166770/7713274220","ca-app-pub-1372656325166770/3774029214"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        personalized_ads=findViewById(R.id.personalized_ads);
        personalized_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Addactivity.this, personalizedads.class));
            }
        });
        rewareded=findViewById(R.id.rewarded);
        rewarded2=findViewById(R.id.rewarded2);
        rewarded1=findViewById(R.id.rewarded1);
        interstitialAd=new InterstitialAd(this);
        loadinterestitial();
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-1372656325166770~1018059934");
        rewardedAd=createAndLoadRewardedAd();
        rewareded=findViewById(R.id.rewarded);
        rewareded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.isLoaded()) {
                    Activity activityContext = Addactivity.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                            loadinterestitial();
                        }

                        @Override
                        public void onRewardedAdClosed() {

                            rewardedAd=createAndLoadRewardedAd();
                            interstitialAd.show();
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            Toast.makeText(Addactivity.this,"You got "+reward.getAmount()+" coins",Toast.LENGTH_LONG).show();
                            user.addEarned(Double.valueOf(reward.getAmount()));
                        }

                        @Override
                        public void onRewardedAdFailedToShow(AdError adError) {
                            // Ad failed to display.
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }
        });
        rewarded1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.isLoaded()) {
                    Activity activityContext = Addactivity.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                            loadinterestitial();
                        }

                        @Override
                        public void onRewardedAdClosed() {

                            rewardedAd=createAndLoadRewardedAd();
                            interstitialAd.show();
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            Toast.makeText(Addactivity.this,"You got "+reward.getAmount()+" coins",Toast.LENGTH_LONG).show();
                            user.addEarned(Double.valueOf(reward.getAmount()));
                        }

                        @Override
                        public void onRewardedAdFailedToShow(AdError adError) {
                            // Ad failed to display.
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }
        });
        rewarded2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.isLoaded()) {
                    Activity activityContext = Addactivity.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                            loadinterestitial();
                        }

                        @Override
                        public void onRewardedAdClosed() {

                            rewardedAd=createAndLoadRewardedAd();
                            interstitialAd.show();
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            Toast.makeText(Addactivity.this,"You got "+reward.getAmount()+" coins",Toast.LENGTH_LONG).show();
                            user.addEarned(Double.valueOf(reward.getAmount()));
                        }

                        @Override
                        public void onRewardedAdFailedToShow(AdError adError) {
                            // Ad failed to display.
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }
        });
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
//                Toast.makeText(Addactivity.this,"You got "+1+" coins",Toast.LENGTH_LONG).show();
//                user.addEarned(Double.valueOf(1));
                loadinterestitial();

            }
        });
    }

    public RewardedAd createAndLoadRewardedAd() {
        Random random=new Random();
        int a=random.nextInt();
        int b=abs(a);
        RewardedAd rewardedAd = new RewardedAd(this, id[b%3]);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {

            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {

            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
    void loadinterestitial(){
        interstitialAd.setAdUnitId("ca-app-pub-1372656325166770/9302166994");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
}