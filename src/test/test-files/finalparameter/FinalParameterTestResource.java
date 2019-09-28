package de.mathema.sonarqube.plugin.java.rules.checks.finalparameter;

import java.io.IOException;
import java.util.Arrays;

public abstract class FinalParameterTestResource {
   
   public FinalParameterTestResource(String foo) { // Noncompliant [[sc=38;ec=48]] {{Use 'final' for parameter foo}}
      // do nothing
   }

   public FinalParameterTestResource(final String foo, final String bar) { // Compliant
      // do nothing
   }

   private void foo(String bar) { // Noncompliant [[sc=21;ec=31]] {{Use 'final' for parameter bar}}
      // do nothing
   }

   private static void bar(String foo) { // Noncompliant [[sc=28;ec=38]] {{Use 'final' for parameter foo}}
      // do nothing
   }

   private static void bar(final String foo, final String bar) { // Compliant
      // do nothing
   }

   private static void foo(final String foo, String bar) { // Noncompliant [[sc=46;ec=56]] {{Use 'final' for parameter bar}}
      // do nothing
   }

   private static void nop(String foo, final String bar) { // Noncompliant [[sc=28;ec=39]] {{Use 'final' for parameter foo}}
      // do nothing
   }

   public abstract void abstractFoo(String bar);// Compliant

   public native void navtivFoo(String bar);// Compliant

   public void catchIt() {
      try {
         // do nothing
      } catch (RuntimeException e) { // Compliant
         // do nothing
      }
   }

   public void catchItFinal() {
      try {
         // do nothing
      } catch (final RuntimeException e) { // Compliant
         // do nothing
      }
   }

   public void catchItMultiCatch() {
      try {
         throw new IOException();
      } catch (RuntimeException | IOException exception) { // Compliant
         // do nothing
      }
   }

   public void catchItMultiCatchFinal() {
      try {
         throw new IOException();
      } catch (final RuntimeException | IOException exception) { // Compliant
         // do nothing
      }
   }
   
   public void forEach() {
      for (String entry : Arrays.asList("Hallo")) { // Compliant
         // do nothing
      }
   }

   public void forEachFinal() {
      for (final String entry : Arrays.asList("Hallo")) { // Compliant
         // do nothing
      }
   }

   public void forEachWithException() {
      for (String entry : Arrays.asList("Hallo")) { // Compliant
         try {
            // do nothing
         } catch (RuntimeException e) { // Compliant
            // do nothing
         }
      }
   }
}
