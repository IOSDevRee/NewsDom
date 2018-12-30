package geoacircle.info.newsdom;


import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import android.widget.Toast;

import com.bumptech.glide.load.resource.file.FileResource;

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



import static geoacircle.info.newsdom.MainActivity.filter;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorldFragment extends Fragment/*implements SearchView.OnQueryTextListener */{

    SearchView searchView;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    private SearchView.OnQueryTextListener queryTextListener;
    RecyclerView rev;
    FrameLayout fr;
    MyAdapter my;
    String url = "https://timesofindia.indiatimes.com/rssfeeds/296589292.cms";
    ArrayList<GetterSetter> al = new ArrayList<>();
    String imgURL;
    Button btn;
    public WorldFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_world, container, false);
        rev = v.findViewById(R.id.rev);

        Toolbar toolbar = v.findViewById(R.id.tf);
        toolbar.setTitle("WORLD NEWS");

        btn = v.findViewById(R.id.nextfragment);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

       // searchView = (SearchView) v.findViewById(R.id.op_search);
      //  Toolbar toolbar = v.findViewById(R.id.tf);
      //  ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

    //    ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

    //    ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  toolbar.setTitle("WORLD NEWS");
      //  actionBar.setDisplayHomeAsUpEnabled(true);
      //  actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        fr = v.findViewById(R.id.mainContainer);


        Snackbar snackbar = Snackbar.make(fr,"Signed to World News ", Snackbar.LENGTH_INDEFINITE).setAction("View", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar1 = Snackbar.make(fr, "Use settings to manage", Snackbar.LENGTH_LONG);
                snackbar1.show();
            }
        });
        snackbar.show();
        snackbar.setActionTextColor(getResources().getColor(R.color.snackbaraction));

        //----
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(url);
            document.normalize();

            //---------

            NodeList nodeImg = document.getElementsByTagName("image");
            for(int i=0; i<nodeImg.getLength(); i++){

                Node node = nodeImg.item(i);
                Element element = (Element) node;
                String title = getElementbyname(element, "title");

                String img = getElementbyname(element, "url");

                Log.d("mydata", "Title: "+ title);

                Log.d("mydata", "Image: "+ img);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                rev.setLayoutManager(layoutManager);


                GetterSetter g1 = new GetterSetter(title, img);

                al.add(g1);

                MyAdapter my = new MyAdapter(getContext(), al);

                rev.setAdapter(my);
            }

            //----------

            NodeList nodeList = document.getElementsByTagName("item");

            for(int i=0; i<nodeList.getLength(); i++) {

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

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                rev.setLayoutManager(layoutManager);
                GetterSetter g1 = new GetterSetter(title, imgURL, detail, link, published);
                al.add(g1);
                my = new MyAdapter(getContext(), al);
                rev.setAdapter(my);

            /*    SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

                //searchView.setOnQueryTextListener(this);

                if (searchView != null) {
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

                  *//*  queryTextListener = new SearchView.OnQueryTextListener() {
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
                    };*//*
                    searchView.setOnQueryTextListener(this);
                }*/

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return v;
    }


/*    public boolean onQueryTextChange(String query) {
        String text = query;
        final ArrayList<GetterSetter> filteredModelList = filter(al, query);
        my = new MyAdapter(this, filteredModelList);
       // my = new MyAdapter(this, filteredModelList);
        rev.setAdapter(my);
        return true;
    }


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
    }*/


    private String getElementbyname(Element element, String title) {

        NodeList nodeList = element.getElementsByTagName(title);
        Node node = nodeList.item(0);
        Element child = (Element) node;
        String data = child.getTextContent();
        return data;

    }

    //-------Repeated code
/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.optionmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.op_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
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
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }*/
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

 }
