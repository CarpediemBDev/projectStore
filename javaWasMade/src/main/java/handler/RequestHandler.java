package handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import servlet.ServletService;
import servlet.service.ServletServiceImpl;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
	// 파일 요청이 없을 경우의 기본 파일
	//private static final String DEFAULT_FILE_PATH = "/index.html";
	  
	private Socket connection;
    private static JSONObject serverConfig;
    private static JSONObject virtualHostConfig;
    private static JSONObject mappingConfig;
    private static String ROOT_Directory = "./webapp" + "/view/";
    private static String httpState = "200";

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}
	/*
	 * jSon 설정파일을 읽어 상태코드에 따른 페이지로 이동
	 * */
	public byte[] goToPage(String host, String page) throws Exception {
		WasConfig wasConfig = new WasConfig();
		byte[] body = null;
		String root = wasConfig.getRootDir(host); //  VirtualHost 기능처리로 html 폴더를 가르킴
		if (page.indexOf("../") > -1) {
			httpState = "403";
		}else if (page.startsWith("/time")){
			ServletService servletService = new ServletServiceImpl();
			String nowDate = servletService.getTime();
			body = nowDate.getBytes();
			return body;
		}
		else{
			httpState = "200";
		}
		String path = ROOT_Directory + root + "/" + WasConfig.getViewPage(host, httpState);
		try {
			byte [] checkBody = Files.readAllBytes(new File(ROOT_Directory + root + "/" + page).toPath());
			body = Files.readAllBytes(new File(path).toPath());
		} catch (IOException e) {
			httpState ="404";
			path = ROOT_Directory + root + "/" + WasConfig.getViewPage(host, httpState);
			try {
				body = Files.readAllBytes(new File(path).toPath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return body;

	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
		InputStream in = null;
		OutputStream out = null;
		String host = null;
		String page = null;
		
		try  {
			//HTTP 헤더를 읽는다.
			in = connection.getInputStream();
			out = connection.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String line = br.readLine(); // HTTP를 Line으로 읽어온다.
			String urlLine = br.readLine();
			
			// Host를 찾아서 ip가 동일하여도 도메인에 따라 별도 처리
			while(!urlLine.isEmpty()){
				log.debug(urlLine);
				urlLine = br.readLine();
				
				if(urlLine.contains("Host")){
					break;
				}
			}
			
			host = urlLine.split(":")[1].trim();
			page = line.split(" ")[1]; 


			if(page == null){
				httpState = "500";
			}
			byte[] body = goToPage(host,page);
			out.write(body, 0, body.length); // 0번째부터 body len 개의바이트를 출력
			br.close();
			in.close();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void response403Header(DataOutputStream dos) {
		try {
			dos.writeBytes("HTTP/1.1 403 Forbidden \r\n");
			dos.writeBytes("Location: /errorPage/error403.html\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	private void response404Header(DataOutputStream dos, String location) {
		try {
			dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("Connection: close\r\n");
            dos.writeBytes("\r\n");
            dos.writeBytes("<h1>404 Not Found</h1>");
//            dos.writeBytes("Location: /errorPage/error404.html\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	private void response500Header(DataOutputStream dos) {
		try {
			dos.writeBytes("HTTP/1.1 500 Internal Server Error \r\n");
			dos.writeBytes("Location: /errorPage/error500.html\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	private void response302Header(DataOutputStream dos) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Location: /errorPage/error500.html\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {

			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	private void responseBody(OutputStream dos, byte[] body) {
		try {
			dos.write(body);
			//dos.writeBytes("\r\n");
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
