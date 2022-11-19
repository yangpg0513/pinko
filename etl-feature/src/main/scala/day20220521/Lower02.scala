package day20220521

import org.apache.hadoop.hive.ql.exec.UDFArgumentException
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory

class Lower02 extends GenericUDF {
  override def initialize(arguments: Array[ObjectInspector]): ObjectInspector = {
    if (arguments.length != 1) {
      throw new UDFArgumentException("输入参数长度异常, 只允许输入一个参数")
    }
    return PrimitiveObjectInspectorFactory.javaStringObjectInspector
  }

  override def evaluate(arguments: Array[GenericUDF.DeferredObject]): AnyRef = ???

  override def getDisplayString(children: Array[String]): String = ???
}
