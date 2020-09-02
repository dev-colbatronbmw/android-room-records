package com.colbyholmstead.dev.records;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;


public class Record {

  @SerializedName("prodId")
  @Expose
  private Integer prodId;
  @SerializedName("prodName")
  @Expose
  private String prodName;
  @SerializedName("prodDesc")
  @Expose
  private String prodDesc;
  @SerializedName("prodPrice")
  @Expose
  private double prodPrice;
  @SerializedName("prodRating")
  @Expose
  private Integer prodRating;
  @SerializedName("prodCreatedDate")
  @Expose
  private Date prodCreatedDate;
  @SerializedName("prodModifiedDate")
  @Nullable
  @Expose
  private Date prodModifiedDate;

  /**
   * No args constructor for use in serialization
   *
   */
  public Record() {
  }

  /**
   *
   * @param prodRating
   * @param prodModifiedDate
   * @param prodName
   * @param prodPrice
   * @param prodCreatedDate
   * @param prodId
   * @param prodDesc
   */
  public Record(Integer prodId, String prodName, String prodDesc, double prodPrice, Integer prodRating, Date prodCreatedDate, @org.jetbrains.annotations.Nullable Date prodModifiedDate) {
    super();
    this.prodId = prodId;
    this.prodName = prodName;
    this.prodDesc = prodDesc;
    this.prodPrice = prodPrice;
    this.prodRating = prodRating;
    this.prodCreatedDate = prodCreatedDate;
    this.prodModifiedDate = prodModifiedDate;
  }



  public Integer getProdId() {
    return prodId;
  }

  public void setProdId(Integer prodId) {
    this.prodId = prodId;
  }

  public String getProdName() {
    return prodName;
  }

  public void setProdName(String prodName) {
    this.prodName = prodName;
  }

  public String getProdDesc() {
    return prodDesc;
  }

  public void setProdDesc(String prodDesc) {
    this.prodDesc = prodDesc;
  }

  public double getProdPrice() {
    return prodPrice;
  }

  public void setProdPrice(double prodPrice) {
    this.prodPrice = prodPrice;
  }

  public Integer getProdRating() {
    return prodRating;
  }

  public void setProdRating(Integer prodRating) {
    this.prodRating = prodRating;
  }

  public Date getProdCreatedDate() {
    return prodCreatedDate;
  }

  public void setProdCreatedDate(Date prodCreatedDate) {
    this.prodCreatedDate = prodCreatedDate;
  }

  public Date getProdModifiedDate() {
    return prodModifiedDate;
  }

  public void setProdModifiedDate(Date prodModifiedDate) {
    this.prodModifiedDate = prodModifiedDate;
  }

}