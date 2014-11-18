package cn.edu.pku.sei.rsrepairjava.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 * This represents an ant project
 * @author Hansheng Zhang
 */
public class AntProjectExecutor implements IProjectExecutor {
	String path;
	public AntProjectExecutor(String path){
		this.path = new File(path).getAbsolutePath();
	}
	
	@Override
	public boolean compile() {
		boolean flag = false;
		String oldDir = System.getProperty("user.dir");
		try {
			System.setProperty("user.dir", this.path);
			String[] command = {"cmd", "/C", "compile.bat"};
			ArrayList<String> environment = new ArrayList<String>();
			for (Map.Entry<String, String> entry : System.getenv().entrySet()){
				if (!entry.getKey().equals("Path") && !entry.getKey().equals("JAVA_HOME"))
					continue;
				String s = entry.getKey() + "=" + entry.getValue();
				environment.add(s);
			}
			Process p = Runtime.getRuntime().exec(command, environment.toArray(new String[0]), new File(this.path));
			p.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line=br.readLine())!=null){
				String lower = line.toLowerCase();
				if (lower.indexOf("build successful")!=-1){
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{System.setProperty("user.dir", oldDir);}
		return flag;
	}

	@Override
	public boolean testOnly(String testName) {
		// TODO Auto-generated method stub
		return false;
	}

}
