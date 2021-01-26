package com.example.random;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;
import android.util.DisplayMetrics;
import android.graphics.Color;



public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    Button random_button;
    public View root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = findViewById(R.id.root);
        random_button = findViewById(R.id.btn_random);
        int number = randomNumber();
        random_button.setText(String.valueOf(number));
        queue = Volley.newRequestQueue(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);


        random_button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                    Random RANDOM = new Random();            // random color of background
                    int red = RANDOM.nextInt(255);
                    int green =RANDOM.nextInt(255);
                    int blue = RANDOM.nextInt(255);
                    int color = Color.rgb(red, green ,blue);
                    root.setBackgroundColor(color);

                    runOnUiThread(new Runnable() {             // random location of button
                        @Override
                        public void run() {
                            Random R = new Random();
                            float boundX=0.5f;
                            float boundY =0.8f;
                            float dx = R.nextFloat()*boundX*displaymetrics.widthPixels;
                            float dy = R.nextFloat() *boundY *displaymetrics.heightPixels;
                            random_button.animate()
                                    .x(dx)
                                    .y(dy)
                                    .setDuration(0)
                                    .start();
                        }
                    });

                int number =randomNumber();                   //random number
                JSONObject postNumber = new JSONObject();      //yazdığım API ye sayıyı yazdırıyorum burada . İsterseniz kendinize bir api
                String url = "http://192.168.5.58:5000/number";   //yazıp oradaki URL i buraya girmeniz gerekli ve emülatörünüzün settings/proxy bölümünden de url vermelisiniz.
                try {
                    postNumber.put("number", number);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, postNumber, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"Numara Gönderildi",Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Başarısız",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

                queue.add(request);

            }
        });
    }
    private int randomNumber(){
        Random r = new Random();
        int number = r.nextInt(100);
        random_button.setText(String.valueOf(number));
        return number;
    }
}