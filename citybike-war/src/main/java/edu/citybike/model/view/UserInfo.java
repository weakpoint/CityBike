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

	private String name;
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
	private User user;
	private Credentials credentials;
	
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

	public UserInfo(User user, Credentials credentials) {
		this.user = user;
		this.credentials = credentials;
		this.name = user.getName();
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
		
		authorities = new ArrayList<>();
		authorities.add(new GrantedAuthorityImpl(this.role));

	}

	public UserInfo(){
		this(new User(), new Credentials());
	}
	
	public User getUser(){
		if(user.getUserCode().equals("")){
			
			user.setAddress(address);
			user.setEmailAddress(emailAddress);
			user.setName(lastName);
			user.setName(lastName);
			user.setNotes(notes);
			user.setOverallRentalCost(overallRentalCost);
			user.setOverallRentalTime(overallRentalTime);
			user.setPhoneNumber(phoneNumber);
			user.setRole(role);
			user.setUserCode(userCode);
			user.setRentalNetworkCode(rentalNetworkCode);
		}
		return user;
	}
	
	public Credentials getCredentials(){
		if(credentials.getPassword().equals("")){
			credentials.setEmailAddress(emailAddress);
			credentials.setPassword(password);
			credentials.setRentalNetworkCode(rentalNetworkCode);
		}
		return credentials;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNotes() {
		return notes.getValue();
	}

	public void setNotes(String notes) {
		this.notes = new Text(notes);
	}

	public Long getOverallRentalTime() {
		return overallRentalTime;
	}

	public void setOverallRentalTime(Long overallRentalTime) {
		this.overallRentalTime = overallRentalTime;
	}

	public Double getOverallRentalCost() {
		return overallRentalCost;
	}

	public void setOverallRentalCost(Double overallRentalCost) {
		this.overallRentalCost = overallRentalCost;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRentalNetworkCode() {
		return rentalNetworkCode;
	}

	public void setRentalNetworkCode(String rentalNetworkCode) {
		this.rentalNetworkCode = rentalNetworkCode;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
		return emailAddress;
	}

}
