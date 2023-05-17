#### Java Notes
1. Oops : Abstraction, Encapsulation, Inheritance and polymorphism.
2. JVM Memory model
	Permanent generation : Contains application meta data like classes and methods. Can be GC.
	    Method Area : Code like Class structure, methods and static variables. 
    Memory pool : Immutable objects collection. like string pools.
    Run time constant pool : 
    Java stack memory : Used for the execution of the threads. 
    Java memory switches.
    Heap
     Young generation = Eden memory(Minor GC) + Survivor memory-1 (s0)+Survivor memory-2 (s1) 
     Old generation = After multiple minor GC's objected moved into this space. When full Major GC takes place. It has
     				performance implications. While GC all the threads are halted.
3. Gc : Serial, Parallel GC,Parallel OLD GC, Concurrent Mark Sweep  & G1 Garbage Collector.

2. Multi-threading

#### Multi threading 
   Daemon Thread
   User defined thread.
   
   Monitors Vs Locks
       


##### Streams
Stream is a sequence of data. In Java a stream is composed of bytes.
Predicate - In mathematical terms it is boolean-valued function P: X? {true, false}, called the predicate on X.
		   Java used same terminology in functional programming.
Operations on stream:
   1) filter
   2)		   


##### Functional programming
Be declarative.
Promote immutability.
Avoid side effects.
Prefer expressions over statements.
Design with higher-order functions.







Logger levels : ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF.
##### Maven commands:
POM- GAV(group,artifcatId,version)

 Maven $>mvn [options] goals [<phase(s)>]
** Important options:
  - am      also make/build project list(-pl) .
  - pl      project list.
  - amd     also build dependents on this project list.

Build life cycles: 1) default(handles deployment)  2) clean(cleans project)  3) site(Documentation)

Reference URL:
1. https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
2. Build phase is made up of plugin goals.
3. Validate -> compile -> test -> package -> verify -> install(into local) -> deploy(Remote)

Important Plugins
$> https://maven.apache.org/plugins/index.html
HELP of plugin $> mvn <plugin-name>:help

1) clean     --  clean
2) compiler  --  compile, testCompile
3) surefire  --  test   (properties ::  -Dskip=true, -DskipTests=true(but compiles), -Dtest=MyTest )
                 $> -Dmaven.test.skip=true              -- skip test compilation
4) jar       --  jar, test-jar,sign,sign-verify
5) resources  -- copy-resources, resources,testResources
6) install -- install, install-file
7) deploy  --  deploy, deploy-file
8) dependency -- analyze-duplicate, analyze, analyze-only, analyze-report, copy, get, list, list-repositories, purge-local-repository, resolve, unpack, properties, tree
9) help   --
10) archetype --
11) jetty  --
12) tomcat --
13) describe --

**Important commands
Main Compile : $>mvn compile 
Test compile : $>mvn test-compilie

1. Avoid compiling main sources :  -DskipMain=true
2. Avoid Test cases : -DskipTests=true
3. Running single Test class : $> $> mvn -Dtest=<class>#<method> test
4. LIst all phases : $> mvn help:describe -Dcmd=deploy      -- lists all phases


### Spring--boot && developer-tools commands:
Plugins
 1) spring-boot-maven-plugin
 2) spring-boot-devtools

$> mvn spring-boot:<goals>    -- start, stop, run, repackage, build-info

##### Gradle
Init gradle project $> gradle init
list tasks $> gradlew tasks --all
Running Tasks $> gradlew <task1> <task2> <task3>             -- Tasks will be executed in order.

1) Like maven Gradle also has plugins to handle tasks.

Running Individual testResources

gradle test --tests org.vgr.app.tst.util*
gradle test --tests *SomeTest.someSpecificFeature
gradle test --tests *SomeSpecificTest
gradle test --tests all.in.specific.package*
gradle test --tests *IntegTest
gradle test --tests *IntegTest*ui*
gradle test --tests *IntegTest.singleMethod


$> gradle –q hello


Task:
task('hello') << {
   println "tutorialspoint"
}


Dpendencies::

1)
task taskX << {
   println 'taskX'
}
task taskY(dependsOn: 'taskX') << {
   println "taskY"
}

$> gradle –q taskY


2)
task taskY << {
   println 'taskY'
}
task taskX << {
   println 'taskX'
}
taskY.dependsOn taskX

$> gradle –q taskY	



Popular frameworks

https://spring.io/projects/spring-boot

1. Spring
	a. framework
	b. Boot
		Enables easy development of stand-alone, Rest API applications. 
		Starters.
	    Provides production-ready features such as metrics, health checks, and externalized 					configuration

	c. Data


	d. Cloud
  	e. Cloud data flow
	f. security
	g.  

ex. configuration management, service discovery, circuit breakers, intelligent routing, micro-proxy, control bus, one-time tokens, global locks, leadership election, distributed sessions, cluster state)
