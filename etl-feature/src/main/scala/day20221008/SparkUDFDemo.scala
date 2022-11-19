package day20221008

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SparkSession


/*
* 自定义函数 ：UDF : 一进一出
*
* 统计每个人的兴趣爱好个数
*
* */

case class Hobbies(name:String,hobby:String)

object SparkUDFDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UDFFunction2")
    val sc = getOrCreate(conf)
    val spark = SparkSession.builder().config(conf).getOrCreate()

    val info = sc.textFile("D:\\workspace\\pinko\\etl-feature\\src\\main\\scala\\day20221008\\hobbies.txt")
    import spark.implicits._
    val hobbyDF = info.map(_.split(" ")).map(x=>Hobbies(x(0),x(1))).toDF
    hobbyDF.show()

    hobbyDF.createOrReplaceTempView("hob")

    //注册自定义函数
    spark.udf.register("hob_num",(s:String)=>s.split(",").size) //第一个参数：函数名，第二个：自定义函数的实现方式
    spark.sql("select name,hobby,hob_num(hobby) as hobby_number from hob").show(false)  //truncate（缩短）: false:全部显示
  }
}