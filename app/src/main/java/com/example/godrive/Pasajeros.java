package com.example.godrive;

import java.sql.ResultSet;
import java.util.Objects;


//SELECT origen[2] as LAT, origen[1] as LONG FROM viajes
public class Pasajeros extends _Default {
    private int id_pasajero; //PK
    private int id_usuario;

    public Pasajeros(){
        super();
        this.id_pasajero = -1; //PK
        this.id_usuario = -1;
    }

    public Pasajeros(int id_pasajero, int id_usuario){
        super();
        this.id_pasajero = id_pasajero; //PK
        this.id_usuario = id_usuario;
    }

    //SET AND GET
    public int getId_pasajero() { return id_pasajero; }
    public void setId_pasajero(int id) { this.id_pasajero = id; }
    public int getId_usuario() { return id_usuario;}
    public void setId_usuario(String id_usuario) {this.id_usuario = id_pasajero; }

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
    public void NewPasajero(int id_pasajero, int id_usuario){
        String comando;
        comando = String.format( "INSERT INTO PASAJEROS (id_pasajero,id_usuario) "+
                "VALUES(%x,%x);", id_pasajero,id_usuario);
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
        db.disconecta();
    }
    public String MaxPasajero(){
        DB db = new DB();
        String name="";
        int ter;
        try {
            ResultSet resultSet = db.select("SELECT max(id_pasajero) from pasajeros");
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
