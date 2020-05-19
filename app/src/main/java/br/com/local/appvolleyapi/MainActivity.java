package br.com.local.appvolleyapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    //Criando as variaveis globais
    TextView txtRespostaWEB;
    Button btnAcessaServer;

    String serverURL = "http://192.168.100.5/Projetovolleyapi/ola_servidor.php";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRespostaWEB = findViewById(R.id.txtRespostaWEB);
        btnAcessaServer = findViewById(R.id.btnAcessaServer);
        //Habilitando o cache para melhorar a performance
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

        //Habilitando a rede
        Network network = new BasicNetwork(new HurlStack());

        //Criando o cache e a rede
        requestQueue = new RequestQueue(cache, network);
        //iniciar o request coom o cache e a rede.
        requestQueue.start();

        btnAcessaServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        serverURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                txtRespostaWEB.setText(response);
                                requestQueue.stop();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtRespostaWEB.setText("Erro ao conectar o servidor...");
                        error.printStackTrace();
                    }
                });
                //Adicionando o requeste a string do objeto
                requestQueue.add(stringRequest);
            }
        });
    }

}
