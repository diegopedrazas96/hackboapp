package com.megasystem.terminales.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.facebook.AccessToken;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.megasystem.terminales.Application;
import com.megasystem.terminales.R;
import com.megasystem.terminales.component.Menu;
import com.megasystem.terminales.data.app.DDisenoFlotas;
import com.megasystem.terminales.data.app.DEmpresas;
import com.megasystem.terminales.data.app.DEstadoAsientos;
import com.megasystem.terminales.data.app.DFlotas;
import com.megasystem.terminales.data.app.DUser;
import com.megasystem.terminales.data.app.DViajes;
import com.megasystem.terminales.data.app.DViajesFlotas;
import com.megasystem.terminales.entity.Action;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.Empresas;
import com.megasystem.terminales.entity.app.EstadoAsientos;
import com.megasystem.terminales.entity.app.Flotas;
import com.megasystem.terminales.entity.app.User;
import com.megasystem.terminales.entity.app.Viajes;
import com.megasystem.terminales.entity.app.ViajesFlota;
import com.megasystem.terminales.service.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import com.megasystem.suiteproformas.activity.audit.Products;
//import com.avatech.fchavez.activity.audit.Search;

public class Main extends AppCompatActivity {

   // private List<Menu> items = new ArrayList<Menu>();
    private List<Empresas> items = new ArrayList<Empresas>();
    private Empresas selected;
    private User user;
    private ImageView imageUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


       // View view = View.inflate(R.layout.activity_options, null);
       // getSupportActionBar().setTitle(getResources().getString(R.string.options));

        if(AccessToken.getCurrentAccessToken()==null){
            Intent intent = new Intent(this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            startActivity(intent);
        }
        user = new DUser(Main.this).get();
        if(user != null && user.getId()!= null ){
            loadActionBar();
            //this.setTitle(user.getNombre());
            Call task = new Call(1,Main.this);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

          //  registerForContextMenu(view);
          //  openContextMenu(view);
          //  unregisterForContextMenu(view);
        }



    }

    private void loadActionBar() {
        URL imageUrl = null;
        HttpURLConnection conn = null;

        try {
            ActionBar mActionBar = getSupportActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            View mCustomView = mInflater.inflate(R.layout.custom_actionbar,null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
            mTitleTextView.setText(user.getNombre());
             imageUser = (ImageView) mCustomView.findViewById(R.id.imageUser);
            ImageButton imageButton = (ImageButton) mCustomView.findViewById(R.id.imageButton);
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);

          //  imageUrl = new URL(user.getFoto());
/*
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2
            Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
            imageUser.setImageBitmap(imagen);*/
            imageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    registerForContextMenu(view);
                    openContextMenu(view);
                    unregisterForContextMenu(view);
                }
            });

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        // menu.setHeaderTitle(selected.getType().getDescripcion());
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reservas: {
                Intent intent = new Intent(Main.this, Reservas.class);
                startActivity(intent);
                break;
            }default:
                return super.onOptionsItemSelected(item);


        }

        return true;
    }
    private void backupDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File path = new File(sd, "/megasystem");
                path.mkdirs();
                String currentDBPath = "//data//" + getApplicationContext().getPackageName() + "//databases//" + Application.databaseName + ".db";
                String backupDBPath = "/megasystem/" + Application.databaseName + new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date()) + ".db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(Main.this, getString(R.string.message_backup_finish), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Main.this, getString(R.string.message_no_backup), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Main.this, getString(R.string.message_error_backup), Toast.LENGTH_LONG).show();
        }
    }
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        private ProgressDialog progreso;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progreso = new ProgressDialog(Main.this);
            progreso.setMessage("Descargando...");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap map = null;

                try {
                    map = downloadImage();
                    User user = new DUser(Main.this).get();
                    Service service = new Service(Main.this);
                    try {
                        Long value = 0L;
                        if (user.getEstado()==1){
                            value = service.postCliente(user,"","");
                            if (value.intValue() > 0){
                                DUser dalUser = new DUser(Main.this);
                                user.setEstado(2L);
                                user.setAction(Action.Update);
                                dalUser.save(user);
                            }
                        }

                        Log.i("Guardado Usuario",value.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            return  map;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
           progreso.dismiss();
            if (bitmap != null){
                imageUser.setImageBitmap(bitmap);
            }


        }
        private  Bitmap  downloadImage() throws MalformedURLException {
            URL imageUrl = null;
            HttpURLConnection conn = null;

            try {
                imageUrl = new URL(user.getFoto());
                conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2
                Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
                return imagen;
            } catch (IOException e) {
                e.printStackTrace();
                return  null;
            }
        }
    }



    private class Call extends AsyncTask<Integer, Void, Boolean> {

        private ProgressDialog progressDialog;
        private int action;
        private Context context;
        private int productRegistries;
        private int countSyncronize = 0;
        public Call(int varAction,Context cont) {
            this.action = varAction;
            this.context= cont;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Main.this, null, getString(R.string.synchronizing));
        }

        @Override
        protected Boolean doInBackground(Integer... arg0) {
            try {
                Service service = new Service(Main.this);
                //Llamada a las Dal
                if (action == 1){


                    DEmpresas dalEmpresas = new DEmpresas(getApplicationContext());
                    DFlotas dalFlotas =  new DFlotas(getApplicationContext());
                    DViajes dalViajes =  new DViajes(getApplicationContext());
                    DViajesFlotas dalViajesFlota =  new DViajesFlotas(getApplicationContext());
                    DDisenoFlotas dalDisenoFlotas =  new DDisenoFlotas(getApplicationContext());
                    DEstadoAsientos dalEstadoAsiento =  new DEstadoAsientos(getApplicationContext());
                    dalEmpresas.clean();
                    dalFlotas.clean();
                    dalViajes.clean();
                    dalDisenoFlotas.clean();
                    dalEstadoAsiento.clean();
                    dalViajesFlota.clean();
                    List<Empresas> lstEmpresas = service.getEmpresas("","");
                    List<Flotas> lstFlotas = service.getFlotas("","");
                    List<ViajesFlota> lstViajesFlotas = service.getViajesFlota("","");
                    List<Viajes> lstViajes = service.getViajes("","");
                    List<DisenoFlotas> lstDisenoFlotases = service.getDisenoFlotas("","");
                    List<EstadoAsientos> lstEstadoAsientos = service.getEstadoAsientos("","");
                    for (Empresas obj : lstEmpresas) {
                        obj.setAction(Action.Insert);
                        dalEmpresas.save(obj);
                    }
                    for (Flotas obj : lstFlotas) {
                        obj.setAction(Action.Insert);
                        dalFlotas.save(obj);
                    }
                    for (Viajes obj : lstViajes) {
                        obj.setAction(Action.Insert);
                        dalViajes.save(obj);
                    }
                    for (DisenoFlotas obj : lstDisenoFlotases) {
                        obj.setAction(Action.Insert);
                        dalDisenoFlotas.save(obj);
                    }
                    for (EstadoAsientos obj : lstEstadoAsientos) {
                        obj.setAction(Action.Insert);
                        dalEstadoAsiento.save(obj);
                    }
                    for (ViajesFlota obj : lstViajesFlotas) {
                        obj.setAction(Action.Insert);
                        dalViajesFlota.save(obj);
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
                //Sincronizacion Terminada
                Log.i(Application.tag, "Actualizando pantalla principal");
                if (action ==1){
                   // Toast toas = Toast.makeText(context,productRegistries + " Registros Obtenidos Correctamente.!",Toast.LENGTH_SHORT);
                   // toas.show();
                    DEmpresas dalEmpresas = new DEmpresas(Main.this);
                    items = dalEmpresas.list();
                    ListView listView = (ListView) findViewById(R.id.menu);
                    listView.setAdapter(new IconAdapter());
                    LoadImage task2 = new LoadImage();
                    task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

            }else{
                Toast toas = Toast.makeText(context, " SIN CONEXION.!",Toast.LENGTH_SHORT);
                toas.show();
            }
        }

    }
    public class IconAdapter extends BaseAdapter {

        public int getCount() {
            return items.size();
        }

        public Empresas getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.menu_item, null);
            }
            Empresas item = items.get(position);
            TextView text = (TextView) v.findViewById(R.id.text);
            FontAwesomeText icon = (FontAwesomeText) v.findViewById(R.id.icon);
            icon.setIcon("fa-bus");
            text.setText(item.getNombre());
            v.setTag(item);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected = (Empresas) view.getTag();
                    Intent intent = new Intent(Main.this, ListBuses.class);
                    intent.putExtra("empresa", selected);
                    startActivity(intent);

                }
            });
            return v;
        }
    }


}
