package day05_watermark

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.windowing.time.Time

object Test01_Water {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //设置事件时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    val soc = env.socketTextStream("DESKTOP-E33G0RS", 8888)
    // 源数据map成二元组 （id，ts）
    val mapStream = soc.map(
      t => {
        var data = t.split(" ")
        (data(0), data(1).toLong)
      })
    //增加水位线
    val waterStream = mapStream.assignTimestampsAndWatermarks(
      new BoundedOutOfOrdernessTimestampExtractor[(String, Long)](Time.seconds(1)) {
      override def extractTimestamp(t: (String, Long)): Long = t._2
    })
    val result = waterStream.keyBy(0)
        .timeWindow(Time.seconds(10))
      .allowedLateness(Time.seconds(1))
        .sum(1)
//    mapStream.print("map")
//    waterStream.print()
    result.print("result").setParallelism(1)
    env.execute("map")
  }
}
