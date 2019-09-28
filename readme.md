# SonarQube Plugin für Java Rules

In den Plugin sind eigene Regeln für SonarQube https://www.sonarqube.org/ implementiert.

## Installation des Plugins in SonarQube

Das gebaute Jar unter target/__sonarqube-java-rules-plugin-&lt;VERSION&gt;.jar__ bzw. aus dem lokalen/remote Repository in das Plugins-Verzeichnis von SonarQube legen und danach SonarQube neu starten.

Plugins-Verzeichnis:

    <SONARQUBE_HOME>/extensions/plugins 

## Plugins schreiben für SonarQube

Eine ausführliche Anleitung mit Beispielen für das Schreiben von SonarQube Plugins befindet sich unter

* https://github.com/SonarSource/sonar-custom-rules-examples
* Java - https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/java-custom-rules

## Eigene Regeln

Eigene Regeln müssen registiert werden, dazu muss die Regel die Liste von

    de.mathema.sonarqube.plugin.java.rules.RulesList.getJavaChecks()

hinzugefügt werden.

Eigene Regeln bestehen aus folgenden Dateien:

* Implementierung der Regel unter `src/main/javaa/de.mathema.sonarqube.plugin.java.rules.checks` bzw. Subpackage
* Konfigurationen: JSON und HTML unter `src/main/resources/org/sonar/l10n/java/rules/squid` (Pfad ist hart in SonarQube kodiert)
* Test für die Regel mit evtl. zu prüfender Datei mit Fehlern unter `src/test/test-files`

## Bestehende (Java) Regeln von SonarQube finden - keine eigene Regeln

* https://rules.sonarsource.com/java/

oder mit key

* https://rules.sonarsource.com/java/RSPEC-__&lt;key&gt;__ ohne führendes S

  z.B. <key>S1213</key> - https://rules.sonarsource.com/java/RSPEC-1213
  
  nur bei keyIds mit __S__xxxx

