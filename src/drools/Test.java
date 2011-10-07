package drools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.CommandFactory;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KnowledgeBuilder kbd = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbd.add(ResourceFactory.newClassPathResource("resources/test.drl",Test.class), ResourceType.DRL);
		if(kbd.hasErrors()) System.out.println(kbd.getErrors().toString());
		Collection<KnowledgePackage> kpackage = kbd.getKnowledgePackages();
		KnowledgeBaseConfiguration kbc = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
		kbc.setProperty("org.drools.sequential", "true");
		KnowledgeBase kbs=KnowledgeBaseFactory.newKnowledgeBase(kbc);
		kbs.addKnowledgePackages(kpackage);
		List<Person> persons = new ArrayList<Person>();
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
		StatefulKnowledgeSession statefulSession= kbs.newStatefulKnowledgeSession();
		
		
		//log
		//statefulSession.addEventListener( new DebugAgendaEventListener() );
		//statefulSession.addEventListener( new DebugWorkingMemoryEventListener() );
		//KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger( statefulSession, "./test" );
		//KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger( statefulSession, "./test", 1000 );
		List<String> lst = new ArrayList<String>();
//		statefulSession.setGlobal("list",lst );
//		statefulSession.insert(p); 
//		statefulSession.fireAllRules();
		StatelessKnowledgeSession stateless= kbs.newStatelessKnowledgeSession();
		stateless.setGlobal("list",lst );
		stateless.execute(persons);
		for(String str: lst){
			System.out.println(str);
		}
//		statefulSession.dispose();
		//logger.close();

		
		}

}
