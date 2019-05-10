package com

object Hell {


  //函数声明：传入两个参数为Int，返回 一个Int
  def func1:(Int,Int) => Int  = (a,b) =>{
    a+b
  }
  //函数声明：传入两个参数为Int，返回？？
  def func2 = (a:Int,b:Int) => {
    a+b
  }

  //方法声明：传入两个Int，返回一个Int
  def method(a: Int, b: Int) = {
    a + b
  }
  //方法声明：传入 一个方法，没有参数，返回一个Int
  def method1(x: => Int)={
    x
  }
  //方法声明：传入一个参数为两个Int的方法,返回值为Int
  def method12( x:(Int,Int) => Int) ={

  }


  def add(a:Int=5,b:Int=5)={
    a+b
  }


  def main(args: Array[String]): Unit = {

     print(add(1))
    val i = method(1, 2)
    //print(i)
  }
}
