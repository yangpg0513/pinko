package day20220422

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Test03_FromArray {
  def main(args: Array[String]): Unit = {
    val data = Array(1, 2, 3, 4, 5)
    val conf = new SparkConf().setAppName("").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val rdd1 = sc.makeRDD(data, 2)
    val rdd2 = rdd1.map(addOne)
    rdd2.checkpoint()
    val rdd = rdd2.cache()

    rdd2.foreach(println)
  }

  def addOne(num: Int): Int = num + 1

}
