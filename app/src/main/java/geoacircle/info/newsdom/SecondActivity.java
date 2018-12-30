package geoacircle.info.newsdom;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    TextView link;
    TextView published;
   // ImageView img;
    WebView wv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        link = findViewById(R.id.link);
        wv = findViewById(R.id.webview);

        ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.INTERNET}, 1);

        wv.loadUrl(getIntent().getStringExtra("link"));
        wv.setWebViewClient(new WebViewClient());
    }
}
