package startup.carvaan.myapplication.ui.about.dailogFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import startup.carvaan.myapplication.R;

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.ViewHolder> {
    private List<commentsModal> mData;
    private LayoutInflater mInflater;
    private Context context;

    public commentsAdapter(List<commentsModal> mData, Context context) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public commentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.singlecomment, parent, false);
        return new commentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commentsAdapter.ViewHolder holder, int position) {
        holder.username.setText(display(mData.get(position).username));
        holder.comment.setText(mData.get(position).comment);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username,comment;

        public ViewHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            comment = (TextView) itemView.findViewById(R.id.comment);
        }
    }
    String display(String username){
        String temp = null;
        int i=0;
        for(;i<username.length();i++){
            if(username.charAt(i)=='/'){
                if(username.charAt(i+1)=='/')
                    if(username.charAt(i+2)!='/')
                        temp=username.substring(0,i);
            }
        }
        return temp;
    }
}
