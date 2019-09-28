package de.mathema.sonarqube.plugin.java.rules.checks.javadoc;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import de.mathema.sonarqube.plugin.java.rules.checks.javadoc.JavadocPrivateMethod;

public class JavadocPrivateMethodTest {

   @Rule
   public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

   @Test
   public void test() {

      JavadocPrivateMethod check = new JavadocPrivateMethod();

      JavaCheckVerifier.verify("src/test/test-files/javadoc/JavadocPrivateMethodTestResource.java", check);
   }
}
