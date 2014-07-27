package edu.citybike.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class TextPasswordEncoder implements PasswordEncoder{
	
	private PasswordEncoder encoder;
	
	public void setEncoder(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public String encode(CharSequence newPassword) {

		return encoder.encode(newPassword);
	}

	@Override
	public boolean matches(CharSequence newPassword, String encodedPassword) {
		return encoder.matches(newPassword, encodedPassword);
	}

}
