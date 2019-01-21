package com.example.qlangeveld.testit;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // setting the top chart
        setTopChart();


        // setting the bottom chart
       setBottomChart();
    }


    public void setTopChart() {
        // retrieve data
        EntryDatabase db = EntryDatabase.getInstance(this);
        Cursor pieCursor = db.selectPieChart();





        // fill the pie chart
        AnyChartView topChart = findViewById(R.id.topChart);

        Pie pie = AnyChart.pie();


        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("John", 10000));
//        data.add(new ValueDataEntry("Jake", 12000));
//        data.add(new ValueDataEntry("Peter", 18000));

        pie.data(data);

        topChart.setChart(pie);
    }


    public void setBottomChart() {
        // retrieve data
        // cursor request



        // fill the graph
        AnyChartView bottomChart = findViewById(R.id.bottomChart);
        // TODO
    }
}
