package com.atgugui.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
/**
 * ����zookper��API
 * @author Administrator
 *
 */
public class ZkClient {
	
	private String connectString="192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181";
	private int sessionTimeout = 2009;
	private ZooKeeper ZkClient=null;
	//�����ͻ���
	@Before
	public void initZk() throws IOException{
		ZkClient=new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				//������������¼�
				System.out.println(event.getType()+"--"+event.getPath());
				
				try {
					Stat exists = ZkClient.exists("/atguigu", true);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//�ڶ���������Ϊtrue,�ɽ��м���
			}});
	}
	//�����ڵ� 
	@Test
	public void createNode() throws Exception{
		//����1��·�� ����2���洢������  ����3��������Ľڵ������е�Ȩ�� ����4���ڵ����� 
		String create = ZkClient.create("/atguigu", "ss.avi".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(create);
	}
	
	//��ȡ�ӽڵ� 
	@Test
	public void getChild () throws KeeperException, InterruptedException{
		List<String> children = ZkClient.getChildren("/", false);
		for (String string : children) {
			System.out.println(string);
		}
	}
	//�жϽڵ��Ƿ����
	@Test
	public void isExist() throws KeeperException, InterruptedException{
		Stat exists = ZkClient.exists("/atguigu", true);//�ڶ���������Ϊtrue,�ɽ��м���
		System.out.println(exists);
		Thread.sleep(Long.MAX_VALUE);
	}

	
	
	
	
	
	
	
	
	
	
	
}
