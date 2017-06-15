package com.parking.news;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Created by lenovo on 2017/6/3.
 */
public class ParseXML {
    private static String link;

    public static Document readXMLFile(InputStream inFile) throws JDOMException, IOException {
        Document doc = null;
        //ʹ��JDOMָ����ʹ�ý�����
        SAXBuilder sb = new SAXBuilder();

//        File fXml = new File(inFile);

//        if (fXml.exists()) {
            //����һ��XML�ļ����õ�document
            doc = sb.build(inFile);

//        }
        return doc;
    }


    public static List parse(Document doc) {
        //����newsFeeds�����б�����
        List newsFeeds = new ArrayList();
        //����newsFeedsʵ��
        NewsFeed newsFeed = null;
        //�õ�XML�ĵ��ĸ��ڵ�"rss"
        Element root = doc.getRootElement();

        Element eChannel = root.getChild("channel");
        List<Element> itemList = eChannel.getChildren("item");

        for (int i = 0; i < itemList.size(); i++) {
            Element item = itemList.get(i);
            newsFeed = item2feed(item);
            newsFeeds.add(newsFeed);

        }

        return newsFeeds;

    }

    public static NewsFeed item2feed(Element item) {
        NewsFeed newsFeed = new NewsFeed();

        String title = item.getChildText("title").trim();
        link = item.getChildText("link");
        String author = item.getChildText("author");
        String guid = item.getChildText("guid");
        String pubDate = item.getChildText("pubDate");
        String category = item.getChildText("category");
        String description = item.getChildText("description");

        newsFeed.setTitle(title);
        newsFeed.setLink(link);
        newsFeed.setAuthor(author);
        newsFeed.setGuid(guid);
        newsFeed.setPubDate(pubDate);
        newsFeed.setCategory(category);
        newsFeed.setDescription(description);

        return newsFeed;

    }


    public static String news2str(NewsFeed newsFeed) {
        String content = null;
        String title = newsFeed.getTitle().trim();
        String author = newsFeed.getAuthor();
        String pubDate = newsFeed.getPubDate();
        link = newsFeed.getLink();
        String description = newsFeed.getDescription().trim();

        content = "Title:"
                + title
                + "\r\n"
                + "Link:"
                + link
                + "\r\n"
                + "Author:"
                + author
                + "\r\n"
                + "Public Date:"
                + pubDate
                + "\r\n"
                + "-------------------------------------------------------------"
                + "\r\n"
                + description + "\r\n" + "\r\n";

        return content;
    }

    public static String linkWeb() {
        return link;

    }

}
