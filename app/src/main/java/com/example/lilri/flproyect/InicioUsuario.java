package com.example.lilri.flproyect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import custom_font.MyTextView;

public class InicioUsuario extends AppCompatActivity {
    MyTextView bt;
    MyTextView ubi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_usuario);
        bt = (MyTextView) findViewById(R.id.buspro);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegUsuario.this,RegUsuario.class);
                String usu = getIntent().getExtras().getString("user");
                it.putExtra("user",usu);
                startActivity(it);
            }
        });
        ubi = (MyTextView) findViewById(R.id.myTextView3);
        ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en = new Intent(RegUsuario.this,RegUsuario.class);
                en.putExtra("pro","emp");
                startActivity(en);
            }
        });
    }
}
