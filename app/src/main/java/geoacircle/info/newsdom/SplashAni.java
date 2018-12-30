package geoacircle.info.newsdom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SplashAni extends AppCompatActivity {


    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        requestQueue = Volley.newRequestQueue(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loadMainActivity = new Intent(SplashAni.this, MainActivity.class);
                startActivity(loadMainActivity);
                finish();
            }
        }, 3000);

    }

}
