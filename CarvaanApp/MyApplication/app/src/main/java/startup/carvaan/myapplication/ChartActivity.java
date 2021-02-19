package startup.carvaan.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import startup.carvaan.myapplication.ui.coins.coinModal;

public class    ChartActivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    private static final String TAG = "ChartActivity";

    coinModal coinModal;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mChart = (LineChart) findViewById(R.id.lineChart);
        mChart.setOnChartGestureListener(ChartActivity.this);
        mChart.setOnChartValueSelectedListener(ChartActivity.this);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        FirebaseFirestore ff=FirebaseFirestore.getInstance();
        ff.collection("Coins").document("coins").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                coinModal=value.toObject(coinModal.class);
            }
        });
//        //OPTIONAL FOR CREATING UPPER AND LOWER LIMITS ON GRAPH
//        LimitLine upper_limit = new LimitLine(65f, "Danger");
//        upper_limit.setLineWidth(4f);
//        upper_limit.enableDashedLine(10f, 10f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upper_limit.setTextSize(15f);
//        //OPTIONAL FOR CREATING UPPER AND LOWER LIMITS ON GRAPH
//        LimitLine lower_limit = new LimitLine(35f, "Too Low");
//        lower_limit.setLineWidth(4f);
//        lower_limit.enableDashedLine(10f, 10f, 0f);
//        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        lower_limit.setTextSize(15f);
//        //OPTIONAL FOR CREATING UPPER AND LOWER LIMITS ON GRAPH
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.removeAllLimitLines();
//        leftAxis.addLimitLine(upper_limit);
//        leftAxis.addLimitLine(lower_limit);
//        leftAxis.setAxisMinimum(25f);
//        leftAxis.setAxisMaximum(25f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0);
//        leftAxis.setDrawLimitLinesBehindData(true);
//
//        //OPTIONAL FOR CREATING UPPER AND LOWER LIMITS ON GRAPH
//        mChart.getAxisLeft().setEnabled(false);

        Map<String,String >map=new HashMap<>();
        map.putAll(coinModal.getGraph());
        ArrayList<Entry> yValues = new ArrayList<>();
        for (Map.Entry<String,String> entry : map.entrySet()){
            yValues.add(new Entry(Integer.valueOf(entry.getKey()),Integer.valueOf(entry.getValue())));
        }

        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED); //optional
        set1.setLineWidth(3f); //optional
        set1.setValueTextSize(10f);//optional
        set1.setValueTextColor(Color.GREEN);//optional
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);

    }

    //optional class for writing custom x axis headers
    public class MyXAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        Log.i(TAG, "onChartGestureStart: X:" + me.getX() + "Y: " + me.getY());

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        Log.i(TAG, "onChartGestureEnd: " + lastPerformedGesture);

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i(TAG, "onChartLongPressed: ");

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i(TAG, "onChartDoubleTapped: ");

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i(TAG, "onChartSingleTapped: ");

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

        Log.i(TAG, "onChartFling: veloX: " + velocityX + "VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i(TAG, "onChartScale: ScaleX: " + scaleX + "ScaleY: " + scaleY);

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i(TAG, "onChartTranslate: dX: " + dX + "dY: " + dY);

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        Log.i(TAG, "onValueSelected: " + e.toString());

    }

    @Override
    public void onNothingSelected() {

        Log.i(TAG, "onNothingSelected: ");

    }
}