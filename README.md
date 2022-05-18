The code contains implementation of the mutation testing framework. Specifically the implementation has two main components:
1. Mutator
2. MutationAnalyzer

MutationAnalyzer is responsible for scoring the output of a mutation test which is a consequence of being processed in the Mutator component. The MutationAnalyzer 
has been fully implemented along with Junit success and failure tests. However, the Mutator components class has only been stubbed with appropriate class documentation.

Use cases that are operational:
1. Parse malformed/valid mutation testing result file
2. Calculate score of a mutation testing result file
3. Junit tests for MutationAnalyzer

How to build and test:
1. Install ant build system
2. Running ant from the top level directory of the project will build the java project and also run the unit tests
3. The CI pipeline is configured to trigger all unit tests on pushing a commit to the remote repository
