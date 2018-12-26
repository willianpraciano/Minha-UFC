package com.example.willian.sigaaufc;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                enviarProvas();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sigaa) {

            String url = "https://si3.ufc.br/sigaa/verTelaLogin.do";
            abriChromeCustomTabs(url);

        } else if (id == R.id.nav_biblioteca) {

            String url = "https://pergamum.ufc.br/pergamum/mobile/index.php";
            String titulo = "Biblioteca UFC";
            abrirWebView(url, titulo);

        } else if (id == R.id.nav_cardapio) {

            String url = "http://www.sobral.ufc.br/ru/cardapio/";
            String titulo = "Cardapio - UFC Sobral";
            abrirWebView(url, titulo);

        } else if (id == R.id.nav_saldoRu) {

            String url = "https://si3.ufc.br/public/iniciarConsultaSaldo.do";
            String titulo = "Saldo Cartão do RU";
            abrirWebView(url, titulo);

        } else if(id == R.id.nav_provas) {

            String url = "https://drive.google.com/folderview?id=1mLtT2NOUWdsRx4oCT8DaKApznaQG0mQ6";
            String titulo = "Provas Anteriores";
            abrirWebView(url, titulo);

        } else if (id == R.id.nav_noticias) {

            String url = "http://www.sobral.ufc.br/";
            String titulo = "Noticias UFC Sobral";
            abrirWebView(url, titulo);

        }else if(id == R.id.nav_enviarProvas){
            enviarProvas();

        } else if(id == R.id.nav_compartilharApp){

            Intent i = new Intent(getIntent().ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(getIntent().EXTRA_TEXT,"SIGAM (SIGAA Mobile) é um app que reúne vários links " +
                    "úteis da UFC. Segue o link para baixar: " + "\n" +
                    "https://bit.ly/2QiC5Os" + "\n\n" +

                    "Instruções de instalação:" + "\n" +
                    "https://github.com/willianpraciano/SIGAMobile");
            startActivity(i);


        }else if (id == R.id.nav_sobre){
            Intent i = new Intent(this, SobreActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Função que testa se o Chrome está instalado
    private boolean chromeInstalled() {
        try {

            //getPackageManager().getPackageInfo("com.android.chrome", 0);
            //return true;

            return getPackageManager().getApplicationInfo("com.android.chrome", 0).enabled;
        } catch (Exception e) {
            return false;
        }
    }


    //Função para abrir links usando ChromeCustomTabs
    public void abriChromeCustomTabs(String url){
        if(chromeInstalled()){
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(Color.parseColor("#1f567d"));
            builder.setShowTitle(true);
            customTabsIntent.launchUrl(this, Uri.parse(url));
        }else{
            Toast.makeText(this, "O APP Funciona melhor com o Google Chrome intalado!!!", Toast.LENGTH_LONG).show();
            abrirWebView(url, "SIGAA UFC");
        }

    }


    //Função para abrir links usando WebView
    public void abrirWebView(String url, String titulo){
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("keyUrl", url);
        i.putExtra("keyTitulo", titulo);
        startActivity(i);
    }


    //Função para abrir o SIGAA através de um botão
    public void abrirSigaa(View view){
        String url = "https://si3.ufc.br/sigaa/verTelaLogin.do";
        abriChromeCustomTabs(url);
    }

    //Função para abrir o site da Biblioteca da UFC através de um botão
    public void abrirBiblioteca(View view){
        String url = "https://pergamum.ufc.br/pergamum/mobile/index.php";
        String titulo = "Biblioteca UFC";
        abrirWebView(url, titulo);
    }

    //Função para abrir o site do cardapio do RU da UFC Campus Sobral através de um botão
    public void abrirCardapio(View view){
        String url = "http://www.sobral.ufc.br/ru/cardapio/";
        String titulo = "Cardapio - UFC Sobral";
        abrirWebView(url, titulo);
    }

    //Função para abrir o site que mostra o saldo do Cartão do RU para alunos da UFC através de um botão
    public void abrirSaldoRu(View view){
        String url = "https://si3.ufc.br/public/iniciarConsultaSaldo.do";
        String titulo = "Saldo Cartão do RU";
        abrirWebView(url, titulo);
    }

    //Função para abir um site com provas passadas através de um botão
    public void abrirProvas(View view){

        String url = "https://drive.google.com/folderview?id=1mLtT2NOUWdsRx4oCT8DaKApznaQG0mQ6";
        /*
        abriChromeCustomTabs(url);
        */
        //startActivity(new Intent(MainActivity.this, WebViewActivity.class));
        String titulo = "Provas Anteriores";
        abrirWebView(url, titulo);
    }

    //Função para abrir o site de noticias da UFC Sobral através de um botão
    public void abrirNoticias(View view){
        String url = "http://www.sobral.ufc.br/";
        String titulo = "Noticias UFC Sobral";
        abrirWebView(url, titulo);
    }

    public void enviarProvas(){
        String[] destinatario = {"willian.s.praciano@outlook.com"};

        Intent i = new Intent(getIntent().ACTION_SENDTO);
        i.setData(Uri.parse("mailto:"));
        //i.setType("text/plain");
        i.putExtra(getIntent().EXTRA_EMAIL, destinatario);
        i.putExtra(getIntent().EXTRA_SUBJECT, "PROVAS ANTERIORES - SIGAM");
        i.putExtra(getIntent().EXTRA_TEXT,
                "Por favor, preencha os campos abaixo e anexe o(s) arquivo(s) da(s) prova(s) que você deseja enviar." + "\n\n" +
                        "Curso: " + "\n" +
                        "Disciplina: " + "\n" +
                        "Professor: "  + "\n" +
                        "Semestre (Ex.: 2018.2): "
        );


        try {
            startActivity(Intent.createChooser(i, "Enviar as Provas pelo:"));
            finish();
            Log.i("Finished sending email.", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "Não há clientes de email instalados!", Toast.LENGTH_SHORT).show();
        }
    }

}
