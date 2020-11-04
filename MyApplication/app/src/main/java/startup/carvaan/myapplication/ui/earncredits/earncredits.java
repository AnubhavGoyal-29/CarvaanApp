package startup.carvaan.myapplication.ui.earncredits;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import startup.carvaan.myapplication.R;

/**

 */
public class earncredits extends Fragment {
    private ImageView downarrow;
    private TextView earnthroughad;
    private LinearLayout earncreditlayout;
    

   public earncredits()
   {

   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_earncredits, container, false);
        //downarrow= view.findViewById(R.id.downarrow);
        //earncreditlayout=view.findViewById(R.id.earnmorelayout);
        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                earncreditlayout.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
}