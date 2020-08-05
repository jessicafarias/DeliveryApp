package com.example.godrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class main extends AppCompatActivity {

    private Usuario usuario;
    private EditText editTextNombre;
    private EditText editTextContraseña;
    int integer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNombre = (EditText)findViewById(R.id.editTMainUsuario);
        editTextContraseña = (EditText)findViewById(R.id.editTMainContraseña);

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
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void OpenList(View view){
        integer = usuario.NameExist(editTextNombre.getText().toString(), editTextContraseña.getText().toString());
        if(integer !=0){
            //if(true)

            Toast.makeText(main.this, "Bienvenenido :)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("UsuarioMain", editTextNombre.getText().toString());
            intent.putExtra("ContraseñaMain", editTextContraseña.getText() .toString());
            intent.putExtra("IDUSUARIO",integer);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(MainActivity.this, "Contraseña incorrecta"+bo, Toast.LENGTH_SHORT).show();

        }
    }

    public void abreLista(View view){
        //this.usuario.setNombre(this.editTextNombre.getText().toString());
        //this.usuario.setContra(this.editTextContras.getText().toString());
        //boolean bo = this.usuario.sal("socio");


    }
}