package praciano.willian.minhaufc;

import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity{

    private WebView webview;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;

    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        getSupportActionBar().setElevation(0); //Remove a sobra da actionbar

        frameLayout = findViewById(R.id.frameLayout);
        progressBar = findViewById(R.id.progressBar);

        //Código para fazer o botão voltar, na parte superior, aparecer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getTitulo());//Titulo para ser exibido na sua Action Bar em frente à seta



        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());
        //webview.setInitialScale(180);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);

        if(getTitulo().equals("Cardápio RU")){

            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1c557d")));

            if(isNetworkAvailable()) {
                Toast.makeText(getApplicationContext(), "Página será baixada para visualização offline", Toast.LENGTH_LONG).show();
                webview.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
                webview.getSettings().setAllowFileAccess(true);
                webview.getSettings().setAppCacheEnabled(true);
                webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

            }else { // loading offline
                    //Toast.makeText(getApplicationContext(), "Página foi baixada anteriormente", Toast.LENGTH_LONG).show();
                    webview.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
            }

        } else if(getTitulo().equals("SIGAA")){

            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b4877")));

        } else if(getTitulo().equals("Biblioteca")){

            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1c557d")));

        } else if(getTitulo().equals("Créditos RU")){

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String cartaoRU = preferences.getString("cartaoRU", "");

            if( !cartaoRU.equals("") ){

                Toast.makeText(getApplicationContext(), "O Nº do seu cartão do RU foi copiado para a área de transferência!", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Cole no campo 'Número do Cartão' ", Toast.LENGTH_LONG).show();
                //clipboard.setText(cartaoRU);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Nº do Cartão do RU", cartaoRU);
                clipboard.setPrimaryClip(clip);

            }
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c4d2eb")));

        } else if(getTitulo().equals("Provas Anteriores")){

            Toast.makeText(getApplicationContext(), "Clique em 'Abrir em Uma Nova Janela' para baixar os arquivos", Toast.LENGTH_LONG).show();
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b4877")));

        } else if(getTitulo().equals("Notícias")){

            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1c557d")));

        }

        webview.loadUrl(getUrl());



        //Barra de progresso
        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView View, int newProgress) {
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);

                if (newProgress == 100) {

                    frameLayout.setVisibility(View.GONE);
                    getSupportActionBar().setElevation(10); //Remove a sobra da actionbar

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    getSupportActionBar().setElevation(0); //Remove a sobra da actionbar
                }
            }

        });


        //Código para fazer Download de arquivos dentro da WebView
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Baixando Arquivo...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(WebViewActivity.this, Environment.DIRECTORY_DOWNLOADS,".pdf");
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Baixando Arquivo", Toast.LENGTH_LONG).show();
            }});

    } //Fim do onCreate()

    //Função para obtero o URL passado da MainActivity
    public String getUrl(){
        Bundle p = getIntent().getExtras();
        String url =p.getString("keyUrl");
        return url;
    }

    //Função para obtero o TITULO passado da MainActivity
    public String getTitulo(){
        Bundle p = getIntent().getExtras();
        String titulo =p.getString("keyTitulo");
        //Toast.makeText(getApplicationContext(), titulo, Toast.LENGTH_LONG).show();
        return titulo;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack(); //Se houver uma pagina anterio a webview volta pra ela
        }else {
            super.onBackPressed(); //Se não o app sai da Avtivity atual
        }
    }


    //Código que faz o botão voltar, na parte superior, funcionar
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }



}
