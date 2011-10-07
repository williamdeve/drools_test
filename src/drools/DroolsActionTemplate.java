package drools;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.FactHandle;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.WorkingMemory;
import org.drools.compiler.DrlParser;
import org.drools.compiler.PackageBuilder;
import org.drools.lang.descr.PackageDescr;
public class DroolsActionTemplate implements RuleTemplate  {
	private RuleBase ruleBase;
	private List<String> rules;
	public DroolsActionTemplate(List<String> rules) {
		this.rules = rules;
		ruleBase = getRuleBase();
	}
	public void ruleAction(List<Object> facts) {
		ruleAction(facts, new HashMap<String, Object>());
	}

	public void ruleAction(List<Object> facts, Map<String, Object> globalParameters) {
		WorkingMemory wm = getWorkingMemory(facts);

		for (Iterator<String> it = globalParameters.keySet().iterator(); it.hasNext();) {
			String globalParameterName = it.next();
			Object globalParameterValue = globalParameters.get(globalParameterName);
			wm.setGlobal(globalParameterName, globalParameterValue);
		}

		wm.fireAllRules();
	}

	private WorkingMemory getWorkingMemory(List<Object> assertObjects) {
		WorkingMemory currentWorkingMemory =getRuleBase().newStatefulSession();
		for (Object object : assertObjects) {
			assertObject(currentWorkingMemory, object);
		}
		

		return currentWorkingMemory;
	}
	private void assertObject(WorkingMemory workingMemory, Object element) {
		if (element == null) {
			return;
		}

		FactHandle fact = workingMemory.getFactHandle(element);
		if (fact == null) {
			workingMemory.insert(element);
		} else {
			workingMemory.update(fact, element);
		}
	}
	private RuleBase getRuleBase() {
		if (ruleBase == null)
			try {
				compileRuleBase();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return ruleBase;
	}
	private void compileRuleBase() {
		PackageBuilder builder = new PackageBuilder();
		ruleBase = RuleBaseFactory.newRuleBase();
		try {
			if (rules != null) {
				for (String rule : rules) {
					PackageDescr packageDescr;
					Reader drlReader = new InputStreamReader(new ByteArrayInputStream(rule.getBytes()));
					packageDescr = new DrlParser().parse(drlReader);
					builder.addPackage(packageDescr);
				}

				ruleBase.addPackage(builder.getPackage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
