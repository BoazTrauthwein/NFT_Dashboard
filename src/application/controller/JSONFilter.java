package application.controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class JSONFilter extends FileFilter {

@Override
public boolean accept(File file) {
	if (file.isDirectory()) {
        return true;
    } else {
        String filename = file.getName().toLowerCase();
        return filename.endsWith(".json");
    }
}

    @Override
    public String getDescription() {
        return "JSON file (*.json)";
    }
}