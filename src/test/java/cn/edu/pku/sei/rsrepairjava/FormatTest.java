package cn.edu.pku.sei.rsrepairjava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.github.antlrjavaparser.JavaParser;
import com.github.antlrjavaparser.ParseException;
import com.github.antlrjavaparser.api.CompilationUnit;

import cn.edu.pku.sei.rsrepairjava.managers.AntProjectExecutor;
import cn.edu.pku.sei.rsrepairjava.managers.ProjectManager;
import junit.framework.TestCase;
/**
 * Test the correction of formatting (not leading to compilation failure) 
 * @author Hansheng Zhang
 */
public class FormatTest extends TestCase {
	
	public void testFormat(){
		ProjectManager pm = new ProjectManager("resources/test/zookeeper-3.4.6");
		pm.setExecutor(new AntProjectExecutor(pm.getPath()));
		assertTrue(pm.compileProject());
		pm.setSourcePath("src/java/main");
		pm.collectSourceFiles();
		pm.backupSourceFiles();
		for (String source_path:pm.getSourceFiles()){
			System.out.print(source_path+":");
			String path = pm.getPath()+ File.separator+source_path;
			try {
				CompilationUnit cu = JavaParser.parse(new File(path));
				BufferedWriter bw = new BufferedWriter(new FileWriter(path));
				bw.write(cu.toString());
				bw.close();
				assertTrue(pm.compileProject());
				System.out.println("done!");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
