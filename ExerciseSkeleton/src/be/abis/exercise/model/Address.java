package be.abis.exercise.model;

import be.abis.exercise.exception.ZipCodeNotCorrectException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {
	
	private String street;
	private String nr;
	private String zipCode;
	private String town;
	private String country;
	private String countryCode;
	
	public Address(){
		
	}
	
	public Address(String street, String nr, String zipCode, String town, String country, String countryCode) {
		this();
		this.street = street;
		this.nr = nr;
		this.zipCode = zipCode;
		this.town = town;
		this.country = country;
		this.countryCode = countryCode;
	}

	// BUSINESS METHODS
	public void checkZipCode() throws ZipCodeNotCorrectException {

		if (countryCode.equals("BE")){
			String regexBE = "\\d{4}";
			Pattern p = Pattern.compile(regexBE);
			Matcher m = p.matcher(zipCode);
			if (!m.matches()) throw new ZipCodeNotCorrectException("Zip code is not correct for Belgium");
		} else if (countryCode.equals("NL")){
			String regexNL = "\\d{4}[A-Z]{2}";
			Pattern p = Pattern.compile(regexNL);
			Matcher m = p.matcher(zipCode);
			if (!m.matches()) throw new ZipCodeNotCorrectException("Zip code is not correct for the Netherlands");
		}
	}

	// GETTERS AND SETTERS
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	
	public String toString(){
		return street + " " + nr + ", " + countryCode + " - " +zipCode+ " " + town;
	}
	

}
