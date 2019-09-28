package de.mathema.sonarqube.plugin.java.rules.checks.whitespaces;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;
import org.sonar.java.AnalyzerMessage;
import org.sonar.java.SonarComponents;
import org.sonar.java.ast.JavaAstScanner;
import org.sonar.java.model.VisitorsBridgeForTests;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.RecognitionException;

import de.mathema.sonarqube.plugin.java.rules.checks.whitespaces.AvoidTrailingWhitespaces;

public class AvoidTrailingWhitespacesTest {

   @Rule
   public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

   @Test
   public void testTrailingWhitespaces() {

      AvoidTrailingWhitespaces check = new AvoidTrailingWhitespaces();

      Set<AnalyzerMessage> issues = scanFile("src/test/test-files/whitespaces/AvoidTrailingWhitespacesResource.java", check);

      Assert.assertEquals(6, issues.size());

      for (AnalyzerMessage issue : issues) {
         Assert.assertEquals("Line ends with blank or tab", issue.getMessage());
      }
   }

   private Set<AnalyzerMessage> scanFile(String filename, JavaFileScanner check) {
      DefaultInputFile inputFile = new TestInputFileBuilder("", new File(filename).getPath()).setCharset(StandardCharsets.UTF_8).build();

      SonarComponents sonarComponents = sonarComponents(inputFile);
      VisitorsBridgeForTests visitorsBridge = new VisitorsBridgeForTests(Lists.newArrayList(check), sonarComponents);
      JavaAstScanner.scanSingleFileForTests(inputFile, visitorsBridge);
      VisitorsBridgeForTests.TestJavaFileScannerContext testJavaFileScannerContext = visitorsBridge.lastCreatedTestContext();

      return testJavaFileScannerContext.getIssues();
   }

   static SonarComponents sonarComponents(InputFile inputFile) {
      SensorContextTester context = SensorContextTester.create(new File(""))
         .setRuntime(SonarRuntimeImpl.forSonarLint(Version.create(6, 7)));
      context.setSettings(new MapSettings().setProperty("sonar.java.failOnException", true));
      SonarComponents sonarComponents = new SonarComponents(null, context.fileSystem(), null, null, null) {
         @Override
         public boolean reportAnalysisError(RecognitionException re, InputFile inputFile) {
            return false;
         }
      };
      sonarComponents.setSensorContext(context);
      context.fileSystem().add(inputFile);
      return sonarComponents;
   }

}
