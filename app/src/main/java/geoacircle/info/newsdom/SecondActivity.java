package geoacircle.info.newsdom;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    TextView link;
    TextView published;
    WebView wv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        title = findViewById(R.id.title);
        description = findViewById(R.id.desc);
        link = findViewById(R.id.link);
        published = findViewById(R.id.pubDate);
        btn = findViewById(R.id.btnView);
        wv = findViewById(R.id.webview);

        ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.INTERNET}, 1);

        title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));
        link.setText(getIntent().getStringExtra("link"));
        published.setText(getIntent().getStringExtra("pubDate"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl(getIntent().getStringExtra("link"));
                wv.setWebViewClient(new WebViewClient());
            }
        });

    }
}
