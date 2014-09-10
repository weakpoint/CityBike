package edu.citybike.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class TextPasswordEncoder implements PasswordEncoder{
	
	private PasswordEncoder encoder;
	
	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public String encode(CharSequence newPassword) {

		return encoder.encode(switchCharacters(newPassword.toString()));
	}

	@Override
	public boolean matches(CharSequence newPassword, String encodedPassword) {
		
		return encoder.matches(switchCharacters(newPassword.toString()), encodedPassword);
	}
	
	private String switchCharacters(String newPassword){
		StringBuilder sb = new StringBuilder(newPassword);
		if(sb.length() >= 2){
			char c = sb.charAt(sb.length()-1);
			sb.setCharAt(sb.length()-1, sb.charAt(0));
			sb.setCharAt(0, c);
			sb.insert((int)(sb.length()/2), '$');
			sb.append("#|#");
			System.out.println("HASLO: "+sb.toString());
			return sb.toString();
		}
		return newPassword;
	}

}
