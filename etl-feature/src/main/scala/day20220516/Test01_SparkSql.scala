package day20220516

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

object Test01_SparkSql {
  def main(args: Array[String]): Unit = {
    //TODO:1.创建SparkSession
    val spark: SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName(this.getClass.getName)
      .getOrCreate()
    //通过SparkSession创建SparkContext
    val sc: SparkContext = spark.sparkContext
    
    //TODO:3.创建RDD
    //学生表
    val studentRDD: RDD[Array[String]] = sc.textFile("C:\\Users\\PINKO\\Desktop\\转项\\sparksql_project\\student.txt").map(_.split("\\s+"))
    //课程表
    val courseRDD: RDD[Array[String]] = sc.textFile("C:\\Users\\PINKO\\Desktop\\转项\\sparksql_project\\course.txt").map(_.split("\\s+"))
    //教师表
    val teacherRDD: RDD[Array[String]] = sc.textFile("C:\\Users\\PINKO\\Desktop\\转项\\sparksql_project\\teacher.txt").map(_.split("\\s+"))
    //成绩表
    val scoreRDD: RDD[Array[String]] = sc.textFile("C:\\Users\\PINKO\\Desktop\\转项\\sparksql_project\\score.txt").map(_.split("\\s+"))
    //TODO:4.转成Row类型的RDD
    val studentRowRDD: RDD[Student] = studentRDD.map(x => Student(x(0).toInt, x(1), x(2), x(3)))
    val courseRowRDD: RDD[Course] = courseRDD.map(x => Course(x(0).toInt, x(1), x(2).toInt))
    val teacherRowRDD: RDD[Teacher] = teacherRDD.map(x => Teacher(x(0).toInt, x(1)))
    val scoreRowRDD: RDD[Score] = scoreRDD.map(x => Score(x(0).toInt, x(1).toInt, x(2).toInt))
    import spark.implicits._
    //TODO:5.创建DataFrame
    val studentDF: DataFrame = studentRowRDD.toDF()
    val courseDF: DataFrame = courseRowRDD.toDF()
    val teacherDF: DataFrame = teacherRowRDD.toDF()
    val scoreDF: DataFrame = scoreRowRDD.toDF()

    //TODO:6.练习题
    //    (1)查询"01"课程比"02"课程成绩高的学生的信息及课程分数:
    scoreDF.as("s1")
      .join(scoreDF.as("s2"),"student_id")
      .filter("s1.course_id=1 and s2.course_id=2 and s1.score>s2.score ")
      .join(studentDF,"student_id").show()

  }



}

//学生表
case class Student(student_id: Int, student_name: String, birth: String, sex: String)
//课程表
case class Course(course_id: Int, course_name: String, teacher_id: Int)
//教师表
case class Teacher(teacher_id: Int, teacher_name: String)
//成绩表
case class Score(student_id: Int, course_id: Int, score: Int)
