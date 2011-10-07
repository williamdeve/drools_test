package test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import drools.DroolsActionTemplate;
import drools.Person;

public class TestDroolsRule {
	private static DroolsActionTemplate droolsTemplate;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		StringBuffer rule = new StringBuffer();
		InputStream is = new FileInputStream("src/resources/test.drl");
		try {
			byte[] buf = new byte[1024];

			do {
				int i = is.read(buf);
				if (i <= 0) {
					break;
				}

				rule.append(new String(buf, 0, i, "gbk"));
			} while (true);
		} finally {
			is.close();
		}

		List<String> rules = new ArrayList<String>();
		rules.add(rule.toString());
		droolsTemplate = new DroolsActionTemplate(rules);
	}

	@Test
	public void testInvoke() throws Throwable {
		List<Object> persons = new ArrayList<Object>();
		Person p = new Person();
		p.setAge(30);
		p.setSex("M");
		p.setName("Mike");
		persons.add(p);
		p = new Person();
		p.setAge(25);
		p.setSex("M");
		p.setName("Mike2");
		persons.add(p);
		p = new Person();
		p.setAge(25);
		p.setSex("F");
		p.setName("John");
		persons.add(p);
		p = new Person();
		p.setAge(22);
		p.setSex("F");
		p.setName("John2");
		persons.add(p);
		persons.add(p);
		p = new Person();
		p.setAge(28);
		p.setSex("F");
		p.setName("John3");
		persons.add(p);
		droolsTemplate.ruleAction(persons);
	}
}
