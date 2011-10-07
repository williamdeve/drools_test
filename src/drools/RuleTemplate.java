package drools;

import java.util.List;
import java.util.Map;

public interface RuleTemplate {
	public void ruleAction(List<Object> facts);
	public void ruleAction(List<Object> facts, Map<String, Object> globalParameters);
}
