import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

object Test01 {
  def main(args: Array[String]): Unit = {
    println(countDown(100))
  }
  //求n。。0的总和
  def countDown(n: Int): Int = {
    var result: Int = 0
    if (n == 0) {
      0
    } else {
      n + countDown(n - 1)
    }
  }
  def sum(args: Int*) = {
    var r = 0;
    for (arg <- args) {
      r += arg
    }
    r
  }


}
