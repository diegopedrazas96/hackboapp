package com.megasystem.terminales.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.megasystem.terminales.R;
import com.megasystem.terminales.data.app.DFlotas;
import com.megasystem.terminales.data.app.DViajes;
import com.megasystem.terminales.data.app.DViajesFlotas;
import com.megasystem.terminales.entity.Enumerators;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.Empresas;
import com.megasystem.terminales.entity.app.Flotas;
import com.megasystem.terminales.entity.app.Viajes;
import com.megasystem.terminales.entity.app.ViajesFlota;
import com.megasystem.terminales.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListBuses extends AppCompatActivity {

    private Viajes selected;
    private Empresas empresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buses);
        empresa = (Empresas) getIntent().getExtras().getSerializable("empresa");
        DViajes dalViajes = new DViajes(ListBuses.this);
        List<Viajes> lstViajes = dalViajes.returnChildByEmpresa(empresa.getBaseId(),new String[]{Viajes.relation.Flotas.name(), Flotas.relation.DisenoFlotas.name(), DisenoFlotas.relation.EstadoFlotas.name()});
        ListView listView = (ListView) findViewById(R.id.menu);
        listView.setAdapter(new Adapter(ListBuses.this, lstViajes));


    }

    public class Adapter extends ArrayAdapter<Viajes> implements Filterable {


        private List<Viajes> items;

        public Adapter(Context context, List<Viajes> registries) {
            super(context, R.layout.flota_item, registries);
            items = registries;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) ListBuses.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.flota_item, null);
            }
            Viajes obj = items.get(position);
            if (obj.getLstFlotas()!= null && obj.getLstFlotas().size()>0){
                ((TextView) v.findViewById(R.id.name)).setText("[Chofer:]"  + obj.getLstFlotas().get(obj.getId().intValue()-1).getChofer() + " - Placa:" + obj.getLstFlotas().get(obj.getId().intValue()-1).getPlaca() + " - Tipo:" + (obj.getLstFlotas().get(obj.getId().intValue()-1).getTipoIdc() == Enumerators.TipoFlota.Leito ? "Leito" : "Normal" ));
                ((TextView) v.findViewById(R.id.address)).setText("De - " + obj.getOrigen() + " A  -" + obj.getDestino() );
                ((TextView) v.findViewById(R.id.horario)).setText("Salida:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(obj.getHorario()).toString() + " - Bs." + Util.formatDouble(obj.getPrecio()));
                v.setTag(obj);
                v.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            selected = (Viajes) view.getTag();
                            Intent intent = new Intent(ListBuses.this, Diseno.class);
                            intent.putExtra("viaje", selected);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });

            }



            return v;
        }
    }
}
