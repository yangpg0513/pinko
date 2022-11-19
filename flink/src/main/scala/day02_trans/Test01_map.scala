package day02_trans

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

object Test01_map {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val ints = env.fromCollection(List(1, 2, 3, 4, 5, 1, 3, 3))
//    val result = ints.map(x => x * 2)
//    val result = ints.flatMap(it => List(it))
//    val result = ints.filter(x => x % 2 == 0)
//    val result = ints.map((_, 1)).keyBy(0).sum(1)
    val result = ints.map((_, 1)).keyBy(0).reduce((x, y) => (x._1, x._2 + y._2))
    result.print("result").setParallelism(1)
    env.execute("result")
  }

}
