package com.example.lilri.flproyect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import custom_font.MyTextView;

public class InicioEmpresa extends AppCompatActivity {
    MyTextView prod;
    MyTextView ubi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_empresa);
        prod=(MyTextView) findViewById(R.id.product);
        prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tierra
                Intent it = new Intent(InicioEmpresa.this,InicioEmpresa.class);
                startActivity(it);
            }
        });
        ubi = (MyTextView) findViewById(R.id.myTextView3);
        ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tierra
                Intent en = new Intent(InicioEmpresa.this,InicioEmpresa.class);
                en.putExtra("pro","emp");
                startActivity(en);
            }
        });
    }
}
