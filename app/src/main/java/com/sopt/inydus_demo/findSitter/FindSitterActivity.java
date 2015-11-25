package com.sopt.inydus_demo.findSitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sopt.inydus_demo.R;

public class FindSitterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_sitter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_findSitter);
        if (toolbar != null) {
            toolbar.setTitle("놀이선생님 찾기");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.findsitter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_condition){
            Intent intent = new Intent(getApplicationContext(), ConditionActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
