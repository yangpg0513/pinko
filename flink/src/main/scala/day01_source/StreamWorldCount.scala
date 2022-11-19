package day01_source

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

object StreamWorldCount {
  def main(args: Array[String]): Unit = {
    //流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // 从文件读入数据
    val text = env.readTextFile("D:\\workspace\\pinko\\flink\\src\\main\\resources\\2.txt")
    val result = text.flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(0)
      .sum(1)
    result.print().setParallelism(1)
    //启动任务
    env.execute("StreamWordCount")
  }

}
