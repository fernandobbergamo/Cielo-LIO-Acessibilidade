package br.com.qrcode.model;

public class Transacao implements Cloneable {

    private String status;
    private String description;
    private long amount;
    private String brand;
    private String lastFourNumbers;

    public Transacao(String status, String description, long amount, String brand, String lastFourNumbers){
        this.status = status;
        this.description = description;
        this.amount = amount;
        this.brand = brand;
        this.lastFourNumbers = lastFourNumbers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLastFourNumbers() {
        return lastFourNumbers;
    }

    public void setLastFourNumbers(String lastFourNumbers) {
        this.lastFourNumbers = lastFourNumbers;
    }

    @Override
    public String toString() {
        return "Bandeira: "+brand+"\nFinal: "+lastFourNumbers;
    }

    @Override
    protected Transacao clone() throws CloneNotSupportedException {
        return (Transacao) super.clone();
    }
}
