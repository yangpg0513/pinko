package day20220515

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Test01_Spark_Exec01 {
  val conf = new SparkConf().setMaster("local[*]").setAppName("")
  val sc = new SparkContext(conf)
  def main(args: Array[String]): Unit = {
    val arr = Array(1,1,5,7)
//    val rdd1 = sc.makeRDD(arr)
    println(fun01_Avg(arr))
    fun01_Most(arr).foreach(println)
  }
  //    任务：求data的平均值
  //    data = [1,5,7,10,23,20,6,5,10,7,10]
  def fun01_Avg(arr: Array[Int]): Int = {
    val rdd1 = sc.makeRDD(arr)
    val sum = rdd1.reduce(_ + _)
    val num = rdd1.count().toInt
    val result = sum/num
    result
  }

  //   任务：求data中出现次数最多的数，若有多个，求这些数的平均值
  def fun01_Most(arr: Array[Int]) = {
    val rdd1 = sc.makeRDD(arr)
    val rdd2 = rdd1.map((_, 1))
    val value = rdd2.groupByKey()
        .map(i => ( i._2.size, i._1)).sortByKey(false).take(1)


//      .map(item => item._1 -> item._2.toList.sortWith(_ > _).take(1))
//    val d = value.map(x => x._1).sum().toInt
    value
  }
}
