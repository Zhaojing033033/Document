package com.atguigu.mapreduce.Table;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TableReduce extends Reducer<Text, TableBean, TableBean, NullWritable> {

	@Override
	protected void reduce(Text key, Iterable<TableBean> values, Context context)
			throws IOException, InterruptedException {
		
		// 0 ׼���洢���ݵĻ���
		TableBean pdbean = new TableBean();
		ArrayList<TableBean> orderBeans = new ArrayList<>();

		// �����ļ��Ĳ�ͬ�ֱ�������

		for (TableBean bean : values) {

			if ("0".equals(bean.getFlag())) {// ���������ݴ���
				// 1001 1
				// 1001 1
				TableBean orBean = new TableBean();

				try {
					BeanUtils.copyProperties(orBean, bean);
				} catch (Exception e) {
					e.printStackTrace();
				}

				orderBeans.add(orBean);
//				orderBeans.add(bean);

			} else {// ��Ʒ���� 01 С��
				try {
					BeanUtils.copyProperties(pdbean, bean);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// ����ƴ��
		for (TableBean bean : orderBeans) {
			// ���²�Ʒ�����ֶ�
			bean.setPname(pdbean.getPname());
			
			// д��
			context.write(bean, NullWritable.get());
		}
	}
}
