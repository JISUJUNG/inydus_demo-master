package com.sopt.inydus_demo.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sopt.inydus_demo.R;

import butterknife.ButterKnife;

public class UserTypeActivity extends AppCompatActivity {

    Button btnSitter, btnParent;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        btnSitter = (Button)findViewById(R.id.btnSitter_type);
        btnParent = (Button)findViewById(R.id.btnParent_type);
        selectType();

        ButterKnife.bind(this);
    }

    private void selectType() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnSitter_type :
                        type = "Sitter";
                        break;
                    case R.id.btnParent_type :
                        type = "Parent";
                        break;
                }

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        };

        btnSitter.setOnClickListener(listener);
        btnParent.setOnClickListener(listener);
    }
}
