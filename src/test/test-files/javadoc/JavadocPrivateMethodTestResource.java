package de.mathema.sonarqube.plugin.java.rules.checks.javadoc;

public class JavadocPrivateMethodTestResource {

   /**
    * Constructor.
    */
   private JavadocPrivateMethodTestResource() {
      super();
   }

   private JavadocPrivateMethodTestResource() { // Noncompliant {{Document this private method by adding an explicit description.}}
      super();
   }

   public JavadocPrivateMethodTestResource(String value) {
      this();
   }
   
   private void doIt() { // Noncompliant {{Document this private method by adding an explicit description.}}
      // do it
   }

   /**
    * Do it. 
    */
   public void doIt(String value) {
      // do it
   }

   /**
    * Do it.
    * 
    * @param value the value
    * @param foo the foo
    */
   private void doIt(String value, String foo) {
      // do it
   }

   /**
    * Do it.
    * 
    * @param value the value
    * @param foo the foo
    * @param bar the bar
    */
   private String doIt(String value, String foo, String bar) { // Noncompliant {{Document this method return value.}}
      // do it
      return "";
   }

   /**
    * Do it.
    * 
    * @param value the value
    * @param foo the foo
    * @param bar the bar
    * @param foobar the foobar
    *  
    * @return the return value.
    */
   private String doIt(String value, String foo, String bar, String foobar) {
      // do it
      return "";
   }

   /**
    * Do it.
    *  
    * @return the return value.
    */
   private String doItException() throws Exception { // Noncompliant {{Document this method thrown exception(s): Exception}}
      // do it
      return "";
   }

   /**
    * Do it.
    *  
    * @return the return value.
    * @throws RuntimeException the exception.
    */
   private String doItRuntimeException() throws RuntimeException {
      // do it
      return "";
   }
}
