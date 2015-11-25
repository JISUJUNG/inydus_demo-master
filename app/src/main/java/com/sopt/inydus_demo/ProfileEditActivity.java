package com.sopt.inydus_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.Bind;

public class ProfileEditActivity extends AppCompatActivity {

    @Bind(R.id.editName)
    EditText editName;
    @Bind(R.id.editAge)
    EditText editAge;
    @Bind(R.id.editArea)
    EditText editArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        editName.setText("");
        editAge.setText("");
        editArea.setText("");
    }
}
