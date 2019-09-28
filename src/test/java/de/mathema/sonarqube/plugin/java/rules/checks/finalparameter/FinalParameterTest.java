package de.mathema.sonarqube.plugin.java.rules.checks.finalparameter;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import de.mathema.sonarqube.plugin.java.rules.checks.finalparameter.FinalParameter;

public class FinalParameterTest {

   @Rule
   public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

   @Test
   public void test() {

      FinalParameter check = new FinalParameter();

      JavaCheckVerifier.verify("src/test/test-files/finalparameter/FinalParameterTestResource.java", check);

   }

   @Test
   public void testCatchAndForEach() {

      FinalParameter check = new FinalParameter();
      check.checkCatch = true;
      check.checkForEachStatement = true;

      JavaCheckVerifier.verify("src/test/test-files/finalparameter/FinalParameterTestResourceCatchForEach.java", check);

   }

   @Test
   public void testInterface() {

      FinalParameter check = new FinalParameter();

      JavaCheckVerifier.verifyNoIssue("src/test/test-files/finalparameter/FinalParameterTestInterfaceResource.java", check);
   }

}
