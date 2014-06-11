package malgm.minecraft.launcher.ui.tabs.options;

import java.awt.event.*;

import javax.swing.*;

import malgm.minecraft.launcher.Logger;
import malgm.minecraft.launcher.ResourceFinder;
import malgm.minecraft.launcher.ResourceLoader;
import malgm.minecraft.launcher.settings.SettingsFile;
import malgm.minecraft.launcher.ui.*;
import malgm.minecraft.launcher.ui.components.PopUp;
import malgm.minecraft.launcher.ui.components.TiledBackground;
import malgm.minecraft.launcher.util.Utils;

public class OptionsInfoPanel extends TiledBackground implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton browse, change;
	private JLabel directory;

	private JTextField field;
	private SettingsFile settings = new SettingsFile(Utils.getLauncherDirectory().toString());

	//Array of modes
	private String[] modes = {settings.defaultDir, settings.customDir};
	private JComboBox<?> list;

	private static ResourceFinder resFinder = new ResourceFinder();

	public OptionsInfoPanel(ResourceLoader loader) {
		super(loader.getImage(resFinder.background()));

		// install directory label
		directory = new JLabel("Install directory...    ");
		directory.setForeground(IndigoUI.COLOR_WHITE_TEXT);
		add(directory);

		// list for selecting between default and custom minecraft installations
		list = new JComboBox<Object>(modes);
		list.setSelectedItem(settings.getSettingsValue(settings.getDefaultDirectory(), settings.getDefaultFileName(), "mcDirectory"));
		list.addActionListener(this);
		list.setEnabled(true);

		add(list);

		// Text field for custom directory
		field = new JTextField(35);
		field.setText(settings.getSettingsValue(settings.getDefaultDirectory(), settings.getDefaultFileName(), "customDirectory"));
		field.setEditable(false);

		add(field);

		// browse button
		browse = new JButton("Browse");
		browse.addActionListener(this);

		// change button
		change = new JButton("Change");
		change.addActionListener(this);
		change.setMnemonic(KeyEvent.VK_ENTER);

		if(list.getSelectedItem() == modes[0]) {
			field.setEnabled(false);
			browse.setEnabled(false);
			change.setEnabled(false);
		} else {
			field.setEnabled(true);
			browse.setEnabled(true);
			change.setEnabled(true);
		}

		add(browse);
		//add(change);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(list.getSelectedItem() == modes[0]) {
			settings.writeToSettingsFile(settings.getDefaultDirectory(), settings.getDefaultFileName(), "mcDirectory", settings.defaultDir);
			field.setEnabled(false);
			browse.setEnabled(false);
			change.setEnabled(false);
		}
		if(list.getSelectedItem() == modes[1]) {
			settings.writeToSettingsFile(settings.getDefaultDirectory(), settings.getDefaultFileName(), "mcDirectory", settings.customDir);
			field.setEnabled(true);
			browse.setEnabled(true);
			change.setEnabled(true);
		}
		if(e.getSource() == this.change) {
			String s = field.getText();
			if(!s.equalsIgnoreCase("")) {
				String su = s.substring(s.length() - 1);
				if(!su.equals("\\")) {
					s += "\\";
				}
				settings.writeToSettingsFile(settings.getDefaultDirectory(), settings.getDefaultFileName(), "customDirectory", s);
				field.setText(settings.getSettingsValue(settings.getDefaultDirectory(), settings.getDefaultFileName(), "customDirectory"));
				Logger.log("Install directory changed");
			} else {
				PopUp.error("ERROR MESSAGE", "You did not specify a directory!");
			}
		}
		if(e.getSource() == this.browse) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(Utils.getLauncherDirectory());
			chooser.setDialogTitle("Install Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            	settings.writeToSettingsFile(settings.getDefaultDirectory(), settings.getDefaultFileName(), "customDirectory", chooser.getSelectedFile().getPath());
            	field.setText(settings.getSettingsValue(settings.getDefaultDirectory(), settings.getDefaultFileName(), "customDirectory"));

            	System.out.println();
            	Logger.log("Install directory changed");
            	System.out.println();
            } else {
            	System.out.println();
            	Logger.log("No selection");
            	System.out.println();
            }
		}
	}

}
