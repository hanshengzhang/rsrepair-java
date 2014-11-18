package cn.edu.pku.sei.rsrepairjava.managers;

public interface IProjectExecutor {
	boolean compile();
	boolean testOnly(String testName);
}
