import scala.collection.mutable

object TestDistStr {
  def main(args: Array[String]): Unit = {
    val str = "A2,A1,A2,A3,A1"
    val arr = str.split(",")
//    println(arr.distinct.mkString(","))
//    val set = new mutable.HashSet[String]()
    val linkSet = new mutable.LinkedHashSet[String]()
    for (a <- arr) {
      linkSet.add(a)
    }
    println(linkSet)

  }

}
