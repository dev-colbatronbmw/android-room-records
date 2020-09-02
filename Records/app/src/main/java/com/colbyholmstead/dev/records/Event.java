package com.colbyholmstead.dev.records;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


@Entity(tableName = "events")
public class Event {
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "eventID")
  private long eventID;

  @ColumnInfo(name = "name")
  private String name;

  @ColumnInfo(name = "description")
  private String description;

  @ColumnInfo(name = "attendees")
  private String attendees;

  @TypeConverters(Converters.class)
  @ColumnInfo(name = "startDate")
  private Date startDate;

  @TypeConverters(Converters.class)
  @ColumnInfo(name = "endDate")
  private Date endDate;
//  private Time startTime;
//  private Time endTime;

  public long getEventID() {
    return eventID;
  }

  public void setEventID(long eventID) {
    this.eventID = eventID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAttendees() {
    return attendees;
  }

  public void setAttendees(String attendees) {
    this.attendees = attendees;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
//  public Time getStartTime() {
//    return startTime;
//  }
//
//  public void setStartTime(Time startTime) {
//    this.startTime = startTime;
//  }
//
//  public Time getEndTime() {
//    return endTime;
//  }
//
//  public void setEndTime(Time endTime) {
//    this.endTime = endTime;
//  }
}
