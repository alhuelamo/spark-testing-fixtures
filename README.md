# spark-testing-fixtures

Preparing data for testing Spark applications can be a tedious process. Reusing fixtures that deal with the testing data loading and cleaning-up the environment after its usage can make testing code less cluttered.

This toy project is an example of how a couple of type classes can set the basis of a Spark-oriented fixture framework.

The type class `Fixture` provides a composable abstraction by which one can read multiple data sources in a test, ensuring that resources created for the test are going to be teared-down. As is the case of the `WithMetastoreDB` fixture.

The type class `DataLoading` abstracts how, and by which format the data is read by Spark in fixtures.

The `ExampleSpec` file contains a usage example.

