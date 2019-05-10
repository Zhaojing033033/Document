package com.atguigu.mapreduce.distributedcache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DistributedMapper extends Mapper<LongWritable, Text, Text, NullWritable>{
	// ����pd.txt����
	private Map<String, String> pdMap = new HashMap<>();
	
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		// ��ȡpd.txt�ļ�,�������ݴ洢�����棨���ϣ�
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("pd.txt"))));
		
		String line ;
		//01	С��
		while (StringUtils.isNotEmpty(line = reader.readLine())) {
			// ��ȡ
			String[] fields = line.split("\t");
			
			// �洢���ݵ�����
			pdMap.put(fields[0], fields[1]);
		}
		
		// �ر���Դ
		reader.close();
	}
	
	Text k = new Text();
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// ����Ҫ�ϲ�pd.txt��order.txt���������
		
		// 1 ��ȡһ�� 1001		01	4  pdName
		String line = value.toString();
		
		// 2 ��ȡ 1001		01	4
		String[] fields = line.split("\t");
		
		// 3 ��ȡpdname
		String pdName = pdMap.get(fields[1]);
		
		// 4 ƴ��  1001		01	4  pdName
		k.set(line + "\t" + pdName);
		
		// 5 д��
		context.write(k, NullWritable.get());
	}

}
