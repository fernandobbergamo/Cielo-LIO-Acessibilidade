package br.com.qrcode.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import br.com.qrcode.model.Pedido;
import br.com.qrcode.model.QrCode;

public class GerarQrCode extends AppCompatActivity {

    private QrCode qrcode;

    private Gson gson;

    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        qrcode = new QrCode();
        Intent intent = getIntent();
        gson = new Gson();

        pedido = gson.fromJson(intent.getStringExtra("pedido"), Pedido.class);
        ImageView imageView = (ImageView) findViewById(R.id.qrCode);
        try{
            Bitmap bitmap = qrcode.encodeAsBitmap(pedido, GerarQrCode.this);
            imageView.setImageBitmap(bitmap);
        } catch(WriterException e){
            e.printStackTrace();
        }
    }
}
