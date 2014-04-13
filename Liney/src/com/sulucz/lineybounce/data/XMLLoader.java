package com.sulucz.lineybounce.data;

import com.badlogic.gdx.files.FileHandle;
import com.sulucz.lineybounce.Globals;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import com.badlogic.gdx.Gdx;

public class XMLLoader
    implements Runnable
{

    private int    loadIndex;
    private Thread thread;


    public void loadLevels()
    {

    }


    public void loadXMLURL(int index)
    {
        URL url;
        try
        {
            url =
                new URL("http://www.badflyer.com/Liney/LineyLevelGen.ashx?x="
                    + index);
        }
        catch (MalformedURLException e1)
        {
            loadXML("data/leveldefs.xml");
            return;
        }

        try
        {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            InputStream istream = url.openStream();
            Document document = documentBuilder.parse(istream);
            NodeList levelsNL = document.getElementsByTagName("l");
            for (int i = 0; i < levelsNL.getLength(); i++)
            {
                Element levelNode = (Element)levelsNL.item(i);
                NodeList bubbs = levelNode.getElementsByTagName("p");
                for (int k = 0; k < bubbs.getLength(); k++)
                {
                    Element bubbleNode = (Element)bubbs.item(k);
                    String x = bubbleNode.getAttribute("x");
                    String y = bubbleNode.getAttribute("y");
                    String color = bubbleNode.getAttribute("c");
                    Globals.xmlLineController.addPoint(x, y, color);
                }
            }
            istream.close();

        }
        catch (Exception e)
        {
            e = e;
            // loadXML("data/leveldefs.xml");
        }
    }


    private void downloadFile(int index)
    {
        URL url;
        try
        {
            url =
                new URL("http://www.badflyer.com/Liney/LineyLevelGen.ashx?x="
                    + index);

            InputStream istream = url.openStream();
            FileHandle output =
                Gdx.files.local("leveldata" + index / 1000 + ".xml");
            output.write(istream, false);
            istream.close();

        }
        catch (Exception e1)
        {
            // loadXML("data/leveldefs.xml");
            return;
        }

    }


    private void loadXML(String source)
    {
        try
        {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            InputStream istream = Gdx.files.local(source).read();
            Document document = documentBuilder.parse(istream);
            NodeList levelsNL = document.getElementsByTagName("l");
            for (int i = 0; i < levelsNL.getLength(); i++)
            {
                Element levelNode = (Element)levelsNL.item(i);
                NodeList bubbs = levelNode.getElementsByTagName("p");
                for (int k = 0; k < bubbs.getLength(); k++)
                {
                    Element bubbleNode = (Element)bubbs.item(k);
                    String x = bubbleNode.getAttribute("x");
                    String y = bubbleNode.getAttribute("y");
                    String color = bubbleNode.getAttribute("c");
                    Globals.xmlLineController.addPoint(x, y, color);
                }
            }
            istream.close();

        }
        catch (Exception e)
        {
            e = e;
        }
    }


    @Override
    public void run()
    {
        if (!Gdx.files.local("leveldata" + this.loadIndex / 1000 + ".xml")
            .exists())
        {
            this.downloadFile(this.loadIndex);
        }
        this.loadXML("leveldata" + this.loadIndex / 1000 + ".xml");
        // this.loadXMLURL(this.loadIndex);
    }


    public void load(int index)
    {
        this.loadIndex = index;
        this.thread = new Thread(this, "LOAD XML");
        this.thread.start();
    }


    public void loadBlock(int index)
    {
        this.loadIndex = index;
        if (!Gdx.files.local("leveldata" + this.loadIndex / 1000 + ".xml")
            .exists())
        {
            this.downloadFile(this.loadIndex);
        }
        this.loadXML("leveldata" + this.loadIndex / 1000 + ".xml");
    }


    public void syncLocalWithServer()
    {
        String file = "leveldata" + 0 + ".xml";
        URL url;
        try
        {
            url =
                new URL("http://www.badflyer.com/Liney/LineyLevelGen.ashx?x="
                    + 0 + "&date=yes");
        }
        catch (MalformedURLException e1)
        {
            return;
        }
        try
        {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            InputStream istream = url.openStream();
            Document document = documentBuilder.parse(istream);
            NodeList levelsNL = document.getElementsByTagName("l");
        }
        catch (Exception e)
        {
            return;
        }
    }
}
