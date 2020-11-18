package startup.carvaan.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import startup.carvaan.myapplication.ui.NewAdaptar;

public class Addactivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        recyclerView = findViewById(R.id.addRecyclerView);
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

        mAdapter=new NewAdaptar(arrayList,this);
        recyclerView.setAdapter(mAdapter);
    }
    // ...
}

