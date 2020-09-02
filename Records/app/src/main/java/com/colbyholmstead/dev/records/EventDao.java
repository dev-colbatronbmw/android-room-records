package com.colbyholmstead.dev.records;

import com.colbyholmstead.dev.records.Event;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EventDao{
  @Query("SELECT * FROM events")
  List<Event> getAll();

  @Query("SELECT * FROM events WHERE name= :event_name LIMIT 1")
 Event findByName(String event_name);

  @Insert
  void addEvent(Event event);

  @Update
  void updateEvent(Event event);

  @Delete
  void deleteEvent(Event event);

}