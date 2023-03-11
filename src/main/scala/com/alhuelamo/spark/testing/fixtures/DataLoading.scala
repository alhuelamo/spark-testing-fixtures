package com.alhuelamo.spark.testing.fixtures

import org.apache.spark.sql.{Dataset, SparkSession}

trait DataLoading[A <: Product] {
  def load(location: String)(implicit spark: SparkSession): Dataset[A]
}

object DataLoading {

  implicit def csv[A <: Product]: DataLoading[A] = new DataLoading[A] {

    override def load(location: String)(implicit spark: SparkSession): Dataset[A] = {
      import spark.implicits._
      spark.read.csv(location).as[A]
    }

  }

  implicit def parquet[A <: Product]: DataLoading[A] = new DataLoading[A] {

    override def load(location: String)(implicit spark: SparkSession): Dataset[A] = {
      import spark.implicits._
      spark.read.parquet(location).as[A]
    }

  }

}
