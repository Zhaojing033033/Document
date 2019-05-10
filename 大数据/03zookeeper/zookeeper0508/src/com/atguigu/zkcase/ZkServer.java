package com.atguigu.zkcase;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
/**
 * 向zookeeper 注册服务
 * @author Administrator
 *
 */
public class ZkServer {

	private String connectString="192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181";
	private int sessionTimeout = 2009;
	private ZooKeeper zkServer=null;
	private String parentNode = "/servers";
	//创建客户端
	public void getConnect() throws IOException{
		zkServer=new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				//监听发生后的事件
			}});
	}
	//2注册
	public void regist(String hostname) throws KeeperException, InterruptedException{
		
		String create = zkServer.create(parentNode+"/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname+"is online "+ create);
		
	}
	//具体的业务逻辑
	public void business() throws Exception{
		System.out.println("business 接客");
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	
	public static void main(String[] args) throws  Exception {
				ZkServer zkServer = new ZkServer();
				zkServer.getConnect();
				zkServer.regist(args[0]);
				zkServer.business();
	}

}
