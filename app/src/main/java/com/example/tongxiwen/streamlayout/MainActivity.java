package com.example.tongxiwen.streamlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String[] str;
    public List<String> str1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str  = new String[20];
        for (int i = 0; i < 20; i++) {
            str[i] = String.valueOf(i);
        }
        str1 = new ArrayList<>();
        str1.addAll(Arrays.asList(str));

        ArrayAdapter adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str1);
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str1);
        ArrayAdapter adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, str1);

        StreamLayout layout = (StreamLayout) findViewById(R.id.streamLayout);
//        layout.setAdapters(adapter1, adapter2, adapter3);
    }
}
