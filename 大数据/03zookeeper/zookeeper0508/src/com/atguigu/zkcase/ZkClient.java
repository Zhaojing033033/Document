package com.atguigu.zkcase;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
/**
 * ��̬����������ע������
 * @author Administrator
 *
 */
public class ZkClient {

	private String connectString = "192.168.111.102:2181,192.168.111.103:2181,192.168.111.104:2181";
	private int sessionTimeout = 2009;
	private ZooKeeper zk = null;
	private String parentNode = "/servers";

	// �����ͻ���
	public void getConnect() throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				// ������������¼�
				System.out.println(event.getType() + "--" + event.getPath());
				try {
					// ��֤��һ�����
					getServers();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void getServers() throws Exception {
		List<String> children = zk.getChildren(parentNode, true);
		ArrayList<Object> servers = new ArrayList<>();
		for (String child : children) {
			byte[] data = zk.getData(parentNode + "/" + child, false, null);
			servers.add(new String(data));
		}
		System.err.println(servers);
	}

	// �����ҵ���߼�
	public void business() throws Exception {
		System.out.println("business �Ϳ�");

		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {
		ZkClient zkServer = new ZkClient();
		zkServer.getConnect();
		zkServer.getServers();
		zkServer.business();
	}
}
