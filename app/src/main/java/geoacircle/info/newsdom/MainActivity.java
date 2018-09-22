package geoacircle.info.newsdom;

import android.content.Intent;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static android.os.StrictMode.*;

public class MainActivity extends AppCompatActivity {


    RecyclerView rev;
    Toolbar toolbar;
    NavigationView nav;
    DrawerLayout drawerLayout;


    String url = "https://timesofindia.indiatimes.com/rssfeedstopstories.cms";

    ArrayList<GetterSetter> al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rev = findViewById(R.id.rev);


        toolbar = findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        //----
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("NEWS NOW");

        //----

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);



        drawerLayout = findViewById(R.id.drawer_layout);
        nav = findViewById(R.id.nav);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFrag = null;
                switch (item.getItemId()){

                    case R.id.op_topstories:

                        item.setChecked(true);
                        selectedFrag = new PhotoFragment();
                        Toast.makeText(MainActivity.this, "Photo", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.op_recentstories:
                        item.setChecked(true);
                        selectedFrag = new PhotoFragment();
                        Toast.makeText(MainActivity.this, "Fav", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.op_india:

                        item.setChecked(true);
                        selectedFrag = new PhotoFragment();
                        Toast.makeText(MainActivity.this, "VideoCall", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.op_city:
                        item.setChecked(true);
                        selectedFrag = new PhotoFragment();
                        Toast.makeText(MainActivity.this, "Sms", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.op_world:
                        item.setChecked(true);
                        selectedFrag = new PhotoFragment();
                        Toast.makeText(MainActivity.this, "Camera", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();
                return false;
            }
        });



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(url);
            document.normalize();

            NodeList nodeList = document.getElementsByTagName("item");
            for(int i=0; i<nodeList.getLength(); i++){

                Node node = nodeList.item(i);
                Element element = (Element) node;

                String title = getElementbyname(element, "title");
                String description = getElementbyname(element, "description");
                String link = getElementbyname(element, "link");
                String published = getElementbyname(element, "pubDate");


                Log.d("mydata", "Title: "+ title);
                Log.d("mydata", "Description: "+ description);
                Log.d("mydata", "Link: "+ link);
                Log.d("mydata", "published: "+ published);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                rev.setLayoutManager(layoutManager);


                GetterSetter g1 = new GetterSetter(title, description, link, published);
                al.add(g1);

                MyAdapter my = new MyAdapter(this, al);

                rev.setAdapter(my);

            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.optionmenu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

        }

        if(item.getItemId() == R.id.op_search){

            Intent myintent = new Intent(MainActivity.this, SearchNews.class);
            startActivity(myintent);
            Toast.makeText(this,"Search", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(item.getItemId() == R.id.op_profile){

            Intent myintent = new Intent(MainActivity.this, ProfileLogin.class);
            startActivity(myintent);
            Toast.makeText(this,"Login TOI", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(item.getItemId() == R.id.op_setting){

            Intent myintent = new Intent(MainActivity.this, SettingNews.class);
            startActivity(myintent);
            Toast.makeText(this,"News Settings", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(item.getItemId() == R.id.op_notification){

            Intent myintent = new Intent(MainActivity.this, NotifyNews.class);
            startActivity(myintent);
            Toast.makeText(this,"Notify News", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(item.getItemId() == R.id.op_manage){

            Intent myintent = new Intent(MainActivity.this, ManageNews.class);
            startActivity(myintent);
            Toast.makeText(this,"Manage NewsPaper", Toast.LENGTH_SHORT).show();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getElementbyname(Element element, String title) {

        NodeList nodeList = element.getElementsByTagName(title);
        Node node = nodeList.item(0);
        Element child = (Element) node;
        String data = child.getTextContent();
        return data;

    }
}