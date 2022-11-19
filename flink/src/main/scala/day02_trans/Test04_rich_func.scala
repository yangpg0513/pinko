package day02_trans

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

object Test04_rich_func {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val strs = env.fromCollection(List("hello world", "spark flink"))
    val result = strs.map(new MyRichMapFunc()).setParallelism(1)
    result.print("result").setParallelism(1)
    env.execute("rich")

  }

}

class MyRichMapFunc extends RichMapFunction[String, String] {
  override def map(value: String): String = value.split(" ").toList.toString()

  override def open(parameters: Configuration): Unit = {
    println("开始：")
  }

  override def close(): Unit = {
    println("关闭：")
  }
}
