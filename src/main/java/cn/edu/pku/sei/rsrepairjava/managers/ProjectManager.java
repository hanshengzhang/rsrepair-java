package cn.edu.pku.sei.rsrepairjava.managers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.github.antlrjavaparser.JavaParser;

/**
 * Define a class for managing certain project
 * @author Hansheng Zhang
 */

public class ProjectManager {
	private String path; // Path of the current project
	private List<String> sourceFiles; // Relative pathes of source files that maybe modified
	
	public ProjectManager(String path){
		this.path = new File(path).getAbsolutePath();
		this.sourceFiles = new ArrayList<String>();
		this.collectSourceFiles(new File(this.path));
		this.backupSourceFiles();
	}
	
	/**
	 * Try to parse .java files and collect those parsed successfully
	 */
	private void collectSourceFiles(File file){
		if (file.isDirectory()){
			File[] subFiles = file.listFiles();
			if (subFiles != null && subFiles.length>0){
				for (File f:subFiles)
					collectSourceFiles(f);
			}
		}else if (file.getName().endsWith(".java") && parseFile(file)){
			String relativePath = file.getAbsolutePath().substring(this.path.length()+1);
			this.sourceFiles.add(relativePath);
		}
	}
	
	private boolean parseFile(File file){
		String errMsg = null;
		try{
			PrintStream oldErr = System.err;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			System.setErr(ps);
			JavaParser.parse(file);
			System.setErr(oldErr);
			errMsg = baos.toString();
		} catch (Exception e){
			return false;
		}
		if (errMsg !=null && errMsg.length()>3)
			return false;
		return true;
	}
	
	/**
	 * Backup source files
	 */
	private void backupSourceFiles(){
		
	}
	
	/**
	 * Restore the source files from the backup
	 */
	public void restoreSourceFiles(){
		
	}
}
