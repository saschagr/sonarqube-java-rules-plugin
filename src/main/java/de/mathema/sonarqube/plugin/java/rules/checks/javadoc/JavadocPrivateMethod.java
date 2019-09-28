package de.mathema.sonarqube.plugin.java.rules.checks.javadoc;

import java.util.List;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.checks.helpers.Javadoc;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Modifier;
import org.sonar.plugins.java.api.tree.PrimitiveTypeTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(key = "JavadocPrivateMethod", priority = Priority.MINOR)
public class JavadocPrivateMethod extends BaseTreeVisitor implements JavaFileScanner {

   private JavaFileScannerContext context;

   @Override
   public void scanFile(JavaFileScannerContext context) {
      this.context = context;

      scan(context.getTree());
   }

   @Override
   public void visitMethod(MethodTree methodTree) {
      if (isPrivate(methodTree)) {
         Javadoc javadoc = new Javadoc(methodTree);
         if (javadoc.noMainDescription() && !isNonVoidMethodWithNoParameter(methodTree, javadoc)) {
            context.reportIssue(this, methodTree.simpleName(), "Document this private method by adding an explicit description.");
         } else {
            List<String> undocumentedParameters = javadoc.undocumentedParameters();
            if (!undocumentedParameters.isEmpty()) {
               context.reportIssue(this, methodTree.simpleName(),
                  "Document the parameter(s): " + undocumentedParameters.stream().collect(Collectors.joining(", ")));
            }
            if (hasNonVoidReturnType(methodTree) && javadoc.noReturnDescription()) {
               context.reportIssue(this, methodTree.simpleName(), "Document this method return value.");
            }
            List<String> undocumentedExceptions = javadoc.undocumentedThrownExceptions();
            if (!undocumentedExceptions.isEmpty()) {
               context.reportIssue(this, methodTree.simpleName(),
                  "Document this method thrown exception(s): " + undocumentedExceptions.stream().collect(Collectors.joining(", ")));
            }
         }
      }
   }

   private boolean isPrivate(MethodTree methodTree) {
      return methodTree.modifiers().modifiers().stream()
         .anyMatch(modifierKeywordTree -> modifierKeywordTree.modifier() == Modifier.PRIVATE);
   }

   private boolean isNonVoidMethodWithNoParameter(MethodTree tree, Javadoc javadoc) {
      return hasNonVoidReturnType(tree) && (tree).parameters().isEmpty() && !javadoc.noReturnDescription();
   }

   private boolean hasNonVoidReturnType(MethodTree tree) {
      Tree returnType = tree.returnType();

      if (isConstructor(tree)) {
         return false;
      }

      return returnType == null
         || !(returnType.is(Tree.Kind.PRIMITIVE_TYPE) && "void".equals(((PrimitiveTypeTree) returnType).keyword().text()));
   }

   private boolean isConstructor(MethodTree tree) {
      return tree.is(Kind.CONSTRUCTOR);
   }

}
