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
		Configuration configuration = HBaseConfiguration.create();//会自动加载hbase-site.xml
		//hbase客户端只需要和zookeeper通信，指定zookeeper的集群。需要在运行本机添加hosts映射，才能运行。否则报Call Excepion 
		configuration.set("hbase.zookeeper.quorum","192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181");
	    
		conn = ConnectionFactory.createConnection(configuration);
	}
	
	/*************** DDL ************************/
	@Test
	public void createTable() throws IOException{
		//构建一个ddl操作器
		Admin admin = conn.getAdmin();
		//表定义描述对象
		HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("user_info"));
		//列族描述对象
		HColumnDescriptor hColumnDescriptor1 = new HColumnDescriptor("base_info");
		hColumnDescriptor1.setMaxVersions(3);
		
		HColumnDescriptor hColumnDescriptor2 = new HColumnDescriptor("extra_info");
		//将列族对象添加到列对象中
		hTableDescriptor.addFamily(hColumnDescriptor1);
		hTableDescriptor.addFamily(hColumnDescriptor2);
		//用ddl操作器
		admin.createTable(hTableDescriptor);
		admin.close();
		conn.close();
	}
	@Test
	public void deleteTable() throws IOException{
		Admin admin = conn.getAdmin();
		//删表前必需要先停用表，否则报错
		admin.disableTable(TableName.valueOf("user_info"));
		//删表
		admin.deleteTable(TableName.valueOf("user_info"));
		admin.close();
		conn.close();
	}
	@Test
	public void modifyTable() throws IOException{
		Admin admin = conn.getAdmin();
		//取出旧表的表定义
		HTableDescriptor tableDescriptor = admin.getTableDescriptor(TableName.valueOf("user_info"));
		//新构建一个一个列族定义
		HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("other_info");
		hColumnDescriptor.setBloomFilterType(BloomType.ROW);//设置布隆过滤器：对信息进行摘要，优化查询。写入效率会变慢
		//将列族定义添加到表定义对象中
		tableDescriptor.addFamily(hColumnDescriptor);
		//修改的过的表定义 交给 admin去提交。
		admin.modifyTable(TableName.valueOf("user_info"), tableDescriptor);
		
		conn.close();
		admin.close();
		
	}
	/*************** DML ************************/
	
}
