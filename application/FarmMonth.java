//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: a2
// Files: Main.java, DataMap.java, ExportData.java, Farm.java, FarmGroup.java,
// FarmMonth.java, FarmYear.java, InputParser.java, MilkWeight.java,
// Months.java
// Course: CS 400, Spring, 2020
//
// Author: Adam Shedivy, Calvin Sienatra, Charlie Mrkvicka
// Email: ajshedivy@wisc.edu,
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: N/A
// Online Sources: N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * FarmMonth Class creates an instance of specific month on a farm and contains the milkweights from
 * each day in a hashmap
 * 
 * @author aTeam 131
 *
 */
public class FarmMonth implements MilkWeight<Integer, Integer> {

  // Hashmap: Key(Day in month), Value(milkWeight of day)
  private HashMap<Integer, Integer> data;

  // Total milk weight from the month
  private int totalMilkWeight;

  /**
   * Default FarmMonth constructor that creates a new hashmap instance and initializes total milk
   * weight to 0
   */
  public FarmMonth() {
    data = new HashMap<>();
    totalMilkWeight = 0;
  }

  /**
   * Insert Milk Weight on certain day in month
   * 
   * @param dateToSet-  Date to insert on
   * @param milkWeight- Milk weight of the date
   */
  @Override
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight) {

    // Insert the milkWeight into the hash of the specific day of the month
    data.put(dateToSet.getDayOfMonth(), milkWeight);

    // Add milkWeight to totalMilkWeight
    totalMilkWeight += milkWeight;
  }

  /**
   * Get total milk weight from the entire month
   * 
   * @return totalMilkWeight
   */
  public int getTotalMilkWeight() {
    return totalMilkWeight;
  }

  /**
   * Get milk weights from specific day
   * 
   * @param key- Specific day integer
   * @return- milkWeight of given day or 0 if day isn't in hashmap
   */
  @Override
  public int getMilkWeight(Integer key) {
    if (!data.containsKey(key)) {
      return 0; // If the day isn't in the hashmap, return 0
    }
    return data.get(key); // Return milkWeight of given day
  }

}
