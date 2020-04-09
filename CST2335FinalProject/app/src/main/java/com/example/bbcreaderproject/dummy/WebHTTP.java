package com.example.bbcreaderproject.dummy;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebHTTP extends AsyncTask<String, String, String> {
    private String title;
    private String description;
    private String date;
    private String weblink;

    @Override
    protected String doInBackground(String ... args){

        URL url = null;
        try {
            url = new URL("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream response = urlConnection.getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( response  , "UTF-8");
            int eventType = xpp.getEventType();

        while(eventType != XmlPullParser.END_DOCUMENT){
            while (eventType == XmlPullParser.START_TAG) {
                if (xpp.getName().equals(title)) {
                        title = xpp.getAttributeValue(null, "title");   //returns “Hello”}
                        break;
                    }
                 if (xpp.getName().equals("description")) {
                        description = xpp.getAttributeValue(null, "description");   //returns “Hello”}
                        break;
                    }
                 if (xpp.getName().equals("pubDate")) {
                        date = xpp.getAttributeValue(null, "pubDate");   //returns “Hello”}
                        break;
                    }
                 if (xpp.getName().equals("link")) {
                        weblink = xpp.getAttributeValue(null, "link");   //returns “Hello”}
                        break;
                    }
                 eventType = xpp.next();
            }
        }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return "Title: "+title+"\nDescription: "+description+"\nDate: "+"\nWeblink: "+weblink;
    }
}