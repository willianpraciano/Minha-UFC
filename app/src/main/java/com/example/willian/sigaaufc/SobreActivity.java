package com.example.willian.sigaaufc;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mehdi.sakout.aboutpage.AboutPage;

public class SobreActivity extends AppCompatActivity {

    private Button btn_cliqueAqui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sobre);
        //btn_cliqueAqui = findViewById(R.id.btn_cliqueAqui);

        //Código para fazer o botão voltar, na parte superior, aparecer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Sobre");//Titulo para ser exibido na sua Action Bar em frente à seta




        //ADICIONANDO PAGINA SOBRE DE UMA BIBLIOTECA link: https://github.com/medyo/android-about-page
        String descricao = "Este é um aplicativo que reúne vários links úteis da UFC para facilitar a vida dos estudantes.\n\n " +
                            "Se tiver dúvidas sobre o projeto ou quiser ajudar futuros estudantes com provas anteriores das suas disiciplinas, entre em contato:";

        View aboutPage = new AboutPage(this)
                //.isRTL(false)
                .setImage(R.drawable.capa)
                .setDescription(descricao)
                //.addItem(versionElement)
                //.addItem(adsElement)
                //.addGroup("Contato")
                .addEmail("willian.s.praciano@outlook.com", "Nos envie um Email")
                .addWebsite("http://loadingjr.ufc.br/")
                .addFacebook("loadingjr", "Curta nossa página no Facebook")
                .addInstagram("loadingjr", "Siga-nos no Instagram")
                //.addTwitter("medyo80")
                //.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                //.addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("willianpraciano/SIGAMobile", "GitHub do projeto - Willian S. Praciano")
                .create();
        setContentView(aboutPage);

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

    public void enviarEmail(View view){
        String[] destinatario = {"willian.s.praciano@outlook.com"};

        Intent i = new Intent(getIntent().ACTION_SENDTO);
        i.setData(Uri.parse("mailto:"));
        //i.setType("text/plain");
        i.putExtra(getIntent().EXTRA_EMAIL, destinatario);
        i.putExtra(getIntent().EXTRA_SUBJECT, "Sobre o aplicativo SIGAM");

        try {
            startActivity(Intent.createChooser(i, "Escolha um cliente de email"));
            finish();
            Log.i("Finished sending email.", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Não há clientes de email instalados!", Toast.LENGTH_SHORT).show();
        }
    }

}
