package startup.carvaan.myapplication.ui.about.dailogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import startup.carvaan.myapplication.R;

public class dialog_buy_success extends DialogFragment {
    private TextView shares;
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_dialog_buy_success,null,false);
        Bundle bundle=getArguments();
        final String nos=bundle.getString("nos");
        shares=view.findViewById(R.id.successshares);
        shares.setText("+"+nos);
        builder.setView(view);
        return builder.create();
    }
}