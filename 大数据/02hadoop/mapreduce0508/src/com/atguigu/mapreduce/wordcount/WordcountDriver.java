package com.atguigu.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// ����������
public class WordcountDriver {

	public static void main(String[] args) throws Exception {

		// 1 ��ȡjob������Ϣ
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);

		// 2 ���ü���jarλ��
		job.setJarByClass(WordcountDriver.class);

		// 3 ����mapper��reducer��class��
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);

		// 4 �������mapper����������
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// 5 ���������������������
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// ����С�ļ�
		job.setInputFormatClass(CombineTextInputFormat.class);
		CombineTextInputFormat.setMaxInputSplitSize(job, 4194304);
		CombineTextInputFormat.setMinInputSplitSize(job, 2097152);
		
		// 6 �����������ݺ��������·��
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		// 8 ��ӷ���
		job.setPartitionerClass(WordCountPartitioner.class);
		job.setNumReduceTasks(2);
		
		// 9 ����Combiner
//		job.setCombinerClass(WordCountCombiner.class);
		
		// 7 �ύ
		boolean result = job.waitForCompletion(true);

		System.exit(result ? 0 : 1);
	}
}
