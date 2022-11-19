package day20220422

import org.apache.spark.{SparkConf, SparkContext}

object Test02_WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val fileName = "C:\\Users\\PINKO\\Desktop\\面试\\code\\a.txt"
    val rdd1 = sc.textFile(fileName)
    val flatRdd = rdd1.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
//    val cnt = flatRdd.filter(x => x._1.contains("scala")).count()
//    println(cnt)
    flatRdd.cache()
    println(flatRdd.count())
  }

}
