package com.yuanye.t12_cit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuanye.t12_cit.Adapter.MainAdapter;
import com.yuanye.t12_cit.constant.YC;
import com.yuanye.t12_cit.system.MyApplication;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public final String tag = "MainActivity";

    private ListView listView;
    private MainAdapter mainAdapter;
    private List<String> testNames, testResults;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_main);
        init();
    }

    private void init() {
//        testNames = YY.getTestItems(this);
//        testResults = YY.getTestResults(this);
//        Log.d(tag, testResults.toString());
        mainAdapter = new MainAdapter(this, MyApplication.testNames, MyApplication.testReults);
        listView.setAdapter(mainAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_1:
                break;
            case R.id.action_2:
                break;
            case R.id.action_3:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try{
            Intent intent = new Intent(this, YC.classes[i]);
            startActivityForResult(intent, 0);
        }catch (Exception e){

        }

    }
}
