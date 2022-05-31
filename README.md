# User Documentation
The system is a mutation testing framework which provides the ability for a user to run their source code and their corresponding unit test class by this framework to see if mutants in the form of arithmetic or logical operators identify bugs in code. The system essentially comprises of three main components, i.e., mutator, mutation runner and mutation analyzer. The mutator is the main driver of the framework which is responsible for mutating the input user source code, feeding the mutated code to the runner to execute a user’s unit test (either a specific provided input function or through an entire test suite) and finally passing the test output through the mutation analyzer to generate a final score. A user would want to do this in order to detect faults in their code, improve quality of software and loopholes in test data to be detected early in the development process.

## Installing the software
The following libraries need to be added as a dependency.
1. Jackson-annotations-2.13.3.jar
2. Jackson-core-2.13.3.jar
3. Jackson-databing-2.13.3.jar
4. Junit-4.5
They should appear in the *Libraries* section under project settings when you click on the project structure in IntelliJ IDE (Ultimate 2020.3). The project should also be compatible for eclipse users. The SDK that worked for me was *liberica 11*. The framework uses ANT as the build system. Once the project is downloaded into the IDE, right click on build.xml and click on “Build as ANT file”.

## Running the software
once the above steps are followed and necessary software pre-requisites are fulfilled, open ***MutatorTest.java*** in the test/ directory and run the junit tests. These run through the entire suite of components such as Mutator, Mutation Runner and Mutation Analyzer. A user also has the ability to set breakpoints in any file in the src/ directory and run the junit tests under debug mode. If a user would like to run the tests from command prompt, they will need to run the command “ant” to execute the full suite of tests. Mutator is the entry point into the framework.

## Using the software
A user will need to perform the following steps to jntegrate with the framework
1. One source code class to be mutated. The source code class can have multiple functions.
2. Corresponding junit test class for the source code class being mutated. The junit test class can have multiple test cases.
3. User places the source code class under src/ directory of the project.
4. User places the junit test class under the test/ directory of the project.
5. To run the mutation framework, user adds a junit test as follows in MutatorTest.java
    - ```@Test
         public void mutateArithTest() {
             Mutator m = new Mutator(MutantOperation.ARITH,
                 "src/MutationTester.java", "test/MutationTesterTest.java",
                  Optional.of("testArithTest_mutantKilled"));
             float score = m.mutate();
             Assert.assertEquals(100.0, score, 0.001);
         }
    - User only needs to be aware of the input arguments to the Mutator class which is the driver of the framework.
    - User provides the mutation operator (ARITH/LOGIC), the source code file path and the junit file path as constructor arguments. Remainder of the test can be copied over from the above snippet and is logical to follow.
    - **Stretch Goals**
        - A driver program which accepts command line arguments is preferred over integrating with the framework via a unit test.
        - Source code allows only 1 mutation operation per run and doesn’t involve mixing multiple operators.

## Submitting a bug report
1.	Go to https://github.com/mariak22/mut-testing
2.	Click on Issues -> New Issue
3.	Click on Get Started on the Bug report template on the screen
4.	This opens a new page with a predefined template for additional context for the developers to further investigate and resolve the issue
5.	Click on Submit new issue
6.	For feature/enhancement requests, follow same steps until #2 and click on “Open a blank issue” instead of a Bug report.

## Known Bugs/System Limitations
1.	2 bugs are currently documents under Issues - https://github.com/mariak22/mut-testing/issues
2.	User are recommended to name their src code file being mutated as MutationTester.java and their corresponding test file to be MutationTesterTest.java

# Developer Documentation
The developer documentation should include at least the following information:

## Obtaining source code
1.	Go to https://github.com/mariak22/mut-testing
2.	Click on “Code” -> SSH
3.	Copy the SSH command
a.	If you need a public github ssh key, add it here - https://github.com/settings/ssh/new
4.	Open Intellij and click on “Get from VCS” and add the link copied in #3 in here
5.	Click Next and follow the prompts till you get to the project.
6.	Dependencies outlined in the user documentation above will need to be added in order to successfully build the project.

## Directory Layout
1.	The source files are present in mut-testing/src
a.	Contains the mutation testing framework source and also the user provided mutation testing src class
2.	The test files are present in mut-testing/test
a.	Contains the mutation testing framework junit tests and also the user provided junit test for their src code being mutated through the framework
3.	The test configuration files for MutationAnalyzer are present in test/mutation_report_files
4.	The documentation file is present in mut-testing/README.md

## Building the software
The project uses ANT build system so you will find build.xml file in the root directory of the project. For more information on ANT please follow this link - https://www.tutorialspoint.com/ant/index.htm. If using an IntelliJ IDE, the project can be built by clicking Build -> Build Project or directly typing the command ANT in a terminal. The build.xml defines a “MainTarget” which compiles all files under the src directory.

## Testing the software
Performing an end to end to test lighting up all the major pieces in mutation testing framework can be achieved by adding junit tests (no mocking necessary) in MutatorTest.java. User will add the source file to be mutated under src/ directory and the corresponding test file under test/ directory. Users have the ability to test specific junit tests of their code on their mutated source code output by passing the optional argument in the Mutator object instantiation. Detailed steps are provided under Integrating with the framework in User Documentation.

## Adding new tests
1.	Click on MutatorTest.java
2.	Add a new junit test to test the mutation testing framework end to end
3.	Copy similar style provided in the same file for other logical mutation operators
4.	Src file to be mutated is to be placed under src/MutationTester.java
5.	Test file to be tested on mutated code is to be placed under test/MutationTesterTest.java
6.	Run via IDE or via ANT command

## Building a release of the software
The framework is currently not hosted in a web services framework. Eventually I can see this being hosted in AWS as a simple lambda function where users can make requests to an endpoint. Currently, users will need to perform the following steps:
1.	Make changes to the source code
2.	Run all junit tests
3.	Run ant on command line to perform a full successful build
4.	Create a branch and send it for peer review
5.	Once approved, merge it to main after CI pipeline succeeds
