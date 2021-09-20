package com.example.demoapplication.mvp.model.jsonmodel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginBean implements Serializable{
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    private final static long serialVersionUID = 3988207235931338973L;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LoginBean withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoginBean withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public LoginBean withResult(List<Result> result) {
        this.result = result;
        return this;
    }



    public class Result implements Serializable
    {

        @SerializedName("driv_id")
        @Expose
        private String drivId;
        @SerializedName("driv_vehi_no")
        @Expose
        private String drivVehiNo;
        @SerializedName("driv_photo")
        @Expose
        private String drivPhoto;
        @SerializedName("driv_name")
        @Expose
        private String drivName;
        @SerializedName("driv_email")
        @Expose
        private String drivEmail;
        @SerializedName("driv_password")
        @Expose
        private String drivPassword;
        @SerializedName("driv_mobile")
        @Expose
        private String drivMobile;
        @SerializedName("driv_org_id")
        @Expose
        private String drivOrgId;
        @SerializedName("driv_documents")
        @Expose
        private String drivDocuments;
        @SerializedName("driv_status")
        @Expose
        private String drivStatus;
        @SerializedName("driv_otp")
        @Expose
        private String drivOtp;
        @SerializedName("driv_created_by")
        @Expose
        private String drivCreatedBy;
        @SerializedName("driv_updated_by")
        @Expose
        private String drivUpdatedBy;
        @SerializedName("driv_created")
        @Expose
        private String drivCreated;
        @SerializedName("driv_updated")
        @Expose
        private String drivUpdated;
        @SerializedName("driv_session")
        @Expose
        private String drivSession;
        @SerializedName("vehi_id")
        @Expose
        private String vehiId;
        @SerializedName("vehi_image")
        @Expose
        private String vehiImage;
        @SerializedName("vehi_no")
        @Expose
        private String vehiNo;
        @SerializedName("vehi_length")
        @Expose
        private String vehiLength;
        @SerializedName("vehi_width")
        @Expose
        private String vehiWidth;
        @SerializedName("vehi_height")
        @Expose
        private String vehiHeight;
        @SerializedName("vehi_volume")
        @Expose
        private String vehiVolume;
        @SerializedName("vehi_transportation_name")
        @Expose
        private String vehiTransportationName;
        @SerializedName("vehi_registration_no")
        @Expose
        private String vehiRegistrationNo;
        @SerializedName("vehi_device_no")
        @Expose
        private String vehiDeviceNo;
        @SerializedName("vehi_device_brand")
        @Expose
        private String vehiDeviceBrand;
        @SerializedName("vehi_iemi_no")
        @Expose
        private String vehiIemiNo;
        @SerializedName("vehi_status")
        @Expose
        private String vehiStatus;
        @SerializedName("vehi_deli_status")
        @Expose
        private String vehiDeliStatus;
        @SerializedName("vehi_fcm_key")
        @Expose
        private String vehiFcmKey;
        @SerializedName("vehi_driv_login_id")
        @Expose
        private String vehiDrivLoginId;
        @SerializedName("vehi_created_by")
        @Expose
        private String vehiCreatedBy;
        @SerializedName("vehi_updated_by")
        @Expose
        private String vehiUpdatedBy;
        @SerializedName("vehi_created")
        @Expose
        private String vehiCreated;
        @SerializedName("vehi_updated")
        @Expose
        private String vehiUpdated;
        private final static long serialVersionUID = 6748327520636077530L;

        public String getDrivId() {
            return drivId;
        }

        public void setDrivId(String drivId) {
            this.drivId = drivId;
        }

        public Result withDrivId(String drivId) {
            this.drivId = drivId;
            return this;
        }

        public String getDrivVehiNo() {
            return drivVehiNo;
        }

        public void setDrivVehiNo(String drivVehiNo) {
            this.drivVehiNo = drivVehiNo;
        }

        public Result withDrivVehiNo(String drivVehiNo) {
            this.drivVehiNo = drivVehiNo;
            return this;
        }

        public String getDrivPhoto() {
            return drivPhoto;
        }

        public void setDrivPhoto(String drivPhoto) {
            this.drivPhoto = drivPhoto;
        }

        public Result withDrivPhoto(String drivPhoto) {
            this.drivPhoto = drivPhoto;
            return this;
        }

        public String getDrivName() {
            return drivName;
        }

        public void setDrivName(String drivName) {
            this.drivName = drivName;
        }

        public Result withDrivName(String drivName) {
            this.drivName = drivName;
            return this;
        }

        public String getDrivEmail() {
            return drivEmail;
        }

        public void setDrivEmail(String drivEmail) {
            this.drivEmail = drivEmail;
        }

        public Result withDrivEmail(String drivEmail) {
            this.drivEmail = drivEmail;
            return this;
        }

        public String getDrivPassword() {
            return drivPassword;
        }

        public void setDrivPassword(String drivPassword) {
            this.drivPassword = drivPassword;
        }

        public Result withDrivPassword(String drivPassword) {
            this.drivPassword = drivPassword;
            return this;
        }

        public String getDrivMobile() {
            return drivMobile;
        }

        public void setDrivMobile(String drivMobile) {
            this.drivMobile = drivMobile;
        }

        public String getDrivOrgId() {
            return drivOrgId;
        }

        public void setDrivOrgId(String drivOrgId) {
            this.drivOrgId = drivOrgId;
        }

        public Result withDrivMobile(String drivMobile) {
            this.drivMobile = drivMobile;
            return this;
        }

        public String getDrivDocuments() {
            return drivDocuments;
        }

        public void setDrivDocuments(String drivDocuments) {
            this.drivDocuments = drivDocuments;
        }

        public Result withDrivDocuments(String drivDocuments) {
            this.drivDocuments = drivDocuments;
            return this;
        }

        public String getDrivStatus() {
            return drivStatus;
        }

        public void setDrivStatus(String drivStatus) {
            this.drivStatus = drivStatus;
        }

        public Result withDrivStatus(String drivStatus) {
            this.drivStatus = drivStatus;
            return this;
        }

        public String getDrivOtp() {
            return drivOtp;
        }

        public void setDrivOtp(String drivOtp) {
            this.drivOtp = drivOtp;
        }

        public Result withDrivOtp(String drivOtp) {
            this.drivOtp = drivOtp;
            return this;
        }

        public String getDrivCreatedBy() {
            return drivCreatedBy;
        }

        public void setDrivCreatedBy(String drivCreatedBy) {
            this.drivCreatedBy = drivCreatedBy;
        }

        public Result withDrivCreatedBy(String drivCreatedBy) {
            this.drivCreatedBy = drivCreatedBy;
            return this;
        }

        public String getDrivUpdatedBy() {
            return drivUpdatedBy;
        }

        public void setDrivUpdatedBy(String drivUpdatedBy) {
            this.drivUpdatedBy = drivUpdatedBy;
        }

        public Result withDrivUpdatedBy(String drivUpdatedBy) {
            this.drivUpdatedBy = drivUpdatedBy;
            return this;
        }

        public String getDrivCreated() {
            return drivCreated;
        }

        public void setDrivCreated(String drivCreated) {
            this.drivCreated = drivCreated;
        }

        public Result withDrivCreated(String drivCreated) {
            this.drivCreated = drivCreated;
            return this;
        }

        public String getDrivUpdated() {
            return drivUpdated;
        }

        public void setDrivUpdated(String drivUpdated) {
            this.drivUpdated = drivUpdated;
        }

        public Result withDrivUpdated(String drivUpdated) {
            this.drivUpdated = drivUpdated;
            return this;
        }

        public String getDrivSession() {
            return drivSession;
        }

        public void setDrivSession(String drivSession) {
            this.drivSession = drivSession;
        }

        public Result withDrivSession(String drivSession) {
            this.drivSession = drivSession;
            return this;
        }

        public String getVehiId() {
            return vehiId;
        }

        public void setVehiId(String vehiId) {
            this.vehiId = vehiId;
        }

        public Result withVehiId(String vehiId) {
            this.vehiId = vehiId;
            return this;
        }

        public String getVehiImage() {
            return vehiImage;
        }

        public void setVehiImage(String vehiImage) {
            this.vehiImage = vehiImage;
        }

        public Result withVehiImage(String vehiImage) {
            this.vehiImage = vehiImage;
            return this;
        }

        public String getVehiNo() {
            return vehiNo;
        }

        public void setVehiNo(String vehiNo) {
            this.vehiNo = vehiNo;
        }

        public Result withVehiNo(String vehiNo) {
            this.vehiNo = vehiNo;
            return this;
        }

        public String getVehiLength() {
            return vehiLength;
        }

        public void setVehiLength(String vehiLength) {
            this.vehiLength = vehiLength;
        }

        public Result withVehiLength(String vehiLength) {
            this.vehiLength = vehiLength;
            return this;
        }

        public String getVehiWidth() {
            return vehiWidth;
        }

        public void setVehiWidth(String vehiWidth) {
            this.vehiWidth = vehiWidth;
        }

        public Result withVehiWidth(String vehiWidth) {
            this.vehiWidth = vehiWidth;
            return this;
        }

        public String getVehiHeight() {
            return vehiHeight;
        }

        public void setVehiHeight(String vehiHeight) {
            this.vehiHeight = vehiHeight;
        }

        public Result withVehiHeight(String vehiHeight) {
            this.vehiHeight = vehiHeight;
            return this;
        }

        public String getVehiVolume() {
            return vehiVolume;
        }

        public void setVehiVolume(String vehiVolume) {
            this.vehiVolume = vehiVolume;
        }

        public Result withVehiVolume(String vehiVolume) {
            this.vehiVolume = vehiVolume;
            return this;
        }

        public String getVehiTransportationName() {
            return vehiTransportationName;
        }

        public void setVehiTransportationName(String vehiTransportationName) {
            this.vehiTransportationName = vehiTransportationName;
        }

        public Result withVehiTransportationName(String vehiTransportationName) {
            this.vehiTransportationName = vehiTransportationName;
            return this;
        }

        public String getVehiRegistrationNo() {
            return vehiRegistrationNo;
        }

        public void setVehiRegistrationNo(String vehiRegistrationNo) {
            this.vehiRegistrationNo = vehiRegistrationNo;
        }

        public Result withVehiRegistrationNo(String vehiRegistrationNo) {
            this.vehiRegistrationNo = vehiRegistrationNo;
            return this;
        }

        public String getVehiDeviceNo() {
            return vehiDeviceNo;
        }

        public void setVehiDeviceNo(String vehiDeviceNo) {
            this.vehiDeviceNo = vehiDeviceNo;
        }

        public Result withVehiDeviceNo(String vehiDeviceNo) {
            this.vehiDeviceNo = vehiDeviceNo;
            return this;
        }

        public String getVehiDeviceBrand() {
            return vehiDeviceBrand;
        }

        public void setVehiDeviceBrand(String vehiDeviceBrand) {
            this.vehiDeviceBrand = vehiDeviceBrand;
        }

        public Result withVehiDeviceBrand(String vehiDeviceBrand) {
            this.vehiDeviceBrand = vehiDeviceBrand;
            return this;
        }

        public String getVehiIemiNo() {
            return vehiIemiNo;
        }

        public void setVehiIemiNo(String vehiIemiNo) {
            this.vehiIemiNo = vehiIemiNo;
        }

        public Result withVehiIemiNo(String vehiIemiNo) {
            this.vehiIemiNo = vehiIemiNo;
            return this;
        }

        public String getVehiStatus() {
            return vehiStatus;
        }

        public void setVehiStatus(String vehiStatus) {
            this.vehiStatus = vehiStatus;
        }

        public Result withVehiStatus(String vehiStatus) {
            this.vehiStatus = vehiStatus;
            return this;
        }

        public String getVehiDeliStatus() {
            return vehiDeliStatus;
        }

        public void setVehiDeliStatus(String vehiDeliStatus) {
            this.vehiDeliStatus = vehiDeliStatus;
        }

        public Result withVehiDeliStatus(String vehiDeliStatus) {
            this.vehiDeliStatus = vehiDeliStatus;
            return this;
        }

        public String getVehiFcmKey() {
            return vehiFcmKey;
        }

        public void setVehiFcmKey(String vehiFcmKey) {
            this.vehiFcmKey = vehiFcmKey;
        }

        public Result withVehiFcmKey(String vehiFcmKey) {
            this.vehiFcmKey = vehiFcmKey;
            return this;
        }

        public String getVehiDrivLoginId() {
            return vehiDrivLoginId;
        }

        public void setVehiDrivLoginId(String vehiDrivLoginId) {
            this.vehiDrivLoginId = vehiDrivLoginId;
        }

        public Result withVehiDrivLoginId(String vehiDrivLoginId) {
            this.vehiDrivLoginId = vehiDrivLoginId;
            return this;
        }

        public String getVehiCreatedBy() {
            return vehiCreatedBy;
        }

        public void setVehiCreatedBy(String vehiCreatedBy) {
            this.vehiCreatedBy = vehiCreatedBy;
        }

        public Result withVehiCreatedBy(String vehiCreatedBy) {
            this.vehiCreatedBy = vehiCreatedBy;
            return this;
        }

        public String getVehiUpdatedBy() {
            return vehiUpdatedBy;
        }

        public void setVehiUpdatedBy(String vehiUpdatedBy) {
            this.vehiUpdatedBy = vehiUpdatedBy;
        }

        public Result withVehiUpdatedBy(String vehiUpdatedBy) {
            this.vehiUpdatedBy = vehiUpdatedBy;
            return this;
        }

        public String getVehiCreated() {
            return vehiCreated;
        }

        public void setVehiCreated(String vehiCreated) {
            this.vehiCreated = vehiCreated;
        }

        public Result withVehiCreated(String vehiCreated) {
            this.vehiCreated = vehiCreated;
            return this;
        }

        public String getVehiUpdated() {
            return vehiUpdated;
        }

        public void setVehiUpdated(String vehiUpdated) {
            this.vehiUpdated = vehiUpdated;
        }

        public Result withVehiUpdated(String vehiUpdated) {
            this.vehiUpdated = vehiUpdated;
            return this;
        }

    }
}