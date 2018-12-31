package com.zeba.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zeba.spf.ZebaSPF;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZebaSPF.init(getApplication(),"mybook",0);
        BookSpf.get().setBookId(1);
        BookSpf.get().setBookName("124");
        BookSpf.get().save();
    }
}
