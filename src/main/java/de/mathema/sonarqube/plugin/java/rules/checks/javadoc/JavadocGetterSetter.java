package de.mathema.sonarqube.plugin.java.rules.checks.javadoc;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.ast.visitors.PublicApiChecker;
import org.sonar.java.checks.helpers.Javadoc;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.PrimitiveTypeTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "JavadocGetterSetter", priority = Priority.MAJOR)
public class JavadocGetterSetter extends BaseTreeVisitor implements JavaFileScanner {

   private final Pattern setterPattern = Pattern.compile("set[A-Z].*");
   private final Pattern getterPattern = Pattern.compile("(get|is)[A-Z].*");

   private JavaFileScannerContext context;

   @Override
   public void scanFile(JavaFileScannerContext context) {
      this.context = context;

      scan(context.getTree());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void visitMethod(MethodTree methodTree) {

      if (!(methodTree.isOverriding() || hasInheritDoc(methodTree)) && isGetterOrSetter(methodTree)) {
         Javadoc javadoc = new Javadoc(methodTree);
         if (javadoc.noMainDescription() && !isNonVoidMethodWithNoParameter(methodTree, javadoc)) {
            context.reportIssue(this, methodTree.simpleName(), "Document this getter/setter by adding an explicit description.");
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

   private boolean hasInheritDoc(MethodTree methodTree) {
      String apiJavadoc = PublicApiChecker.getApiJavadoc(methodTree);
      if (apiJavadoc != null) {
         return apiJavadoc.contains("@inheritDoc");
      }
      return false;
   }

   private boolean isGetterOrSetter(MethodTree methodTree) {
      String name = methodTree.simpleName().name();
      return (setterPattern.matcher(name).matches() && methodTree.parameters().size() == 1)
         || (getterPattern.matcher(name).matches() && methodTree.parameters().isEmpty());
   }

   private boolean isNonVoidMethodWithNoParameter(MethodTree tree, Javadoc javadoc) {
      return hasNonVoidReturnType(tree) && (tree).parameters().isEmpty() && !javadoc.noReturnDescription();
   }

   private boolean hasNonVoidReturnType(MethodTree tree) {
      Tree returnType = tree.returnType();
      return returnType == null
         || !(returnType.is(Tree.Kind.PRIMITIVE_TYPE) && "void".equals(((PrimitiveTypeTree) returnType).keyword().text()));
   }

}
