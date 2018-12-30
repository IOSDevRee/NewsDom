package geoacircle.info.newsdom;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.View;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import android.app.SearchManager;

import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    NavigationView nav;
    DrawerLayout drawerLayout;
    String imgURL;
    MyAdapter my;

    LinearLayout lr;
    RecyclerView rev;


    String url = "https://timesofindia.indiatimes.com/rssfeeds/296589292.cms";

    ArrayList<GetterSetter> al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rev = findViewById(R.id.rev);


        Toolbar toolbar = findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        //----
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("NEWS NOW");
        //-------
        lr = findViewById(R.id.mainContainer);


        Snackbar snackbar = Snackbar.make(lr,"Signed to Top Stories ", Snackbar.LENGTH_INDEFINITE).setAction("View", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar1 = Snackbar.make(lr, "Use settings to manage", Snackbar.LENGTH_LONG);
                snackbar1.show();
            }
        });
        snackbar.show();
        snackbar.setActionTextColor(getResources().getColor(R.color.snackbaraction));
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
                        selectedFrag = new RecentStoriesFragment();
                        Toast.makeText(MainActivity.this, "Fav", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.op_india:

                        item.setChecked(true);
                        selectedFrag = new IndiaFragment();
                        Toast.makeText(MainActivity.this, "VideoCall", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.op_city:
                        item.setChecked(true);
                        selectedFrag = new CityFragment();
                        Toast.makeText(MainActivity.this, "Sms", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.op_world:
                        item.setChecked(true);
                        selectedFrag = new WorldFragment();
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

            //--------------------
            NodeList nodeImg = document.getElementsByTagName("image");
            for(int i=0; i<nodeImg.getLength(); i++){

                Node node = nodeImg.item(i);
                Element element = (Element) node;
                String title = getElementbyname(element, "title");

                String img = getElementbyname(element, "url");

                Log.d("mydata", "Title: "+ title);

                Log.d("mydata", "Image: "+ img);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                rev.setLayoutManager(layoutManager);


                GetterSetter g1 = new GetterSetter(title, img);

                al.add(g1);

                MyAdapter my = new MyAdapter(this, al);

                rev.setAdapter(my);

            }

            //--------------------

            NodeList nodeList = document.getElementsByTagName("item");
            for(int i=0; i<nodeList.getLength(); i++){

                Node node = nodeList.item(i);
                Element element = (Element) node;

                String title = getElementbyname(element, "title");
                String link = getElementbyname(element, "link");
                String published = getElementbyname(element, "pubDate");
                String description = getElementbyname(element, "description");

                String detail = description.substring(description.indexOf("/a>")+3);


                Log.d("mydata", "Title: "+ title);
                Log.d("mydata", "Link: "+ link);
                Log.d("mydata", "published: "+ published);
                Log.d("mydata", "detail: "+ detail);


                StringTokenizer st = new StringTokenizer(description," ");
                String currentToken="";



                while (st.hasMoreTokens()) {
                    currentToken = st.nextToken();

                    if(currentToken.startsWith("src")){

                        imgURL = currentToken.substring(currentToken.indexOf("src")+ 5, currentToken.indexOf(".cms")+ 4);
                        Log.d("mydata", "Image: "+imgURL);
                    }

                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                rev.setLayoutManager(layoutManager);
                GetterSetter g1 = new GetterSetter(title, imgURL, detail, link, published);
                al.add(g1);
                my = new MyAdapter(this, al);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.optionmenu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.op_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onQueryTextChange(String query) {
        String text = query;
        final ArrayList<GetterSetter> filteredModelList = filter(al, query);
        my = new MyAdapter(this, filteredModelList);
        rev.setAdapter(my);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    static ArrayList<GetterSetter> filter(ArrayList<GetterSetter> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final ArrayList<GetterSetter> filteredModelList = new ArrayList<>();
        for (GetterSetter model : models) {

            final String title = model.getTitle().toLowerCase();
            final String description = String.valueOf(model.getDescription());
            if (title.contains(lowerCaseQuery) || description.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

        }
        if(item.getItemId() == R.id.op_search){

            Toast.makeText(this,"News Search", Toast.LENGTH_SHORT).show();
            return false;
        }


        if(item.getItemId() == R.id.op_setting){

            Intent myintent = new Intent(MainActivity.this, SettingNews.class);
            startActivity(myintent);
            Toast.makeText(this,"News Settings", Toast.LENGTH_SHORT).show();
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