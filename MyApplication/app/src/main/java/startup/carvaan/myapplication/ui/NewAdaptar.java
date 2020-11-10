package startup.carvaan.myapplication.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import startup.carvaan.myapplication.NewListItems;
import startup.carvaan.myapplication.R;

public class NewAdaptar extends ArrayAdapter<NewListItems> {
    public NewAdaptar(@NonNull Context context, @NonNull List<NewListItems> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.new_single_view, parent, false);
        }

        NewListItems currentItem = getItem(position);
        TextView newText = listItemView.findViewById(R.id.newText);
        newText.setText(currentItem.getmTextViewText());

        Button newBtn = listItemView.findViewById(R.id.newBtn);
        newBtn.setText(currentItem.getmBtnText());

        return listItemView;

    }
}
