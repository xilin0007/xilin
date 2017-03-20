package com.fxl.template.hospital.entity;

import java.util.Date;

import com.fxl.frame.base.BaseEntity;

public class HospitalInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String introduction;

    private String imgUrl;

    private String address;

    private Integer provinceId;

    private Integer cityId;

    private String orderKey;

    private Date addTime;

    private Integer level;

    private Integer parentId;

    private String phone;

    private Integer isValid;

    private Integer orderBy;

    private Integer isRemote;

    private Integer isConsultant;

    private Integer remotes;

    private Integer consultants;

    private Integer isWeight;

    private Integer isSchool;

    private Byte isBlood;

    private Byte isClass;

    private Byte isMobile;

    private Byte isPayment;

    private Integer isAutonomy;

    private Byte isVideo;

    private Byte isNetwork;

    private Byte isLease;

    private String hospitalKey;

    private Byte isIntegratedService;

    private String foodWarning;

    @Override
	public Integer getId() {
        return id;
    }

    @Override
	public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey == null ? null : orderKey.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(Integer isRemote) {
        this.isRemote = isRemote;
    }

    public Integer getIsConsultant() {
        return isConsultant;
    }

    public void setIsConsultant(Integer isConsultant) {
        this.isConsultant = isConsultant;
    }

    public Integer getRemotes() {
        return remotes;
    }

    public void setRemotes(Integer remotes) {
        this.remotes = remotes;
    }

    public Integer getConsultants() {
        return consultants;
    }

    public void setConsultants(Integer consultants) {
        this.consultants = consultants;
    }

    public Integer getIsWeight() {
        return isWeight;
    }

    public void setIsWeight(Integer isWeight) {
        this.isWeight = isWeight;
    }

    public Integer getIsSchool() {
        return isSchool;
    }

    public void setIsSchool(Integer isSchool) {
        this.isSchool = isSchool;
    }

    public Byte getIsBlood() {
        return isBlood;
    }

    public void setIsBlood(Byte isBlood) {
        this.isBlood = isBlood;
    }

    public Byte getIsClass() {
        return isClass;
    }

    public void setIsClass(Byte isClass) {
        this.isClass = isClass;
    }

    public Byte getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(Byte isMobile) {
        this.isMobile = isMobile;
    }

    public Byte getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(Byte isPayment) {
        this.isPayment = isPayment;
    }

    public Integer getIsAutonomy() {
        return isAutonomy;
    }

    public void setIsAutonomy(Integer isAutonomy) {
        this.isAutonomy = isAutonomy;
    }

    public Byte getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Byte isVideo) {
        this.isVideo = isVideo;
    }

    public Byte getIsNetwork() {
        return isNetwork;
    }

    public void setIsNetwork(Byte isNetwork) {
        this.isNetwork = isNetwork;
    }

    public Byte getIsLease() {
        return isLease;
    }

    public void setIsLease(Byte isLease) {
        this.isLease = isLease;
    }

    public String getHospitalKey() {
        return hospitalKey;
    }

    public void setHospitalKey(String hospitalKey) {
        this.hospitalKey = hospitalKey == null ? null : hospitalKey.trim();
    }

    public Byte getIsIntegratedService() {
        return isIntegratedService;
    }

    public void setIsIntegratedService(Byte isIntegratedService) {
        this.isIntegratedService = isIntegratedService;
    }

    public String getFoodWarning() {
        return foodWarning;
    }

    public void setFoodWarning(String foodWarning) {
        this.foodWarning = foodWarning == null ? null : foodWarning.trim();
    }
}