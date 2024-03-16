package day02_trans

import day01_source.MySensorSource
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

object Test02_split {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val custom = env.addSource(new MySensorSource)

    val splitStream = custom.split(sensor =>
      if (sensor.temperature > 30) Seq("high") else Seq("low")
    )
    val high = splitStream.select("high")
    val low = splitStream.select("low")
//    high.print("high").setParallelism(1)
//    low.print("low").setParallelism(1)
//    val connStream = high.connect(low)
//    val result = connStream.map(x => (x, "1"), y => (y, "2"))
    val result = high.union(low)
    result.print("conn").setParallelism(1)
    env.execute("split")
  }

}
