package e.android9ed.googlemaps;

import java.io.Serializable;

/**
 * Created by joseamontenegromontes on 24/7/17.
 */

public class Local implements Serializable {

    private int     _ID;
    private String  _nombre;
    private String  _tipo;
    private double  _latitude;
    private double  _longitude;

    public  Local (int id, String name){
        _ID     =   id;
        _nombre =   name;
    }

    public int getID(){
        return _ID;
    }

    public String getNombre (){
        return _nombre;
    }

    public void setTipo (String tipo){
        _tipo = tipo;
    }
    public String getTipo (){
        return _tipo;
    }
    public void setLatitude (double latitude){
        _latitude=latitude;
    }

    public double getLatitude (){
        return _latitude;
    }

    public void setLongitude (double longitude){
        _longitude = longitude;
    }

    public double getLongitude (){
        return _longitude;
    }


}
