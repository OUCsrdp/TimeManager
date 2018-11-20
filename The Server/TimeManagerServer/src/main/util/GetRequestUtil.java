package main.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class GetRequestUtil {  
	  
    /*** 
     * ��ȡ request �� json �ַ��������� 
     *  
     * @param request 
     * @return : <code>byte[]</code> 
     * @throws IOException 
     */  
    public static String getRequestJsonString(HttpServletRequest request)  
            throws IOException {  
        String submitMehtod = request.getMethod();  
        // GET  
        if (submitMehtod.equals("GET")) {  
            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");  
        // POST  
        } else {  
            return getRequestPostStr(request);  
        }  
    }  
  
    /**       
     * ����:��ȡ post ����� byte[] ���� 
     * <pre> 
     * ������ 
     * </pre> 
     * @param request 
     * @return 
     * @throws IOException       
     */  
    public static byte[] getRequestPostBytes(HttpServletRequest request)  
            throws IOException {  
        int contentLength = request.getContentLength();  
        if(contentLength<0){  
            return null;  
        }  
        byte buffer[] = new byte[contentLength];  
        for (int i = 0; i < contentLength;) {  
            int readlen = request.getInputStream().read(buffer, i,  
                    contentLength - i);  
            if (readlen == -1) {  
                break;  
            }  
            i += readlen;  
        } 
        return buffer;
    	/*BufferedReader br = request.getReader();

    	String str, wholeStr = "";
    	while((str = br.readLine()) != null){
    	wholeStr += str;
    	}
    	System.out.println("wholestr:"+wholeStr);
        int length = request.getContentLength();//��ȡ����������ȡ�
        byte[] bytes = new byte[length];//�������飬����Ϊ��������ĳ���
        ServletInputStream inStream = request.getInputStream();
        inStream.read(bytes, 0, length);
        return bytes;*/
    }  
  
    /**       
     * ����:��ȡ post �������� 
     * <pre> 
     * ������ 
     * </pre> 
     * @param request 
     * @return 
     * @throws IOException       
     */  
    public static String getRequestPostStr(HttpServletRequest request)  
            throws IOException {  
        byte buffer[] = getRequestPostBytes(request);  
        String charEncoding = request.getCharacterEncoding();  
        if (charEncoding == null) {  
            charEncoding = "UTF-8";  
        }
        String newString=new String(buffer, charEncoding);
        return newString;  
    }  
  
}
