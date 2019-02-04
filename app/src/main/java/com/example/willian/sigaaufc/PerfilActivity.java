package com.example.willian.sigaaufc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilActivity extends AppCompatActivity {

    private static String[] campi = new String[]{"", "Benfica", "Crateús", "LABOMAR", "PICI", "Porangabuçu", "Quixadá", "Russas", "Sobral"};
    private static String[] cursos = new String[]{"Engenharia da Computação", "Engenharia Elétrica", "Psicologia", "Dontologia", "Medicina"};
    private static String[] emails = new String[]{"nadia.menezes@sobral.ufc.br", "michelle@sobral.ufc.br"};

    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";

    private EditText editNome;
    private EditText editMatricula;
    private EditText editCartaoRU;
    private AutoCompleteTextView autoCompleteCurso;
    private AutoCompleteTextView autoComplete2Chamada;
    private Spinner spinnerCampus;
    private CheckBox checkCardapio;

    private Button buttonSalvar;

    private String campus;
    private int posicao;
    private String nome;
    private String matricula;
    private String cartaoRU;
    private String curso;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getSupportActionBar().setTitle("Perfil"); //Difinindo Titulo na ActionBar

        /*
        //Código para fazer o botão voltar, na parte superior, aparecer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        */


        editNome = findViewById(R.id.editNome);
        editMatricula = findViewById(R.id.editMatricula);
        editCartaoRU = findViewById(R.id.editCartaoRU);
        spinnerCampus = findViewById(R.id.spinnerCampus);
            ArrayAdapter<String> adapterCampus = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, campi );
            adapterCampus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCampus.setAdapter(adapterCampus);
            spinnerCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        campus = spinnerCampus.getSelectedItem().toString();
                        posicao = spinnerCampus.getSelectedItemPosition();
                        //Toast.makeText(getApplicationContext(), "Campus: "+ campus + " posição: "+ posicao, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        autoCompleteCurso = findViewById(R.id.autoCompleteCurso);
            ArrayAdapter<String> adapterCurso = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, cursos );
            autoCompleteCurso.setAdapter(adapterCurso);
        autoComplete2Chamada = findViewById(R.id.autoComplete2Chamada);
            ArrayAdapter<String> adapter2Chamada = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, emails );
            autoComplete2Chamada.setAdapter(adapter2Chamada);

        checkCardapio = findViewById(R.id.checkCardapio);

        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                salvarDados();

            }
        });


        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        if(preferences.contains("nome")){
            editNome.setText(preferences.getString("nome", ""));
        }
        if(preferences.contains("matricula")){
            editMatricula.setText(preferences.getString("matricula", "0"));
        }
        if(preferences.contains("posicao")){
            spinnerCampus.setSelection( preferences.getInt("posicao", 0) );
        }
        if(preferences.contains("curso")){
            autoCompleteCurso.setText(preferences.getString("curso", ""));
        }
        if(preferences.contains("email")){
            autoComplete2Chamada.setText(preferences.getString("email", ""));
        }
        if(preferences.contains("cartaoRU")){
            editCartaoRU.setText(preferences.getString("cartaoRU", ""));
        }
        if(preferences.contains("notificar")){
            checkCardapio.setChecked(preferences.getBoolean("notificar", false));
        }


    }//Fim do onCreate()

    public void salvarDados(){
        if (campus.equals("")){
            Toast.makeText(getApplicationContext(), "Por favor, escolha o seu Campus!", Toast.LENGTH_LONG).show();
        }else{

            nome = editNome.getText().toString();
            matricula = editMatricula.getText().toString(); //Quando não tem nenhum númeor da erro
            cartaoRU = editCartaoRU.getText().toString();
            curso = autoCompleteCurso.getText().toString();
            email = autoComplete2Chamada.getText().toString();

            SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("nome", nome);
            editor.putString("matricula", matricula);
            editor.putString("cartaoRU", cartaoRU);
            editor.putString("campus", campus);
            editor.putInt("posicao", posicao);
            editor.putString("curso", curso);
            editor.putString("email", email);
            editor.putBoolean("notificar", checkCardapio.isChecked());
            editor.commit();

            Toast.makeText(getApplicationContext(), "Seus dados foram salvos com sucesso!", Toast.LENGTH_SHORT).show();

            finish();
            //startActivity( new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (campus.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, escolha o seu Campus!", Toast.LENGTH_LONG).show();
        } else {
            salvarDados();
            super.onBackPressed();
        }
    }

    /*
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
    */
}
