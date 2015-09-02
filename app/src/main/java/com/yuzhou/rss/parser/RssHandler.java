package com.yuzhou.rss.parser;

import java.lang.reflect.Method;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class RssHandler extends DefaultHandler
{
    private RssFeed rssFeed;
    private RssItem rssItem;
    private StringBuilder sb;

    public RssFeed getResult()
    {
        return rssFeed;
    }

    @Override
    public void startDocument()
    {
        rssFeed = new RssFeed();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs)
    {
        sb = new StringBuilder();
        if ("item".equals(qName) && rssFeed != null) {
            rssItem = new RssItem();
            rssFeed.addRssItem(rssItem);
        }
        if ("media:content".equals(qName) && rssItem != null) {
            String image = attrs.getValue("url");
            if (image != null && !image.isEmpty()) {
                rssItem.addImage(image);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length)
    {
        sb.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    {
        if (rssFeed != null && rssItem == null) {
            // Parse feed properties
            try {
                if (qName != null && !qName.isEmpty()) {
                    String methodName = "set" + qName.substring(0, 1).toUpperCase() + qName.substring(1);
                    Method method = rssFeed.getClass().getMethod(methodName, String.class);
                    method.invoke(rssFeed, sb.toString());
                }
            } catch (Exception e) {
            }
        } else if (rssItem != null) {
            // Parse item properties
            try {
                if ("content:encoded".equals(qName)) {
                    qName = "content";
                }
                String methodName = "set" + qName.substring(0, 1).toUpperCase() + qName.substring(1);
                Method method = rssItem.getClass().getMethod(methodName, String.class);
                method.invoke(rssItem, sb.toString());
            } catch (Exception e) {
            }
        }
    }

}
