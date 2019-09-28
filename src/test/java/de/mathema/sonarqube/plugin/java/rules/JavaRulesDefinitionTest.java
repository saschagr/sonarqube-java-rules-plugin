package de.mathema.sonarqube.plugin.java.rules;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Param;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;

import de.mathema.sonarqube.plugin.java.rules.JavaRulesDefinition;
import de.mathema.sonarqube.plugin.java.rules.RulesList;

public class JavaRulesDefinitionTest {

   @Test
   public void test() {
      JavaRulesDefinition rulesDefinition = new JavaRulesDefinition();
      RulesDefinition.Context context = new RulesDefinition.Context();
      rulesDefinition.define(context);
      RulesDefinition.Repository repository = context.repository(JavaRulesDefinition.REPOSITORY_KEY);

      assertThat(repository.name()).isEqualTo("MATHEMA Repository");
      assertThat(repository.language()).isEqualTo("java");
      assertThat(repository.rules()).hasSize(RulesList.getChecks().size());

      assertAllRuleParametersHaveDescription(repository);
   }

   private void assertAllRuleParametersHaveDescription(Repository repository) {
      for (Rule rule : repository.rules()) {
         for (Param param : rule.params()) {
            assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
         }
      }
   }

}
