package br.com.local.sqliteappempregados;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CarroAdapter extends ArrayAdapter<Carros> {

    Context mCtx;
    int listaLayoutRes;
    List<Carros> listaCarros;
    SQLiteDatabase meuBancoDeDados;

    public CarroAdapter(Context mCtx, int listaLayoutRes, List<Carros> listaCarros, SQLiteDatabase meuBancoDeDados) {
        super(mCtx, listaLayoutRes, listaCarros);

        this.mCtx = mCtx;
        this.listaLayoutRes = listaLayoutRes;
        this.listaCarros = listaCarros;
        this.meuBancoDeDados = meuBancoDeDados;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listaLayoutRes, null);

        final Carros carros = listaCarros.get(position);

        TextView txtViewModelo = view.findViewById(R.id.txtModeloViewCarro);
        TextView txtViewMarca = view.findViewById(R.id.txtMarcaViewCarro);
        TextView txtViewKm = view.findViewById(R.id.txtKmViewCarro);
        TextView txtViewDataEntrada = view.findViewById(R.id.txtEntradaviewCarro);

        txtViewModelo.setText(carros.getModelo());
        txtViewMarca.setText(carros.getMarca());
        txtViewKm.setText(String.valueOf(carros.getKm()));
        txtViewDataEntrada.setText(carros.getDataEntrada());

        Button btnExcluir = view.findViewById(R.id.btnExcluirViewCarro);
        Button btnEditar = view.findViewById(R.id.btnEditarViewCarro);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarCarro(carros);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Deseja excluir?");
                builder.setIcon(android.R.drawable.ic_notification_clear_all);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM funcionarios WHERE id = ?";
                        meuBancoDeDados.execSQL(sql, new Integer[]{carros.getId()});
                        recarregarCarrosDB(); // recarregarEmpregadosDB
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    public void alterarCarro(final Carros carros) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(R.layout.alterar_carro, null);
        builder.setView(view);

        final EditText txtEditarCarro = view.findViewById(R.id.txtEditarCarro);
        final EditText txtEditarKm = view.findViewById(R.id.txtEditarKm);
        final Spinner spnMarcas = view.findViewById(R.id.spnMarcas);

        txtEditarCarro.setText(carros.getModelo());
        txtEditarKm.setText(String.valueOf(carros.getKm()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.btnAlterarCarro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String modelo = txtEditarCarro.getText().toString().trim();
                String km = txtEditarKm.getText().toString().trim();
                String marca = spnMarcas.getSelectedItem().toString().trim();

                if (modelo.isEmpty()) {
                    txtEditarCarro.setError("Modelo está vazio");
                    txtEditarCarro.requestFocus();
                    return;
                }
                if (km.isEmpty()) {
                    txtEditarKm.setError("Km está vazio");
                    txtEditarKm.requestFocus();
                    return;
                }

                String sql = "UPDATE carros SET modelo = ?, marca = ?, km = ? WHERE id = ?";
                meuBancoDeDados.execSQL(sql,
                        new String[]{modelo, marca, km, String.valueOf(carros.getId())});
                Toast.makeText(mCtx, "Carro alterado com sucesso!!!", Toast.LENGTH_LONG).show();
                recarregarCarrosDB(); // recarregarEmpregadosDB
                dialog.dismiss();
            }
        });
    }

    public void recarregarCarrosDBDB() {
        Cursor cursorCarros = meuBancoDeDados.rawQuery("SELECT * FROM carros", null);
        if (cursorCarros.moveToFirst()) {
            listaCarros.clear();
            do {
                listaCarros.add(new Carros(
                        cursorCarros.getInt(0),
                        cursorCarros.getString(1),
                        cursorCarros.getString(2),
                        cursorCarros.getString(3),
                        cursorCarros.getDouble(4)
                ));
            } while (cursorCarros.moveToNext());
        }
        cursorCarros.close();
        notifyDataSetChanged();
    }
}
