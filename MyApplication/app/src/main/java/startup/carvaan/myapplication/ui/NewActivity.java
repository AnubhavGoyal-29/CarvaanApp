package startup.carvaan.myapplication.ui;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import startup.carvaan.myapplication.NewListItems;
import startup.carvaan.myapplication.R;

public class NewActivity extends AppCompatActivity {

   RecyclerView recyclerView;
   RecyclerView.Adapter mAdaptar;
   RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        recyclerView = findViewById(R.id.newRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        ArrayList<NewListItems> arrayList = new ArrayList<>();

        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));

        mAdaptar = new NewAdaptar(arrayList,this);
        recyclerView.setAdapter(mAdaptar);




    }
}