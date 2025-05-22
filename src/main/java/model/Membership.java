package model;

/**
 * Servlet implementation class Membership
 */
public class Membership {
	
	private String Membership_ID;
	private String Member_ID;
	private String Plan_ID;
	private String Start_Date;
	
	// For storing associated objects (not stored in database)
	private Member member;
	private Plan plan;
	
	public Membership(String membership_ID, String member_ID, String plan_ID, String start_Date) {
		this.Membership_ID = membership_ID;
		this.Member_ID = member_ID;
		this.Plan_ID = plan_ID;
		this.Start_Date = start_Date;
	}
	
	public Membership() {
		// TODO Auto-generated constructor stub
	}
	
	public String getMembership_ID() {
		return Membership_ID;
	}
	
	public void setMembership_ID(String membership_ID) {
		Membership_ID = membership_ID;
	}
	
	public String getMember_ID() {
		return Member_ID;
	}
	
	public void setMember_ID(String member_ID) {
		Member_ID = member_ID;
	}
	
	public String getPlan_ID() {
		return Plan_ID;
	}
	
	public void setPlan_ID(String plan_ID) {
		Plan_ID = plan_ID;
	}
	
	public String getStart_Date() {
		return Start_Date;
	}
	
	public void setStart_Date(String start_Date) {
		Start_Date = start_Date;
	}
	
	
	public Member getMember() {
		return member;
	}
	
	public void setMember(Member member) {
		this.member = member;
	}
	
	public Plan getPlan() {
		return plan;
	}
	
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
}