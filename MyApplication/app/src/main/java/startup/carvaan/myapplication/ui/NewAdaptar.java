package startup.carvaan.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import startup.carvaan.myapplication.NewListItems;
import startup.carvaan.myapplication.R;

public class NewAdaptar extends RecyclerView.Adapter<NewAdaptar.ViewHolder> {
    private List<NewListItems> mData;
    private LayoutInflater mInflater;

    public NewAdaptar(List<NewListItems> mData, Context mContext) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.redeem_single, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(mData.get(position));
        NewListItems currentItem = mData.get(position);

        holder.redeemText.setText("may be its good");
        holder.redeemButton.setText("watch add");

    }
    @Override
    public int getItemCount() {
        return mData.size();
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