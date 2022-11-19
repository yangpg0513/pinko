package day20221017

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DataTypes, IntegerType, StructField, StructType}
import org.apache.spark.{SparkConf, SparkContext}

object TestRDD {
  def main(args: Array[String]): Unit = {
    val ints = List(1, 2, 3, 4)
    val conf = new SparkConf().setMaster("local[*]").setAppName("")
    val sc = new SparkContext(conf)
    val rdd1: RDD[Int] = sc.makeRDD(ints)
//    rdd1.foreach(println)
    val spark = SparkSession.builder().config(conf).getOrCreate()
    val scama = StructType(Array(StructField("id", DataTypes.IntegerType)))
    val value = rdd1.map(x => Row(x))
    val frame = spark.createDataFrame(value, scama)
    frame.show()
    frame.createTempView("user")
    val frame1 = spark.sql("select * from user")

  }

}
