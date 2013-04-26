JPP Packaging just extends EAP packaging from GateIn - so all instructions mentioned in README.txt should be still valid. 
There are two additional 'jpp' and 'jpp-bundle' profiles. First packages JPP, second creates distribution zip. 

You need to have proper version of EAP unzipped in servers directory (same as for JBoss AS7). Additionally enteprise maven repository 
for a given EAP needs to be unpacked into your local maven repository folder. Alternatively you can define dedicated profile in your 
settings.xml to include local eap maven repository. 

With all default options this command should package JPP:

mvn clean install -DskipTests -Dgatein.dev=jpp -Pbundle-jpp
