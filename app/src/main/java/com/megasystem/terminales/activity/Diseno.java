package com.megasystem.terminales.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.megasystem.terminales.Application;
import com.megasystem.terminales.R;
import com.megasystem.terminales.data.app.DDisenoFlotas;
import com.megasystem.terminales.data.app.DEmpresas;
import com.megasystem.terminales.data.app.DEstadoAsientos;
import com.megasystem.terminales.data.app.DFlotas;
import com.megasystem.terminales.data.app.DUser;
import com.megasystem.terminales.data.app.DViajes;
import com.megasystem.terminales.data.app.DViajesCliente;
import com.megasystem.terminales.data.app.DViajesFlotas;
import com.megasystem.terminales.entity.Action;
import com.megasystem.terminales.entity.Enumerators;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.Empresas;
import com.megasystem.terminales.entity.app.EstadoAsientos;
import com.megasystem.terminales.entity.app.Flotas;
import com.megasystem.terminales.entity.app.User;
import com.megasystem.terminales.entity.app.Viajes;
import com.megasystem.terminales.entity.app.ViajesCliente;
import com.megasystem.terminales.entity.app.ViajesFlota;
import com.megasystem.terminales.service.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Diseno extends AppCompatActivity {

    private Viajes objViaje;
    private List<ViajesCliente> lstViajesClientes = new ArrayList<ViajesCliente>();
    private List<EstadoAsientos> lstEstadoAsiento = new ArrayList<EstadoAsientos>();
    private GridView menu = null;
    Button btnReservar ;
    private Dialog clienteViajeDialog;
    private EstadoAsientos selectedAsiento;
    private List<EstadoAsientos> items = new ArrayList<EstadoAsientos>();
    private Flotas flota = null;
    private User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseno);
        objViaje = (Viajes) getIntent().getExtras().getSerializable("viaje");
        btnReservar = (Button)findViewById(R.id.btnReservar);
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Diseno.this).setTitle(null).setMessage(R.string.check_viajes).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Call task = new Call(1,Diseno.this);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();

            }
        });
        items = objViaje.getLstFlotas().get(0).getLstDisenoFlotas().get(0).getLstEstadoAsientos();
        flota = objViaje.getLstFlotas().get(0);
        loadSeats(1);
       initDialog();
    }
    private void initDialog() {
        clienteViajeDialog = new Dialog(Diseno.this);
        clienteViajeDialog.setContentView(R.layout.dialog_reason);
        clienteViajeDialog.setTitle("Otros Datos");
        Button btnSave = (Button) clienteViajeDialog.findViewById(R.id.save);
        Button btnCancel = (Button) clienteViajeDialog.findViewById(R.id.cancel);
        final EditText etCi = (EditText) clienteViajeDialog.findViewById(R.id.etCi);
        final EditText etNombre = (EditText) clienteViajeDialog.findViewById(R.id.etNombre);
        final EditText etEdad = (EditText) clienteViajeDialog.findViewById(R.id.etEdad);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new DUser(Diseno.this).get();
                ViajesCliente objViajeCliente = new ViajesCliente();
                objViajeCliente.setViajeId(objViaje.getId().intValue());
                objViajeCliente.setCi(etCi.getText().toString());
                objViajeCliente.setAsientoId(selectedAsiento.getId().intValue());
                objViajeCliente.setClienteId(user.getId().intValue());
                objViajeCliente.setNombre(etNombre.getText().toString());
                objViajeCliente.setEdad(Integer.parseInt(etEdad.getText().toString()));
                objViajeCliente.setAction(Action.Insert);
                lstViajesClientes.add(objViajeCliente);
                selectedAsiento.setEstado(2);
                selectedAsiento.setAction(Action.Update);
                lstEstadoAsiento.add(selectedAsiento);
                clienteViajeDialog.cancel();
                loadSeats(1);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clienteViajeDialog.cancel();
            }
        });
    }
    private class Call extends AsyncTask<Integer, Void, Boolean> {

        private ProgressDialog progressDialog;
        private int action;
        private Context context;
        private int productRegistries;
        private int countSyncronize = 0;

        public Call(int varAction, Context cont) {
            this.action = varAction;
            this.context = cont;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Diseno.this, null, getString(R.string.send_rep));
        }

        @Override
        protected Boolean doInBackground(Integer... arg0) {
            try {
                Service service = new Service(Diseno.this);
                if (action == 1) {
                    for (ViajesCliente objViajeCliente:lstViajesClientes) {
                        DViajesCliente dalCliente = new DViajesCliente(Diseno.this);
                        dalCliente.save(objViajeCliente);
                        try {
                            service.postViajeCliete(objViajeCliente,"","");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    for (EstadoAsientos objViajeCliente:lstEstadoAsiento) {
                        DEstadoAsientos dalCliente = new DEstadoAsientos(Diseno.this);
                        objViajeCliente.setAction(Action.Update);
                        objViajeCliente.setEstado(2);
                        dalCliente.save(objViajeCliente);
                        try {
                            service.postEstadoAsiento(objViajeCliente,"","");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                Log.i(Application.tag, "Sincronizacion Terminada");
                return true;
            } catch (Exception e) {

                Log.e(Application.tag, e.getMessage());
                return false;
            }

        }

        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result) {

                Log.i(Application.tag, "Actualizando pantalla principal");
                if (action == 1) {
                    Intent intent = new Intent(Diseno.this,Main.class);
                    startActivity(intent);
                  //  loadSeats(2);
                }

            } else {
                Toast toas = Toast.makeText(context, " SIN CONEXION.!", Toast.LENGTH_SHORT);
                toas.show();

            }
        }
    }
        public  void loadSeats(int valor){


        if(valor == 1){
            items = objViaje.getLstFlotas().get(0).getLstDisenoFlotas().get(0).getLstEstadoAsientos();

            if(flota.getTipoIdc() == Enumerators.TipoFlota.Normal){
                menu = (GridView) findViewById(R.id.gridView);
                menu.setVisibility(View.VISIBLE);
                menu.setAdapter(new IconAdapter());

            }else{
                menu = (GridView) findViewById(R.id.gridViewLeito);
                menu.setVisibility(View.VISIBLE);
                menu.setAdapter(new IconAdapter());

            }
        }
        if(valor == 2){
            DEstadoAsientos dEstadoAsientos = new DEstadoAsientos(Diseno.this);
            List<EstadoAsientos> lstEstado = dEstadoAsientos.returnChildByDisenho(objViaje.getLstFlotas().get(0).getLstDisenoFlotas().get(0).getId().intValue());
            items = lstEstado;

            if(flota.getTipoIdc() == Enumerators.TipoFlota.Normal){
                menu = (GridView) findViewById(R.id.gridView);
                menu.setVisibility(View.VISIBLE);
                menu.setAdapter(new IconAdapter());

            }else{
                menu = (GridView) findViewById(R.id.gridViewLeito);
                menu.setVisibility(View.VISIBLE);
                menu.setAdapter(new IconAdapter());

            }
        }


    }



    @Override
    protected void onResume() {
        super.onResume();
        //load aditional data



    }

    @Override
    protected void onStart() {
        super.onStart();
     /*   if (serviceIntent != null) {
            startService(serviceIntent);
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*  if (serviceIntent != null) {
            stopService(serviceIntent);
        }*/
        //startActivity(new Intent(Main.this, Main.class));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(null).setMessage(R.string.message_close_proforma).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }


    public class IconAdapter extends BaseAdapter {

        public int getCount() {
            return items.size();
        }

        public EstadoAsientos getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.diseno_item, null);
            }
            EstadoAsientos item = items.get(position);

            TextView text = (TextView) v.findViewById(R.id.text);
            if (item.getEstado().intValue() == 1) {
                v.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_active));
            } else {
                if(item.getEstado().intValue() == 2){
                    v.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_reserved));
                }else {
                    v.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_inactive));
                }

            }
            FontAwesomeText icon = (FontAwesomeText) v.findViewById(R.id.icon);
            icon.setIcon("fa-slideshare");
            text.setText(item.getCodAsiento());
            v.setTag(item);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedAsiento = (EstadoAsientos) view.getTag();
                    if(selectedAsiento.getEstado() == 1){
                        clienteViajeDialog.show();
                    }
                    if(selectedAsiento.getEstado() == 2){
                        for (EstadoAsientos objAsiento: lstEstadoAsiento) {
                            if (objAsiento.getId() == selectedAsiento.getId()){
                                lstEstadoAsiento.remove(objAsiento);
                                break;
                            }

                        }
                    }

                }
            });
            return v;
        }
    }


}
