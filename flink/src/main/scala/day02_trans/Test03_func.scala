package day02_trans

import org.apache.flink.api.common.functions.{FilterFunction, MapFunction}
import org.apache.flink.shaded.netty4.io.netty.handler.codec.http2.Http2Exception.StreamException
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

object Test03_func {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val ints = env.fromCollection(List(1, 2, 3, 4, 5))

//    val result = ints.map(new MyMapFunc)
//    val result = ints.filter(new FilterFunction[Int] {
//      override def filter(t: Int): Boolean = t % 2 == 0
//    })
    val result = ints.filter(t => t % 2 == 0)
    result.print("map")
    env.execute("map")
  }

}

class MyMapFunc extends MapFunction[Int, Int] {
  override def map(t: Int): Int = t * 2
}
