package core

import Utils.{FileUtil, StringUtil, UDFRegister}
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession

object RunHqlFile {
  lazy val logger = Logger.getLogger(this.getClass)
  def main(args: Array[String]): Unit = {
    logger.info("show parameters: " + args.mkString(","))
    if (args.size < 2) {
      logger.error("Usage:RunHqlFile file date hour")
      System.exit(1)
    }
    val file = args(0)
    val date = args(1)
    val hour = args(2)
    val spark = SparkSession.builder().appName("feature-etl-service").master("local[2]").getOrCreate()
    UDFRegister.register(spark)

    val hqlText = StringUtil.replaceHql(FileUtil.read(file),date,hour,spark)
    logger.info("show hql text:\n"+hqlText)
    hqlText.split(";\n").filterNot(text=>text.trim.isEmpty).foreach(spark.sql(_))
    logger.info("job finished,stop spark session")
    spark.stop()

  }

}
