package servlet.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import servlet.ServletService;




public class ServletServiceImpl implements ServletService {


	@Override
	public String getTime() throws Exception{
	    
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년 MM월 dd일 kk:mm:ss");
		String nowDate = dayTime.format(new Date(time));
		
		return nowDate;
	}
	
}
