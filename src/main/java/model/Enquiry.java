package model;

/**
 * Servlet implementation class Enquiry
 */
public class Enquiry {
	
	private String Enquiry_ID;
	private String Member_ID;
	private String Subject;
	private String Message;
	private String Enquiry_Date;
	
	// For storing associated member object (not stored in database)
	private Member member;
	
	public Enquiry(String enquiry_ID, String member_ID, String subject, String message, String enquiry_Date) {
		this.Enquiry_ID = enquiry_ID;
		this.Member_ID = member_ID;
		this.Subject = subject;
		this.Message = message;
		this.Enquiry_Date = enquiry_Date;
	}
	
	public Enquiry() {
		// TODO Auto-generated constructor stub
	}
	
	public String getEnquiry_ID() {
		return Enquiry_ID;
	}
	
	public void setEnquiry_ID(String enquiry_ID) {
		Enquiry_ID = enquiry_ID;
	}
	
	public String getMember_ID() {
		return Member_ID;
	}
	
	public void setMember_ID(String member_ID) {
		Member_ID = member_ID;
	}
	
	public String getSubject() {
		return Subject;
	}
	
	public void setSubject(String subject) {
		Subject = subject;
	}
	
	public String getMessage() {
		return Message;
	}
	
	public void setMessage(String message) {
		Message = message;
	}
	
	public String getEnquiry_Date() {
		return Enquiry_Date;
	}
	
	public void setEnquiry_Date(String enquiry_Date) {
		Enquiry_Date = enquiry_Date;
	}
	
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
}