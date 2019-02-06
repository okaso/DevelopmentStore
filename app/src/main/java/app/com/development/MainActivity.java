package app.com.development;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<String> item ;
    private List<String> ruta ;
    private ArrayAdapter<String> adapter;
    private  String directorio;
    private EditText actual;
    private ListView listas;

    String conte="";
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this,"Se requieren Permisos de almacenamiento",Toast.LENGTH_SHORT).show();


        MobileAds.initialize(this,"ca-app-pub-9234222684473855~9343784971");

        mInterstitialAd = new InterstitialAd ( this );
        mInterstitialAd . setAdUnitId ( "ca-app-pub-9234222684473855/2339392920" );
        mInterstitialAd.loadAd(new AdRequest.Builder ().build ());
        mInterstitialAd.setAdListener ( new AdListener(){
            @Override public void onAdLoaded () {
                // mInterstitialAd.show();
                // Código que se ejecutará cuando un anuncio termine de cargarse.
            }@Override public void onAdFailedToLoad ( int errorCode ) {
                // Código que se ejecutará cuando falle una solicitud de anuncio.
            }@Override public void onAdOpened () {
                // Código que se ejecutará cuando se muestre el anuncio.
            }@Override public void onAdLeftApplication   () {
                // Código que se ejecutará cuando el usuario haya abandonado la aplicación.
            }@Override public void onAdClosed () {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                // Código que se ejecutará cuando se cierre el anuncio intersticial.
            } });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }
        }

        String contenido,contenido1,contenido2,contenido3;
        conte="Al momento de crear nuevo proyecto este te creara el folder en \n" +
                "el almacenamiento interno el archivo de encontrara dentro de la carpeta 'DesarrolloWEB'\n" +
                "en esta carpeta se deben mantener todos los proyectos para que la App pueda leerlos \n" +
                "ademas no se deben cambiar el nombre a los archivos html, css, js(javascript) y php \n" +
                "esta app contiene su propio visor de codigo html leyendo asi los otros 3 archivos \n" +
                ", la carpeta Formulario que este contiene es una muestra de como se pueden trabajar con los 4 \n" +
                "tipos de codigo web la app solo leera los archivos que tengan los nombres que contiene el menu desplegable \n" +
                "ADVERTENCIA no puedes crear otro poryecto con el mismo nombre ya que al hacerlo se perdera todo el codigo \n" +
                "que contenia anteriormente. \n" +
                "y por ultimo por mas cambios que se le haga al proyecto formulario este siempre volvera a lo que fue al principio\n" +
                "***********este es un proyecto en desarrollo************************";

        contenido="<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                " <head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<title>Formulario Animado</title>\n" +
                "  <link rel=\"stylesheet\" href=\"estilos.css\">\n" +
                " </head>\n" +
                " <body>\n" +
                "\t\n" +
                "\t\t<form  action=\"document.php\" class=\"formulario\" method=\"post\">\n" +
                "      <h1 class=\"fomulario__titulo\">Contáctenos</h1>\n" +
                "      <input type=\"text\"  name=\"nombre\" class=\"formulario__input\" required>\n" +
                "      <label for=\"\" class=\"formulario_label\">Nombres</label>\n" +
                "      <input type=\"text\"  name=\"correo\" class=\"formulario__input\" required>\n" +
                "      <label for=\"\" class=\"formulario_label\">Correo</label>\n" +
                "      <input type=\"text\" name=\"mensaje\" class=\"formulario__input\" required>\n" +
                "      <label for=\"\" class=\"formulario_label\">Mensaje</label>\n" +
                "      <input type=\"submit\" value=\"ENVIAR\" id=\"boton\" class=\"formulario__submit\">\n" +
                "\n" +
                "    </form>\n" +
                "\t\n" +
                "    <script src=\"form.js\"></script>\n" +
                " </body>\n" +
                "</html>";
        contenido1="*{\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "body{\n" +
                "  margin: 0;\n" +
                "  font-family: sans-serif;\n" +
                "}\n" +
                ".formulario{\n" +
                "  width: 500px;\n" +
                "  max-width: 100%;\n" +
                "  margin: auto;\n" +
                "  margin-top: 30px;\n" +
                "  padding: 20px;\n" +
                "  box-shadow: 0 0 20px 1px ;\n" +
                "  position: relative;\n" +
                "}\n" +
                ".fomulario__titulo{\n" +
                "text-align: center;\n" +
                "margin-top: 0;\n" +
                "color: rgba(0,0,0,0.7);\n" +
                "\n" +
                "}\n" +
                ".formulario__input, .formulario_label, .formulario__submit{\n" +
                "\n" +
                "  display: block;\n" +
                "  width: 100%;\n" +
                "  font-size: 1.3em;\n" +
                "\n" +
                "}\n" +
                "\n" +
                ".formulario__input{\n" +
                "  padding: 20px;\n" +
                "  background: rgba(0,0,0,0.2);\n" +
                "  border: 1px solid rgba(0,0,0,0.3);\n" +
                "  margin-bottom: 40px;\n" +
                "}\n" +
                "\n" +
                ".formulario__input:focus{\n" +
                "  outline: 1px solid rgba(0,0,0,0.7);\n" +
                "\n" +
                "}\n" +
                ".formulario__input:focus + .formulario_label{\n" +
                "  margin-top: -150px;\n" +
                "}\n" +
                "\n" +
                ".formulario_label{\n" +
                "  padding: 15px;\n" +
                "  position: absolute;\n" +
                "  margin-top: -90px;\n" +
                "  z-index: -20;\n" +
                "  color: rgba(0,0,0,0.6);\n" +
                "  transition: all 0.20s;\n" +
                "}\n" +
                ".formulario__submit{\n" +
                "  background: rgba(0,0,0,0.7);\n" +
                "  color: white;\n" +
                "  padding: 10px 20px;\n" +
                "  cursor: pointer;\n" +
                "}\n" +
                ".fijar {\n" +
                "  margin-top: -150px;\n" +
                "}\n";
        contenido2="var inputs = document.getElementsByClassName('formulario__input');\n" +
                "for(var i = 0;i< inputs.length;i++){\n" +
                "  inputs[i].addEventListener('keyup', function(){\n" +
                "    if(this.value.length>=1){\n" +
                "      this.nextElementSibling.classList.add('fijar');\n" +
                "    }else{\n" +
                "      this.nextElementSibling.classList.remove('fijar');\n" +
                "    }\n" +
                "  });\n" +
                "}\n";
        contenido3="<?php\n" +
                "\t$destino =\"jp.ice000@gmail.com\";\n" +
                "\t$nombre = $_POST[\"nombre\"];\n" +
                "\t$correo = $_POST[\"correo\"];\n" +
                "\t$mensaje = $_POST[\"mensaje\"];\n" +
                "\t$contenido =\"Nombre: \".$nombre . \"\\nCorreo: \" .$correo . \"\\nMensaje: \" .$mensaje;\n" +
                "\tmail($destino,\"Contanto APP\",$contenido);\n" +
                "\t\n" +
                "\t\n" +
                "\t?>";
        try {

            File tarjeta = Environment.getExternalStorageDirectory();
//            Toast.makeText(this,tarjeta.getAbsolutePath(),Toast.LENGTH_LONG).show();
            //creacion de nueva carpeta en memoria interna del celular
            File file1 = new File(tarjeta.getAbsolutePath()+"/DesarrolloWEB/Formulario");
            file1.mkdirs();
            File f1= new File(tarjeta.getAbsolutePath()+"/DesarrolloWEB");


            File file =new File(file1, "index.html");
            File file2=new File(file1, "estilos.css");
            File file3= new File(file1, "form.js");
            File file4= new File(file1, "document.php");

            try {
                OutputStreamWriter osw = new OutputStreamWriter(
                        new FileOutputStream(file));
                OutputStreamWriter osw1 = new OutputStreamWriter(
                        new FileOutputStream(file2));
                OutputStreamWriter osw2 = new OutputStreamWriter(
                        new FileOutputStream(file3));
                OutputStreamWriter osw3 = new OutputStreamWriter(
                        new FileOutputStream(file4));



                osw.write(contenido);
                osw1.write(contenido1);
                osw2.write(contenido2);
                osw3.write(contenido3);
                osw1.flush();


                osw2.flush();
                osw3.flush();
                osw.flush();
                osw.close();
                osw1.close();
                osw2.close();
                osw3.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //Toast.makeText(this,"Se creo La carpeta DesarrolloWEB en la memoria del celular \n De donde se leeran todos los proyectos ",Toast.LENGTH_LONG).show();


        } catch (Exception ioe) {
            //Toast.makeText(this,"La Aplicacion Requiere permiso de Almacenamiento",Toast.LENGTH_LONG).show();
        }

        listas=(ListView)findViewById(R.id.list);
        actual=(EditText)findViewById(R.id.textView);
        directorio =Environment.getExternalStorageDirectory().getPath();
        ver(directorio+"/DesarrolloWEB/");

        listas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //actual.setText(listas.getItemAtPosition(position).toString());
                Intent intent = new Intent(MainActivity.this, EditorProyecto.class);
                intent.putExtra("carpeta", listas.getItemAtPosition(position).toString());
                String l =listas.getItemAtPosition(position).toString();
                if (l.equals("../sdcard/DesarrolloWEB/") || l.equals("IMPORTANTE_LEER.txt")) {


                    nomostrar(l);



                    //if (mInterstitialAd.isLoaded()) {

                    mInterstitialAd.show();

                    // } else {
                    //     Log.d("TAG", "The interstitial wasn't loaded yet.");
                    //Toast.makeText(this,"no cargo",Toast.LENGTH_SHORT).show();
                    //}

                } else if (intent != null) {
                    //if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    //} else {
                    //   Log.d("TAG", "The interstitial wasn't loaded yet.");
                    //Toast.makeText(this,"no cargo",Toast.LENGTH_SHORT).show();
                    //}
                    startActivity(intent);
                }

            }
        });

    }



    public String nomostrar(String name){
        if(name.equals("../sdcard/DesarrolloWEB/")){
            Toast.makeText(this,"Carpeta raiz ",Toast.LENGTH_LONG).show();





        }
        if(name.equals("IMPORTANTE_LEER.txt")){
            Toast.makeText(this,conte,Toast.LENGTH_LONG).show();
        }
        return null;
    }



    public void Nuevo(View v){
        String nuevo2 = actual.getText().toString();
        if(!nuevo2.equals("")){

            File tarjeta = Environment.getExternalStorageDirectory();
            File file1 = new File(tarjeta.getAbsolutePath() + "/DesarrolloWEB/" + nuevo2);
            file1.mkdir();
            File file =new File(file1, "index.html");
            File file2=new File(file1, "estilos.css");
            File file3= new File(file1, "form.js");
            try {
                OutputStreamWriter osw = new OutputStreamWriter(
                        new FileOutputStream(file));
                OutputStreamWriter osw1 = new OutputStreamWriter(
                        new FileOutputStream(file2));
                OutputStreamWriter osw2 = new OutputStreamWriter(
                        new FileOutputStream(file3));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ver(tarjeta.getAbsolutePath() + "/DesarrolloWEB");
            actual.setText("");

        }else{
            Toast.makeText(this,"Debe ingresar nombre",Toast.LENGTH_SHORT).show();
        }

    }

    private void ver(String rutadi){
        item = new ArrayList<String>();
        ruta= new ArrayList<String>();
        int cont = 0;
        File dirActual = new File(rutadi);
        File[] lista= dirActual.listFiles();
        if(!rutadi.equals(directorio)){
            item.add("../sdcard/DesarrolloWEB/");
            ruta.add(dirActual.getParent());
            cont =1;
        }
        for(File archivo : lista){
            ruta.add(archivo.getPath());
        }
        Collections.sort(ruta,String.CASE_INSENSITIVE_ORDER);
        for(int i =cont ;i<ruta.size();i++){
            File archivo=new File(ruta.get(i));
            if(archivo.isFile()){
                item.add(archivo.getName());
            }else{
                item.add(archivo.getName());
            }

        }
        if(lista.length<1){
            item.add("no hat");
            ruta.add(rutadi);
        }
        adapter= new ArrayAdapter<String>(this,R.layout.lista,item);
        listas.setAdapter(adapter);

        mInterstitialAd.show();



    }

}
