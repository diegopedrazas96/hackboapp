package com.megasystem.terminales.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.megasystem.terminales.R;
import com.megasystem.terminales.data.app.DUser;
import com.megasystem.terminales.data.app.DViajes;
import com.megasystem.terminales.data.app.DViajesCliente;
import com.megasystem.terminales.entity.Action;
import com.megasystem.terminales.entity.Enumerators;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.Empresas;
import com.megasystem.terminales.entity.app.Flotas;
import com.megasystem.terminales.entity.app.User;
import com.megasystem.terminales.entity.app.Viajes;
import com.megasystem.terminales.entity.app.ViajesCliente;
import com.megasystem.terminales.util.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Reservas extends AppCompatActivity {
    private Dialog clienteViajeDialog;
    private List<ViajesCliente> items = new ArrayList<ViajesCliente>();
    private Viajes selected;
    private User user;
    private ImageView imageUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        User user = new DUser(Reservas.this).get();
        DViajes dalViajes = new DViajes(Reservas.this);
        List<Viajes> lstViajesCliente = dalViajes.returnChildByUser(user.getId().intValue(),new String[]{Viajes.relation.Flotas.name(), Flotas.relation.DisenoFlotas.name(), DisenoFlotas.relation.EstadoFlotas.name()});
        ListView listView = (ListView) findViewById(R.id.menu);
        listView.setAdapter(new Adapter(Reservas.this, lstViajesCliente));
        initDialog();
    }
    private void initDialog() {
        clienteViajeDialog = new Dialog(Reservas.this);
        clienteViajeDialog.setContentView(R.layout.dialog_reserva);
        clienteViajeDialog.setTitle("Pagar");
        Button btnSave = (Button) clienteViajeDialog.findViewById(R.id.save);
        Button btnCancel = (Button) clienteViajeDialog.findViewById(R.id.cancel);
        final EditText etCi = (EditText) clienteViajeDialog.findViewById(R.id.etCi);
        final EditText etNombre = (EditText) clienteViajeDialog.findViewById(R.id.etNombre);
        final EditText etEdad = (EditText) clienteViajeDialog.findViewById(R.id.etEdad);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Reservas.this,"Se ha realizado su pago correctamente!",Toast.LENGTH_LONG).show();
                clienteViajeDialog.cancel();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clienteViajeDialog.cancel();
            }
        });
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
                LayoutInflater vi = (LayoutInflater) Reservas.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.flota_item, null);
            }
            Viajes obj = items.get(position);

                ((TextView) v.findViewById(R.id.name)).setText("[Chofer:]"  + obj.getLstFlotas().get(obj.getId().intValue()-1).getChofer() + " - Placa:" + obj.getLstFlotas().get(obj.getId().intValue()-1).getPlaca() + " - Tipo:" + (obj.getLstFlotas().get(obj.getId().intValue()-1).getTipoIdc() == Enumerators.TipoFlota.Leito ? "Leito" : "Normal" ));
                ((TextView) v.findViewById(R.id.address)).setText("De - " + obj.getOrigen() + " A  -" + obj.getDestino() );
                ((TextView) v.findViewById(R.id.horario)).setText("Salida:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(obj.getHorario()).toString() + " - Bs." + Util.formatDouble(obj.getPrecio()));
                v.setTag(obj);
                v.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            selected = (Viajes) view.getTag();
                            clienteViajeDialog.show();
                            return true;
                        }
                        return false;
                    }
                });





            return v;
        }
    }
}
