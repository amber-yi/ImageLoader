package com.amber.simple.transformations;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amber.simple.R;

/**
 * 详情请看https://github.com/wasabeef/glide-transformations 3.7.0版本的commit
 */
public class TransformationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformations);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<TransformationsAdapter.Type> dataSet = new ArrayList<>();
        dataSet.add(TransformationsAdapter.Type.Mask);
        dataSet.add(TransformationsAdapter.Type.NinePatchMask);
        dataSet.add(TransformationsAdapter.Type.CropTop);
        dataSet.add(TransformationsAdapter.Type.CropCenter);
        dataSet.add(TransformationsAdapter.Type.CropBottom);
        dataSet.add(TransformationsAdapter.Type.CropSquare);
        dataSet.add(TransformationsAdapter.Type.CropCircle);
        dataSet.add(TransformationsAdapter.Type.ColorFilter);
        dataSet.add(TransformationsAdapter.Type.Grayscale);
        dataSet.add(TransformationsAdapter.Type.RoundedCorners);
        dataSet.add(TransformationsAdapter.Type.Blur);
        dataSet.add(TransformationsAdapter.Type.Toon);
        dataSet.add(TransformationsAdapter.Type.Sepia);
        dataSet.add(TransformationsAdapter.Type.Contrast);
        dataSet.add(TransformationsAdapter.Type.Invert);
        dataSet.add(TransformationsAdapter.Type.Pixel);
        dataSet.add(TransformationsAdapter.Type.Sketch);
        dataSet.add(TransformationsAdapter.Type.Swirl);
        dataSet.add(TransformationsAdapter.Type.Brightness);
        dataSet.add(TransformationsAdapter.Type.Kuawahara);
        dataSet.add(TransformationsAdapter.Type.Vignette);

        recyclerView.setAdapter(new TransformationsAdapter(this, dataSet));
    }
}
