package Utils

import org.apache.calcite.rel.core.Collect
import org.apache.spark.sql.SparkSession

object UDFRegister {

  def register(spark: SparkSession): Unit = {
    spark.udf.register("arrayDistint", CollectFunctions.arrayDistint _)
  }
}
