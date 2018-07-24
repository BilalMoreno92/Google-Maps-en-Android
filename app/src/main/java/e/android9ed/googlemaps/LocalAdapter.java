package e.android9ed.googlemaps;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joseamontenegromontes on 24/7/17.
 */

public class LocalAdapter extends BaseAdapter {
    private Context mContext;
    int resource;
    ArrayList<Local> listp;


    public LocalAdapter(Context context,int resourceP,ArrayList<Local> list_baresP){
        mContext = context;
        resource = resourceP;
        listp = list_baresP;
    }

    @Override
    public int getCount() {
        return listp.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridElement=null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) { //Nuevo es necesariocrear
            gridElement = inflater.inflate(resource, null);

        } else { //Reciclado, ya creado
            gridElement = (View) view;
        }

        TextView textViewID  = (TextView) gridElement.findViewById(R.id.txtId);
        TextView textViewName = (TextView) gridElement.findViewById(R.id.txtNombre);
        TextView textViewTipo = (TextView) gridElement.findViewById(R.id.txtTipo);

        textViewID.setText  ( ""+listp.get(i).getID());
        textViewName.setText( listp.get(i).getNombre());
        textViewTipo.setText(listp.get(i).getTipo());

        if (listp.get(i).getTipo().contentEquals("pub"))
            textViewTipo.setTextColor(Color.BLUE);
        else textViewTipo.setTextColor(Color.RED);
        return gridElement;
    }
}
