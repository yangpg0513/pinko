package day20221009

import java.io.FileInputStream

object FileUtil {
  def read(fileName: String): String = {
    val lines =scala.io.Source.fromInputStream(new FileInputStream(fileName),"utf-8").getLines
    val sb = new StringBuilder()
    lines.foreach(sb.append(_).append("\n"))
    return sb.toString
  }

}
