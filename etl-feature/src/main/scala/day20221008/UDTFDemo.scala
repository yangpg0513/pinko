package day20221008
import java.util
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory
import org.apache.hadoop.hive.serde2.objectinspector.{ObjectInspector, ObjectInspectorFactory, StructObjectInspector}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object UDTFDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("UDFFunction2")
    val sc = SparkContext.getOrCreate(conf)
    val spark = SparkSession.builder().master("local[*]").appName("utd")
      .getOrCreate()

    val info = sc.textFile("D:\\workspace\\pinko\\etl-feature\\src\\main\\scala\\day20221008\\udtf.txt")
    import spark.implicits._

    val infoDF = info.map(x => x.split("//")).filter(x => x(1).equals("ls"))
      .map(x => (x(0), x(1), x(2))).toDF("id","name","class")
    infoDF.createOrReplaceTempView("table_udtf")
    //    infoDF.printSchema()
    //    infoDF.show()
    //    spark.sql("select * from table_udtf").show(false)

    spark.sql("create temporary function myudtf as 'udf.myUDTF'") //创建临时方法
    spark.sql("select myudtf(class) from table_udtf").show()      //使用自定义方法
  }
}
class myUDTF extends GenericUDTF{

  //初始化
  override def initialize(argOIs: Array[ObjectInspector]): StructObjectInspector = {
    val fieldName = new util.ArrayList[String]()
    val fieldOIS = new util.ArrayList[ObjectInspector]()
    //定义输出数据类型
    fieldName.add("type")
    fieldOIS.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector)   //String类型
    ObjectInspectorFactory.getStandardStructObjectInspector(fieldName,fieldOIS)
  }

  //处理
  //传入 Hadoop scala spark hive hbase
  /*输出 head  type String
               Hadoop
               scala
               spark
               hive
               hbase
  */
  override def process(objects: Array[AnyRef]): Unit = {
    val strs = objects(0).toString.split(" ")  //将字符串切割为单个字符，形成字符数组
    for( str<- strs){
      val temp = new Array[String](1)
      temp(0) = str
      forward(temp)
    }
  }

  //关闭资源
  override def close(): Unit = {}
}