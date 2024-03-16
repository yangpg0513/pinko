package Utils

import org.apache.spark.sql.SparkSession

object StringUtil {
  def replaceHql(hql: String, date: String,hour: String,spark: SparkSession) : String = {
    var hqlText = hql
    hqlText = replacePartitions(hqlText, date)
    return hqlText
  }

  def replacePartitions(hql: String, date: String) : String = {
    return hql.replaceAll("$date",date)
  }

}
