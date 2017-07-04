package br.com.qrcode.qrcode;

import android.os.AsyncTask;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.qrcode.model.Pedido;
import br.com.qrcode.model.Transacao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHTTP extends AsyncTask<String, Void, List<Pedido>> {

    private String merchant = "26069f94-c2d1-4813-81cb-649ab934833d";
    private String clientId = "hmFVzxImJ3r5";

    @Override
    protected List<Pedido> doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.cielo.com.br/order-management/v1/orders")
                .get()
                .addHeader("merchant-id", merchant)
                .addHeader("client-id", clientId)
                .addHeader("access-token", "hW0vNhWHvLQd")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = null;
        String resposta = null;
        try {
            response = client.newCall(request).execute();
            resposta = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Pedido> pedidos = null;
        List<Transacao> transacoes = null;
        try {
            JSONArray ja = new JSONArray(resposta);
            JSONObject jo = null;
            pedidos = new ArrayList<>();
            Pedido pedido;
            int qtde = 0;
            for (int i = 0; i < ja.length(); i++) {
                if (qtde >= 10) {
                    break;
                }
                jo = ja.getJSONObject(i);
                if ("CLOSED".equals(jo.getString("status")) || "PAID".equals(jo.getString("status"))) {
                    String reference;
                    try {
                        reference = jo.getString("reference");
                    } catch (JSONException a) {
                        reference = jo.getString("number");
                    }
                    String updated = jo.getString("updated_at");
                    pedido = new Pedido(reference, updated);
                    JSONArray aux = jo.getJSONArray("transactions");
                    Transacao transacao;
                    transacoes = new ArrayList<>();
                    if(aux.length() != 0) {
                        for (int j = 0; j < aux.length(); j++) {
                            JSONObject transaction = aux.getJSONObject(j);
                            String status = transaction.getString("status");
                            String description = transaction.getString("description");
                            long amount = transaction.getLong("amount");
                            String brand = transaction.getJSONObject("card").getString("brand");
                            String lastFourNumbers = transaction.getJSONObject("card").getString("mask").replace("*", "");
                            transacao = new Transacao(status, description, amount, brand, lastFourNumbers);
                            transacoes.add(transacao);
                        }
                        pedido.setTransactions(transacoes);
                        pedidos.add(pedido);
                        qtde++;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pedidos;
    }



}
