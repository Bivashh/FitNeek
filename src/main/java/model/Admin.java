package model;
import java.io.File;
import jakarta.servlet.http.Part;
import util.StringUtils;
/**
 * Servlet implementation class Admin
 */
public class Admin {
	
	private String Admin_ID;
	private String Admin_Username;
	private String Admin_Password;
	private String Admin_FullName;
	private String Admin_Email;
	private String Admin_PhoneNumber;
	private String imageUrlFromPart;
	
	public Admin(String admin_ID, String admin_Username, String admin_Password, String admin_FullName,
			String admin_Email, String admin_PhoneNumber, Part imagePart) {
		this.Admin_ID = admin_ID;
		this.Admin_Username = admin_Username;
		this.Admin_Password = admin_Password;
		this.Admin_FullName = admin_FullName;
		this.Admin_Email = admin_Email;
		this.Admin_PhoneNumber = admin_PhoneNumber;
		this.imageUrlFromPart = getImageUrl(imagePart);
	}
	
	public Admin() {
		// TODO Auto-generated constructor stub
	}
	
	public String getAdmin_ID() {
		return Admin_ID;
	}
	
	public void setAdmin_ID(String admin_ID) {
		Admin_ID = admin_ID;
	}
	
	public String getAdmin_Username() {
		return Admin_Username;
	}
	
	public void setAdmin_Username(String admin_Username) {
		Admin_Username = admin_Username;
	}
	
	public String getAdmin_Password() {
		return Admin_Password;
	}
	
	public void setAdmin_Password(String admin_Password) {
		Admin_Password = admin_Password;
	}
	
	public String getAdmin_FullName() {
		return Admin_FullName;
	}
	
	public void setAdmin_FullName(String admin_FullName) {
		Admin_FullName = admin_FullName;
	}
	
	public String getAdmin_Email() {
		return Admin_Email;
	}
	
	public void setAdmin_Email(String admin_Email) {
		Admin_Email = admin_Email;
	}
	
	public String getAdmin_PhoneNumber() {
		return Admin_PhoneNumber;
	}
	
	public void setAdmin_PhoneNumber(String admin_PhoneNumber) {
		Admin_PhoneNumber = admin_PhoneNumber;
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
	
	// Add this missing getter method
	public String getImageUrlFromDB() {
		return imageUrlFromPart;
	}
	
	private String getImageUrl(Part part) {
		String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_ADMIN;
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