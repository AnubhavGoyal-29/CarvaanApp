package startup.carvaan.myapplication.ui.about.dailogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import startup.carvaan.myapplication.R;
public class sell_success extends DialogFragment {
    private ImageView closesell;
    private TextView sellsuccess;
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle bundle=getArguments();

        final View view = inflater.inflate(R.layout.fragment_sell_success,null,false);

        sellsuccess=view.findViewById(R.id.sellsuccess);
        sellsuccess.setText("+"+ "Rci "+bundle.getString("credits"));
        closesell=view.findViewById(R.id.closesell);
        closesell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}
