package com.colbyholmstead.dev.records;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

import androidx.room.TypeConverter;

public class Converters {
  @TypeConverter
  public Date fromTimeStamp(Long value){
    return value == null ? null : new Date(value);
  }

//  @TypeConverter
//  public  String dateStringFromDate(Date date){
//    DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
//    String strDate = dateFormat.format(date);
//    return strDate;
//  }

  @TypeConverter
  public Long dateToTimeStamp(Date date){
    return date == null ? null : date.getTime();
  }

}
