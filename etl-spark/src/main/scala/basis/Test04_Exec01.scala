package basis

import org.apache.spark.{SparkConf, SparkContext}

object Test04_Exec01 {
  val conf = new SparkConf().setMaster("local[*]").setAppName("")
  val sc = new SparkContext(conf)
  def main(args: Array[String]): Unit = {
    val arr = Array(1,1,5,7)
    println(fun01_Avg(arr))
    fun01_Most(arr).foreach(println)
  }
  // 求data的平均值
  def fun01_Avg(arr: Array[Int]): Int = {
    val rdd1 = sc.makeRDD(arr)
    val sum = rdd1.reduce(_ + _)
    val num = rdd1.count().toInt
    val result = sum/num
    result
  }

  //求data中出现次数最多的数
  def fun01_Most(arr: Array[Int]) = {
    val rdd1 = sc.makeRDD(arr)
    val rdd2 = rdd1.map((_, 1))
      .groupByKey()
      .map(i => ( i._2.size, i._1))
      .sortByKey(false)
      .take(1)
    rdd2
  }
}
