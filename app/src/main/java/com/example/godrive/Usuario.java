package com.example.godrive;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by evandro on 16/03/2015.
 */


public class Usuario extends _Default {

    private int id;
    private String nome;
    private String email;
    private String telefone;


    public Usuario(){
        super();
        this.id = -1;
        this.email = "";
        this.nome = "";
        this.telefone = "";
    }

    public ArrayList<Usuario> getLista(String id){
        DB db = new DB();
        String comando = String.format("select *from pedidos WHERE provedor= %s ",
                id);
            ArrayList<Usuario> lista = new ArrayList<>();
        try {

            ResultSet resultSet = db.select(comando);
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("id"));
                    obj.setNome(resultSet.getString("productos"));
                    //obj.setEmail(resultSet.getString("pago"));
                    obj.setTelefone("Pedido de " + resultSet.getString("kilos")+" kilos");
                    lista.add(obj);
                    obj = null;
                }
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        return lista;
    }



    public ArrayList<String> getListaProductos(String id){
        DB db = new DB();
        String comando = String.format("select *from productos WHERE provedor= %s ",
                id);
        ArrayList<String> lista = new ArrayList<>();
        try {
            ResultSet resultSet = db.select(comando);
            if (resultSet != null) {
                while (resultSet.next()) {
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("id"));
                    obj.setNome(resultSet.getString("descripcion"));
                    String s = Integer.toString(obj.getId());
                    lista.add(s + "      " + obj.getNome());
                    obj = null;
                }
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        return lista;
    }




    public int ObtenerNumeroInventarioConIdProducto(String id_producto){
        DB db = new DB();
        String comando = String.format("select * from inventario where id=%s ",
                id_producto);
        int retorno=0;
        try {
            ResultSet resultSet = db.select(comando);
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("id"));
                    obj.setTelefone(resultSet.getString("existencias"));
                    retorno = Integer.parseInt(obj.getTelefone());
                    //obj.setEmail(resultSet.getString("s"));
                    //obj.setTelefone(resultSet.getString("kilos"));
                    obj = null;
                }
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        return retorno;
    }





    public int NameExist(String nombresssin, String contras){
        DB db = new DB();
        String name= nombresssin;
        int ter = 0;
        try {
            ResultSet resultSet = db.select("select*from socios");
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("id"));
                    obj.setNome(resultSet.getString("nombre"));
                    obj.setEmail(resultSet.getString("contra"));

                    String prub= obj.getNome();

                    if((Objects.equals(prub,nombresssin))&&(Objects.equals(contras,obj.getEmail())))
                    {
                        ter=obj.getId();
                        break;
                    }
                    else{   ter=0;}

                    obj = null;
                }
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        db.disconecta();
        return  ter;
    }



    public void salvar(String id, String id_provedor, String descripcion, String precio){
        String comando = String.format("INSERT INTO productos(id, provedor, codigo, descripcion,precio) VALUES (%s,%s,545, '%s', %s)",id, id_provedor,descripcion, precio);
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
        db.disconecta();
    }



    public void GuardarNuevoSocio(String NomUsuario, String Contraseña, String direccion, String NumMaxsocios){
        String comando = String.format("INSERT INTO socios(administrador, nombre, contra,direccion,id, estrellas) VALUES (False,'%s','%s','%s',%s,5)",NomUsuario, Contraseña,direccion, NumMaxsocios);
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
        db.disconecta();
    }

    public void UpDateInventario(String idproducto, String cantidadnueva){
        // String comando = String.format("UPDATE inventario set existencias = %s WHERE id= %s ",cantidadnueva,idproducto);
        String comando = String.format("UPDATE inventario set existencias = 8 WHERE id=3;");
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
        db.disconecta();

    }

    public String ObtenerNumeroMaximoDeProducto()
    {
        DB db = new DB();
        String name="";
        int ter;
        try {
            ResultSet resultSet = db.select("SELECT max(id) from  productos");
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("max"));
                    ter=obj.getId()+1;
                    name = String.valueOf(ter);
                    obj = null;
                }
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        return  name;
    }




    public String ObtenerNumeroMaximoDeSocio()
    {
        DB db = new DB();
        String name="";
        int ter;
        try {
            ResultSet resultSet = db.select("SELECT max(id) from  socios");
            if (resultSet != null){
                while (resultSet.next()){
                    Usuario obj = new Usuario();
                    obj.setId(resultSet.getInt("max"));
                    ter=obj.getId()+1;
                    name = String.valueOf(ter);
                    obj = null;
                }
            }
            else
            {
                name="HO";
            }
        }catch (Exception ex){
            this._mensagem = ex.getMessage();
            this._status = false;
        }
        return  name;
    }

    public void CrearInventario(String id_producto, String cantidad){
        String comando = String.format("INSERT INTO inventario (id, existencias) VALUES (%s,%s)",id_producto,cantidad);
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
    }




    public void ModifiInv(int Cantidad, int Id_Producto){
        String comando = "";
        if (this.getId() == -1){
            comando = String.format("INSERT INTO socio (nome, email, telefone) VALUES ('%s','%s','%s');",
                    this.getNome(),this.getEmail(),this.getTelefone());
        }else{
            comando = String.format("UPDATE usuario SET nome = '%s', email = '%s', telefone = '%s' WHERE id = %d;",
                    this.getNome(),this.getEmail(),this.getTelefone(),this.getId());
        }
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
    }





    public void apagar(){
        String comando = String.format("DELETE FROM usuario WHERE id = %d;",this.getId());
        DB db = new DB();
        db.execute(comando);
        this._mensagem = db._mensagem;
        this._status = db._status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
