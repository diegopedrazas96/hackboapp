package com.megasystem.terminales.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.facebook.AccessToken;
import com.megasystem.terminales.Application;
import com.megasystem.terminales.R;
import com.megasystem.terminales.component.Menu;
import com.megasystem.terminales.data.app.DUser;
import com.megasystem.terminales.entity.app.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import com.megasystem.suiteproformas.activity.audit.Products;
//import com.avatech.fchavez.activity.audit.Search;

public class Main extends AppCompatActivity {

    private List<Menu> items = new ArrayList<Menu>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

       // getSupportActionBar().setTitle(getResources().getString(R.string.options));

        if(AccessToken.getCurrentAccessToken()==null){
            Intent intent = new Intent(this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            startActivity(intent);
        }
        User user = new DUser(Main.this).get();
        if(user != null){
            getSupportActionBar().setTitle(user.getNombre());
            //this.setTitle(user.getNombre());
        }
        items.add(new Menu(R.string.backup_database, R.string.backup_database, "fa-copy"));
        items.add(new Menu(R.string.restore_database, R.string.restore_database, "fa-database"));
        items.add(new Menu(R.string.update, R.string.update, "fa-star-o"));

        ListView listView = (ListView) findViewById(R.id.menu);
        listView.setAdapter(new IconAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Menu item = items.get(i);
                switch (item.getId()) {
                    case R.string.backup_database:
                       // backupDatabase();
                        break;
                    case R.string.restore_database:
                       // showPromptsPassword(Options.this);
                        break;
                    case R.string.update:
                       // Downloader asyncTaskPrint = new Downloader(Options.this);
                        //asyncTaskPrint.execute();
                        break;
                }
            }
        });
    }




    public class IconAdapter extends BaseAdapter {

        public int getCount() {
            return items.size();
        }

        public Menu getItem(int position) {
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
            Menu item = items.get(position);
            TextView text = (TextView) v.findViewById(R.id.text);
            FontAwesomeText icon = (FontAwesomeText) v.findViewById(R.id.icon);
            icon.setIcon(item.getIcon());
            text.setText(item.getLabel());
            return v;
        }
    }

}
