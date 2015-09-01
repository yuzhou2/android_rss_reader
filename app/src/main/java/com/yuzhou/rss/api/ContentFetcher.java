package com.yuzhou.rss.api;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhou on 2015/08/31.
 */
public class ContentFetcher extends AsyncTask<String, Void, List<String>>
{
    private final EventBus eventBus;

    public ContentFetcher(EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    @Override
    protected List<String> doInBackground(String... urls)
    {
        List result = new ArrayList();
        for (int i = 0, n = urls.length; i < n; i++) {
            try {
                String rss = getContent(urls[i]);
                result.add(rss);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<String> result)
    {
        eventBus.post(result);
    }

    protected String getContent(String address) throws IOException
    {
        URL url = parseUrlString(address);
        URLConnection connection = getUrlConnection(url);
        InputStream in = connection.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inr);

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }

    private URLConnection getUrlConnection(URL url) throws IOException
    {
        return url.openConnection();
    }

    private URL parseUrlString(String address) throws MalformedURLException
    {
        return new URL(address);
    }

}
