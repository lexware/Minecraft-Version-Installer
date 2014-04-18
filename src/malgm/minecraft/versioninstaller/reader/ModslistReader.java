package malgm.minecraft.versioninstaller.reader;

import javax.xml.parsers.*;

import org.w3c.dom.*;

public class ModslistReader {
	
	private String[] infoURLs = null;
	
	public ModslistReader(String url) {
		try {
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(url);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("mod");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				int i = temp;
				System.out.println(i);
				Node nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					
					String infoURL = eElement.getElementsByTagName("url").item(0).getTextContent();
					System.out.println(infoURL);
					
					infoURLs[i] = infoURL.toString();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getInfoURLs() {
		return infoURLs;
	}

}
