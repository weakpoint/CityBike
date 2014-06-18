package edu.citybike.model.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.appengine.api.datastore.Text;

import edu.citybike.model.Address;
import edu.citybike.model.Credentials;
import edu.citybike.model.User;

public class UserInfo implements UserDetails{

/*	private String name;
	private String lastName;
	private Address address;
	private String phoneNumber;
	private String emailAddress;
	private String password;
	private Text notes;
	private String role;
	private Long overallRentalTime;
	private Double overallRentalCost;
	private String userCode;
	private String rentalNetworkCode;
*/	private User user;
	private Credentials credentials;
	
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

	public UserInfo(User user, Credentials credentials) {
		this.user = user;
		this.credentials = credentials;
/*		this.name = user.getName();
		this.lastName = user.getLastName();
		this.address = user.getAddress();
		this.phoneNumber = user.getPhoneNumber();
		this.emailAddress = user.getEmailAddress();
		this.password = credentials.getPassword();
		this.notes = user.getNotes();
		this.role = user.getRole();
		this.overallRentalTime = user.getOverallRentalTime();
		this.overallRentalCost = user.getOverallRentalCost();
		this.userCode = user.getUserCode();
		this.rentalNetworkCode = user.getRentalNetworkCode();
*/		
		authorities = new ArrayList<>();
		authorities.add(new GrantedAuthorityImpl(user.getRole()));

	}

	public UserInfo(){
		this(new User(), new Credentials());
	}
	
	public User getUser(){
		///if(user.getUserCode().equals("")){
		/*	
			user.setAddress(address);
			user.setEmailAddress(emailAddress);
			user.setLastName(lastName);
			user.setName(name);
			user.setNotes(notes);
			user.setOverallRentalCost(overallRentalCost);
			user.setOverallRentalTime(overallRentalTime);
			user.setPhoneNumber(phoneNumber);
			user.setRole(role);
			user.setUserCode(userCode);
			user.setRentalNetworkCode(rentalNetworkCode);
		*/
		//}
		return user;
	}
	
	public Credentials getCredentials(){
/*		//if(credentials.getPassword().equals("")){
			credentials.setEmailAddress(emailAddress);
			credentials.setPassword(password);
			credentials.setRentalNetworkCode(rentalNetworkCode);
		//}
*/		return credentials;
	}
	
	public String getPassword() {
		return credentials.getPassword();
	}

	public void setPassword(String password) {
		credentials.setPassword(password);
	}
	
	public String getPhoneNumber() {
		return user.getPhoneNumber();
	}

	public void setPhoneNumber(String phoneNumber) {
		user.setPhoneNumber(phoneNumber);
	}

	public String getName() {
		return user.getName();
	}

	public void setName(String name) {
		user.setName(name);
	}

	public String getLastName() {
		return user.getLastName();
	}

	public void setLastName(String lastName) {
		user.setLastName(lastName);
	}

	public Address getAddress() {
		return user.getAddress();
	}

	public void setAddress(Address address) {
		user.setAddress(address);
	}

	public String getEmailAddress() {
		return user.getEmailAddress();
	}

	public void setEmailAddress(String emailAddress) {
		user.setEmailAddress(emailAddress);
	}

	public String getNotes() {
		return user.getNotes().getValue();
	}

	public void setNotes(String notes) {
		user.setNotes(new Text(notes));
	}

	public Long getOverallRentalTime() {
		return user.getOverallRentalTime();
	}

	public void setOverallRentalTime(Long overallRentalTime) {
		user.setOverallRentalTime(overallRentalTime);
	}

	public Double getOverallRentalCost() {
		return user.getOverallRentalCost();
	}

	public void setOverallRentalCost(Double overallRentalCost) {
		user.setOverallRentalCost(overallRentalCost);
	}

	public String getUserCode() {
		return user.getUserCode();
	}

	public void setUserCode(String userCode) {
		user.setUserCode(userCode);
	}

	public String getRentalNetworkCode() {
		return user.getRentalNetworkCode();
	}

	public void setRentalNetworkCode(String rentalNetworkCode) {
		user.setRentalNetworkCode(rentalNetworkCode);
	}

	public String getRole() {
		return user.getRole();
	}

	public void setRole(String role) {
		user.setRole(role);
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
     
    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
     
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }
     
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
     
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
 
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
 
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	@Override
	public String getUsername() {
		return user.getEmailAddress();
	}

	@Override
	public String toString() {
		return "UserInfo [user=" + user + ", credentials=" + credentials + ", authorities=" + authorities
				+ ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled + "]";
	}
	
}
