package controller.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Admin;
import model.Enquiry;
import model.Member;
import model.Membership;
import model.UserLoginModel;
import model.PasswordEncryptionWithAes;
import model.Plan;
import util.StringUtils;

/**
 * Database controller class for handling database operations
 */
public class DbController {

    /**
     * Establishes a connection to the database
     * 
     * @return Connection object
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the driver class is not found
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(StringUtils.DRIVER_NAME);
        return DriverManager.getConnection(StringUtils.LOCALHOST_URL, StringUtils.LOCALHOST_USERNAME,
                StringUtils.LOCALHOST_PASSWORD);
    }
    
    /**
     * Registers a new member in the database
     * 
     * @param member_details Member object containing all details
     * @return 1 if registration successful, 0 if failed, -1 if exception occurred
     */
    public int registerMember(Member memberDetails) {

        try {
        	PreparedStatement stmt = getConnection().prepareStatement(StringUtils.QUERY_REGISTER_USER);

            stmt.setString(1, memberDetails.getMember_Username());
            String encryptedPassword = PasswordEncryptionWithAes.encrypt(
                memberDetails.getMember_Username(), memberDetails.getMember_Password());
            stmt.setString(2, encryptedPassword);
            stmt.setString(3, memberDetails.getMember_FirstName());
            stmt.setString(4, memberDetails.getMember_LastName());
            stmt.setString(5, memberDetails.getMember_Email());
            stmt.setString(6, memberDetails.getMember_PhoneNumber());
            stmt.setString(7, memberDetails.getMember_Age());
            stmt.setString(8, memberDetails.getImageUrlFromPart());

            int registerd_user = stmt.executeUpdate();
			if (registerd_user>0) {
				return 1;
				
			}
			else {
				return 0;
			}
        } catch (ClassNotFoundException | SQLException ex) {
    		// Print the stack trace for debugging purposes
    		ex.printStackTrace();
    		return -1; 
    	}
    		
    	}
    	
    /**
     * Authenticates a user (Member, Agent, or Admin)
     * 
     * @param userID User ID
     * @param userPassword User password
     * @return 1 if Member login successful, 2 if Agent login successful, 3 if Admin login successful,
     *         0 if credentials invalid, -1 if user not found, -2 if exception occurred
     */
    public int login(UserLoginModel loginUser) {
        try (Connection conn = getConnection()) {
            // Try regular user login first (with encryption)
            if (checkUserCredentials(conn, StringUtils.QUERY_LOGIN_USER_CHECK, loginUser)) {
                return 1; // Regular user login successful
            }

            // Then try admin login (without encryption)
            if (checkAdminCredentials(conn, StringUtils.QUERY_LOGIN_ADMIN_CHECK, loginUser)) {
                return 2; // Admin login successful
            }

            return 0; // Credentials do not match

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return -2; // Internal error
        }
    }

    /**
     * Checks credentials for regular users with password decryption
     */
    private boolean checkUserCredentials(Connection conn, String query, UserLoginModel loginUser) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginUser.getUser_ID());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString(StringUtils.USERNAME);
                    String encryptedPassword = rs.getString(StringUtils.PASSWORD);
                    
                    // Decrypt password for regular users
                    String decryptedPassword = PasswordEncryptionWithAes.decrypt(encryptedPassword, username);
                    
                    return username.equals(loginUser.getUser_ID()) && decryptedPassword.equals(loginUser.getUser_password());
                }
            }
        }
        return false;
    }

    /**
     * Checks credentials for admins without password decryption
     */
    private boolean checkAdminCredentials(Connection conn, String query, UserLoginModel loginUser) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, loginUser.getUser_ID());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString(StringUtils.USERNAME);
                    String plainPassword = rs.getString(StringUtils.PASSWORD);
                    
                    // No decryption for admin passwords - direct comparison
                    return username.equals(loginUser.getUser_ID()) && plainPassword.equals(loginUser.getUser_password());
                }
            }
        }
        return false;
    }

    /**
     * Gets the profile image name for a member
     * 
     * @param username Member username
     * @return Image name or null if not found or exception occurred
     */
    public String getMemberImageName(String username) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_USER_IMAGE);
            
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return result.getString("user_photo");
            }
            return null;
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * Gets the profile image name for an admin
     * 
     * @param username Admin username
     * @return Image name or null if not found or exception occurred
     */
    public String getAdminImageName(String username) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_ADMIN_IMAGE);
            
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return result.getString("admin_photo");
            }
            return null;
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int addNewPlan(Plan plan) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(StringUtils.QUERY_ADD_PLANS);
            statement.setString(1, plan.getPlan_Name());
            statement.setString(2, plan.getPlan_Description());
            statement.setString(3, plan.getPlan_Duration());
            statement.setString(4, plan.getPlan_Price());
            statement.setString(5, plan.getAdmin_ID());
            statement.setString(6, plan.getImageUrlFromPart());
            
            int plan_added = statement.executeUpdate();
            
            if (plan_added > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    public ArrayList<Member> getAllMemberInfo( ){
    	try {
    		PreparedStatement stmt = getConnection()
    				.prepareStatement(StringUtils.QUERY_GET_ALL_MEM_INFO);
    		
    					
    		ResultSet result = stmt.executeQuery();
    		ArrayList<Member> allMem = new ArrayList<Member>();
    		
    		while(result.next()) { // Move cursor to the first row
    			Member mem = new Member();
    			 mem.setMember_Username(result.getString("username"));
    			 mem.setMember_FirstName(result.getString("first_name"));
    			 mem.setMember_LastName(result.getString("last_name"));
    			 mem.setMember_Email(result.getString("email"));
    			 mem.setMember_PhoneNumber(result.getString("phone_number"));
    			 mem.setMember_Age(result.getString("age"));
    		   
    		   
    			 allMem.add(mem);
    		}	
    		return allMem;
    		
    	}catch (SQLException | ClassNotFoundException ex) {
    		ex.printStackTrace();
    		return null;
    		
    	}
    }
    
    
    public ArrayList<Membership> getAllMembershipInfo() {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_ALL_MEMBERSHIP_INFO);

            ResultSet result = stmt.executeQuery();
            ArrayList<Membership> allMembership = new ArrayList<>();

            while (result.next()) {
                Membership membership = new Membership();
                membership.setMembership_ID(result.getString("membership_id"));
                membership.setMember_ID(result.getString("member_id"));
                membership.setPlan_ID(result.getString("plan_id"));
                membership.setStart_Date(result.getString("start_date"));



                allMembership.add(membership);
            }
            return allMembership;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    
    public ArrayList<Plan> getAllPlanInfo() {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_ALL_PLANS);

            ResultSet result = stmt.executeQuery();
            ArrayList<Plan> allPlan = new ArrayList<>();

            while (result.next()) {
                Plan plan = new Plan();
                plan.setPlan_ID(result.getString("plan_id"));
                plan.setPlan_Name(result.getString("plan_name"));
                plan.setPlan_Description(result.getString("description"));
                plan.setPlan_Duration(result.getString("duration"));
                plan.setPlan_Price(result.getString("price"));
                plan.setAdmin_ID(result.getString("admin_id"));
                plan.setImageUrlFromDB(result.getString("image")); // If exists in DB

                allPlan.add(plan);
            }
            return allPlan;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    
    
    public ArrayList<Enquiry> getAllEnqInfo() {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_ALL_ENQUIRY_INFO);

            ResultSet result = stmt.executeQuery();
            ArrayList<Enquiry> enquiryList = new ArrayList<>();

            while (result.next()) {
                Enquiry enq = new Enquiry();
                enq.setEnquiry_ID(result.getString("enquiry_id"));
                enq.setMember_ID(result.getString("member_id"));
                enq.setSubject(result.getString("subject"));
                enq.setMessage(result.getString("message"));
                enq.setEnquiry_Date(result.getString("enquiry_date"));



                enquiryList.add(enq);
            }
            return enquiryList;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Admin getAdminInfoFromId(String admin_id) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_ADMIN_INFO_FROM_ADMIN_ID);

            stmt.setString(1, admin_id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Admin admin = new Admin();
                admin.setAdmin_ID(result.getString("admin_id"));
                admin.setAdmin_Username(result.getString("username"));
                admin.setAdmin_Password(result.getString("password"));
                admin.setAdmin_FullName(result.getString("full_name"));
                admin.setAdmin_Email(result.getString("email"));
                admin.setAdmin_PhoneNumber(result.getString("phone_number"));
                admin.setImageUrlFromDB(result.getString("image")); // if this column exists
                return admin;
            }
            return null;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Member getMemberInfoFromId(String mem_id) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_MEM_INFO_FROM_MEM_ID);

            stmt.setString(1, mem_id);

            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Member mem = new Member();
                mem.setMember_Username(result.getString("username"));
                mem.setMember_FirstName(result.getString("first_name"));
                mem.setMember_LastName(result.getString("last_name"));
                mem.setMember_Email(result.getString("email"));
                mem.setMember_PhoneNumber(result.getString("phone_number"));
                mem.setMember_Age(result.getString("age"));
                mem.setImageUrlFromDB(result.getString("profile_image"));
                return mem;
            }
            return null;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    
    
    
    public int addNewEnquiry(Enquiry enq) {
        try {
            PreparedStatement statement = getConnection().prepareStatement(StringUtils.QUERY_ADD_ENQUIRY);
            statement.setString(1, enq.getSubject());
            statement.setString(2, enq.getMessage());
            statement.setString(3, enq.getMember_ID());
            statement.setString(4, enq.getEnquiry_Date());

            int enq_added = statement.executeUpdate();

            if (enq_added > 0) {
                return 1;
            } else {
                return 0;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    
    public int addNewMembership(Membership membership) {
    	 try {
             PreparedStatement statement = getConnection().prepareStatement(StringUtils.QUERY_ADD_MEMBERSHIP);
             statement.setString(1, membership.getMember_ID());
             statement.setString(2, membership.getPlan_ID());
             statement.setString(3, membership.getStart_Date());
            
             int memship_added = statement.executeUpdate();

             if (memship_added > 0) {
                 return 1;
             } else {
                 return 0;
             }
         } catch (ClassNotFoundException | SQLException ex) {
             ex.printStackTrace();
             return -1;
         }
     }
     
    
    public Plan getPlanInfoFromId(String plan_id) {
        try {
            PreparedStatement stmt = getConnection()
                    .prepareStatement(StringUtils.QUERY_GET_PLAN_INFO_FROM_PLAN_ID);
            
            // Set the plan ID in the prepared statement
            stmt.setString(1, plan_id);
                        
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) { // Move cursor to the first row
                Plan plan = new Plan();
                
                plan.setPlan_ID(result.getString("plan_id"));
                plan.setPlan_Name(result.getString("plan_name"));
                plan.setPlan_Description(result.getString("description"));
                plan.setPlan_Duration(result.getString("duration"));
                plan.setPlan_Price(result.getString("price"));
                plan.setAdmin_ID(result.getString("admin_id"));
                plan.setImageUrlFromDB(result.getString("image"));
                
                
                return plan;
            }    
            return null;
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    
    public int updatePlan(Plan plan) {
        try {
            // Prepare a statement using the predefined query for plan update
            PreparedStatement statement = getConnection().prepareStatement(StringUtils.QUERY_UPDATE_PLAN);

            // Set the plan information in the prepared statement
            statement.setString(1, plan.getPlan_Name());
            statement.setString(2, plan.getImageUrlFromPart());
            statement.setString(3, plan.getPlan_Duration());
            statement.setString(4, plan.getPlan_Description());
            statement.setString(5, plan.getPlan_Price());
            statement.setString(6, plan.getAdmin_ID());
            statement.setString(7, plan.getPlan_ID()); 


            // Execute the update statement and store the number of affected rows
            int result = statement.executeUpdate();

            // Check if the update was successful (i.e., at least one row affected)
            if (result > 0) {
                return 1; 
            } else {
                return 0; 
            }

        } catch (ClassNotFoundException | SQLException ex) {
            // Print the stack trace for debugging purposes
            ex.printStackTrace();
            return -1; // Internal error
        } catch (Exception ex) {
            ex.printStackTrace();
            return -2; // Server error
        }
    }
    
    public int deletePlan(String plan_id) {
        try {
            // Start a transaction
            Connection connection = getConnection();
            connection.setAutoCommit(false);

            // Step 1: Delete any enrollments associated with this plan (if applicable)
            PreparedStatement deleteEnrollmentsStmt = connection.prepareStatement("DELETE FROM membership WHERE plan_id = ?");
            deleteEnrollmentsStmt.setString(1, plan_id);
            deleteEnrollmentsStmt.executeUpdate();

            // Step 2: Delete the plan itself
            PreparedStatement deletePlanStmt = connection.prepareStatement("DELETE FROM plan WHERE plan_id = ?");
            deletePlanStmt.setString(1, plan_id);
            int result = deletePlanStmt.executeUpdate();

            // Commit transaction
            connection.commit();

            // Return success if at least one plan was deleted
            if (result > 0) {
                return 1;
            } else {
                return 0; // No plan deleted (plan ID not found)
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return -1; // Internal error
        } catch (Exception ex) {
            ex.printStackTrace();
            return -2; // Server error
        }
    }
    
    
    public int deleteMember(String username) {
        try {
            // Start a transaction
            Connection connection = getConnection();
            connection.setAutoCommit(false);

            // Step 1: Delete any memberships associated with this member (if applicable)
            PreparedStatement deleteMembershipsStmt = connection.prepareStatement("DELETE FROM membership WHERE member_id = ?");
            deleteMembershipsStmt.setString(1, username);
            deleteMembershipsStmt.executeUpdate();
            
            // Step 2: Delete any bookings associated with this member (if applicable)
            PreparedStatement deleteBookingsStmt = connection.prepareStatement("DELETE FROM enquiry WHERE member_id = ?");
            deleteBookingsStmt.setString(1, username);
            deleteBookingsStmt.executeUpdate();
            

            // Step 3: Delete the member itself
            PreparedStatement deleteMemberStmt = connection.prepareStatement("DELETE FROM member WHERE username = ?");
            deleteMemberStmt.setString(1, username);
            int result = deleteMemberStmt.executeUpdate();

            // Commit transaction
            connection.commit();
            connection.setAutoCommit(true);

            // Return success if at least one member was deleted
            if (result > 0) {
                return 1;
            } else {
                return 0; // No member deleted (username not found)
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return -1; // Internal error
        } catch (Exception ex) {
            ex.printStackTrace();
            return -2; // Server error
        }
    }
    
    
    public int updateMember(Member mem) {
        try {
            // Prepare a statement using the predefined query for member update
            PreparedStatement statement = getConnection().prepareStatement(StringUtils.QUERY_UPDATE_MEMBER);

            // Set the member information in the prepared statement
            // Make sure to follow the same order as the query parameters
            statement.setString(1, mem.getMember_FirstName());    // first_name
            statement.setString(2, mem.getMember_LastName());     // last_name
            statement.setString(3, mem.getMember_Email());        // email
            statement.setString(4, mem.getMember_PhoneNumber());  // phone_number
            statement.setString(5, mem.getMember_Age());          // age
            statement.setString(6, mem.getImageUrlFromPart());    // profile_image
            statement.setString(7, mem.getMember_Username());     // WHERE username = ?

            // Execute the update statement and store the number of affected rows
            int result = statement.executeUpdate();

            // Check if the update was successful (i.e., at least one row affected)
            if (result > 0) {
                return 1;
            } else {
                return 0;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            // Print the stack trace for debugging purposes
            ex.printStackTrace();
            return -1; // Internal error
        } catch (Exception ex) {
            ex.printStackTrace();
            return -2; // Server error
        }
    }
    
    
    /**
     * Updates an admin's profile information in the database
     * 
     * @param admin The Admin object containing updated information
     * @return 1 if successful, 0 if no update occurred, negative values for errors
     */
    public int updateAdmin(Admin admin) {
        try {
            // Prepare a statement using the predefined query for admin update
            // Assuming you have this query defined in StringUtils as QUERY_UPDATE_ADMIN
            PreparedStatement statement = getConnection().prepareStatement(StringUtils.QUERY_UPDATE_ADMIN);

            // Set the admin information in the prepared statement
            // Make sure to follow the same order as the query parameters
            statement.setString(1, admin.getAdmin_FullName());    // full_name
            statement.setString(2, admin.getAdmin_Email());       // email
            statement.setString(3, admin.getAdmin_PhoneNumber()); // phone_number
            statement.setString(4, admin.getImageUrlFromPart());  // profile_image
            statement.setString(5, admin.getAdmin_Username());    // WHERE username = ?

            // Execute the update statement and store the number of affected rows
            int result = statement.executeUpdate();

            // Check if the update was successful (i.e., at least one row affected)
            if (result > 0) {
                return 1;
            } else {
                return 0;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            // Print the stack trace for debugging purposes
            ex.printStackTrace();
            return -1; // Internal error
        } catch (Exception ex) {
            ex.printStackTrace();
            return -2; // Server error
        }
    }

    /**
     * Deletes an admin from the database
     * 
     * @param username The username of the admin to delete
     * @return 1 if successful, 0 if not found, negative values for errors
     */
    public int deleteAdmin(String username) {
        try {
            // Start a transaction
            Connection connection = getConnection();
            connection.setAutoCommit(false);

            // First, delete any related records where this admin is referenced
            // For example, if admins are connected to system logs:
            PreparedStatement deleteLogsStmt = connection.prepareStatement("DELETE FROM plan WHERE admin_id = ?");
            deleteLogsStmt.setString(1, username);
            deleteLogsStmt.executeUpdate();

            // Then delete the admin account itself
            PreparedStatement deleteAdminStmt = connection.prepareStatement("DELETE FROM admin WHERE username = ?");
            deleteAdminStmt.setString(1, username);
            int result = deleteAdminStmt.executeUpdate();

            // Commit transaction
            connection.commit();
            connection.setAutoCommit(true);

            // Return success if at least one admin was deleted
            if (result > 0) {
                return 1;
            } else {
                return 0; // No admin deleted (username not found)
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            return -1; // Internal error
        } catch (Exception ex) {
            ex.printStackTrace();
            return -2; // Server error
        }
    }
    
}