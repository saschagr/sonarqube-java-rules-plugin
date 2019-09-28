package de.mathema.sonarqube.plugin.java.rules.checks.javadoc;

import java.time.LocalDate;
import java.util.Locale;

public class JavadocGetterSetterTestResource {

   private String name;

   private LocalDate date;

   private boolean enabled;

   private boolean visible;

   private int count;

   private Object value;
   
   private Locale locale;

   public String getName() { // Noncompliant {{Document this getter/setter by adding an explicit description.}}
      return name;
   }

   public void setName(String name) { // Noncompliant {{Document this getter/setter by adding an explicit description.}}
      this.name = name;
   }

   /**
    * @return the date
    */
   public LocalDate getDate() {
      return date;
   }

   /**
    * @param date the date to set
    */
   public void setDate(LocalDate date) { // Noncompliant {{Document this getter/setter by adding an explicit description.}}
      this.date = date;
   }

   public boolean isEnabled() { // Noncompliant {{Document this getter/setter by adding an explicit description.}}
      return enabled;
   }

   /**
    * Set enabled.
    * 
    * @param enabled the enabled to set
    */
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * Get the count.
    */
   public int getCount() { // Noncompliant {{Document this method return value.}}
      return count;
   }

   /**
    * Set the count.
    */
   public void setCount(int count) { // Noncompliant {{Document the parameter(s): count}}

      this.count = count;
   }

   /**
    * Get visible.
    * 
    * @return the visible
    */
   public boolean isVisible() {

      return visible;
   }

   /**
    * Set visible.
    * 
    * @param visible the visible to set
    */
   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   /**
    * Get the value.
    * 
    * @return the value
    * @throws RuntimeException if an error occurs.
    * 
    */
   public Object getValue() throws RuntimeException {
      return value;
   }

   /**
    * Set the value.
    * 
    * @param value the value to set
    */
   public void setValue(Object value) throws RuntimeException { // Noncompliant {{Document this method thrown exception(s): RuntimeException}}
      this.value = value;
   }
   
   /**
    * Get the locale.
    */
   public Locale getLocale() { // Noncompliant {{Document this method return value.}}
      return locale;
   }
   
   /**
    * Set the locale.
    * 
    * @param locale the locale to set
    */
   public void setLlocale(Locale locale) {
      this.locale = locale;
   }
   
   @Override
   public void setFoo(String foo) {
      //do nothing
   }

   /**
    * {@inheritDoc}
    */
   public String getFoo() {
      return null;
   }
}
