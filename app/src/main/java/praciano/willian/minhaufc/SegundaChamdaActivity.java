package praciano.willian.minhaufc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SegundaChamdaActivity extends AppCompatActivity {

    private EditText editNome, editMatricula, editDisciplina, editProfessor, editData, editEmail;
    private Button buttonEnviar;

    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencias";

    private static String nome, matricula, email, disciplina, professor, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_chamda);

        editNome = findViewById(R.id.editNome);
        editMatricula = findViewById(R.id.editMatricula);
        editDisciplina = findViewById(R.id.editDisciplina);
        editProfessor = findViewById(R.id.editProfessor);
        editData = findViewById(R.id.editData);
        editEmail = findViewById(R.id.editEmail);

        buttonEnviar = findViewById(R.id.buttonEnviar);

        final SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        editNome.setText(preferences.getString("nome", ""));
        editMatricula.setText(preferences.getString("matricula", ""));
        editEmail.setText(preferences.getString("email", ""));

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome = editNome.getText().toString();
                matricula = editMatricula.getText().toString();
                email = editEmail.getText().toString();
                disciplina = editDisciplina.getText().toString();
                professor = editProfessor.getText().toString();
                data = editData.getText().toString();

                String[] destinatario = {email};
                Intent i = new Intent(getIntent().ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"));
                //i.setType("text/plain");
                i.putExtra(getIntent().EXTRA_EMAIL, destinatario);
                i.putExtra(getIntent().EXTRA_SUBJECT, "SOLICITAÇÃO DE 2ª CHAMADA");
                i.putExtra(getIntent().EXTRA_TEXT,
                                "Aluno: " + nome + ";\n" +
                                "Matrícula: " + matricula + ";\n" +
                                "Disciplina: " + disciplina + ";\n" +
                                "Professor: " + professor + ";\n" +
                                "Data da 1ª Chamada: " + data + ";\n"
                );

                try {
                    startActivity(Intent.createChooser(i, "Escolha o Gerenciador de Email:"));
                    Log.i("Finished sending email.", "");
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Não há clientes de email instalados!", Toast.LENGTH_SHORT).show();
                }


                Toast.makeText(getApplicationContext(), "Boa Sorte, Guerreiro(a), você vai precisar!", Toast.LENGTH_LONG).show();

            }
        });

        //Código para fazer o botão voltar, na parte superior, aparecer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Sobre");//Titulo para ser exibido na sua Action Bar em frente à seta


    }//fim do onCreate()


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
