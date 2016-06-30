package com.alijiujiu.koncheng.restrant;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class CertificationRestrant extends BmobObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String address;
	private Object location;
	private String shopPhone;
	private Integer industryType;
	private String certificationManName;
	private String certificationManTelephone;
	private BmobFile shopPicture;
	private BmobFile licensePicture;
	private String licenseNumber;
	private BmobFile idCardPicture;
	private BmobFile holdIdCardPicture;
	private String idCardNumber;
	private String submitTime;
	private String certificationTime;
	private Integer certificationType;
	private String certificationManId;
	private String title;
	private String taste;
	private String cuisine;
	private Float price;
	private String restrantId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Object getLocation() {
		return location;
	}

	public void setLocation(Object location) {
		this.location = location;
	}

	public String getShopPhone() {
		return shopPhone;
	}

	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}

	public Integer getIndustryType() {
		return industryType;
	}

	public void setIndustryType(Integer industryType) {
		this.industryType = industryType;
	}

	public String getCertificationManName() {
		return certificationManName;
	}

	public void setCertificationManName(String certificationManName) {
		this.certificationManName = certificationManName;
	}

	public String getCertificationManTelephone() {
		return certificationManTelephone;
	}

	public void setCertificationManTelephone(String certificationManTelephone) {
		this.certificationManTelephone = certificationManTelephone;
	}

	public BmobFile getShopPicture() {
		return shopPicture;
	}

	public void setShopPicture(BmobFile shopPicture) {
		this.shopPicture = shopPicture;
	}

	public BmobFile getLicensePicture() {
		return licensePicture;
	}

	public void setLicensePicture(BmobFile licensePicture) {
		this.licensePicture = licensePicture;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public BmobFile getIdCardPicture() {
		return idCardPicture;
	}

	public void setIdCardPicture(BmobFile idCardPicture) {
		this.idCardPicture = idCardPicture;
	}

	public BmobFile getHoldIdCardPicture() {
		return holdIdCardPicture;
	}

	public void setHoldIdCardPicture(BmobFile holdIdCardPicture) {
		this.holdIdCardPicture = holdIdCardPicture;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getCertificationTime() {
		return certificationTime;
	}

	public void setCertificationTime(String certificationTime) {
		this.certificationTime = certificationTime;
	}

	public Integer getCertificationType() {
		return certificationType;
	}

	public void setCertificationType(Integer certificationType) {
		this.certificationType = certificationType;
	}

	public String getCertificationManId() {
		return certificationManId;
	}

	public void setCertificationManId(String certificationManId) {
		this.certificationManId = certificationManId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getRestrantId() {
		return restrantId;
	}

	public void setRestrantId(String restrantId) {
		this.restrantId = restrantId;
	}
}
