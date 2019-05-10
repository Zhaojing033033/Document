package com.atguigu.mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class WordCountPartitioner extends Partitioner<Text, IntWritable>{

	// hadoop 
	@Override
	public int getPartition(Text key, IntWritable value, int numPartitions) {
		// ���󣺰��յ�������ĸ��ASCII����ż����
		String line = key.toString();
		
		// 1 ��ȡ����ĸ
		String firword = line.substring(0, 1);
		
		// 2 ת����ASCII
		char[] charArray = firword.toCharArray();
		
		int result = charArray[0];
		
		// 3 ������ż����
		if (result % 2 ==0 ) {
			return 0;
		}else {
			return 1;
		}
	}
}
