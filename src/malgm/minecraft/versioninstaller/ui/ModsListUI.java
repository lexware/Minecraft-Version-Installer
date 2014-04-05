package malgm.minecraft.versioninstaller.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ModsListUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JSONParser parser = new JSONParser();
	
	private String[] modslist = null, urllist = null;

	public String[] getModsList(String url) throws ParseException {
		JSONArray results = null;
		JSONObject json = (JSONObject) parser.parse(url);
		
		results = (JSONArray) json.get("mods");

	    for (int i = 0; i < ((CharSequence) results).length(); i++) {
	    	JSONObject childJSONObject = (JSONObject) results.get(i);
	        modslist[i] += childJSONObject.get("name");
	    }
		
		return modslist;
	}
	
	public String[] getURLList(String url) throws ParseException {
		JSONArray results = null;
		JSONObject json = (JSONObject) parser.parse(url);
		
		results = (JSONArray) json.get("mods");

	    for (int i = 0; i < ((CharSequence) results).length(); i++) {
	    	JSONObject childJSONObject = (JSONObject) results.get(i);
	        urllist[i] += childJSONObject.get("url");
	    }
		
		return urllist;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
	}
}