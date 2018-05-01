package was.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class WasUtil {
	/**
	 * json 파일을 읽어 들여서 StringBuffer에 담는다.
	 * */
    public static String getFileContent(String filename) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream is = classLoader.getResourceAsStream(filename);

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        int read;
        char[] bf = new char[1024];
        while ((read = br.read(bf)) > 0) {
            buffer.append(bf, 0, read);
        }
        br.close();
        is.close();

        return buffer.toString();
    }


}
