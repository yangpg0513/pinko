package day20220516

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Test02_Rdd {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("")
    val sc = new SparkContext(conf)
    val value2: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4, 5))
    val value: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4, 5))
    val value3: RDD[Int] = sc.makeRDD(Array(1, 2, 3, 4, 5))
    println(value2.id)
    println(value.id)
    println(value3.id)
  }

}
