package e.android9ed.googlemaps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    final int MAPS_PERMISSIONS = 100;
    final int ZOOM = 17;
    GoogleMap mapa;
    EditText direccion;
    Spinner locales;
    ArrayList<Local> listaLocales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        direccion = findViewById(R.id.txtAdress);
        locales = findViewById(R.id.spLocales);

        String[][] localesListString = {
                {"-4.089618", "36.742749", "pub", "O' Donnell's"},
                {"-4.420728", "36.722902", "pub", "Morrissey's"},
                {"-4.493384", "36.676734", "pub", "O'Learys Bar"},
                {"-4.420206", "36.719974", "bar", "La Taberna Del Obispo"},
                {"-4.418951", "36.722044", "bar", "Casa Lola"},
                {"-4.430179", "36.710847", "bar", "Universitas Cafe"},
        };

        listaLocales = new ArrayList<>();
        Local temp;
        for (int i = 0; i < localesListString.length; i++) {
            temp = new Local(i, localesListString[i][3]);
            temp.setLongitude(Double.parseDouble(localesListString[i][0]));
            temp.setLatitude(Double.parseDouble(localesListString[i][1]));
            temp.setTipo(localesListString[i][2]);
            listaLocales.add(temp);
        }

        LocalAdapter localAdapter = new LocalAdapter(this, R.layout.listview_ubicacion, listaLocales);
        locales.setAdapter(localAdapter);

        locales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setMarker((Local) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Sin permisos", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, MAPS_PERMISSIONS);
        } else {
            Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void setMarker(Local local) {

        mapa.clear();
        LatLng marker = new LatLng(local.getLatitude(), local.getLongitude());
        int zoom = 17;
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, zoom));
        mapa.addMarker(new MarkerOptions()
                .title(local.getNombre())
                .position(marker));

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btSearch:
                if (mapa != null){
                    LatLng ubicacion = getLocationFromAddress(this, direccion.getText().toString());
                    if (ubicacion != null){
                        mapa.clear();
                        mapa.addMarker(new MarkerOptions().title(direccion.getText().toString()).position(ubicacion));
                        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, ZOOM));
                    }else {
                        Toast.makeText(this, "Dirección no encontrada", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "El mapa aún no está listo", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
//        LatLng greenRay = new LatLng(36.71853911463124,-4.496980905532837);
//        int zoom = 17;
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(greenRay, zoom));
//        googleMap.addMarker(new MarkerOptions()
//                .title("The Green Ray")
//                .snippet("SamsungTech.")
//                .position(greenRay));
        mapa.animateCamera(CameraUpdateFactory.newLatLng());
    }

    public LatLng getLocationFromAddress(Context context, String strAddress)
    {
        Geocoder coder= new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try
        {
            address = coder.getFromLocationName(strAddress, 5); //Obtiene las direcciones coincidentes, ordenadas por probabilidad de coincidencia.
            if(address==null) //si no se encuentra
            {
                return null;
            }
            Address location = address.get(0); //obtener la dirección más probable
            p1 = new LatLng(location.getLatitude(), location.getLongitude()); // se crea la ubicación
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;
    }
}
