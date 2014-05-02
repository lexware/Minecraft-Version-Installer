package malgm.minecraft.launcher.ui.tabs.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import malgm.minecraft.launcher.Data;
import malgm.minecraft.launcher.Logger;
import malgm.minecraft.launcher.ResourceFinder;
import malgm.minecraft.launcher.ResourceLoader;
import malgm.minecraft.launcher.ui.controls.TiledBackground;

public class ConsoleInfoPanel extends TiledBackground {
	
	private static final long serialVersionUID = 1L;
	
	private static ResourceFinder resFinder = new ResourceFinder();
	
	public static PipedInputStream outPipe;
	public static PrintWriter inWriter;
	
	public ConsoleInfoPanel(ResourceLoader loader) {
		super(loader.getImage(resFinder.background()));
		
		setLayout(new BorderLayout());
		
		try {
	    	// 1. create the pipes
		    PipedInputStream inPipe = new PipedInputStream();
		    outPipe = new PipedInputStream();

		    // 2. set the System.in and System.out streams
		    System.setIn(inPipe);
	    	
			System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));
			inWriter = new PrintWriter(new PipedOutputStream(inPipe), true);
	    } catch (IOException e) {
			e.printStackTrace();
		}
		
		// this is the on-screen console
		JTextArea a = console(outPipe, inWriter);
		a.setEditable(false);
		a.setBackground(Color.BLACK);
		a.setForeground(Color.WHITE);
		a.setMargin(new Insets(10, 10, 10, 10));
		
		JScrollPane p = new JScrollPane(a);
		
		add(p, BorderLayout.CENTER);
		
		// Prints launcher info to the console
		Data data = new Data();
		Logger.log(data.getMMLName() + " build " + data.getMMLBuild() + " Loading");
		System.out.println();

		// Prints system variables to the console
		Logger.log("System.getProperty('os.name') == " + System.getProperty("os.name"));
		Logger.log("System.getProperty('os.version') == " + System.getProperty("os.version"));
		Logger.log("System.getProperty('os.arch') == " + System.getProperty("os.arch"));
		System.out.println();
		Logger.log("System.getProperty('java.vendor') == " + System.getProperty("java.vendor"));
		Logger.log("System.getProperty('java.version') == " + System.getProperty("java.version"));
		Logger.log("System.getProperty('java.vendor.url') == " + System.getProperty("java.vendor.url"));
	}
	
	public static JTextArea console(final InputStream out, final PrintWriter in) {
	    final JTextArea area = new JTextArea();

	    // handle "System.out"
	    new SwingWorker<Void, String>() {
	        @SuppressWarnings("resource")
			@Override protected Void doInBackground() throws Exception {
	            Scanner s = new Scanner(out);
	            while (s.hasNextLine()) publish(s.nextLine() + "\n");
	            return null;
	        }
	        @Override protected void process(List<String> chunks) {
	            for (String line : chunks) area.append(line);
	        }
	    }.execute();

	    // handle "System.in"
	    area.addKeyListener(new KeyAdapter() {
	        private StringBuffer line = new StringBuffer();
	        @Override public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
	            if (c == KeyEvent.VK_ENTER) {
	                in.println(line);
	                line.setLength(0); 
	            } else if (c == KeyEvent.VK_BACK_SPACE) { 
	                line.setLength(line.length() - 1); 
	            } else if (!Character.isISOControl(c)) {
	                line.append(e.getKeyChar());
	            }
	        }
	    });

	    return area;
	}

}