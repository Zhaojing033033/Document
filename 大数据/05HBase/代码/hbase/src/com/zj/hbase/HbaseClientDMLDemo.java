package com.zj.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.jute.compiler.CSharpGenerator;
import org.junit.Before;
import org.junit.Test;

public class HbaseClientDMLDemo {
	Connection conn;
	@Before
	public void getConn() throws IOException{
		Configuration configuration = HBaseConfiguration.create();//���Զ�����hbase-site.xml
		//hbase�ͻ���ֻ��Ҫ��zookeeperͨ�ţ�ָ��zookeeper�ļ�Ⱥ����Ҫ�����б������hostsӳ�䣬�������С�����Call Excepion 
		configuration.set("hbase.zookeeper.quorum","192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181");
	    
		conn = ConnectionFactory.createConnection(configuration);
	}
	@Test
	public void  add() throws IOException{
		//��ȡ����� 
		Table table = conn.getTable(TableName.valueOf("user_info"));
		
		//Ҫ���������Ϊһ��put���͵Ķ��� .һ��put����ֻ�ܶ�Ӧһ�� �м�
		Put put = new Put(Bytes.toBytes("004"));
		put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("username"), Bytes.toBytes("����4"));
		put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes("134"));
		put.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("addr"), Bytes.toBytes("����4"));
		
		//����һ������ 
		//table.put(put);
		
		//����������
		ArrayList<Put> puts = new ArrayList();
		puts.add(put);
		table.put(puts);
		
		table.close();
		conn.close();
		
	}
	
	@Test
	public void  del() throws IOException{
		
		Table table = conn.getTable(TableName.valueOf("user_info"));
		
		Delete delete1 = new Delete(Bytes.toBytes("001"));
		Delete delete2 = new Delete(Bytes.toBytes("002"));
		delete2.addColumn(Bytes.toBytes("extra_info"), Bytes.toBytes("addr"));
		
		ArrayList<Delete> dels = new ArrayList();
		dels.add(delete1);
		dels.add(delete2);
		table.delete(dels);
		
		table.close();
		conn.close();
	}
	
	
	
	@Test
	public void  getOneByRowKey() throws IOException{
		Table table = conn.getTable(TableName.valueOf("user_info"));
		
		Get get = new Get(Bytes.toBytes("002"));
		
		//�������н���е����� kv��Ԫ��
		Result result = table.get(get);
		CellScanner cellScanner = result.cellScanner();
		while(cellScanner.advance()){
			Cell cell= cellScanner.current();
			
			byte[] rowArray = cell.getRowArray();				//��kv�������м����ֽ�����
			byte[] familyArray = cell.getFamilyArray();			//���������ֽ�����
			byte[] qualifierArray = cell.getQualifierArray();	//�������ֽ�
			byte[] valueArray = cell.getValueArray();			//value���ֽ�����
			
			System.out.println("�м�     :"+new String(rowArray,cell.getRowOffset(),cell.getRowLength()));
			System.out.println("������  :"+new String(familyArray,cell.getFamilyOffset(),cell.getFamilyLength()));
			System.out.println("����	  :"+new String(qualifierArray,cell.getQualifierOffset(),cell.getQualifierLength()));
			System.out.println("value :"+new String(valueArray,cell.getValueOffset(),cell.getValueLength()));
			
			
		}
		
		table.close();
		conn.close();
		
		
		
	}
	@Test
	public void  getList() throws IOException{
		Table table = conn.getTable(TableName.valueOf("user_info"));
		//������ʼ�м��������������м������������Ҫ�����м�����ô�����ڽ����м���ƴ��һ�����ɼ����ֽڣ�\000��
		Scan scan = new Scan("001".getBytes(),"004\000".getBytes());
		ResultScanner scanner = table.getScanner(scan);
		Iterator<Result> iterator = scanner.iterator();
		while(iterator.hasNext()){
			Result result = iterator.next();
			CellScanner cellScanner = result.cellScanner();
			while(cellScanner.advance()){
				Cell cell= cellScanner.current();
				
				byte[] rowArray = cell.getRowArray();				//��kv�������м����ֽ�����
				byte[] familyArray = cell.getFamilyArray();			//���������ֽ�����
				byte[] qualifierArray = cell.getQualifierArray();	//�������ֽ�
				byte[] valueArray = cell.getValueArray();			//value���ֽ�����
				
				System.out.println("�м�     :"+new String(rowArray,cell.getRowOffset(),cell.getRowLength()));
				System.out.println("������  :"+new String(familyArray,cell.getFamilyOffset(),cell.getFamilyLength()));
				System.out.println("����	  :"+new String(qualifierArray,cell.getQualifierOffset(),cell.getQualifierLength()));
				System.out.println("value :"+new String(valueArray,cell.getValueOffset(),cell.getValueLength()));
				
				
			}
			
		}
		table.close();
		conn.close();
		
		
		
	}
	
	
	
	
	
	
	
	@Test
	public void  mod() throws IOException{
		Table table = conn.getTable(TableName.valueOf("user_info"));
	}
}
