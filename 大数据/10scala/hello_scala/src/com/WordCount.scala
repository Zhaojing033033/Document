package com

object WordCount {
  def main(args: Array[String]): Unit = {
    val array: Array[String] = Array[String]("hello tom","hello zj","hello world")
    val array1 = array.flatMap(_.split(" ")).groupBy(x => x).mapValues(x => x.length).toList.sortBy(_._2)
    array.flatMap(x => x.split(" ")).groupBy(x => x).mapValues(x => x.length).toList.sortBy(x =>x._2)
  }

}
