package com.zj.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.junit.Before;
import org.junit.Test;

public class HbaseClientDDLDemo {
	Connection conn;
	@Before
	public void getConn() throws IOException{
		Configuration configuration = HBaseConfiguration.create();//���Զ�����hbase-site.xml
		//hbase�ͻ���ֻ��Ҫ��zookeeperͨ�ţ�ָ��zookeeper�ļ�Ⱥ����Ҫ�����б������hostsӳ�䣬�������С�����Call Excepion 
		configuration.set("hbase.zookeeper.quorum","192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181");
	    
		conn = ConnectionFactory.createConnection(configuration);
	}
	
	/*************** DDL ************************/
	@Test
	public void createTable() throws IOException{
		//����һ��ddl������
		Admin admin = conn.getAdmin();
		//������������
		HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("user_info"));
		//������������
		HColumnDescriptor hColumnDescriptor1 = new HColumnDescriptor("base_info");
		hColumnDescriptor1.setMaxVersions(3);
		
		HColumnDescriptor hColumnDescriptor2 = new HColumnDescriptor("extra_info");
		//�����������ӵ��ж�����
		hTableDescriptor.addFamily(hColumnDescriptor1);
		hTableDescriptor.addFamily(hColumnDescriptor2);
		//��ddl������
		admin.createTable(hTableDescriptor);
		admin.close();
		conn.close();
	}
	@Test
	public void deleteTable() throws IOException{
		Admin admin = conn.getAdmin();
		//ɾ��ǰ����Ҫ��ͣ�ñ����򱨴�
		admin.disableTable(TableName.valueOf("user_info"));
		//ɾ��
		admin.deleteTable(TableName.valueOf("user_info"));
		admin.close();
		conn.close();
	}
	@Test
	public void modifyTable() throws IOException{
		Admin admin = conn.getAdmin();
		//ȡ���ɱ�ı���
		HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf("user_info"));
		//�¹���һ��һ�����嶨��
		HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("other_info");
		hColumnDescriptor.setBloomFilterType(BloomType.ROW);//���ò�¡������������Ϣ����ժҪ���Ż���ѯ��д��Ч�ʻ����
		//�����嶨����ӵ����������
		tableDescriptor.addFamily(hColumnDescriptor);
		//�޸ĵĹ��ı��� ���� adminȥ�ύ��
		admin.modifyTable(TableName.valueOf("user_info"), tableDescriptor);
		
		conn.close();
		admin.close();
		
	}
	/*************** DML ************************/
	
}
