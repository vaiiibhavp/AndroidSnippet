package com.example.demoapplication.mvp.model.jsonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HomeBean implements Serializable
{

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("result")
@Expose
private Result result;
private final static long serialVersionUID = 8302499116014785896L;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public HomeBean withStatus(Integer status) {
this.status = status;
return this;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public HomeBean withMsg(String msg) {
this.msg = msg;
return this;
}

public Result getResult() {
return result;
}

public void setResult(Result result) {
this.result = result;
}

public HomeBean withResult(Result result) {
this.result = result;
return this;
}

    public class OrgDetails implements Serializable
    {

        @SerializedName("org_name")
        @Expose
        private String orgName;
        @SerializedName("org_address")
        @Expose
        private String orgAddress;
        private final static long serialVersionUID = 1201176606122583309L;

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public OrgDetails withOrgName(String orgName) {
            this.orgName = orgName;
            return this;
        }

        public String getOrgAddress() {
            return orgAddress;
        }

        public void setOrgAddress(String orgAddress) {
            this.orgAddress = orgAddress;
        }

        public OrgDetails withOrgAddress(String orgAddress) {
            this.orgAddress = orgAddress;
            return this;
        }

    }

    public class Result implements Serializable
    {

        @SerializedName("vehi_status")
        @Expose
        private String vehiStatus;
        @SerializedName("org_details")
        @Expose
        private OrgDetails orgDetails;
        @SerializedName("booking_no")
        @Expose
        private String bookingNo;
        @SerializedName("current_book_id")
        @Expose
        private String currentBookId;
        @SerializedName("km")
        @Expose
        private String km;@SerializedName("hour")
        @Expose
        private String hour;
        @SerializedName("ride_no")
        @Expose
        private String rideNo;

        @SerializedName("cust_name")
        @Expose
        private String cust_name;
        @SerializedName("cust_address")
        @Expose
        private String cust_address;
        @SerializedName("cust_lat")
        @Expose
        private String custLat;
        @SerializedName("cust_long")
        @Expose
        private String custLong;
        @SerializedName("org_lat")
        @Expose
        private String orgLat;
        @SerializedName("org_long")
        @Expose
        private String orgLong;
  @SerializedName("vehicle_image")
        @Expose
        private String vehicleImage;

        @SerializedName("total_rides")
        @Expose
        private List<TotalRide> totalRides = null;
        @SerializedName("total_rides_count")
        @Expose
        private Integer totalRidesCount;
        private final static long serialVersionUID = 7974246535259697861L;

        public String getCust_name() {
            return cust_name;
        }

        public void setCust_name(String cust_name) {
            this.cust_name = cust_name;
        }

        public String getCust_address() {
            return cust_address;
        }

        public void setCust_address(String cust_address) {
            this.cust_address = cust_address;
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

        public OrgDetails getOrgDetails() {
            return orgDetails;
        }

        public void setOrgDetails(OrgDetails orgDetails) {
            this.orgDetails = orgDetails;
        }

        public Result withOrgDetails(OrgDetails orgDetails) {
            this.orgDetails = orgDetails;
            return this;
        }

        public String getBookingNo() {
            return bookingNo;
        }

        public void setBookingNo(String bookingNo) {
            this.bookingNo = bookingNo;
        }

        public Result withBookingNo(String bookingNo) {
            this.bookingNo = bookingNo;
            return this;
        }

        public List<TotalRide> getTotalRides() {
            return totalRides;
        }

        public void setTotalRides(List<TotalRide> totalRides) {
            this.totalRides = totalRides;
        }

        public Result withTotalRides(List<TotalRide> totalRides) {
            this.totalRides = totalRides;
            return this;
        }

        public Integer getTotalRidesCount() {
            return totalRidesCount;
        }

        public void setTotalRidesCount(Integer totalRidesCount) {
            this.totalRidesCount = totalRidesCount;
        }

        public String getCustLat() {
            return custLat;
        }

        public void setCustLat(String custLat) {
            this.custLat = custLat;
        }

        public String getCustLong() {
            return custLong;
        }

        public void setCustLong(String custLong) {
            this.custLong = custLong;
        }

        public String getOrgLat() {
            return orgLat;
        }

        public void setOrgLat(String orgLat) {
            this.orgLat = orgLat;
        }

        public String getOrgLong() {
            return orgLong;
        }

        public void setOrgLong(String orgLong) {
            this.orgLong = orgLong;
        }

        public String getVehicleImage() {
            return vehicleImage;
        }

        public void setVehicleImage(String vehicleImage) {
            this.vehicleImage = vehicleImage;
        }

        public Result withTotalRidesCount(Integer totalRidesCount) {
            this.totalRidesCount = totalRidesCount;
            return this;
        }

        public String getCurrentBookId() {
            return currentBookId;
        }

        public void setCurrentBookId(String currentBookId) {
            this.currentBookId = currentBookId;
        }

        public String getKm() {
            return km;
        }

        public void setKm(String km) {
            this.km = km;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getRideNo() {
            return rideNo;
        }

        public void setRideNo(String rideNo) {
            this.rideNo = rideNo;
        }
    }

    public class TotalRide implements Serializable
    {

        @SerializedName("book_id")
        @Expose
        private String bookId;
        @SerializedName("book_no")
        @Expose
        private String bookNo;
        @SerializedName("book_cust_id")
        @Expose
        private String bookCustId;
        @SerializedName("book_delivery_status")
        @Expose
        private String bookDeliveryStatus;
        @SerializedName("book_cust_location")
        @Expose
        private String bookCustLocation;
        @SerializedName("cust_name")
        @Expose
        private String custName;
        @SerializedName("today_rides")
        @Expose
        private Integer todayRides;
        private final static long serialVersionUID = 9108827224608579979L;

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public TotalRide withBookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public String getBookNo() {
            return bookNo;
        }

        public void setBookNo(String bookNo) {
            this.bookNo = bookNo;
        }

        public TotalRide withBookNo(String bookNo) {
            this.bookNo = bookNo;
            return this;
        }

        public String getBookCustId() {
            return bookCustId;
        }

        public void setBookCustId(String bookCustId) {
            this.bookCustId = bookCustId;
        }

        public TotalRide withBookCustId(String bookCustId) {
            this.bookCustId = bookCustId;
            return this;
        }

        public String getBookDeliveryStatus() {
            return bookDeliveryStatus;
        }

        public void setBookDeliveryStatus(String bookDeliveryStatus) {
            this.bookDeliveryStatus = bookDeliveryStatus;
        }

        public TotalRide withBookDeliveryStatus(String bookDeliveryStatus) {
            this.bookDeliveryStatus = bookDeliveryStatus;
            return this;
        }

        public String getBookCustLocation() {
            return bookCustLocation;
        }

        public void setBookCustLocation(String bookCustLocation) {
            this.bookCustLocation = bookCustLocation;
        }

        public TotalRide withBookCustLocation(String bookCustLocation) {
            this.bookCustLocation = bookCustLocation;
            return this;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public TotalRide withCustName(String custName) {
            this.custName = custName;
            return this;
        }

        public Integer getTodayRides() {
            return todayRides;
        }

        public void setTodayRides(Integer todayRides) {
            this.todayRides = todayRides;
        }
    }
}