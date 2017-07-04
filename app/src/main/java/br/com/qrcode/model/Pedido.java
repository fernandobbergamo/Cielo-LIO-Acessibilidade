package br.com.qrcode.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.qrcode.util.ConverterData;


public class Pedido implements Cloneable {

    private String reference;
    private String updated_at;
    private List<Transacao> transactions;

    public Pedido(String reference, String updated_at){
        this.reference = reference;
        this.updated_at = updated_at;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<Transacao> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transacao> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return reference+"("+ ConverterData.retornaDataComBarras(updated_at)+")\nNumero de transações: "+(transactions != null? transactions.size() : "0");
    }

    @Override
    public Pedido clone() throws CloneNotSupportedException {
        Pedido p = new Pedido("", "");
        p.setReference(this.reference);
        p.setUpdated_at(this.updated_at);
        List<Transacao> transacoes = new ArrayList<>();
        for (Transacao t : this.getTransactions()){
            transacoes.add(t.clone());
        }
        p.setTransactions(transacoes);
        return p;
    }
}
