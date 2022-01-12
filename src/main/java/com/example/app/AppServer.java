package com.example.app;

import org.glassfish.tyrus.server.Server;


public class AppServer{

	/* サーバ側のサンプルプログラム
	 * このメインメソッドの例ではサーバインスタンスの生成と起動のみを行っている．
	 */

	static String contextRoot = "/app";
	static String protocol = "ws";
	static int port = 8080;


    public static void main(String[] args) throws Exception {
        Server server = new Server(protocol, port, contextRoot, null, EndpointTest.class);

        try {
        	System.out.println("---SERVER START---");  
            server.start();
            System.in.read();
        } finally {
            server.stop();
            System.out.println("---SERVER STOP---");
        }
    }
}
