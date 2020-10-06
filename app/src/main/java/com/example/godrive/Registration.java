package com.example.godrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Registration extends AppCompatActivity {
    Usuario user;
    private EditText editTextNombre;
    private EditText editTextPhone;
    private EditText Password;
    private EditText Password2,editTextCountryCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextNombre = (EditText)findViewById(R.id.editextName);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        Password = (EditText)findViewById(R.id.editextPasword);
        Password2 = (EditText)findViewById(R.id.editextConfirm);
        editTextCountryCode = findViewById(R.id.editTextCountryCode);
        editTextPhone.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});
    }


    public void AddUser(View view){
        user = new Usuario();
        String password = Password.getText().toString();
        String password2 = Password2.getText().toString();
        String name = editTextNombre.getText().toString();
        String phone = editTextPhone.getText().toString();

        if(password.equals(password2)) {
            if(TextUtils.isEmpty(name)) {
                editTextNombre.setError("Agrega un nombre válido");
                editTextNombre.requestFocus();
                return;
            }
            else if(TextUtils.isEmpty(phone)||phone.length()!=10) {
                editTextPhone.setError("Agrega 10 dígitos");
                editTextPhone.requestFocus();
                return;
            }
            else {
                String code = editTextCountryCode.getText().toString().trim();
                String number = editTextPhone.getText().toString().trim();
                String phoneNumber = code + number;
                Intent intent = new Intent(Registration.this, VerifyPhoneActivity.class);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("password", Password.getText().toString());
                intent.putExtra("name", editTextNombre.getText().toString());
                password = getIntent().getStringExtra("password");
                name = getIntent().getStringExtra("name");
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, "Contraseña de confirmación incorrecta",Toast.LENGTH_LONG).show();
        }
    }
}