package startup.carvaan.myapplication.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import startup.carvaan.myapplication.NewListItems;
import startup.carvaan.myapplication.R;

public class NewActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        ArrayList<NewListItems> arrayList = new ArrayList<>();

        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));
        arrayList.add(new NewListItems("Sample text","Sample button"));


        listView = findViewById(R.id.newList);

        NewAdaptar newAdaptar = new NewAdaptar(getApplicationContext(),arrayList);
        listView.setAdapter(newAdaptar);

    }
}