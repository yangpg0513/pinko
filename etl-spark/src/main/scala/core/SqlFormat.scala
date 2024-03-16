package core

import java.io.{BufferedWriter, FileInputStream, FileWriter}

object SqlFormat {
  def main(args: Array[String]): Unit = {
    file("etl-spark/src/main/resources/select.sql")
//    val hql = "select * from a"
//
//    println(replaceHql(hql, "select"))
  }


  def read(fileName: String): String = {
    val lines =scala.io.Source.fromInputStream(new FileInputStream(fileName),"utf-8").getLines
    val sb = new StringBuilder()
    lines.foreach(sb.append(_).append("\n"))
    return sb.toString
  }

  def replaceHql(hql:String, key:String):String = {
    return hql.replaceAll("\\b" + key + "\\b",key.toUpperCase)
  }

  def file(fileName: String): Unit = {
    var hql=read(fileName)
    val keyWord =read("etl-spark/src/main/resources/keyword.txt")
    val keyArr=keyWord.split(",")
    for(w <- keyArr) {
      hql =replaceHql(hql,w)
    }
//    println(hql)

    val bufferWriter =new BufferedWriter(new FileWriter(fileName))
    bufferWriter.write(hql)
    bufferWriter.close
  }

}
