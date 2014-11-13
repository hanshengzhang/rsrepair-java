package cn.edu.pku.sei.rsrepairjava;

import java.util.ArrayList;

import cn.edu.pku.sei.rsrepairjava.managers.ProjectManager;
import junit.framework.TestCase;

public class BackupAndRestoreTest extends TestCase {
	public static String path = "resources/test/sample-project-backup-restore";
	
	public void testBackupAndRestore(){
		ProjectManager pm = new ProjectManager(path, false);
		ArrayList<String> sourceFiles = pm.getSourceFiles();
		assertNotNull(sourceFiles);
		assertEquals(sourceFiles.size(), 1);
		assertEquals(sourceFiles.get(0).replaceAll("\\\\", "/"), "sub1/Hello.java");
		// validate backup
		pm.restoreSourceFiles();
		// validate restore
	}
}
