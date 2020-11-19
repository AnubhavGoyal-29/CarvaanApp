package startup.carvaan.myapplication.ui.about.dailogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import startup.carvaan.myapplication.R;
import startup.carvaan.myapplication.ui.user.User;

public class comments extends DialogFragment {
    User user=new User();
    EditText nos;
    Button sell;
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.comments,null,false);
        builder.setView(view);
        return builder.create();
    }
}