package com.example.lilri.flproyect;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import custom_font.MyEditText;
import custom_font.MyTextView;

public class login extends AppCompatActivity {
    private LoginButton loginButton;
        TextView zoo;
        MyTextView create;
        MyTextView pass;
        MyTextView emp;
        MyEditText usuario;
        MyEditText contra;
        CallbackManager callbackManager;
        Request request;
        RequestQueue requestQueue;
        private static final String URL="http://192.168.1.67/base/user_control.php";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            FacebookSdk.sdkInitialize(login.this);
            AppEventsLogger.activateApp(login.this);
            setContentView(R.layout.activity_login);
            emp = (MyTextView) findViewById(R.id.bemp);
            requestQueue = Volley.newRequestQueue(this);
            callbackManager = CallbackManager.Factory.create();
            Typeface custom_fonts = Typeface.createFromAsset(getAssets(), "fonts/Swistblnk Duwhoers Brush.ttf");
            zoo = (TextView)findViewById(R.id.zoo);
            zoo.setTypeface(custom_fonts);
            pass = (MyTextView) findViewById(R.id.login);
            create = (MyTextView)findViewById(R.id.create);
            usuario = (MyEditText) findViewById(R.id.usuario);
            contra = (MyEditText) findViewById(R.id.contraseña);
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it= new Intent(login.this,RegUsuario.class);
                    startActivity(it);
                }
            });
            emp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //es el intent para la siguiente actividad
                    Intent it = new Intent(login.this,RegEmpresa.class);
                    startActivity(it);
                }
            });
            pass.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.names().get(0).equals("bien")){
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("bien"),Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(login.this,InicioUsuario.class);
                                    it.putExtra("user",usuario.getText().toString());
                                    startActivity(it);
                                }

                                else {
                                    if(jsonObject.names().get(0).equals("bien1")){
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("bien1"),Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(login.this,InicioEmpresa.class));
                                    } else{
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error){
                            //
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("email",usuario.getText().toString());
                            hashMap.put("contraseña",contra.getText().toString());

                            return hashMap;
                        }
                    };
                    requestQueue.add(request);



                    ////Intent it = new Intent(MainActivity.this, personal.class);

                    ////startActivity(it);

                    //

                }

            });/*
        pass.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Sqlitehandler admin = new Sqlitehandler(MainActivity.this,"admin",null,1);
                SQLiteDatabase bd = admin.getReadableDatabase();
                String usu = usuario.getText().toString();
                String con = contra.getText().toString();
                Cursor fila = bd.rawQuery("select usuario from usuarios where usuario='"+usu.toString()+"' and contraseña ='"+con.toString()+"'",null);
                if(fila.moveToFirst()){
                    Toast.makeText(MainActivity.this, "Bienvenido "+fila.getString(0), Toast.LENGTH_SHORT).show();
                    Intent next = new Intent(MainActivity.this,inicio.class);
                    startActivity(next);
                }
                else{
                    Toast.makeText(MainActivity.this, "El usuario o la contraseña estan mal", Toast.LENGTH_SHORT).show();
                }


            }
        });*/
            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList("public_profile","user_status","email","user_birthday","user_location"));
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    GraphRequest gp =  GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject ob, GraphResponse response) {
                                    Log.v("Loginactivity", response.toString());
                                    try {
                                        String nombre = ob.getString("name");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        String status = ob.getString("location");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        String email = ob.getString("email");
                                        //Toast.makeText(MainActivity.this, "Hola"+email.toString(), Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        String fecha = ob.getString("birthday");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields","id,name, email, gender, birthday,link,location");
                    gp.setParameters(parameters);
                    gp.executeAsync();
                    irainicio();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(login.this, "Accion Cancelada", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException e) {
                    Toast.makeText(login.this,"Error",Toast.LENGTH_SHORT).show();
                }


            });


        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    private void irainicio(){
        Intent it = new Intent(login.this, RegUsuario.class);
        it.putExtra("user",usuario.getText().toString());
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
    }
}

