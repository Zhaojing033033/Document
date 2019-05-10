package com.atguigu.mapreduce.Table;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean>{
	TableBean bean = new TableBean();
	Text k = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
//		1 ��ȡ�����ļ�����
		FileSplit inputSplit = (FileSplit) context.getInputSplit();
		String name = inputSplit.getPath().getName();
		
//		2 ��ȡ��������
		String line = value.toString();
		
//		3 ��ͬ�ļ��ֱ���
		if (name.startsWith("order")) {// ���������Ϣ����
			// �и�
			String[] fields = line.split("\t");
			
			// ��װbean���� 1001	01	1
			bean.setOrder_id(fields[0]);
			bean.setP_id(fields[1]);
			bean.setAmount(Integer.parseInt(fields[2]));
			
			bean.setPname("");
			bean.setFlag("0");
			
			// ����keyֵ
			k.set(fields[1]);
			
		}else {// ��Ʒ����Ϣ����     01	С��

			// �и�
			String[] fields = line.split("\t");
			
			// ��װbean����
			bean.setOrder_id("");
			bean.setP_id(fields[0]);
			bean.setAmount(0);
			bean.setPname(fields[1]);
			bean.setFlag("1");
			
			// ����keyֵ
			k.set(fields[0]);
		}
		
//		4 ��װbean�������
		context.write(k, bean);
	}

}
