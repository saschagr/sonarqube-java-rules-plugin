package de.mathema.sonarqube.plugin.java.rules.checks.whitespaces;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;

@Rule(key = "AvoidTrailingWhitespaces", priority = Priority.MINOR)
public class AvoidTrailingWhitespaces extends BaseTreeVisitor implements JavaFileScanner {

   @Override
   public void scanFile(JavaFileScannerContext context) {
      int lineNumber = 0;
      for (String line : context.getFileLines()) {
         lineNumber++;
         if (hasBlankOrTabAtEnd(line)) {
            context.addIssue(lineNumber, this, "Line ends with blank or tab");
         }
      }
   }

   private boolean hasBlankOrTabAtEnd(String line) {
      return line.endsWith(" ") || line.endsWith("\t");
   }

}
