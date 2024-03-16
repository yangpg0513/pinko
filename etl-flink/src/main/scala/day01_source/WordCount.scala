package day01_source

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

object WordCount {
  def main(args: Array[String]): Unit = {
    //创建批处理执行环境
    val env = ExecutionEnvironment.getExecutionEnvironment
    //从文件读取数据
    val text = env.readTextFile("D:\\workspace\\pinko\\flink\\src\\main\\resources\\2.txt")
    val result = text.flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)
    result.print()
  }


}
