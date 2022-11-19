package day01_source

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

object SocketWordCount {
  def main(args: Array[String]): Unit = {
    //从外部命令获取参数
    val para = ParameterTool.fromArgs(args)
    val host = para.get("host")
    val port = para.get("port")

    //流处理环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //接收socket文本流
    val socket = env.socketTextStream(host, port.toInt)
//    val socket = env.socketTextStream("DESKTOP-E33G0RS", 7777)
    val result = socket.flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(0)
      .sum(1)
    result.print().setParallelism(1)
    //启动任务
    env.execute("socketWordCount")
  }

}
