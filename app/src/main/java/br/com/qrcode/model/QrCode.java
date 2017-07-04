package br.com.qrcode.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;

import br.com.qrcode.util.ConverterData;


public class QrCode {
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;

    public Bitmap encodeAsBitmap(Pedido pedido, Context context) throws WriterException {

        BitMatrix result;

        // formular a mensagem
        String mensagem = getMensagem(pedido);

        try {
            result = new MultiFormatWriter().encode(mensagem, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private String getMensagem(Pedido pedido) {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        Transacao transaction = pedido.getTransactions().get(0);
        long valor = pedido.getTransactions().get(0).getAmount();

        int valorInteiro = (int) (valor / 100);
        int valorCentavos = (int) (valor % 100);
        String textoValor = "";
        if(valorInteiro > 0){
            if(valorInteiro > 1) {
                textoValor += valorInteiro + " reais ";
            }
            else{
                textoValor += valorInteiro + " real ";
            }
        }
        if(valorCentavos > 0){
            if(valorInteiro > 0){
                textoValor += "e ";
            }
            if(valorCentavos > 1) {
                textoValor += valorCentavos + " centavos ";
            }
            else{
                textoValor += valorCentavos + " centavo ";
            }
        }
        String formaPagamento;
        String numeros = "";
        if("CREDITO/A VISTA".equals(transaction.getDescription())){
            formaPagamento = "Crédito à vista";
        }
        else if("DEBITO/A VISTA".equals(transaction.getDescription())){
            formaPagamento = "Débito à vista";
        }
        else{
            formaPagamento = transaction.getDescription();
        }


        String aux = transaction.getLastFourNumbers();
        for (int i =0; i < 4; i++){
            numeros += aux.toCharArray()[i]+" ";
        }
        numeros = numeros.substring(0, numeros.length()-1);

        return "Compra "+formaPagamento+" em "+ ConverterData.retornaDataFormatada(pedido.getUpdated_at())+
                ". Cartão: "+transaction.getBrand()+". Final: "+ numeros+
                ". Valor: "+textoValor+". Status: APROVADA.";
    }
}
