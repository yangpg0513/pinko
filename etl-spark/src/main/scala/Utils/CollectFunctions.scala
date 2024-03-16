package Utils

import scala.collection.mutable

object CollectFunctions {
  def arrayDistint(array: Seq[String]): Seq[String] = {
    val arrayResult = mutable.LinkedHashSet[String]()
    if(arrayResult==null) {
      return array
    }
    for (i<-0 until array.length){
      arrayResult.add(array.apply(i))
    }
    return arrayResult.toSeq
  }
}
