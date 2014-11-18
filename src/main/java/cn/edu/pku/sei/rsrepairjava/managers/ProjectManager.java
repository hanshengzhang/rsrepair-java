package cn.edu.pku.sei.rsrepairjava.managers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import com.github.antlrjavaparser.JavaParser;

/**
 * Define a class for managing certain project
 * @author Hansheng Zhang
 */

public class ProjectManager {
	
	private static String backupSuffix = "_backup";
	
	private String path; // Path of the current project
	private ArrayList<String> sourceFiles; // Relative pathes of source files that maybe modified
	private String srcPath;
	private IProjectExecutor executor;
	public ProjectManager(String path){
		this.path = new File(path).getAbsolutePath();
		this.sourceFiles = new ArrayList<String>();
	}
	
	public void setExecutor(IProjectExecutor executor){
		this.executor = executor;
	}
	
	public boolean compileProject(){
		return executor.compile();
	}
	
	public void setSourcePath(String path){
		this.srcPath = path;
	}
	
	public ArrayList<String> collectSourceFiles(){
		if (this.srcPath ==null) this.srcPath = "";
		File file = new File(this.path+"/"+this.srcPath);
		this.collectSourceFiles(file);
		return this.sourceFiles;
	}
	
	public ProjectManager(String path, boolean backup){
		this.path = new File(path).getAbsolutePath();
		this.sourceFiles = new ArrayList<String>();
		this.collectSourceFiles(new File(this.path));
		if (backup)
			this.backupSourceFiles();
	}
	
	public String getPath(){
		return this.path;
	}
	
	public ArrayList<String> getSourceFiles(){
		return this.sourceFiles;
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
	public void backupSourceFiles(){
		for (String filePath : this.sourceFiles){
			File origFile = new File(this.path+"/"+filePath);
			File backupFile  = new File(this.path+backupSuffix+"/"+filePath);
			if (!backupFile.getParentFile().exists())
				backupFile.getParentFile().mkdirs();
			if (!backupFile.exists())
			try { backupFile.createNewFile(); } 
			catch (IOException e) { e.printStackTrace();}
			try(
					FileChannel in = new FileInputStream(origFile).getChannel();
					FileChannel out = new FileOutputStream(backupFile).getChannel()
					){
				out.transferFrom(in, 0, in.size());
			}catch(IOException e){
			}
		}
	}
	
	/**
	 * Restore the source files from the backup
	 */
	public void restoreSourceFiles(){
		for (String filePath : this.sourceFiles){
			File origFile = new File(this.path+"/"+filePath);
			File backupFile  = new File(this.path+backupSuffix+"/"+filePath);
			try(
					FileChannel in = new FileInputStream(backupFile).getChannel();
					FileChannel out = new FileOutputStream(origFile).getChannel()
					){
				out.transferFrom(in, 0, in.size());
			}catch(IOException e){
			}
		}
	}
	
}
