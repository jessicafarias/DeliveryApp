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

public class Registration extends AppCompatActivity {
    Usuario user;
    private EditText editTextNombre;
    private EditText editTextPhone;
    private EditText Password;
    private EditText Password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextNombre = (EditText)findViewById(R.id.editextName);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        Password = (EditText)findViewById(R.id.editextPasword);
        Password2 = (EditText)findViewById(R.id.editextConfirm);
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
                return;
            }
            else if(TextUtils.isEmpty(phone)) {
                editTextPhone.setError("Agrega 10 dígitos");
                return;
            }
            else {
                try {
                    int a = Integer.parseInt(this.user.UserMax());
                    this.user.NewUser(a, name, password, phone, false, 1);
                    Toast.makeText(this,"NUEVO USUARIO CREADO", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    String error =e.getMessage();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
            Toast.makeText(this, "Contraseña de confirmación incorrecta",Toast.LENGTH_LONG).show();
        }

    }
}