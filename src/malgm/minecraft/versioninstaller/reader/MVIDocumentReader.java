package malgm.minecraft.versioninstaller.reader;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class MVIDocumentReader {
	
	private String name = null;
	private String version = null;
	private String jar = null;
	private String json = null;
	private String fileName = null;
	
	public void readDoc(String url) {
		try {
			  DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			  DocumentBuilder b = f.newDocumentBuilder();
			  Document doc = b.parse(url);
			  
			  Element ele = doc.getDocumentElement();
			  
			  name = getTextValue(name, ele, "name");
			  version = getTextValue(version, ele, "version");
			  jar = getTextValue(jar, ele, "jar");
			  json = getTextValue(json, ele, "json");
			  fileName = getTextValue(fileName, ele, "fileName");
			} catch (Exception e) {}
	}
	
	public String getName() {
		return name;
	}
	public String getVersion() {
		return version;
	}
	public String getJar() {
		return jar;
	}
	public String getJson() {
		return json;
	}
	public String getFileName() {
		return fileName;
	}
	
	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}

}
