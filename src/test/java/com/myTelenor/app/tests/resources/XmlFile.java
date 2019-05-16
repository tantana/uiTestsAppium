package com.myTelenor.app.tests.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlFile extends FilesAndDirs{
	public static String screenShotName = "ScreenShot";
	
    protected void writeToFileXml(String xml, String filename, int indent) {
		 try {
		        // Turn xml string into a document
		        Document document = DocumentBuilderFactory.newInstance()
		                .newDocumentBuilder()
		                .parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

		        // Remove whitespaces outside tags
		        document.normalize();
		        XPath xPath = XPathFactory.newInstance().newXPath();
		        NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
		                                                      document,
		                                                      XPathConstants.NODESET);

		        for (int i = 0; i < nodeList.getLength(); ++i) {
		            Node node = nodeList.item(i);
		            node.getParentNode().removeChild(node);
		        }

		        // Setup pretty print options
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        transformerFactory.setAttribute("indent-number", indent);
		        Transformer transformer = transformerFactory.newTransformer();
		        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		        // Return pretty print xml string
		        StringWriter stringWriter = new StringWriter();
		        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
		        
		        try (PrintWriter out = new PrintWriter(filename)) {
		            out.println(stringWriter.toString());
		        }
		    } catch (Exception e) {
		        throw new RuntimeException(e);
		    }		
    }
    

    protected void processScreenShot(String p, String sn, String tempDir, boolean iOS) throws IOException {
    	//Write ScreenShot to file		
    			writeToFileXml(p, tempDir+sn+".xml", 2);
    	//Read ScreenShot into string		
    			byte[] encoded = Files.readAllBytes(Paths.get(tempDir+sn+".xml"));
    			String str = new String(encoded, StandardCharsets.UTF_8);

    			if (iOS) {
					//Remove bounds
					str=str.replaceAll(" y=\"[0-9]*\"", "");
					str=str.replaceAll(" x=\"[0-9]*\"", "");
					str=str.replaceAll(" width=\"[0-9]*\"", "");
					str=str.replaceAll(" height=\"[0-9]*\"", "");
					str=str.replaceAll(" visible=\".*\"", "");
					str=str.replaceAll("New data will arrive.*\"", "New data will arrive...\"");

					str = Pattern.compile("\\<XCUIElementTypeStatusBar .*XCUIElementTypeStatusBar\\>", Pattern.DOTALL).matcher(str).replaceAll("");
				}
    			else {
					//Remove bounds
					str=str.replaceAll("bounds=\\\"\\[.*\\]\\\" ", "");
					str=str.replaceAll("instance=\\\".*\\\" l", "l");
					str=str.replaceAll(" package=\\\"se.telenor.mytelenor\\\"", "");
					//str=str.replaceAll("android.widget.", "");
					str=str.replaceAll(" scrollable=\"false\"", "");
					str=str.replaceAll(" scrollable=\"true\"", "");
					
					str=str.replaceAll(" focusable=\"false\"", "");
					str=str.replaceAll(" focusable=\"true\"", "");
					
					// For BrowserStack
					str=str.replaceAll("android.view.ViewGroup",		"android.view...");					
					str=str.replaceAll("android.view.View",				"android.view...");					
					str=str.replaceAll("android.widget.FrameLayout",	"android.widget...");	
					str=str.replaceAll("android.widget.ScrollView",		"android.widget...");
					
					str=str.replaceAll("New data will arrive.*\"", "New data will arrive...\"");
				}
    	//Save ScreenShot to file			
    			removeFile(tempDir+sn+".xml");
    			writeToFileXml(str, tempDir+sn+".xml", 2);
    }

	private String cleanLineBreaks(String xml) {
		int pos1;
		int pos2;
		while (xml.contains("/>")) {
			pos1 = xml.indexOf("/>");
			pos2 = xml.lastIndexOf("<", pos1)+1;
			xml=xml.substring(0,pos1)+">"+
					System.getProperty("line.separator")+
					xml.substring(xml.lastIndexOf("\n",  pos2)+1, pos2-1)+
					"</"+xml.substring(pos2, xml.indexOf(" ", pos2))+
					">"+xml.substring(pos1+2, xml.length());
		}
		return xml;
	}
    
    protected void mergeTwoScreenShots(String f1, String f2, String fn, String tempDir) throws IOException {
    	//Read ScreenShot into string		
    	byte[] encoded = Files.readAllBytes(Paths.get(tempDir+f1+".xml"));
    	String str1 = new String(encoded, StandardCharsets.UTF_8);

    	encoded = Files.readAllBytes(Paths.get(tempDir+f2+".xml"));
    	String str2 = new String(encoded, StandardCharsets.UTF_8);
		
    	// Double check if somehow two screens are identical
    	if (str1.equals(str2)) {
    		renameFile(tempDir+f1+".xml", tempDir+fn+".xml");
        	removeFile(tempDir+f2+".xml");
        	
        	return;
    	}
    	
    	str1 = cleanLineBreaks(str1);
    	str2 = cleanLineBreaks(str2);
    	
    	String[] lines1 = str1.split(System.getProperty("line.separator"));
    	String[] lines2 = str2.split(System.getProperty("line.separator"));
 	
    	// find top header
    	int i=0;
    	while (i<lines1.length && lines1[i].equals(lines2[i++])) {}
    	int posT=--i;
    	   	
    	// find bottom header
    	i=lines1.length-1;
    	int j=lines2.length-1;
    	while (i>posT && j>posT && lines1[i--].equals(lines2[j--])) {}
    	    	
    	String[] arrT =  Arrays.copyOfRange(lines1, 0, posT);
    	String[] arrB =  Arrays.copyOfRange(lines1, (++i)+1, lines1.length);
    	
    	String strT=String.join("\n", arrT);
    	String strB=String.join("\n", arrB);
    	
    	//cutted arrays / strings
    	String[] linesC1 = Arrays.copyOfRange(lines1, posT, ++i);
    	String[] linesC2 = Arrays.copyOfRange(lines2, posT, (++j)+1);    	
    	
    	String strC1=String.join("\n", linesC1);
    	
		i=0;j=0;
		StringBuilder res = new StringBuilder();
		int pos=0;

		while (i<linesC1.length && j<linesC2.length) {
			String lC1=linesC1[i].replaceAll("index=\\\".*\\\" l", "index=\\\"\\\" l");
			String lC2=linesC2[j].replaceAll("index=\\\".*\\\" l", "index=\\\"\\\" l");
			
			if (lC1.equals(lC2)) {
				res.append(linesC2[j]); res.append("\n");
				pos += linesC1[i].length()+1;
				if (pos<strC1.length())
					strC1 = strC1.substring(pos, strC1.length());
				pos = 0;		
				i++;
				j++;
			}
			else {
				while (i<linesC1.length && j<linesC2.length && !lC1.equals(lC2)) {
					if (strC1.contains(linesC2[j])) {
						res.append(linesC1[i]); res.append("\n");
						pos += linesC1[i++].length()+1;							
					}
					else {
						while (i<linesC1.length && j<linesC2.length && !lC1.equals(lC2)) {
							res.append(linesC2[j++]); res.append("\n");
							if (linesC2[j-1].startsWith( (linesC2[j].replaceAll("/", "") ).replaceAll(">", "")) ) {
								res.append(linesC2[j++]); res.append("\n");
							}
							lC2=linesC2[j].replaceAll("index=\\\".*\\\" l", "index=\\\"\\\" l");
						}
					}
					lC1=linesC1[i].replaceAll("index=\\\".*\\\" l", "index=\\\"\\\" l");
					lC2=linesC2[j].replaceAll("index=\\\".*\\\" l", "index=\\\"\\\" l");
				}
			}
		}
	
		if (i<linesC1.length) {
			res.append(String.join("\n", Arrays.copyOfRange(linesC1, i, linesC1.length)));
		}
    			
		if (j<linesC2.length) {
			res.append(String.join("\n", Arrays.copyOfRange(linesC2, j, linesC2.length)));
		}
		
    	writeToFileXml(strT+"\n"+res+"\n"+strB, tempDir+fn+".xml", 2);

    	removeFile(tempDir+f1+".xml");
    	removeFile(tempDir+f2+".xml"); 
    }
    
    protected String getXmlFile(String dir, String f) throws IOException {
       	byte[] encoded = Files.readAllBytes(Paths.get(dir+screenShotName+f+".xml"));
       	String str = new String(encoded, StandardCharsets.UTF_8);
       	str = str.replaceAll("index=\\\".*\\\" l", "index=\\\"\\\" l");
       	return str;       	
    }
}
