package day03_sink

import day01_source.{MySensorSource, SensorReading}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}
import org.apache.flink.api.scala._

object Test01_redis {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val custom = env.addSource(new MySensorSource)

    custom.print("redis")
    val conf = new FlinkJedisPoolConfig.Builder().setHost("127.0.0.1").setPort(6379).build()
    custom.addSink(new RedisSink[SensorReading](conf, new MyRedisMapper))
    env.execute("redis")
  }

}

class MyRedisMapper extends RedisMapper[SensorReading] {
  //定义redis命令
  override def getCommandDescription: RedisCommandDescription = {
    new RedisCommandDescription(RedisCommand.HSET, "sensor_temperature")
  }

  override def getKeyFromData(data: SensorReading): String = data.id

  override def getValueFromData(data: SensorReading): String = data.temperature.toString
}