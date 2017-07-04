package br.com.qrcode.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.qrcode.model.Pedido;

public class Home extends AppCompatActivity {

    OKHTTP orders;

    ListView listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listaPedidos = (ListView) findViewById(R.id.lista_pedidos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaPedidos.removeAllViewsInLayout();
        orders = new OKHTTP();
        String strings = "";
        try {
            List<Pedido> pedidos = new OKHTTP().execute(strings).get();
            ArrayAdapter<Pedido> adapter = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, pedidos);
            listaPedidos.setAdapter(adapter);

            listaPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Pedido p = (Pedido) listaPedidos.getItemAtPosition(position);

                    Gson gson = new Gson();
                    if(p.getTransactions().size() == 1){
                        Intent enviarPara = new Intent(Home.this, GerarQrCode.class);
                        enviarPara.putExtra("pedido", gson.toJson(p));
                        startActivity(enviarPara);
                    }
                    else if(p.getTransactions().size() > 1){
                        Intent enviarPara = new Intent(Home.this, SelecionarTransacao.class);
                        enviarPara.putExtra("pedido", gson.toJson(p));
                        startActivity(enviarPara);
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
