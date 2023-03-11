package com.alhuelamo.spark.testing.fixtures

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.apache.spark.sql.{Dataset, SparkSession}

class ExampleSpec extends AnyFlatSpec with Matchers {

  implicit val spark: SparkSession = ???
  val dbName = "test_metastore"
  val tableName = "my_table"
  val dataPath = getClass.getResource("/asfafasdf").getPath

  case class MyRow(name: String, age: Int)

  val sut: Dataset[MyRow] = {
    // reads `tableName`
    ???
  }

  it should "have a fixture available for a test metastore database" in {
    // Bringing a data loader to scope
    import DataLoading.csv

    for {
      db_name <- new Fixture.WithMetastoreDB(dbName)
      _ <- new Fixture.WithMetastoreTable[MyRow](s"$db_name.$tableName", dataPath)
      expected <- new Fixture.WithDataset[MyRow]("another path")
    } yield {
      val result = sut.collect()
      val expectedResult = expected.collect()

      result should contain theSameElementsAs expectedResult
    }
  }

}
