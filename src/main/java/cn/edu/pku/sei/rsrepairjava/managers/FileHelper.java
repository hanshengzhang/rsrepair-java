package cn.edu.pku.sei.rsrepairjava.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.edu.pku.sei.rsrepairjava.visitors.CollectStatementsVisitor;

import com.github.antlrjavaparser.JavaParser;
import com.github.antlrjavaparser.api.CompilationUnit;
import com.github.antlrjavaparser.api.stmt.Statement;

/**
 * Help manage files
 * @author Hansheng Zhang
 */

public class FileHelper {
	/**
	 * @param srcPath Path of the source file
	 * @param desPath Path of the destination file
	 * @param startIndex Starting index 
	 * @return  Next index
	 * @throws Exception
	 */
	public static int addPrintForeachStatement(String srcPath, String desPath, int startIndex) throws Exception{
		int counter = startIndex;
		CompilationUnit cu = JavaParser.parse(new File(srcPath));
		CollectStatementsVisitor csv = new CollectStatementsVisitor();
		cu.accept(csv, true);
		ArrayList<Statement> statements = csv.getStatements();
		Collections.sort(statements, new Comparator<Statement>() {
			@Override
			public int compare(Statement o1, Statement o2) {
				if (o1.getBeginLine() < o2.getBeginLine())
					return -1;
				else if (o1.getBeginLine()>o2.getBeginLine())
					return 1;
				return o1.getBeginColumn() - o2.getBeginColumn();
			}
		});
		int index = 0;
		int totalCnt = statements.size();
		System.out.println(statements.get(0).toString());
		System.out.println("lines:"+statements.get(0).getBeginLine());
		
		List<String> allLines = Files.readAllLines(Paths.get(srcPath), Charset.defaultCharset());
		System.out.println("number of lines: "+ allLines.size());
		StringBuilder sb = new StringBuilder();
		int lineIndex = 0;
		for (String line:allLines){
			int offset = 0;
			lineIndex++;
			for (;index<totalCnt && statements.get(index).getBeginLine()==lineIndex; ++index){
				String code = "System.out.println("+(counter++)+");";
				Statement s = statements.get(index);
				int pos = offset + s.getBeginColumn();
				String new_line = line.substring(0, pos) + code + line.substring(pos);
				line = new_line;
				offset += code.length();
			}
			sb.append(line+"\n");
		}
		try( BufferedWriter bw = new BufferedWriter(new FileWriter(desPath)) ){
			bw.write(sb.toString());
		}
		if (counter-startIndex != statements.size())
			throw new Exception("Failed to add print for each statement "+(counter-startIndex)+"/" + statements.size() +"\t"+ srcPath);
		return startIndex + statements.size();
	}
}
