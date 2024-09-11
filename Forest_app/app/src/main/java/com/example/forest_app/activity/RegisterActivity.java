package com.example.forest_app.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.forest_app.R;
import com.example.forest_app.api.ApiManager;
import com.example.forest_app.form.AuthForm;
import com.example.forest_app.form.RegisterForm;
import com.example.forest_app.form.ResponseForm;
import com.example.forest_app.utils.LocalDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ApiManager apiManager = new ApiManager();
    private LocalDatabase ldb;
    private RegisterForm form = new RegisterForm();
    private Button summitButton;
    private TextView nameText;
    private TextView birthText;
    private Spinner diseaseType;
    private TextView phoneNumText;
    private TextView emailText;
    private LinearLayout layout;
    private boolean isFirstPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //initialize
        ldb = LocalDatabase.getInstance(this);
        summitButton = findViewById(R.id.register_summit_button);
        nameText = findViewById(R.id.register_name_text);
        birthText = findViewById(R.id.register_birth_text);
        diseaseType = findViewById(R.id.register_type_dropbox);
        phoneNumText = findViewById(R.id.register_phone_num_text);
        emailText = findViewById(R.id.register_email_text);
        layout = findViewById(R.id.register_layout);

        // should deleted
        Button skipButton = findViewById(R.id.register_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthForm token = new AuthForm((long)1, "972872889e790e0813606ff2a82efbefea6a8da6d14a16ce8c3f74e86236c520");
                LocalDatabase.getInstance(getApplicationContext()).putAuthForm("token", token);
                finish();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.disease_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diseaseType.setAdapter(adapter);

        summitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String birth = birthText.getText().toString();
                String phoneNum = phoneNumText.getText().toString();
                String email = emailText.getText().toString();
                String type = isFirstPage ?
                        diseaseType.getSelectedItem().toString() :
                        null;

                if(!isValidName(name)){
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isValidDate(birth)){
                    Toast.makeText(getApplicationContext(), "날짜를 YYYY-MM-DD 형식으로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isNumeric(phoneNum)){
                    Toast.makeText(getApplicationContext(), "번호를 공백, 구분자 없이 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isValidEmail(email)){
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isFirstPage && type.equals("실어증 유형 선택")){
                    Toast.makeText(getApplicationContext(), "실어증 유형을 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isFirstPage) {
                    form.setName(name);
                    form.setBirth(birth);
                    form.setDiseaseType(type); // nullable
                    form.setPhoneNum(phoneNum);
                    form.setEmail(email);
                    Log.d("RegisterForm", "first page done: "+form);
                    loadSecondPage();
                }
                else{
                    form.setFamName(name);
                    form.setFamBirth(birth);
                    form.setFamPhoneNum(phoneNum);
                    form.setFamEmail(email);
                    Log.d("RegisterForm", "second page done: "+form);
                    createUser();
                    finish();
                }
            }
        });
    }

    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // 엄격한 검증
        try {
            sdf.parse(dateStr); // 문자열을 Date로 변환 시도
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z.]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private boolean isValidName(String str) {
        // 한글 공백문자 +(영어) 허용
        String specialCharactersPattern = "^[a-zA-Z가-힣 ]+$";
        return str.matches(specialCharactersPattern);
    }

    private void loadSecondPage(){
        isFirstPage = false;
        TextView removeView = findViewById(R.id.register_type_header);
        layout.removeView(removeView);
        layout.removeView(diseaseType);

        nameText.setText("");
        birthText.setText("");
        phoneNumText.setText("");
        emailText.setText("");
        summitButton.setText("재출");

        Button button = new Button(getApplicationContext());
        button.setText("보호자 등록 건너뛰기");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                finish();
            }
        });
        layout.addView(button);
    }

    private void createUser(){
        Call<AuthForm> call = apiManager.getApiService().createUser(form);
        call.enqueue(new Callback<AuthForm>() {
            @Override
            public void onResponse(Call<AuthForm> call, Response<AuthForm> response) {
                if(response.isSuccessful()){
                    AuthForm authForm = response.body();
                    ldb.putAuthForm("token", authForm);
                }
                else{
                    Log.e("RegisterAcrivity", "http fail code: "+response.code()+"\n"+
                            "request info: "+call.request());
                    Toast.makeText(getApplicationContext(), "회원가입 실패. 다시 시도해 주세요", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthForm> call, Throwable t) {
                Log.e("RegisterAcrivity", "network error: "+t.getMessage()+"\n"+
                        "request info: "+call.request());
                Toast.makeText(getApplicationContext(), "회원가입 실패. 다시 시도해 주세요", Toast.LENGTH_LONG).show();
            }
        });
    }
}