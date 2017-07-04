package br.com.qrcode.qrcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import br.com.qrcode.model.Pedido;
import br.com.qrcode.model.Transacao;

public class SelecionarTransacao extends AppCompatActivity {

    private Gson gson;

    private Intent intent;

    private Pedido pedido;

    private ListView listaTransacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_transacao);

        gson = new Gson();
        intent = getIntent();

        pedido = gson.fromJson(intent.getStringExtra("pedido"), Pedido.class);

        listaTransacao = (ListView) findViewById(R.id.lista_transacoes);
        ArrayAdapter<Transacao> adapter = new ArrayAdapter<Transacao>(this, android.R.layout.simple_list_item_1, pedido.getTransactions());
        listaTransacao.setAdapter(adapter);

        listaTransacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Transacao t = (Transacao) listaTransacao.getItemAtPosition(position);

                try {
                    Pedido p = (Pedido) pedido.clone();
                    p.getTransactions().clear();
                    p.getTransactions().add(t);

                    Gson gson = new Gson();
                    Intent enviarPara = new Intent(SelecionarTransacao.this, GerarQrCode.class);
                    enviarPara.putExtra("pedido", gson.toJson(p));
                    startActivity(enviarPara);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaTransacao.requestLayout();
    }
}
