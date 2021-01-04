package startup.carvaan.myapplication.ui.coins;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class coinModal {
    FirebaseFirestore ff=FirebaseFirestore.getInstance();
    Map<String, String> graph;
    String value;

    public Map<String, String> getGraph() {
        return graph;
    }

    public void setGraph(Map<String, String> graph) {
        this.graph = graph;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public coinModal() {
        ff.collection("Coins").document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                setValue(value.getString("value"));
            }
        });
    }

    public coinModal(Map<String, String> graph, String value) {
        this.graph = graph;
        this.value = value;
    }
}
