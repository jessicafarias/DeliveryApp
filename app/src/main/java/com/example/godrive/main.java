package com.example.godrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class main extends AppCompatActivity {
    Usuario usuario = new Usuario();
    private EditText editTextNombre;
    private EditText editTextContraseña;
    private Button btnSignIn, btnRegister;
    int integer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNombre = (EditText)findViewById(R.id.editTMainUsuario);
        editTextContraseña = (EditText)findViewById(R.id.editTMainContraseña);
        btnSignIn = (Button)findViewById(R.id.SignIn);
        btnRegister = (Button) findViewById(R.id.btnRegister) ;

        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                this.editTextNombre.setText(bundle.getString("nome"));
                this.editTextContraseña.setText(bundle.getString("contraseña"));
            }
        }

    }

    public void SignIn(View view){
        integer = usuario.GetIdRegisteredUser(editTextNombre.getText().toString(), editTextContraseña.getText().toString());
        if (integer != 0) {
            Toast.makeText(main.this, "Bienvenenido :)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapsActivity.class);
            //intent.putExtra("UsuarioMain", editTextNombre.getText().toString());
            //intent.putExtra("ContraseñaMain", editTextContraseña.getText().toString());
            intent.putExtra("IDUSUARIO", integer);
            startActivity(intent);
        } else {
            Toast.makeText(main.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

        }
    }

    public void Register(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);

    }

}