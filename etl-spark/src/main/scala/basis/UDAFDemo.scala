package basis

import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/*
*  自定义聚合函数 --- 多进一出
*
*  实现 Avg 函数
* */

case class StudentS(id:Integer,name:String,gender:String,age:Integer)

object UDAFDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UDAF")
    val sc = SparkContext.getOrCreate(conf)
    val spark = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    //数据
    val students = Seq(
      StudentS(1, "zhangsan", "F", 20),
      StudentS(2, "lisi", "M", 24),
      StudentS(3, "wangwu", "M", 23),
      StudentS(4, "zhaoliu", "F", 21),
      StudentS(5, "qianqi", "F", 45),
      StudentS(6, "sunba", "F", 39),
      StudentS(7, "zhoujiu", "F", 43),
      StudentS(8, "wuhuang", "M", 29),
      StudentS(9, "zhengtan", "F", 24),
      StudentS(10, "xujiao", "M", 22),
      StudentS(11, "hanxing", "F", 24)
    )

    val frame = students.toDF()
    frame.createOrReplaceTempView("student")
    val myudaf = new MyAgeAvgFunction
    spark.udf.register("myavg",myudaf)
    spark.sql("select gender,myavg(age) as avgAge from student group by gender").show()
  }
}
class MyAgeAvgFunction extends UserDefinedAggregateFunction{   //自定义函数
  //输入数据的数据类型
  override def inputSchema: StructType = {
    new StructType().add("age",LongType)
    //    StructType(StructField("age","LongType")) :: Nil
  }
  //缓存区内数据结构
  override def bufferSchema: StructType = {
    new StructType().add("sum",LongType).add("count",LongType)
  }
  //聚合函数返回的数据结构
  override def dataType: DataType = DoubleType

  //聚合函数 相同的输入是否要相同的输出，聚合函数是否幂等操作
  override def deterministic: Boolean = true

  //数据初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }

  //传入一条新的数据后需要进行的操作
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = buffer.getLong(0)+input.getLong(0)   //年龄累加
    buffer(1) = buffer.getLong(1)+1                  //个数+1
  }

  //合并各分区内的数据
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0)+buffer2.getLong(0)  //不同分组内的年龄相加
    buffer1(1) = buffer1.getLong(1)+buffer2.getLong(1)  //不同分组内的个数相加
  }

  //计算最终结果
  override def evaluate(buffer: Row): Any = {
    (buffer.getLong(0)/buffer.getLong(1)).toDouble      // 年龄总和/个数
  }
}