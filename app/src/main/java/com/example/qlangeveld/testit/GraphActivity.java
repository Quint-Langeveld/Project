package com.example.qlangeveld.testit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private String challenge;
    private EntryDatabase db = EntryDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Intent intent = getIntent();
        challenge = (String) intent.getSerializableExtra("challenge");

        TextView name = findViewById(R.id.name);
        name.setText(challenge);

        // setting the top chart
        setTopChart();


        // setting the bottom chart
        setBottomChart();
    }


    public void setTopChart() {
        // retrieve data
        Cursor pieCursor = db.selectPieChart(challenge);

        int lengthSucces = 0;
        int lengthFail = 0;

        if (pieCursor.moveToFirst()) {
            do {
                int status = pieCursor.getInt(pieCursor.getColumnIndex("succeeded"));
                if (status == 1) {
                    lengthSucces += 1;
                } else {
                    lengthFail += 1;
                }
            } while (pieCursor.moveToNext());
        }
        pieCursor.close();


        // fill the pie chart
        AnyChartView topChart = findViewById(R.id.topChart);
        topChart.setBackgroundColor("#11AABB");
        Pie pie = AnyChart.pie();


        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Succeeded", lengthSucces));
        data.add(new ValueDataEntry("Failed", lengthFail));

        pie.data(data);

        pie.background("#11AABB");

        pie.legend()
                .position("right")
                .itemsLayout(LegendLayout.VERTICAL)
                .align(Align.RIGHT);

        topChart.setChart(pie);
    }


    public void setBottomChart() {
        // retrieve data
        Cursor feelingCursor = db.selectFeeling(challenge);

        List<Integer> ratings = new ArrayList<>();
        List<Integer> xPositions = new ArrayList<>();

        if (feelingCursor.moveToFirst()) {
            int counter = 0;
            do {

                int feel = feelingCursor.getInt(feelingCursor.getColumnIndex("feeling"));
                ratings.add(feel);
                xPositions.add(counter);

                counter += 1;

            } while (feelingCursor.moveToNext());
        }
        feelingCursor.close();


        int size = ratings.size();
        DataPoint[] data = new DataPoint[size];

        for (int i=0; i<size; i++) {
            int y = ratings.get(i);
            int x = xPositions.get(i);
            DataPoint newDataPoint = new DataPoint(x, y);

            data[i] = newDataPoint;
        }


        // and fill the graph!
        GraphView graph = findViewById(R.id.bottomGraph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        series.setThickness(25);

        graph.addSeries(series);

    }

}
