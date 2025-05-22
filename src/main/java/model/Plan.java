package model;

import java.io.File;

import jakarta.servlet.http.Part;
import util.StringUtils;

/**
 * Servlet implementation class Plan
 */
public class Plan {
	private String plan_ID;
	private String Plan_Name;
	private String Plan_Description;
	private String Plan_Duration;
	private String Plan_Price;
	private String Admin_ID;
	private String imageUrlFromPart;

	// Default constructor
	public Plan() {
		// TODO Auto-generated constructor stub
	}

	// Parameterized constructor including plan_ID
	public Plan(String plan_ID, String plan_Name, String plan_Description, String plan_Duration,
			String plan_Price, String admin_ID, Part imagePart) {
		this.plan_ID = plan_ID;
		this.Plan_Name = plan_Name;
		this.Plan_Description = plan_Description;
		this.Plan_Duration = plan_Duration;
		this.Plan_Price = plan_Price;
		this.Admin_ID = admin_ID;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}

	// Constructor without plan_ID (for insert operations where ID is auto-generated)
	public Plan(String plan_Name, String plan_Description, String plan_Duration,
			String plan_Price, String admin_ID, Part imagePart) {
		this.Plan_Name = plan_Name;
		this.Plan_Description = plan_Description;
		this.Plan_Duration = plan_Duration;
		this.Plan_Price = plan_Price;
		this.Admin_ID = admin_ID;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}

	// Getter and Setter for plan_ID
	public String getPlan_ID() {
		return plan_ID;
	}

	public void setPlan_ID(String plan_ID) {
		this.plan_ID = plan_ID;
	}

	public String getPlan_Name() {
		return Plan_Name;
	}

	public void setPlan_Name(String plan_Name) {
		Plan_Name = plan_Name;
	}

	public String getPlan_Description() {
		return Plan_Description;
	}

	public void setPlan_Description(String plan_Description) {
		Plan_Description = plan_Description;
	}

	public String getPlan_Duration() {
		return Plan_Duration;
	}

	public void setPlan_Duration(String plan_Duration) {
		Plan_Duration = plan_Duration;
	}

	public String getPlan_Price() {
		return Plan_Price;
	}

	public void setPlan_Price(String plan_Price) {
		Plan_Price = plan_Price;
	}

	public String getAdmin_ID() {
		return Admin_ID;
	}

	public void setAdmin_ID(String admin_ID) {
		Admin_ID = admin_ID;
	}

	public String getImageUrlFromPart() {
		return imageUrlFromPart;
	}

	public void setImageUrlFromPart(String imageUrlFromPart) {
		this.imageUrlFromPart = imageUrlFromPart;
	}

	public void setImageUrlFromDB(String imageUrl) {
		this.imageUrlFromPart = imageUrl;
	}

	private String getImageUrl(Part part) {
		String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_PLAN;
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
