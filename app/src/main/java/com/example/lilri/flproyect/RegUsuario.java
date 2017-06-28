package com.example.lilri.flproyect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import custom_font.MyEditText;
import custom_font.MyTextView;

public class RegUsuario extends AppCompatActivity {
    MyTextView signin1;
    MyEditText user;
    MyEditText email;
    MyEditText con;
    MyEditText nombre;
    MyEditText ape;
    MyEditText fechanac;
    RadioGroup rg;
    MyTextView registrar;
    RadioButton rb;
    TextView zoo;
    MyTextView create;
    MyTextView pass;
    MyEditText usuario;
    MyEditText contra;
    CallbackManager callbackManager;
    Request request;
    RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.67/base/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_usuario);
        signin1 = (MyTextView) findViewById(R.id.signin1);
        nombre = (MyEditText) findViewById(R.id.regno);
        ape = (MyEditText) findViewById(R.id.regape);
        rg = (RadioGroup) findViewById(R.id.rgp);
        fechanac = (MyEditText) findViewById(R.id.fechnac);
        signin1 = (MyTextView) findViewById(R.id.signin1);
        user = (MyEditText) findViewById(R.id.regusu);
        email = (MyEditText) findViewById(R.id.regmail);
        con = (MyEditText) findViewById(R.id.regcontra);
        pass = (MyTextView) findViewById(R.id.register);
        requestQueue = Volley.newRequestQueue(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) findViewById(checkedId);
            }
        });
        pass.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("bien")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("bien"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegUsuario.this, RegUsuario.class));
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("nombre", nombre.getText().toString());
                        hashMap.put("apellido", ape.getText().toString());
                        hashMap.put("mail", email.getText().toString());
                        hashMap.put("genero", rb.getText().toString());
                        hashMap.put("fechanac", fechanac.getText().toString());
                        hashMap.put("usuario", user.getText().toString());
                        hashMap.put("contraseña", con.getText().toString());

                        return hashMap;
                    }
                };
                requestQueue.add(request);


                ////Intent it = new Intent(MainActivity.this, personal.class);

                ////startActivity(it);

                //

            }

        });

        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(RegUsuario.this, login.class);
                startActivity(it);
            }
        });
    }
}
