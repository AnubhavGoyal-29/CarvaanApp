package startup.carvaan.myapplication;

import android.os.Bundle;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Addactivity extends AppCompatActivity {
    private RecyclerView adds;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        adds=findViewById(R.id.addRecyclerView);
        adds.setLayoutManager(new LinearLayoutManager(this));
        adds.setAdapter((RecyclerView.Adapter) adapter);
    }
}