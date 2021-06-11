package br.com.local.sqliteappempregados;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Carros_Activity extends AppCompatActivity {

    List<Carros> carrosList;
    CarroAdapter carroAdapter;
    SQLiteDatabase meuBancoDeDados;
    ListView listViewCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carros_layout);

        listViewCarros = findViewById(R.id.listarCarrosView);
        carrosList = new ArrayList<>();

        meuBancoDeDados = openOrCreateDatabase(MainActivity.NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        visualizarCarrosDatabase();
    }

    private void visualizarCarrosDatabase() {
        Cursor cursorCarros = meuBancoDeDados.rawQuery("SELECT * FROM carros", null);


        if (cursorCarros.moveToFirst()) {
            do {
                carrosList.add(new Carros(
                        cursorCarros.getInt(0),
                        cursorCarros.getString(1),
                        cursorCarros.getString(2),
                        cursorCarros.getString(3),
                        cursorCarros.getDouble(4)
                ));
            } while (cursorCarros.moveToNext());
        }
        cursorCarros.close();

        carroAdapter = new CarroAdapter(this,R.layout.lista_view_carros,carrosList,meuBancoDeDados);

        listViewCarros.setAdapter(carroAdapter);
    }
}