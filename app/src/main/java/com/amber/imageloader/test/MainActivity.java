package com.amber.imageloader.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import com.amber.imageloader.R;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<MainAdapter.Type> dataSet = new ArrayList<>();
        dataSet.add(MainAdapter.Type.Mask);
        dataSet.add(MainAdapter.Type.NinePatchMask);
        dataSet.add(MainAdapter.Type.CropTop);
        dataSet.add(MainAdapter.Type.CropCenter);
        dataSet.add(MainAdapter.Type.CropBottom);
        dataSet.add(MainAdapter.Type.CropSquare);
        dataSet.add(MainAdapter.Type.CropCircle);
        dataSet.add(MainAdapter.Type.ColorFilter);
        dataSet.add(MainAdapter.Type.Grayscale);
        dataSet.add(MainAdapter.Type.RoundedCorners);
        dataSet.add(MainAdapter.Type.Blur);
        dataSet.add(MainAdapter.Type.Toon);
        dataSet.add(MainAdapter.Type.Sepia);
        dataSet.add(MainAdapter.Type.Contrast);
        dataSet.add(MainAdapter.Type.Invert);
        dataSet.add(MainAdapter.Type.Pixel);
        dataSet.add(MainAdapter.Type.Sketch);
        dataSet.add(MainAdapter.Type.Swirl);
        dataSet.add(MainAdapter.Type.Brightness);
        dataSet.add(MainAdapter.Type.Kuawahara);
        dataSet.add(MainAdapter.Type.Vignette);

        recyclerView.setAdapter(new MainAdapter(this, dataSet));
    }
}
