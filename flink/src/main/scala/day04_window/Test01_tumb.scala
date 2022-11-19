package day04_window

import day01_source.MySensorSource
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.WindowAssigner
import org.apache.flink.streaming.api.windowing.time.Time

object Test01_tumb {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val custom = env.addSource(new MySensorSource())
    val result =
//      custom.map(s => (s.id, s.temperature))
      custom.map(s => (s.id, 1))
      .keyBy(0)
      .timeWindow(Time.seconds(15)) //滚动窗口
//      .sum(1) //求和
//        .reduce((x, y) => (x._1, x._2.max(y._2))) //求最大值
          .reduce((x, y) => (x._1, x._2 + y._2))
    custom.print("custom").setParallelism(1)
    result.print("result").setParallelism(1)
    env.execute("result")
  }

}
