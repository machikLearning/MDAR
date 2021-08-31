package kr.ac.cbnu.computerengineering.common;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import kr.ac.cbnu.computerengineering.board.common.datatype.FileDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.util.Utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class AbsADRMServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected List<FileDataType> uploadBoardFile(HttpServletRequest request, HttpServletResponse response, String uploadFolder) throws ServletException, IOException, ParseException {		
        try{
		int sizeLimit = 30 * 1024 * 1024;
        List<FileDataType> uploadFiles = new ArrayList<>();
        File file = new File(uploadFolder);
		if(!file.exists()){
			file.mkdirs();
		}
        MultipartRequest multi = new MultipartRequest(request, uploadFolder, sizeLimit,"euc-kr",new DefaultFileRenamePolicy());
        @SuppressWarnings("unchecked")
		Enumeration <String> formNames = multi.getFileNames();
        String formName="";
        String fileName="";
        while (formNames.hasMoreElements()) {    
        	formName = (String)formNames.nextElement();    
        	fileName = multi.getFilesystemName(formName);
        	if(fileName != null) {   
        		File uploadFile = new File(uploadFolder+"/"+fileName);
        		String now = new SimpleDateFormat("yyyyMMddHmss").format(new Date()) + " ";
        		String saveName= now + fileName;
        		uploadFile.renameTo(new File(uploadFolder+"/"+saveName));
        		uploadFile = new File(uploadFolder+"/"+saveName);
        		String nickName = this.makeNickName(saveName);
        		uploadFiles.add(new FileDataType(uploadFile.getCanonicalPath(), saveName,uploadFile.length(),nickName));
        	}
       	}
        return uploadFiles;
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
	}
	
	
	
	private String makeNickName(String saveName) throws ParseException {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String returnName = saveName.substring(0, saveName.indexOf("."))+transFormat.format(Utils.getDate())+saveName.substring(saveName.indexOf("."));
		return returnName;
	}



	protected void download(HttpServletRequest request, HttpServletResponse response, String filename, String uploadFolder) 
			throws IOException {
		InputStream in = null;
		OutputStream os = null;
		
	    try{
            File file = new File(uploadFolder, filename);
            in = new FileInputStream(file);
	        String userAgent = request.getHeader("User-Agent");
	        response.reset() ;
	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Description", "JSP Generated Data");
        	if (userAgent != null && userAgent.indexOf("MSIE 5.5") > -1)  // MS IE 5.5 이하
        		response.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(filename, "UTF-8") + ";");
        	else if (userAgent != null && userAgent.indexOf("MSIE") > -1) // MS IE  6.x 이상
        		response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8") + ";");
        	else  // 모질라나 오페라
        		response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("euc-kr"), "latin1") + ";");
            response.setHeader ("Content-Length", ""+file.length() );
            os = response.getOutputStream();
            byte b[] = new byte[(int)file.length()];
            int leng = 0;
             
            while( (leng = in.read(b)) > 0 ){
                os.write(b,0,leng);
            }
	    } catch(FileNotFoundException e) {
	    	response.setContentType("text/html;charset=UTF-8");
	    	throw new FileNotFoundException(filename);
	    } catch (UnsupportedEncodingException e) {
	    	response.setContentType("text/html;charset=UTF-8");
			throw new UnsupportedEncodingException();
		} catch (IOException e) {
	    	response.setContentType("text/html;charset=UTF-8");
			throw new IOException();
		} finally {
			if(in != null) {
		        try {
					in.close();
				} catch (IOException e) {
					throw new IOException();
				}
			}
			if(os != null) {
		        try {
		        	os.close();
				} catch (IOException e) {
					throw new IOException();
				}
			}
	    }
	}
	
	protected void mailSendRequest(UserDataType user, String content) throws AddressException, MessagingException, ServletException, IOException, NullPointerException {
		// 네이버일 경우 smtp.naver.com 을 입력합니다.
		// Google일 경우 smtp.gmail.com 을 입력합니다.
		String host = "smtp.naver.com";
		
		final String username = "ubigamelab";       //네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.
		final String password = "E8-1423";   //네이버 이메일 비밀번호를 입력해주세요.
		int port=465; //포트번호
		 
		// 메일 내용
		String recipient = user.getEmail();    //받는 사람의 메일주소를 입력해주세요.
		String subject = "안녕하세요. 충북대 알레르기 내과 입니다."; //메일 제목 입력해주세요.
		String body=content;
		
		Properties props = System.getProperties(); // 정보를 담기 위한 객체 생성
		 
		// SMTP 서버 정보 설정
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		   
		//Session 생성
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
		String un=username;
		String pw=password;
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(un, pw);
			}
		});
		session.setDebug(true); //for debug
		   
		Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
		mimeMessage.setFrom(new InternetAddress("ubigamelab@naver.com")); //발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); //수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음


		mimeMessage.setSubject(subject);  //제목셋팅
		mimeMessage.setText(body);        //내용셋팅
		Transport.send(mimeMessage); //javax.mail.Transport.send() 이용
	}



	public List<File> uploadFile(HttpServletRequest request, HttpServletResponse response,
			String devlopmentUploadPath) {
		List<File> uploadFileList = new ArrayList<File>();
		return uploadFileList;
	}
}
