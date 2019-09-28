package de.mathema.sonarqube.plugin.java.rules.checks.finalparameter;

public interface FinalParameterTestInterfaceResource {

   abstract public void foo(String bar) { // Compliant
      // do nothing
   }

   public void bar(String foo) { // Compliant
      // do nothing
   }

   public void bar(final String foo, final String bar) { // Compliant
      // do nothing
   }

   public void foo(final String foo, String bar) { // Compliant
      // do nothing
   }

   public nop(String foo, final String bar) { // Compliant
      // do nothing
   }
}
