package day01_source

import java.util.Random

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

//自定义数据源
object Test03_source_custom {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val custom = env.addSource(new MySensorSource)

    custom.print("custom").setParallelism(1)
    env.execute("custom")
  }

}

class MySensorSource extends SourceFunction[SensorReading] {
  //表示数据源是否正常运行
  var running = true
  //代码逻辑
  override def run(sourceContext: SourceFunction.SourceContext[SensorReading]): Unit = {
    //初始化一个随机数发生器
    val rand = new Random()
    var curTemp = 1.to(3).map(
      i => ("sensor_" + i, 65 + rand.nextGaussian() * 20))
    while (running) {
      //更新温度值
      curTemp = curTemp.map(i => (i._1, i._2 + rand.nextGaussian()))
      //获取当前时间戳
      val curTime = System.currentTimeMillis()
      curTemp.foreach(t => sourceContext.collect(
        SensorReading(t._1, curTime, t._2)))
      Thread.sleep(6000)
    }
  }

  //数据源停止
  override def cancel(): Unit = {
    running = false
  }
}