package basis

import org.apache.spark.sql.SparkSession

object Test01_SparkSql {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("").master("local[*]").getOrCreate()
    val fileName = "etl-feature/src/main/resources/user.txt"
    val df1 = spark.read.json(fileName)
    df1.show(false)
    spark.udf.register("toupper", Test02_Functions.toUpper _)
    df1.createTempView("user")
    val result = spark.sql(
      """
        |select toupper(name) as up_name from user
        |""".stripMargin)
    result.show(false)
    spark.stop()
  }

}
