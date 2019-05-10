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
 * 调用zookper的API
 * @author Administrator
 *
 */
public class ZkClient {
	
	private String connectString="192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181";
	private int sessionTimeout = 2009;
	private ZooKeeper ZkClient=null;
	//创建客户端
	@Before
	public void initZk() throws IOException{
		ZkClient=new ZooKeeper(connectString, sessionTimeout, new Watcher(){

			@Override
			public void process(WatchedEvent event) {
				//监听发生后的事件
				System.out.println(event.getType()+"--"+event.getPath());
				
				try {
					Stat exists = ZkClient.exists("/atguigu", true);
				} catch (KeeperException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//第二个参数，为true,可进行监听
			}});
	}
	//创建节点 
	@Test
	public void createNode() throws Exception{
		//参数1：路径 参数2：存储的数据  参数3：创建后的节点所具有的权限 参数4：节点类型 
		String create = ZkClient.create("/atguigu", "ss.avi".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(create);
	}
	
	//获取子节点 
	@Test
	public void getChild () throws KeeperException, InterruptedException{
		List<String> children = ZkClient.getChildren("/", false);
		for (String string : children) {
			System.out.println(string);
		}
	}
	//判断节点是否存在
	@Test
	public void isExist() throws KeeperException, InterruptedException{
		Stat exists = ZkClient.exists("/atguigu", true);//第二个参数，为true,可进行监听
		System.out.println(exists);
		Thread.sleep(Long.MAX_VALUE);
	}

	
	
	
	
	
	
	
	
	
	
	
}
