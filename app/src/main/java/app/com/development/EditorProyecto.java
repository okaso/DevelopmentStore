package app.com.development;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EditorProyecto extends AppCompatActivity {
    private EditText et2;
    private TextView et1, tv2;
    private InterstitialAd mInterstitialAd;
    private Metodo hilo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_proyecto);
        hilo = new Metodo();
        MobileAds.initialize(this,"ca-app-pub-9234222684473855~9343784971");

        mInterstitialAd = new InterstitialAd( this );
        mInterstitialAd . setAdUnitId ( "ca-app-pub-9234222684473855/2339392920" );
        //time();
        mInterstitialAd.loadAd(new AdRequest.Builder ().build ());
        hilo.run();
        mInterstitialAd.setAdListener ( new AdListener(){
            @Override public void onAdLoaded () {
                //mInterstitialAd.show();

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
        et1 = (TextView) findViewById(R.id.textoNombre);
        et2 = (EditText) findViewById(R.id.texto1);
        tv2 = (TextView) findViewById(R.id.textoNombre2);
        tv2.setText(getIntent().getStringExtra("carpeta"));


        recuperar("index.html", tv2.getText().toString());


    }




    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.ventanas,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        Intent intent= null;
        String carpeta1= tv2.getText().toString();

        if(id == R.id.itemhtml) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
                //Toast.makeText(this,"no cargo",Toast.LENGTH_SHORT).show();
            }
            String carpeta= tv2.getText().toString();
            String html = "index.html";
            guardar();
            //Toast.makeText(this,"opcion1",Toast.LENGTH_LONG).show();
            recuperar(html,carpeta);

        }if(id== R.id.itemestilos){
            String carpeta= tv2.getText().toString();
            String css = "estilos.css";
            guardar();
            recuperar(css,carpeta);
        }if(id== R.id.itemform){
            String carpeta= tv2.getText().toString();
            String js = "form.js";
            guardar();
            recuperar(js,carpeta);

        }if(id== R.id.itemphp){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
                //Toast.makeText(this,"no cargo",Toast.LENGTH_SHORT).show();
            }
            String carpeta= tv2.getText().toString();
            String js = "document.php";
            guardar();
            recuperar(js,carpeta);

        }if(id== R.id.itemacerca){
            guardar();
            intent = new Intent(EditorProyecto.this, AcercaDe.class);

        }if(id==R.id.itemweb){
            hilo.run();
            mInterstitialAd.show();

            guardar();
            intent = new Intent(EditorProyecto.this, WEB.class);
            intent.putExtra("Url","DesarrolloWEB/"+carpeta1);

        }if(id==R.id.itemayuda){
            hilo.run();
            mInterstitialAd.show();


            intent = new Intent(EditorProyecto.this, AyudaUsuario.class);


        }


        if(intent!=null){
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void guardar(){
        String nomarchivo = et1.getText().toString();
        String contenido = et2.getText().toString();
        try {
            File tarjeta = Environment.getExternalStorageDirectory();
//            Toast.makeText(this,tarjeta.getAbsolutePath(),Toast.LENGTH_LONG).show();
            //creacion de nueva carpeta en memoria interna del celular
            File file1 = new File(tarjeta.getAbsolutePath()+"/DesarrolloWEB/"+tv2.getText().toString());

            File file = new File(file1,nomarchivo);

            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file));
            osw.write(contenido);
            osw.flush();
            osw.close();
            Toast.makeText(this, "Los datos fueron grabados correctamente",
                    Toast.LENGTH_SHORT).show();

        } catch (IOException ioe) {
            Toast.makeText(this, "No se pudo grabar "+ioe.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    public void Grabar(View v){
        String nomarchivo = et1.getText().toString();
        String contenido = et2.getText().toString();
        try {
            File tarjeta = Environment.getExternalStorageDirectory();
            Toast.makeText(this,tarjeta.getAbsolutePath(),Toast.LENGTH_LONG).show();
            //creacion de nueva carpeta en memoria interna del celular
            File file1 = new File(tarjeta.getAbsolutePath()+"/DesarrolloWEB/"+tv2.getText().toString());

            File file = new File(file1,nomarchivo);

            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file));
            osw.write(contenido);
            osw.flush();
            osw.close();
            Toast.makeText(this, "Los datos fueron grabados correctamente",
                    Toast.LENGTH_SHORT).show();
            //et1.setText("");
            //et2.setText("");
        } catch (IOException ioe) {
            Toast.makeText(this, "No se pudo grabar "+ioe.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }


    public void recuperar(String nomarchivo,String carpeta) {
        //String nomarchivo = et1.getText().toString();
        et1.setText(nomarchivo);
        File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(tarjeta.getAbsolutePath()+"/DesarrolloWEB/"+carpeta, nomarchivo);
        try {
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archivo = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String todo = "";
            while (linea != null) {
                todo = todo + linea + " \n";
                linea = br.readLine();
            }
            br.close();
            archivo.close();
            et2.setText(todo);
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo leer "+e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


}
