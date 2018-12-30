package geoacircle.info.newsdom;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bumptech.glide.load.resource.file.FileResource;

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


/**
 * A simple {@link Fragment} subclass.
 */

public class RecentStoriesFragment extends Fragment {

    RecyclerView rev;
    FrameLayout fr;
    Button btn;
    String url = "https://timesofindia.indiatimes.com/rssfeeds/1221656.cms";
    String imgURL;
    ArrayList<GetterSetter> al = new ArrayList<>();

    public RecentStoriesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_recent_stories, container, false);
        rev = v.findViewById(R.id.rev);
        Toolbar toolbar = v.findViewById(R.id.tf);
        toolbar.setTitle("RECENT STORIES");

        btn = v.findViewById(R.id.nextfragment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        //-------
        fr = v.findViewById(R.id.mainContainer);


        Snackbar snackbar = Snackbar.make(fr,"Signed to Recent Stories ", Snackbar.LENGTH_INDEFINITE).setAction("View", new View.OnClickListener() {
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
                String description = getElementbyname(element, "description");
                String link = getElementbyname(element, "link");
                String published = getElementbyname(element, "pubDate");
                //  String img = getElementbyname(element, "img");

                Log.d("mydata", "Title: "+ title);
                Log.d("mydata", "Description: "+ description);
                Log.d("mydata", "Link: "+ link);
                Log.d("mydata", "published: "+ published);
                //  Log.d("mydata", "image: "+ img);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                rev.setLayoutManager(layoutManager);

                GetterSetter g1 = new GetterSetter(title, description, link, published);
                al.add(g1);

                MyAdapter my = new MyAdapter(getContext(), al);

                rev.setAdapter(my);

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

    private String getElementbyname(Element element, String title) {

        NodeList nodeList = element.getElementsByTagName(title);
        Node node = nodeList.item(0);
        Element child = (Element) node;
        String data = child.getTextContent();
        return data;

    }

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
