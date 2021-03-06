package kr.ac.cbnu.computerengineering.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;

import kr.ac.cbnu.computerengineering.common.datatype.ApprovalDataType;
import kr.ac.cbnu.computerengineering.common.datatype.Constraints;
import kr.ac.cbnu.computerengineering.common.datatype.HospitalDatatype;
import kr.ac.cbnu.computerengineering.common.datatype.PagingDataType;
import kr.ac.cbnu.computerengineering.common.datatype.RegistrationDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchDataType;
import kr.ac.cbnu.computerengineering.common.datatype.SearchParam;
import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;

public class Utils {
	
	public static enum errorCheck{PROHIBITION_SAME_CAPABLE, CAPABLE_IN_UPPERATC, PROHIBITION_SAME_UPPERATC, UPPERATC_NOT_IN_PROHIBITION, UPPERATC_IN_CAPABLE}
	
	public static int roleCnt(UserDataType userDataType)
	{
		int result = 0;
		result = userDataType.getRoles().size();
		return result;
	}
	public static HttpSession setSessionRoles(HttpSession session, List<UserRoleType> list){
		// 
		int i=1;
		for(UserRoleType type: list){
			session.setAttribute("role"+i,type.toString());
			System.out.println((String) session.getAttribute("role"+i));
			i++;
		}
		return session;
	}
	
	public static List<UserRoleType> getRoles(List<UserRoleDataType> list){
		List<UserRoleType> result = new ArrayList<>();
		for(int i=0; i<list.size(); i++){
			result.add(list.get(i).getUserRole());
		}
		return result;
		
	}
	public static List<UserRoleDataType> setRoles(String userId, String[] str){
		List<UserRoleDataType> result = new ArrayList<>();
		
		for(int i=0; i<str.length; i++){
			UserRoleDataType dataType = new UserRoleDataType();
			dataType.setUserId(userId);
			switch(Integer.parseInt(str[i])) {
				case 1:
					dataType.setUserRole(UserRoleType.ADMIN);
					break;
				case 2:
					dataType.setUserRole(UserRoleType.DOCTOR);
					break;
				case 3:
					dataType.setUserRole(UserRoleType.PATIENT);
					break;
				default:
					break;
					
			}
			result.add(dataType);
		}
			
		return result;
		
	}
	public static String encryptSHA256(String str) throws NoSuchAlgorithmException {
		String sha = "";
		MessageDigest sh = MessageDigest.getInstance("SHA-256");
		sh.update(str.getBytes());
		byte byteData[] = sh.digest();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<byteData.length; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
		sha = sb.toString();
		
		return sha;
	}
	
	public static String base64 (String str){	
		byte[] encoded = Base64.encodeBase64(str.getBytes());
		System.out.println(encoded);
		return encoded.toString();
	}
	
	public static PagingDataType computePagingData(int totalCnt, int page) {
		PagingDataType result = new PagingDataType();
		int startRow = (page-1) * Constraints.PAGE_SIZE;
		int endRow = Constraints.PAGE_SIZE;
		int pageGroupCount = totalCnt / 
				(Constraints.PAGE_SIZE * Constraints.PAGE_GROUP_SIZE) + ((totalCnt % (Constraints.PAGE_SIZE*Constraints.PAGE_GROUP_SIZE))==0? 0 : 1);
		int nowPageGroup = (int) Math.ceil((double)page/Constraints.PAGE_GROUP_SIZE);
		int pageCount = totalCnt/Constraints.PAGE_SIZE + (totalCnt % Constraints.PAGE_SIZE == 0 ? 0 : 1);
		int startPage = Constraints.PAGE_GROUP_SIZE*(nowPageGroup-1)+1;
		int endPage = startPage+Constraints.PAGE_GROUP_SIZE -1;
		if(endPage > pageCount)
			endPage = pageCount;
		result.setNowPage(page);
		result.setPageGroupCount(pageGroupCount);
		result.setNowPageGroup(nowPageGroup);
		result.setPageCount(pageCount);
		result.setStartPage(startPage);
		result.setEndPage(endPage);
		result.setPageGroupSize(Constraints.PAGE_GROUP_SIZE);
		result.setStartRow(startRow);
		result.setEndRow(endRow);
		result.setCount(totalCnt);
		
		return result;
	}
	
	public static SearchParam makeSearchParm(String param, String id, String cbnuCode, int idx,
			String medicineCode,String atcFirst,  int startRow, int endRow,String searchOption,Date date, int no) {
		SearchParam searchParam = new SearchParam();
		searchParam.setParam(param);
		searchParam.setId(id);
		searchParam.setCbnuCode(cbnuCode);
		searchParam.setIdx(idx);
		searchParam.setMedicineCode(medicineCode);
		searchParam.setAtcFirst(atcFirst);
		searchParam.setStartRow(startRow);
		searchParam.setEndRow(endRow);
		searchParam.setSearchOption(searchOption);
		searchParam.setDate(date);
		searchParam.setNo(no);
		return searchParam;
	}
	
	public static UserDataType makeUserDataType(String id, String pw, String name, String disable, String email,
			String cbnuCode, String[] roles, Date date, int hospitalId, ApprovalDataType approval) {
		UserDataType result = new UserDataType();
		result.setUserId(id);
		result.setPassword(pw);
		result.setName(name);
		result.setDisable(disable);
		result.setEmail(email);
		result.setCbnuCode(cbnuCode);
		result.setDate(date);
		HospitalDatatype hospital = new HospitalDatatype();
		hospital.setId(hospitalId);
		result.setHospital(hospital);
		if(approval != null){
		result.setApproval(approval.getApproval());
		}
		List<UserRoleDataType> roleList = null;
		if(roles != null){
			roleList = Utils.setRoles(id, roles);
		}
		result.setRoles(roleList);
		return result;
	}
	
	public static SearchDataType makeSearchDataType(int page, SearchParam param, int count) {
		SearchDataType searchDataType = new SearchDataType();
		searchDataType.setSearchValue(param.getParam());
		searchDataType.setSearchOption(param.getSearchOption());
		
		return searchDataType.extendParent(Utils.computePagingData(count, page));
	}
	
	public static Date getDate() throws ParseException {
		long currentTime =  System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd HH:mm:ss", Locale.ENGLISH);
		String strDate = format.format(new Date(currentTime));
		Date date = format.parse(strDate);
		return date;
	}
	
	public static int convertPageStringToInt(String page) {
		if(page == null){
			return 1;
		}
		return Integer.valueOf(page);
	}
	public static RegistrationDataType makeRegistrationDataType(Integer registrationID, UserDataType user,
			UserDataType patient) {
		RegistrationDataType registrationDataType = new RegistrationDataType();
		registrationDataType.setDoctor(user);
		registrationDataType.setPatient(patient);
		registrationDataType.setIdx(registrationID);
		return registrationDataType;
	}
	
	public static String newlineToBRTag(String str) {
		return str.replace("\n", "<br>").replace("\r", "");
	}
}
