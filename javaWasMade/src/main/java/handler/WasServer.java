package handler;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WasServer {
	private static final Logger log = LoggerFactory.getLogger(WasServer.class);
	private static final int DEFAULT_PORT = 8080;
	
    public static void main(String argv[]) throws Exception {
    	// try-with-resource 지원으로 close 필요없어
    	try (ServerSocket listenSocket = new ServerSocket(DEFAULT_PORT) ) {
    		log.info("Web Application Server started {} port.", DEFAULT_PORT);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
            	RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            }
    	}
    }
}
