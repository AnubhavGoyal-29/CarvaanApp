package startup.carvaan.myapplication;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ProgressButton {

    private CardView cardView;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private TextView textView;
    private Animation fade_in;
    private int IS_ENABLED;
    //Context mContext;

    public ProgressButton(Context mContext, View mView) {
        cardView = mView.findViewById(R.id.cardView);
        constraintLayout = mView.findViewById(R.id.constraintLayout);
        progressBar = mView.findViewById(R.id.progressBar);
        textView = mView.findViewById(R.id.textView5);

    }


    public void buttonsetEnabledFalse(Boolean isLoading){
        if (isLoading){
            progressBar.setVisibility(View.VISIBLE);
            textView.setText("PLEASE WAIT..");
        }

        constraintLayout.setBackgroundColor(cardView.getResources().getColor(R.color.disabledBtn));
        IS_ENABLED = -1;

    }

    public void initialPhase(String prompt, Boolean isDisabled){

        if(isDisabled) {
            constraintLayout.setBackgroundColor(cardView.getResources().getColor(R.color.disabledBtn));
            IS_ENABLED = -1;
        }

        textView.setText(prompt);
    }

    public void buttonsetEnabledTrue(String prompt){
        constraintLayout.setBackgroundColor(cardView.getResources().getColor(R.color.enabledBtn));
        textView.setText(prompt);
        progressBar.setVisibility(View.GONE);
        IS_ENABLED = 1;

    }

    public boolean isEnabled(){
        return IS_ENABLED == 1;
    }
}
