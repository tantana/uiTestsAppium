package com.myTelenor.app.tests.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

public class FilesAndDirs {

    public void removeFile(String fn) {
		File file = new File(fn); 
        if (!file.delete()) {
        	System.out.println("Error while deleting "+fn);
		}
    }
        
    public static void renameFile(String oldName, String newName) throws IOException {
    	File file = new File(oldName);
    	if (file.exists()) {
    		File file2 = new File(newName);
    		Files.move(file.toPath(), file2.toPath(), StandardCopyOption.REPLACE_EXISTING);
    	}
    }

	public static boolean dirExists(String d) {
        File file = new File(d);
        return file.exists();
	}
	
    protected static boolean createDir(String d) {
        File file = new File(d);
        if (!file.exists()) {
        	System.out.println("Dir "+d+" is created");
        	return file.mkdir();
        }
        System.out.println(d+" already exists");
        return true;
    }
    
    public static void moveDir(String srcDirName, String destDirName) throws IOException {
   		File srcDir = new File(srcDirName);
   		File destDir = new File(destDirName+"/"+srcDirName);
   		if (srcDir.exists())
   			FileUtils.moveDirectory(srcDir, destDir);
    }
}
