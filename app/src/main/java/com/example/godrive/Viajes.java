package com.example.godrive;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.Objects;

public class Viajes extends _Default  {
    private int id_viaje; //PK
    private int id_taxista;
    private int id_pasajero;
    private float precio;
    private float long_origen;
    private float lat_origen;
    private float long_destino;
    private float lat_destino;
    private boolean status;


    public Viajes(){
        super();

        this.id_viaje = -1; //PK
        this.id_taxista = -1;
        this.id_pasajero = -1;
        this.precio = 1;
        this.long_origen = -1;
        this.lat_origen = -1;
        this.long_origen = -1;
        this.lat_destino = -1;
        this.status = false;
    }


    //SET AND GET
    public int getId() { return id_viaje; }
    public void setId(int id) { this.id_viaje = id; }

    public int getIdTaxi() { return id_taxista;}
    public void setIdTaxi(int id_taxista) {this.id_taxista = id_taxista; }

    public int getId_pasajero() { return id_pasajero; }
    public void setId_pasajero(int id_pasajero) { this.id_pasajero = id_pasajero; }

    public float getPrecio() { return precio; }
    public void setPrecio(String telefono) {this.precio = precio;}


    //GET SET LAT_ORIGEN
    public float getLat_origen() { return lat_origen ;}
    public void setLat_origen(float lat_origen) { this.lat_destino = lat_origen; }
    //GET SET LONG_ORIGEN
    public float getLong_origen() { return long_origen ;}
    public void setLong_origen(float long_origen) { this.long_origen = long_origen; }
    //GET SET LAT_DESTINO
    public float getLat_destino() { return lat_destino ;}
    public void setLat_destino(float lat_destino) { this.lat_destino = lat_destino; }
    //GET SET LONG_ORIGEN
    public float getLong_destino() { return long_destino ;}
    public void setLong_destino(float long_destino) { this.lat_destino = long_destino; }

    public Boolean getStatus() { return status ;}
    public void setStatus(boolean status) { this.status = status; }


    //METHODS
    public String ViajeMax(){
        DB db = new DB();
        String name="";
        int ter;
        try {
            ResultSet resultSet = db.select("SELECT max(id_viaje)+1 as max from viajes");
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("max"));
                    ter=obj.getId();
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
