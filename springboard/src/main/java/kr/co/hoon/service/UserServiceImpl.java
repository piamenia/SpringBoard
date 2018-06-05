package kr.co.hoon.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.co.hoon.dao.UserDao;
import kr.co.hoon.domain.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public String emailCheck(HttpServletRequest request) {
		return userDao.emailCheck(request.getParameter("email"));
	}

	@Override
	public void register(MultipartHttpServletRequest request) {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		String nickname = request.getParameter("nickname");
		// 파일 읽기
		MultipartFile image = request.getFile("image");
		// 파일을 저장할 경로
		// webapp/userimage 의 절대경로
		String uploadPath = request.getRealPath("/userimage");
		// 랜덤한 64자리 문자열
		UUID uid = UUID.randomUUID();
		// 파일이름
		String filename = uid + "_" + image.getOriginalFilename();
		// 업로드할 파일의 실제경로
		String filepath = uploadPath + "\\" + filename;
		
		// Dao의 파라미터로 사용할 객체
		User user = new User();
		// 업로드할 파일 객체 만들기
		File f = new File(filepath);
		try {
			// 파일전송 - 파일업로드
			image.transferTo(f);
			// Dao의 파라미터 만들기
			user.setEmail(email);
			// 비밀번호는 암호화
			user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
			user.setNickname(nickname);
			// DB에는 파일이름만 저장
			user.setImage(filename);
			userDao.register(user);
		}catch(Exception e) {
			System.out.println("회원가입 실패: "+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public User login(HttpServletRequest request) {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		// 서비스 메소드 호출
		User user = userDao.login(email);
		String userpw = user.getPw();
		// 비밀번호 확인
		if(user != null) {
			if(BCrypt.checkpw(pw, userpw)==true) {
				// 비밀번호가 일치하면 비밀번호만 제거
				user.setPw("");
			}else {
				// 비밀번호가 일치하지 않으면 전부 제거
				user = null;
			}
		}
		return user;
	}

	@Override
	public String address(String loc) {
		String[] ar = loc.split("-");
		String lat = ar[0];
		String lng = ar[1];
		String addr =
			"https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?"
			+ "x=" + lng + "&y=" + lat;
//		System.out.println(addr);
		try {
			// 위의 주소를 가지고 URL객체 생성
			URL url = new URL(addr);
			// URL 객체를 가지고 HttpConnection 객체 생성
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			// 인증받기
			// 카카오 좌표 -> 주소 변환에서 키 가져와서 등록
			con.setRequestProperty("Authorization", "KakaoAK ffa1dc94b8586a3a60811e9629e26082");
			// 옵션 설정
			con.setConnectTimeout(10000);
			con.setUseCaches(false);
			// 데이터를 줄단위로 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			while(true) {
				String line = br.readLine();
				if(line==null) {
					break;
				}
				sb.append(line);
			}
			br.close();
			con.disconnect();
//			System.out.println(sb);
			
			// JSONObject 생성
			JSONObject obj = new JSONObject(sb.toString());
//			System.out.println(obj);
			JSONArray array = obj.getJSONArray("documents");
//			System.out.println(array);
			JSONObject obj0 = array.getJSONObject(0);
			String address = obj0.getString("address_name");
//			System.out.println(address);
			return address;
			
		}catch(Exception e) {
			System.out.println("주소 가져오기 실패: "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// 메일을 보내주는 객체
	@Autowired
	private MailSender mailSender;
	
	@Override
	public void sendmail(HttpServletRequest request) {
		String receiver = request.getParameter("receiver");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		try {
			// 매일보내기 객체
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("piamenia@naver.com");
			// 받는사람
			message.setTo(receiver);
			// 제목
			message.setSubject(title);
			// 내용
			message.setText(contents);
			// 메일 보내기
			mailSender.send(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
