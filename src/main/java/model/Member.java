package model;

import java.io.File;

import jakarta.servlet.http.Part;
import util.StringUtils;

/**
 * Servlet implementation class Member
 */
public class Member {
	
	private String Member_Username;
	private String Member_Password;
	private String Member_FirstName;
	private String Member_LastName;
	private String Member_Email;
	private String Member_PhoneNumber;
	private String Member_Age;
	private String imageUrlFromPart;
	private String Join_Date;
	
	public Member( String member_Username, String member_Password, String member_FirstName,
			String member_LastName, String member_Email, String member_PhoneNumber, String member_Age,
			Part imagePart) {
		this.Member_Username = member_Username;
		this.Member_Password = member_Password;
		this.Member_FirstName = member_FirstName;
		this.Member_LastName = member_LastName;
		this.Member_Email = member_Email;
		this.Member_PhoneNumber = member_PhoneNumber;
		this.Member_Age = member_Age;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}
	
	public Member() {
		
	}
	
	
	public String getMember_Username() {
		return Member_Username;
	}
	
	public void setMember_Username(String member_Username) {
		Member_Username = member_Username;
	}
	
	public String getMember_Password() {
		return Member_Password;
	}
	
	public void setMember_Password(String member_Password) {
		Member_Password = member_Password;
	}
	
	public String getMember_FirstName() {
		return Member_FirstName;
	}
	
	public void setMember_FirstName(String member_FirstName) {
		Member_FirstName = member_FirstName;
	}
	
	public String getMember_LastName() {
		return Member_LastName;
	}
	
	public void setMember_LastName(String member_LastName) {
		Member_LastName = member_LastName;
	}
	
	public String getMember_FullName() {
		return Member_FirstName + " " + Member_LastName;
	}
	
	public String getMember_Email() {
		return Member_Email;
	}
	
	public void setMember_Email(String member_Email) {
		Member_Email = member_Email;
	}
	
	public String getMember_PhoneNumber() {
		return Member_PhoneNumber;
	}
	
	public void setMember_PhoneNumber(String member_PhoneNumber) {
		Member_PhoneNumber = member_PhoneNumber;
	}
	
	public String getMember_Age() {
		return Member_Age;
	}
	
	public void setMember_Age(String member_Age) {
		Member_Age = member_Age;
	}
	
	public String getImageUrlFromPart() {
		return imageUrlFromPart;
	}
	
	public void setImageUrlFromPart(String imageUrlFromPart) {
		this.imageUrlFromPart = imageUrlFromPart;
	}
	
	public String getJoin_Date() {
		return Join_Date;
	}
	
	public void setJoin_Date(String join_Date) {
		Join_Date = join_Date;
	}
	
	public void setImageUrlFromDB(String imageUrl) {
		this.imageUrlFromPart = imageUrl;
	}
	
	private String getImageUrl(Part part) {
		String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_MEMBER;
		File fileSaveDir = new File(savePath);
		String imageUrlFromPart = null;
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				imageUrlFromPart = s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		if (imageUrlFromPart == null || imageUrlFromPart.isEmpty()) {
			imageUrlFromPart = "default.png";
		}
		return imageUrlFromPart;
	}
}