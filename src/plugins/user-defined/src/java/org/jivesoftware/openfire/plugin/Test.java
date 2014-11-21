package org.jivesoftware.openfire.plugin;

import java.io.File;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;

public class Test implements Plugin{
	
	private XMPPServer server;

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		server = XMPPServer.getInstance();
        System.out.println("初始化…… 安装插件！");
        System.out.println(server.getServerInfo());
	}

	@Override
	public void destroyPlugin() {
		System.out.println("服务器停止，销毁插件！");
	}
	
	

}
