package org.vgr.app.domain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Date;

public class Profile implements Serializable{
   
	private static final long serialVersionUID = 1L;
	private int userId;
    private String homeurl;
    private String password;
    private String firstName;
    private String lastName;
    private long  phoneNbr;
    private String email;
    private Date dob;
    private String img;
    private char activeInd;
    private Date createDate;
    private Date modifyDate;
    private FileReader inputText;
    private FileWriter outputText;
    private FileInputStream inputImage ;
    private FileOutputStream ouputImage;
    private int genderNbr;
    private String gender;
    private String religion;
    private int religionId;
    private int maritalStatusId;
    private String martitalStatus;
    private String aboutMe;
    private String address;
    private String country;
    private String state;
    private String district;

    public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getHomeurl() {
		return homeurl;
	}
	public void setHomeurl(String homeurl) {
		this.homeurl = homeurl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getPhoneNbr() {
		return phoneNbr;
	}
	public void setPhoneNbr(long phoneNbr) {
		this.phoneNbr = phoneNbr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public char getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(char activeInd) {
		this.activeInd = activeInd;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public FileReader getInputText() {
		return inputText;
	}
	public void setInputText(FileReader inputText) {
		this.inputText = inputText;
	}
	public FileWriter getOutputText() {
		return outputText;
	}
	public void setOutputText(FileWriter outputText) {
		this.outputText = outputText;
	}
	public FileInputStream getInputImage() {
		return inputImage;
	}
	public void setInputImage(FileInputStream inputImage) {
		this.inputImage = inputImage;
	}
	public FileOutputStream getOuputImage() {
		return ouputImage;
	}
	public void setOuputImage(FileOutputStream ouputImage) {
		this.ouputImage = ouputImage;
	}
	public int getGenderNbr() {
		return genderNbr;
	}
	public void setGenderNbr(int genderNbr) {
		this.genderNbr = genderNbr;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public int getReligionId() {
		return religionId;
	}
	public void setReligionId(int religionId) {
		this.religionId = religionId;
	}
	public int getMaritalStatusId() {
		return maritalStatusId;
	}
	public void setMaritalStatusId(int maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}
	public String getMartitalStatus() {
		return martitalStatus;
	}
	public void setMartitalStatus(String martitalStatus) {
		this.martitalStatus = martitalStatus;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
    

}
