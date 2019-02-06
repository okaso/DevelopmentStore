package app.com.development;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Metodo extends Thread {
    @Override
    public void run() {
        super.run();
        for (int i=1 ; i>0;i--){

            try {
                Metodo.sleep(100);
            } catch (InterruptedException ex) {
                //Logger.getLogger(Metodo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

