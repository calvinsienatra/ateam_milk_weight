//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: a2
// Files: Main.java, MilkWeight.java, DataMap.java, InputParser.java, FarmMonth.java, FarmYear.java,
// Farm.java, FarmGroup.java, ExportData.java
//
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
import java.util.MissingFormatArgumentException;


/**
 * Farm year class creates an instance of specific year on a farm and contains the FarmMonth
 * instances of that year in a HashMap
 * 
 * @author ajshe
 *
 */
public class FarmYear implements MilkWeight<Integer, Integer> {

  // Hashmap: Key(Month in year), Value(FarmMonth instance)
  private HashMap<Integer, FarmMonth> data;

  // Total milk weight from the month
  private int totalMilkWeight;

  /**
   * FarmYear default constructor initializes new HashMap and sets totalMilkWeight to 0
   */
  public FarmYear() {
    data = new HashMap<>();
    totalMilkWeight = 0;
  }


  /**
   * Insert Milk Weight on certain date
   * 
   * @param dateToSet- Date to insert on
   * @param milkWeight- Milk weight of the date
   */
  @Override
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight) {

    // If the date isn't in the HashMap then create a new month instance and insert into HashMap
    if (!data.containsKey(dateToSet.getMonthValue())) {
      data.put(dateToSet.getMonthValue(), new FarmMonth());
    }

    // Create a temp FarmMonth instance to insert milkWeight in
    FarmMonth temp = data.get(dateToSet.getMonthValue());

    System.out.println(dateToSet + ": " + temp);

    // Call insertMilkWeight on the temp FarmMonth instance
    temp.insertMilkWeight(dateToSet, milkWeight);

    // Add milkWeight to totalMilkWeight for the year
    totalMilkWeight += milkWeight;
  }


  /**
   * Get milk weights from specific month
   * 
   * @param key- Specific month integer
   * @throws- MissingFormatArgumentException if there are less than 12 months in this year
   * @return- milkWeight of given month
   */
  @Override
  public int getMilkWeight(Integer key) throws MissingFormatArgumentException {
    System.out.println(key);
    System.out.println(data.keySet());

    // Throw exception if the year isn't formatted correctly, less than 12 months
    if (data.keySet().size() < 12) {
      throw new MissingFormatArgumentException(
          "Month incomplete. Program should ignore this farm.");
    }

    // Create temp FarmMonth instance
    FarmMonth temp = data.get(key);

    // Return totalMilkWeight of that month
    return temp.getTotalMilkWeight();
  }


  /**
   * Get milk weight from specific day in month of this year
   * 
   * @param month
   * @param day
   * @return - Milk Weight of day
   */
  public int getMilkWeight(Integer month, Integer day) {

    // Create temp FarmMonth instance
    FarmMonth temp = data.get(month);

    // Return totalMilkWeight of that day
    return temp.getMilkWeight(day);
  }

  /**
   * Get total milk weight from the entire year
   * 
   * @return totalMilkWeight
   */
  public int getTotalMilkWeight() {
    return totalMilkWeight;
  }

} // end FarmYear class
