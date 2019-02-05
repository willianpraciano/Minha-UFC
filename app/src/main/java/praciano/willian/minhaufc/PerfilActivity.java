package praciano.willian.minhaufc;

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
import android.widget.Toast;


public class PerfilActivity extends AppCompatActivity {

    private static String[] campi = new String[]{"", "Benfica", "Crateús", "LABOMAR", "PICI", "Porangabuçu", "Quixadá", "Russas", "Sobral"};
    private static String[] cursos = new String[]{
            "Administração (Diurno)",
            "Administração (Noturno)",
            "Administração Pública",
            "Agronomia",
            "Arquitetura e Urbanismo",
            "Biblioteconomia",
            "Biotecnologia",
            "Ciência da Computação",
            "Ciências Ambientais",
            "Ciências Atuariais",
            "Ciências Biológicas",
            "Ciências Contábeis (Diurno)",
            "Ciências Contábeis (Noturno)",
            "Ciências Econômicas",
            "Ciências Econômicas (Diurno)",
            "Ciências Econômicas (Noturno)",
            "Ciências Sociais (Diurno)",
            "Ciências Sociais (Noturno)",
            "Cinema e Audiovisual",
            "Computação",
            "Comunicação Social",
            "Comunicação Social - Jornalismo",
            "Comunicação Social - Jornalismo da Terra",
            "Comunicação Social - Publicidade e Propaganda",
            "Dança - Bacharelado",
            "Dança - Licenciatura",
            "Design",
            "Design - Moda",
            "Design Digital",
            "Direito (Diurno)",
            "Direito (Noturno)",
            "Economia Doméstica",
            "Economia Ecológica",
            "Educação Física",
            "Educação Física - Bacharelado (Diurno)",
            "Educação Física - Bacharelado (Vespertino-Noturno)",
            "Educação Física - Licenciatura (Diurno)",
            "Educação Física - Licenciatura (Vespertino-Noturno)",
            "Enfermagem",
            "Engenharia Ambiental",
            "Engenharia Civil",
            "Engenharia de Alimentos",
            "Engenharia de Computação",
            "Engenharia de Energias E Meio Ambiente",
            "Engenharia de Energias Renováveis",
            "Engenharia de Minas",
            "Engenharia de Pesca",
            "Engenharia de Petróleo",
            "Engenharia de Produção",
            "Engenharia de Produção Mecânica",
            "Engenharia de Software",
            "Engenharia de Telecomunicações",
            "Engenharia de Teleinformatica (Diurno)",
            "Engenharia de Teleinformática (Noturno)",
            "Engenharia Elétrica",
            "Engenharia Mecânica",
            "Engenharia Metalúrgica",
            "Engenharia Química",
            "Estatística",
            "Estilismo d Moda",
            "Farmácia",
            "Filosofia",
            "Finanças",
            "Física",
            "Física - Bacharelado",
            "Física - Licenciatura",
            "Fisioterapia",
            "Gastronomia",
            "Geografia",
            "Geologia",
            "Gestão de Políticas Públicas",
            "História",
            "Letras",
            "Letras - Espanhol",
            "Letras - Inglês",
            "Letras - Libras",
            "Licenciatura Intercultural Indígena Pitakaja",
            "Magistério Indígena Tremembé Superior",
            "Matemática",
            "Matemática - Bacharelado",
            "Matemática - Licenciatura",
            "Matemática Industrial",
            "Medicina",
            "Música",
            "Música - Licenciatura",
            "Oceanografia",
            "Odontologia",
            "Pedagogia (Diurno)",
            "Pedagogia (Noturno)",
            "Pedagogia Da Terra",
            "Pedagogia/PARFOR",
            "Psicologia",
            "Química",
            "Química - Bacharelado",
            "Química - Licenciatura",
            "Química Industrial",
            "Redes de Computadores",
            "Secretariado Executivo",
            "Sistemas de Informação",
            "Sistemas e Mídias Digitais (Diurno)",
            "Sistemas e Mídias Digitais (Noturno)",
            "Teatro - Licenciatura",
            "Tecnologia em Gestão da Educação Superior",
            "Tecnologia em Gestão da Qualidade",
            "Tecnologia em Gestão de Hospitais Universitários",
            "Zootecnia"

    };
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
        editNome.setText(preferences.getString("nome", ""));
        editMatricula.setText(preferences.getString("matricula", ""));
        spinnerCampus.setSelection( preferences.getInt("posicao", 0) );
        autoCompleteCurso.setText(preferences.getString("curso", ""));
        autoComplete2Chamada.setText(preferences.getString("email", ""));
        editCartaoRU.setText(preferences.getString("cartaoRU", ""));
        checkCardapio.setChecked(preferences.getBoolean("notificar", false));


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

}
