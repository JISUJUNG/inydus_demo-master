package com.sopt.inydus_demo.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sopt.inydus_demo.R;
import com.sopt.inydus_demo.model.User;
import com.sopt.inydus_demo.network.NetworkService;
import com.sopt.inydus_demo.network.ServiceGenerator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private String type;
    private Boolean nameValid= false, emailValid = false, pwValid= false, addressValid= false, phoneNumValid= false;
    private Boolean inputValid = true;

    @Bind(R.id.editName_reg)
    EditText editName;
    @Bind(R.id.editEmail_reg)
    EditText editEmail;
    @Bind(R.id.editPW_reg)
    EditText editPW;
    @Bind(R.id.editPW_test)
    EditText editPW_test;
    @Bind(R.id.editAddress_reg)
    EditText editAddress;
    @Bind(R.id.editAddress_detail)
    EditText editAddress_detail;
    @Bind(R.id.editPhoneNum_reg)
    EditText editPhoneNum;
    @Bind(R.id.editVerification)
    EditText editVerification;

    /*@Bind(R.id.btnDuplication) Button btnDuplication;
    @Bind(R.id.btnAddress_reg) Button btnAddress;
    @Bind(R.id.btnVerification) Button btnVerification;
    @Bind(R.id.btnRegister_reg) Button btnRegister;*/

    NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        ButterKnife.bind(this);
        checkEmailAndPW();
    }



    @OnClick(R.id.btnDuplication)
    public void setBtnDuplication() {
        String email = editEmail.getText().toString();

        if(TextUtils.isEmpty(email)) editEmail.setError(getString(R.string.error_field_required));
        else{
            if(!isEmailValid(email)) editEmail.setError(getString(R.string.error_invalid_email));
            else emailValid = true;
        }

        // TODO: 이메일 중복 서버 이용해서 확인

        networkService = ServiceGenerator.createService(NetworkService.class);
        Call<Boolean> duplicationCall = networkService.duplicationTest(email);
        duplicationCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Response<Boolean> response, Retrofit retrofit) {
                Boolean isDuplicated = response.body();
                if (isDuplicated) {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @OnClick(R.id.btnAddress_reg)
    public void setBtnAddress() {
        // TODO: 우편번호 api 이용해서 만들어야 함
    }

    @OnClick(R.id.btnVerification)
    public void setBtnVerification() {
        phoneNumValid = true;
        // TODO: 이것도 인증 api가 있을 듯
    }

    @OnClick(R.id.btnRegister_reg)
    public void setBtnRegister() {

        checkEditText();

        if(nameValid && emailValid && pwValid && addressValid && phoneNumValid){
            sendData();
            Toast.makeText(getApplicationContext(),"회원가입 완료", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendData() {
        User user = new User();
        user.user_id = editEmail.getText().toString();
        user.user_name = editName.getText().toString();
        user.user_address = editAddress.getText().toString() + " " + editAddress_detail.getText().toString();
        user.user_phone_number = editPhoneNum.getText().toString();
        user.user_type = type;
        networkService = ServiceGenerator.createService(NetworkService.class);
        Call<User> registerCall = networkService.registerUser(user);
        registerCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                } else {
                    Log.i("MyTag", "" + response.code());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("MyTag", t.getMessage());
            }
        });
    }

    private void checkEditText() {
        String name = editName.getText().toString();
        String pw = editPW.getText().toString();
        String address = editAddress.getText().toString();
        String address_detail = editAddress_detail.getText().toString();

        if(TextUtils.isEmpty(name)) editName.setError(getString(R.string.error_field_required));
        else{
            if(!isNameValid(name)) editName.setError(getString(R.string.error_invalid_name));
            else nameValid = true;
        }

        if(TextUtils.isEmpty(pw)) editPW.setError(getString(R.string.error_field_required));
        else{
            if(!isPasswordValid()) editPW.setError(getString(R.string.error_invalid_password));
            else{
                if(!isPasswordCheck()) editPW_test.setError(getString(R.string.error_incorrect_password));
                else pwValid = true;
            }
        }

        if(TextUtils.isEmpty(address)) editAddress.setError(getString(R.string.error_field_required));
        else{
            if(TextUtils.isEmpty(address_detail)) editAddress_detail.setError(getString(R.string.error_field_required));
            else addressValid = true;
        }

        if(!nameValid) Toast.makeText(getApplicationContext(),"이름을 확인해주세요.", Toast.LENGTH_SHORT).show();
        else if(!emailValid) Toast.makeText(getApplicationContext(),"이메일을 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show();
        else if(!pwValid) Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        else if(!addressValid) Toast.makeText(getApplicationContext(),"주소를 확인해주세요.", Toast.LENGTH_SHORT).show();
        else if(!phoneNumValid) Toast.makeText(getApplicationContext(),"전화번호 인증을 해주세요.", Toast.LENGTH_SHORT).show();

    }



    private void checkEmailAndPW() {
        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String text = ((EditText) v).getText().toString();
                    if(v.getId() == R.id.editEmail_reg){
                        if(!isEmailValid(text)){
                            editEmail.setError(getString(R.string.error_invalid_email));
                        }
                    }
                    else if(v.getId() == R.id.editPW_reg){
                        if(!isPasswordValid()){
                            editPW.setError(getString(R.string.error_invalid_password));
                        }
                    }
                }
            }
        };
        editEmail.setOnFocusChangeListener(focusChangeListener);
        editPW.setOnFocusChangeListener(focusChangeListener);
    }

    private boolean isNameValid(String name) {
        return name.length() >= 2;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid() {
        return editPW.getText().toString().length() >= 3;
    }
    private boolean isPasswordCheck() {
        return editPW.getText().toString().equals(editPW_test.getText().toString());
    }


}
