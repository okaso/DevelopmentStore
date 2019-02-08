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
        et1 = (TextView) findViewById(R.id.textoNombre);
        et2 = (EditText) findViewById(R.id.texto1);
        tv2 = (TextView) findViewById(R.id.textoNombre2);
        tv2.setText(getIntent().getStringExtra("carpeta"));
        String archivo=getIntent().getStringExtra("Url");


        recuperar(archivo);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.ventanas,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        Intent intent= null;
        String carpeta1= tv2.getText().toString();

        if(id==R.id.itemweb){
            hilo.run();
            mInterstitialAd.show();

            guardar();
            intent = new Intent(EditorProyecto.this, WEB.class);
            intent.putExtra("Url","DesarrolloWEB/"+carpeta1);
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


    public void recuperar(String nomarchivo) {
        //String nomarchivo = et1.getText().toString();
        et1.setText(nomarchivo);
        File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(nomarchivo);
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
