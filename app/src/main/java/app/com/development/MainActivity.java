package app.com.development;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<String> item ;
    private List<String> ruta ;

    private  String directorio;
    private EditText actual;
    private ListView listas;

    //Añadiendo las variables de contencion de los archivos
    private List<String> listaNombresArchivos;
    private  List<String> listaRutasArchivos;

    private ArrayAdapter<String> adapter;
    private TextView carpetaActual;

    //Comprobando Acceso a La tarjeta de memoria externa
    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;

    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;
    String conte="";
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String contenido,contenido1 = null,contenido2,contenido3;
        conte="";

        listas=(ListView)findViewById(R.id.list);


        //directorio =Environment.getExternalStorageDirectory().getPath();
        directorio="/sdcard";

        ListarArchivos(directorio);
        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= null;
                File archivo= new File(listaRutasArchivos.get(position));
                if(archivo.isFile()){

                    // Toast.makeText(this,"audio"+archivo.getName(),Toast.LENGTH_SHORT).show();
                    String ruta=listaRutasArchivos.get(position);
                    Uri dato = Uri.parse(ruta);
                    //iniciar(dato,archivo.getName(),carpetaActual.getText().toString());
                     intent = new Intent(MainActivity.this, EditorProyecto.class);
                    intent.putExtra("Url",listaRutasArchivos.get(position));
                    if(intent!=null){
                        startActivity(intent);
                    }

                }else{
                    ListarArchivos(listaRutasArchivos.get(position));
                }

            }
        });

    }



    //Verificacion del permiso de almacenamiento en android
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ListarArchivos(directorio);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this,"La aplicacion no funcionara sin acceso al almacenamiento",Toast.LENGTH_LONG).show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    public void ListarArchivos(String rutaDirectorio){
        try {
            setTitle(rutaDirectorio);
            listaNombresArchivos = new ArrayList<String>();
            listaRutasArchivos = new ArrayList<String>();
            File directorioActual = new File(rutaDirectorio);
            File[] listaArchivos = directorioActual.listFiles();
            int x = 0;
            if (!rutaDirectorio.equals(directorio)) {
                listaNombresArchivos.add("../");
                listaRutasArchivos.add(directorioActual.getParent());
                x = 1;
            }
            for (File archivo : listaArchivos) {
                listaRutasArchivos.add(archivo.getPath());
            }
            //Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);

            for (int i = x; i < listaRutasArchivos.size(); i++) {
                File archivo = new File(listaRutasArchivos.get(i));
                if (archivo.isFile()) {
                    listaNombresArchivos.add(archivo.getName());
                    String file,audio = "";
                    int LongFile,menos;
                    file=archivo.getName();
                    LongFile=file.length();
                    menos= LongFile-4;
                    // Toast.makeText(this," Total"+menos,Toast.LENGTH_LONG).show();
                    while(LongFile!=menos){
                        audio+=file.substring(LongFile-1,LongFile);
                        LongFile--;

                    }
                    // Toast.makeText(this,audio,Toast.LENGTH_LONG).show();
                } else {
                    listaNombresArchivos.add("/" + archivo.getName());
                }
            }
            if (listaArchivos.length < 1) {
                listaNombresArchivos.add("No hay ningun archivo");
                listaRutasArchivos.add(rutaDirectorio);
            }

            adapter = new ArrayAdapter<String>(this, R.layout.lista, listaNombresArchivos);
            listas.setAdapter(adapter);
        }catch (Exception e){
            System.out.println("Error 1 "+e.getMessage());
            Toast.makeText(this,"Error aqui "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void Nuevo(String nombre){

        if(!nombre.equals("")){
            String dir = getTitle().toString();

            File file1 = new File(dir+"/"+nombre);
            file1.mkdir();
            ListarArchivos(dir);

        }else{
            Toast.makeText(this,"Debe ingresar nombre del archivo",Toast.LENGTH_SHORT).show();
        }

    }

    public void NuevoArchivo(String Nombre){
        String dir = getTitle().toString();

        File file1 = new File(dir,Nombre);
        try {
            file1.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListarArchivos(dir);

    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id == R.id.page) {
            final LayoutInflater inflater = getLayoutInflater();
            final View dialoglayout = inflater.inflate(R.layout.newfile, null);
            final EditText etAsunto = (EditText) dialoglayout.findViewById(R.id.et_EmailAsunto);

            Button btnEnviarMail = (Button) dialoglayout.findViewById(R.id.btnEnviarMail);


            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final AlertDialog alert=builder.create();
            alert.setView(dialoglayout);
            alert.show();

            btnEnviarMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String NombreFolder =  etAsunto.getText().toString();
                    NuevoArchivo(NombreFolder);
                    alert.cancel();
                }
            });
        }
        if(id == R.id.folder) {


            final LayoutInflater inflater = getLayoutInflater();
            final View dialoglayout = inflater.inflate(R.layout.newfolder, null);
            final EditText etAsunto = (EditText) dialoglayout.findViewById(R.id.et_EmailAsunto);

            Button btnEnviarMail = (Button) dialoglayout.findViewById(R.id.btnEnviarMail);


            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final AlertDialog alert=builder.create();
            alert.setView(dialoglayout);
            alert.show();

            btnEnviarMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String NombreFolder =  etAsunto.getText().toString();
                    Nuevo(NombreFolder);
                    alert.cancel();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
