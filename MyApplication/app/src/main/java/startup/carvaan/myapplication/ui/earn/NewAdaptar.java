package startup.carvaan.myapplication.ui.earn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.List;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.user.User;

public class NewAdaptar extends RecyclerView.Adapter<NewAdaptar.ViewHolder> implements RewardedVideoAdListener {
    private List<NewListItems> mData;
    private LayoutInflater mInflater;
    private RewardedVideoAd mRewardedVideoAd;
    private Context context;
    private User user=new User();
    private String[] ids=new String[]{"ca-app-pub-1372656325166770/5659801461",
            "ca-app-pub-1372656325166770/1473144441",
            "ca-app-pub-1372656325166770/1190390643"};


    public NewAdaptar(List<NewListItems> mData, Context mContext) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(mContext);
        this.context=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.redeem_single, parent, false);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        return new ViewHolder(view);
    }
    private void loadRewardedVideoAd(String id) {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(mData.get(position));
        NewListItems currentItem = mData.get(position);
        holder.redeemText.setText("may be its good");
        holder.redeemButton.setText("watch add");
        holder.redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRewardedVideoAd(ids[position%3]);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onRewarded(RewardItem reward) {
        user.addEarned(reward.getAmount());
        // Reward the user.
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        user.addEarned(5*3);
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Toast.makeText(context, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(context, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(context, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(context, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
        Toast.makeText(context, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {
        Toast.makeText(context, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView redeemText;
        public Button redeemButton;

        public ViewHolder(View itemView) {
            super(itemView);

            redeemText = (TextView) itemView.findViewById(R.id.Redeemtext);
            redeemButton = (Button) itemView.findViewById(R.id.RedeemNow);
        }
    }

}