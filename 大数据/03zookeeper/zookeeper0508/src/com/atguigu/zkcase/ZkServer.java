package com.atguigu.zkcase;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
/**
 * ��zookeeper ע�����
 * @author Administrator
 *
 */
public class ZkServer {

	private String connectString="192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181";
	private int sessionTimeout = 2009;
	private ZooKeeper zkServer=null;
	private String parentNode = "/servers";
	//�����ͻ���
	public void getConnect() throws IOException{
		zkServer=new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				//������������¼�
			}});
	}
	//2ע��
	public void regist(String hostname) throws KeeperException, InterruptedException{
		
		String create = zkServer.create(parentNode+"/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname+"is online "+ create);
		
	}
	//�����ҵ���߼�
	public void business() throws Exception{
		System.out.println("business �ӿ�");
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	
	public static void main(String[] args) throws  Exception {
				ZkServer zkServer = new ZkServer();
				zkServer.getConnect();
				zkServer.regist(args[0]);
				zkServer.business();
	}

}
