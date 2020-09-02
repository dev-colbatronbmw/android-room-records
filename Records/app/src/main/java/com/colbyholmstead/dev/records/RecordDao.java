//package com.colbyholmstead.dev.records;
//
//import android.content.Intent;
//
//import java.util.List;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//@Dao
//public interface RecordDao {
//  @Query("SELECT * FROM records")
//  LiveData<List<Record>> getAllRecords();
//
//  @Query("SELECT name FROM records")
//  LiveData<List<String>> getAllNames();
//
//
//  @Query("SELECT * FROM records WHERE name= :record_name LIMIT 1")
//  Record findRecordByName(String record_name);
//
//  @Query("SELECT * FROM records WHERE recordId = :recordID")
//  LiveData<Record> findLiveRecordById(int recordID);
//  @Query("SELECT * FROM records WHERE recordId = :recordID")
//  Record findRecordById(int recordID);
//
//  @Insert
//  void addRecord(Record record);
//
//  @Update
//  void updateRecord(Record record);
//
//
//  @Delete
//  void deleteRecord(Record record);
//
//}
