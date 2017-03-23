It is Java maven project. import the project and  run maven clean install.  This project is coding example of spring-boot, error-handling, spring-test,rest-API 

Software requirement for development
----------------------------------
1. Java - JDK 1.8.0_121
2. Spring  boot- 1.5.2.RELEAS
3. eclipse- Neon.3 Release (4.6.3RC3)
4. Maven - 3.3.9





STEP1: Running jar file (Start Server)
--------------------------------------------------------
Jar file is in target folder. Clone/download project and make sure you are running java command using Java 8.  


C:\...\spring-boot>java -jar target/wordcount_octus-0.0.1-SNAPSHOT.jar

STEP2:  Below are curl commands to run 
--------------------------------------------------------

Running curl using  basic user name and password
---------------------------------------------------------
1. curl -X POST -H "Content-Type: application/json" -v -u admin:admin123 http://127.0.0.1:8080/counter-api/search -d"{"""searchText""":["""Duis""","""Sed""","""Donec""","""Augue""","""Pellentesque""","""123"""]}" -k

2. curl -H "Content-Type: application/json" -H "Accept: text/csv" -v -u admin:admin123 http://127.0.0.1:8080/counter-api/top/20  -k



Running curl using Basic Authentication
-------------------------------------------------------
3. curl -X POST -H "Content-Type: application/json" -H "authorization:Basic YWRtaW46YWRtaW4xMjM=" -v http://127.0.0.1:8080/counter-api/search -d"{"""searchText""":["""Duis""","""Sed""","""Donec""","""Augue""","""Pellentesque""","""123"""]}" -k

4. curl -H "Content-Type: application/json" -H "Accept: text/csv" -H "authorization:Basic YWRtaW46YWRtaW4xMjM=" -v http://127.0.0.1:8080/counter-api/top/20  -k



