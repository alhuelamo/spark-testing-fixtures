package com.alhuelamo.spark.testing.fixtures

import org.apache.spark.sql.{Dataset, SparkSession}

trait Fixture[A] {

  def test[B](testCode: A => B): B

  def map[B](f: A => B): Fixture[B] =
    flatMap(a =>
      new Fixture[B] {
        override def test[C](testCode: B => C) = testCode(f(a))
      },
    )

  def flatMap[B](f: A => Fixture[B]): Fixture[B] =
    test(f)

}

object Fixture {

  class WithMetastoreDB(name: String)(implicit spark: SparkSession) extends Fixture[String] {

    override def test[B](testCode: String => B): B = {
      spark.sql(s"CREATE DATABASE IF NOT EXIST $name;")
      try {
        testCode(name)
      } finally spark.sql(s"DROP DATABASE IF EXISTS $name;")
    }

  }

  class WithMetastoreTable[D <: Product](name: String, dataPath: String)(implicit spark: SparkSession, loading: DataLoading[D])
      extends Fixture[String] {

    override def test[B](testCode: String => B): B = {
      val dataset = loading.load(dataPath)
      dataset.createOrReplaceTempView(name)
      try {
        testCode(name)
      }
    }

  }

  class WithDataset[D <: Product](dataPath: String)(implicit spark: SparkSession, loading: DataLoading[D]) extends Fixture[Dataset[D]] {

    override def test[B](testCode: Dataset[D] => B): B = {
      val dataset = loading.load(dataPath)
      try {
        testCode(dataset)
      }
    }

  }

}
