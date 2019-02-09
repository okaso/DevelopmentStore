package app.com.development;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class WEB extends AppCompatActivity {
    private WebView web;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String sd=getIntent().getStringExtra("Url");
        web=(WebView)findViewById(R.id.webView);

        web.getSettings().setAllowFileAccessFromFileURLs(true);
        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                return false;
            }
        });
        web.setScrollbarFadingEnabled(true);

        web.getSettings().setSupportZoom(true);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.setWebChromeClient(new WebChromeClient());
        web.getSettings().setBuiltInZoomControls(true);
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        web.getSettings().setUserAgentString(newUA);

        web.loadUrl("file:///"+sd);
    }

    public void setDesktopMode(WebView webView,boolean enabled) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1
                );
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newUserAgent = null;
        }
        webView.getSettings().setUserAgentString(newUserAgent);
        webView.getSettings().setUseWideViewPort(enabled);
        webView.getSettings().setLoadWithOverviewMode(enabled);
        webView.reload();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.configuracionweb,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id == R.id.desktop) {
            setDesktopMode(web,true);
        }
        if(id == R.id.movil) {
            setDesktopMode(web,false);
        }
        return super.onOptionsItemSelected(item);
    }
}
