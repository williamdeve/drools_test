package drools;

import java.util.ArrayList;
import java.util.List;

import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class TestAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResourceChangeScannerConfiguration sconf = ResourceFactory
				.getResourceChangeScannerService()
				.newResourceChangeScannerConfiguration();
		// 设置定时扫描时间间隔，默认60s
		sconf.setProperty("drools.resource.scanner.interval", "30");
		ResourceFactory.getResourceChangeScannerService().configure(sconf);
		//启动扫描和监听服务
		ResourceFactory.getResourceChangeScannerService().start();
		ResourceFactory.getResourceChangeNotifierService().start();
		//create a agent cinfig
		KnowledgeAgentConfiguration aconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		//是否扫描目录
		aconf.setProperty("drools.agent.scanDirectories","false");  
        aconf.setProperty("drools.agent.newInstance","true"); 
        //create a agent
        KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("testname",aconf);  
        kagent.applyChangeSet(ResourceFactory.newClassPathResource("resources/resource.xml"));
        //create a stateful session
        StatefulKnowledgeSession statefulSession=kagent.getKnowledgeBase().newStatefulKnowledgeSession();
        //这样的用法表示使用最近一次编译的knowledgebase的session
       //StatelessKnowledgeSession statelessSession= kagent.newStatelessKnowledgeSession();
        Person p = new Person();
		p.setAge(30);
		p.setSex("M");
		p.setName("Mike");
		statefulSession.insert(p);
//		statelessSession.execute(p);
		List<String> lst = new ArrayList<String>();
		statefulSession.setGlobal("list",lst );
		statefulSession.fireAllRules();
		for(String str: lst){
			System.out.println(str);
		}
		
//		statefulSession.dispose();
        
	}

}
