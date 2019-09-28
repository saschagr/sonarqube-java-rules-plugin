package de.mathema.sonarqube.plugin.java.rules.checks.finalparameter;

import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.CatchTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

@Rule(key = "FinalParameter", priority = Priority.MAJOR)
public class FinalParameter extends BaseTreeVisitor implements JavaFileScanner {

   @RuleProperty(key = "checkCatch", description = "Set to 'true' to check catch blocks are final 'catch(final Exception e)'",
      defaultValue = "false")
   public boolean checkCatch = false;

   @RuleProperty(key = "checkForEachStatement",
      description = "Set to 'true' to check for-each blocks are final 'for(final String e : ...)'", defaultValue = "false")
   public boolean checkForEachStatement = false;

   private JavaFileScannerContext context;

   @Override
   public void scanFile(JavaFileScannerContext context) {
      this.context = context;

      scan(context.getTree());
   }

   @Override
   public void visitClass(ClassTree tree) {
      if (!isInterface(tree)) {
         super.visitClass(tree);
      }
   }

   /* visitMethod besucht auch Konstruktoren) */
   @Override
   public void visitMethod(MethodTree tree) {
      if (!isAbstractOrNativeMethod(tree)) {
         checkFinalParameter(tree.parameters());
      }
      super.visitMethod(tree);
   }

   @Override
   public void visitForEachStatement(ForEachStatement tree) {
      if (checkForEachStatement) {
         checkFinalParameter(tree.variable());
      }

      super.visitForEachStatement(tree);
   }

   @Override
   public void visitCatch(CatchTree tree) {
      if (checkCatch) {
         checkFinalParameter(tree.parameter());
      }
      super.visitCatch(tree);
   }

   private void checkFinalParameter(final List<VariableTree> parameters) {
      parameters.forEach(param -> checkFinalParameter(param));
   }

   private void checkFinalParameter(final VariableTree param) {
      if (!param.modifiers().modifiers().stream().anyMatch(m -> m.modifier() == Modifier.FINAL)) {
         context.reportIssue(this, param, String.format("Use 'final' for parameter %s", param.symbol().name()));
      }
   }

   private boolean isInterface(ClassTree tree) {
      return tree.is(Kind.INTERFACE);
   }

   private boolean isAbstractOrNativeMethod(MethodTree tree) {
      return tree.modifiers().modifiers().stream().anyMatch(m -> m.modifier() == Modifier.ABSTRACT || m.modifier() == Modifier.NATIVE);
   }

}
