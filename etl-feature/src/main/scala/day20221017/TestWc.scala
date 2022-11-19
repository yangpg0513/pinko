package day20221017

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object TestWc {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TestWc").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val text: RDD[(String, Int)] = sc.textFile("D:\\workspace\\pinko\\etl-feature\\src\\main\\scala\\day20221017\\word.txt")
      .flatMap(_.split(" "))
      .map((_, 1))
      .reduceByKey(_ + _)
    text.foreach(println)
  }

}
