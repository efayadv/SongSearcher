run: App.class
	java App
App.class: App.java
	javac App.java
runBDTests:
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests
runFDTests: FrontendDeveloperTests.class
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java
clean:
	rm *.class
