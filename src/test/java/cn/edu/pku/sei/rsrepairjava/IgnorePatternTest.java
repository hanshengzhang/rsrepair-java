package cn.edu.pku.sei.rsrepairjava;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.edu.pku.sei.rsrepairjava.visitors.CollectStatementsVisitor;

public class IgnorePatternTest {

	@Test
	public void testMatching() {
		assertTrue("super(hello, sd)".matches(CollectStatementsVisitor.ignorePattern));
		assertTrue("this(hello, sd)".matches(CollectStatementsVisitor.ignorePattern));
		assertFalse("super.create(hello, sd)".matches(CollectStatementsVisitor.ignorePattern));
		assertTrue(CollectStatementsVisitor.canIgnore("super(new FileOutputStream(new File(f.getParentFile(), f.getName() \n+ TMP_EXTENSION)))"));
	}

}
