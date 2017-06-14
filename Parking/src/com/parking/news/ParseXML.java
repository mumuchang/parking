package com.parking.news;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/6/3.
 */
public class ParseXML {
    private static String link;

    public static Document readXMLFile(InputStream inFile) throws JDOMException, IOException {
        Document doc = null;
        //使用JDOM指定，使用解析器
        SAXBuilder sb = new SAXBuilder();

//        File fXml = new File(inFile);

//        if (fXml.exists()) {
            //加载一个XML文件，得到document
            doc = sb.build(inFile);

//        }
        return doc;
    }


    public static List parse(Document doc) {
        //定义newsFeeds返回列表类型
        List newsFeeds = new ArrayList();
        //创建newsFeeds实例
        NewsFeed newsFeed = null;
        //得到XML文档的根节点"rss"
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
