package org.plasma.common.xslt;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class XSLTUtils
{       
    public XSLTUtils()
    {
    } 

    public void transform(File dest, File src, URL styleSheet)
        throws IOException, TransformerConfigurationException, TransformerException
    {
        transform(dest, src, styleSheet, null);
    }

    public void transform(File dest, File src, URL styleSheet, Properties params)
        throws IOException, TransformerConfigurationException, TransformerException
    {
        String srcXML = getContent(src);
        String destXML = transform(srcXML, styleSheet, params);
        writeContent(dest, destXML);
    }

    public String transform(String xml, URL styleSheet)
        throws IOException, TransformerConfigurationException, TransformerException
    {
        return transform(xml, styleSheet, null);
    }
    
    public String transform(String xml, URL styleSheet, Properties params)
        throws TransformerConfigurationException, TransformerException
    {
        if (xml == null || "".equals(xml.trim()))
            return "";
        int indent_amount = 0;
        String encoding = "utf-8";
        StringWriter sw = new StringWriter();

        //TransformerFactory factory = com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl.newInstance();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer =             
                factory.newTransformer(new StreamSource(styleSheet.toExternalForm()));
                    
        if (indent_amount > 0)
        {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http\u003a//xml.apache.org/xslt}indent-amount", 
                                        Integer.toString(indent_amount));
        }
        if (params != null)
        {
            Iterator iter = params.keySet().iterator();           
            while (iter.hasNext())                                
            {                                                     
                String key = (String)iter.next();                 
                transformer.setParameter(key, params.get(key));   
            }                                                     
        }
                    
        if (encoding != null && !"".equals(encoding.trim()))
            transformer.setOutputProperty(OutputKeys.ENCODING, encoding);                        
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(sw));

        return sw.toString();
    }   

    private void writeContent(File dest, String content)
        throws IOException
    {
        FileOutputStream fos = new FileOutputStream(dest);
        BufferedOutputStream os = new BufferedOutputStream(fos);
        os.write(content.getBytes());
        os.flush();
        fos.close();
    }

    private String getContent(File source)
        throws IOException
    {
        long size = source.length();
        byte[] buf = new byte[4000];
        ByteArrayOutputStream os = new ByteArrayOutputStream((int)size); // bad!
        FileInputStream is = new FileInputStream(source);              
        int len = -1;                                                
        while ((len = is.read(buf)) != -1)                           
            os.write(buf, 0, len);                                                           
        is.close();
        os.flush();                                                           
        return new String(os.toByteArray());            
    }

    public static void main(String[] args)
    {
        try {
            XSLTUtils ut = new XSLTUtils();
            if (args.length == 3)
            {
                ut.transform(new File(args[0]), new File(args[1]), (new File(args[2])).toURL()); 
            }
            else if (args.length == 4)
            {
                Properties params = new Properties();
                StringTokenizer st = new StringTokenizer(args[3], " =");
                while (st.hasMoreTokens())
                    params.put(st.nextToken(), st.nextToken());
                ut.transform(new File(args[0]), new File(args[1]), (new File(args[2])).toURL(), params);
            }
            else
                throw new IllegalArgumentException("expected either 3 or 4 args"); 
           
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}