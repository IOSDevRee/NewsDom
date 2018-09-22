package geoacircle.info.newsdom;


import android.os.Bundle;

import android.os.StrictMode;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class PhotoFragment extends Fragment {

    RecyclerView rev;
    String url = "https://timesofindia.indiatimes.com/rssfeedsvideo/3812907.cms";

    ArrayList<GetterSetter> al = new ArrayList<>();

    public PhotoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_photo, container, false);
        rev = v.findViewById(R.id.rev);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(url);
            document.normalize();

            NodeList nodeList = document.getElementsByTagName("item");

            for(int i=0; i<nodeList.getLength(); i++) {

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

}
