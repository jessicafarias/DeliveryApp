package com.example.godrive;

import android.content.Context;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.Objects;

public class Usuario extends _Default {

    private int id_usuario; //PK
    private String nombre;
    private String password;
    private String telefono;
    private boolean confirmado;
    private int tipo;

    public Usuario(){
        super();
        this.id_usuario = -1; //PK
        this.nombre = "";
        this.password = "";
        this.telefono = "";
        this.confirmado = false;
        this.tipo = -1;
    }

    //SET AND GET
    public int getId() { return id_usuario; }
    public void setId(int id) { this.id_usuario = id; }
    public String getNombre() { return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTelefono() { return telefono; }
    public void setTelefone(String telefono) {this.telefono = telefono;}
    public void setConfirmado(Boolean confirmado) { this.confirmado = confirmado; }
    public Boolean getConfirmado() { return confirmado ;}
    public void setTipo(int tipo) { this.tipo = tipo; }
    public int getTipo() { return tipo ;}

    //METHODS
    public int GetIdRegisteredUser(String telefono, String password){
        DB db = new DB();
        int ter = 0;
        try {
            ResultSet resultSet = db.select(String.format("select*from usuarios WHERE telefono = '%s'", telefono));
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("id_usuario"));

                    if (Objects.equals(password,resultSet.getString("contraseña")))
                    {
                        ter=obj.getId();
                        return ter;
                    }
                }
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        db.disconecta();
        return  ter;
    }
    public void NewUser(int id, String name, String password, String phone, Boolean confirm, int type){
        String comando;
        comando = String.format( "INSERT INTO usuarios (id_usuario, nombre, contraseña, telefono, confirmado, tipo)"+
                "VALUES (%x,'%s','%s', '%s','%b', %x);", id,name,password,phone,confirm,type);
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
        db.disconecta();
    }
    public String UserMax(){
        DB db = new DB();
        String name="";
        int ter;
        try {
            ResultSet resultSet = db.select("SELECT max(id_usuario) from usuarios");
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("max"));
                    ter=obj.getId()+1;
                    name = String.valueOf(ter);
                }
            }
            else
            {
                name="1";
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        return  name;
    }


}
