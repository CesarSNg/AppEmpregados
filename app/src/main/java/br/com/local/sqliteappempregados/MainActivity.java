package br.com.local.sqliteappempregados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NOME_BANCO_DE_DADOS = "bdAutomoveis";

    TextView lblCarro;
    EditText txtModelo, txtKm;
    Spinner spnMarcas;
    Button btnIncluirCarro;
    SQLiteDatabase meuBancoDeDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblCarro = findViewById(R.id.lblVisualizaCarro);
        txtModelo = findViewById(R.id.txtmodelo);
        txtKm = findViewById(R.id.txtkm);
        spnMarcas = findViewById(R.id.spnMarcas);

        btnIncluirCarro = findViewById(R.id.btnIncluirCarro);

        btnIncluirCarro.setOnClickListener(this);

        lblCarro.setOnClickListener(this);

        meuBancoDeDados = openOrCreateDatabase(NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        criarTabelaAutomovel();
    }

    private boolean verificarEntrada(String modelo, String km) {
        if (modelo.isEmpty()) {
            txtModelo.setError("Modelo do carro");
            txtModelo.requestFocus();
            return false;
        }

        if (km.isEmpty() || Integer.parseInt(km) <= 0) {
            txtKm.setError("Km do carro");
            txtKm.requestFocus();
            return false;
        }
        return true;
    }

    private void adicionarCarro() {

        Locale meuLocal = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getCurrencyInstance(meuLocal);


        String modeloCar = txtModelo.getText().toString().trim();
        String kmCar = txtKm.getText().toString().trim();
        String marcaCar = spnMarcas.getSelectedItem().toString();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dataEntrada = simpleDateFormat.format(calendar.getTime());

        if (verificarEntrada(modeloCar, kmCar)) {

            String insertSQL = "INSERT INTO carros (" +
                    "modelo, " +
                    "marca, " +
                    "dataEntrada," +
                    "km)" +
                    "VALUES(?, ?, ?, ?);";


            meuBancoDeDados.execSQL(insertSQL, new String[]{modeloCar, marcaCar, dataEntrada, kmCar});

            Toast.makeText(getApplicationContext(), "Carro adicionado com sucesso!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnIncluirCarro:
                adicionarCarro();
                break;

            case R.id.lblVisualizaCarro:
                startActivity(new Intent(getApplicationContext(), Funcionarios_Activity.class));
                break;
        }

    }

    private void criarTabelaAutomovel() {
        meuBancoDeDados.execSQL(
                "CREATE TABLE IF NOT EXISTS carros (" +
                        "id integer PRIMARY KEY AUTOINCREMENT," +
                        "modelo varchar(200) NOT NULL," +
                        "marca varchar(200) NOT NULL," +
                        "dataEntrada datetime NOT NULL," +
                        "km double NOT NULL );"
        );
    }
}