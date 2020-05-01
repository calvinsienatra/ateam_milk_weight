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
import java.util.NoSuchElementException;

/**
 * Farm Class creates an instance of specific farm and contains the FarmYear instances of that farm
 * in a HashMap
 * 
 * @author Charlie
 *
 */
public class Farm implements MilkWeight<Integer, Integer> {

  // HashMap Key(Year of the farm), Value(FarmYear instance)
  private HashMap<Integer, FarmYear> data;

  // Unique FarmID
  private final String farmID;

  // Total milk weights for all years of the farm
  private int totalMilkWeight;

  /**
   * Constructor for a Farm class instance, assigns the farm a unique farmID, creates a new HashMap
   * to store FarmYear instances, and sets totalMilkWeight to 0
   * 
   * @param farmID
   */
  public Farm(String farmID) {
    this.farmID = farmID;
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

    // If there isn't a FarmYear instance of given date, create one and insert into the HashMap
    if (!data.containsKey(dateToSet.getYear())) {
      data.put(dateToSet.getYear(), new FarmYear());
    }

    // Create temp FarmYear instance to insert Milk Weight into
    FarmYear year = data.get(dateToSet.getYear());

    // Call insertMilkWeight on the temp FarmYear instance
    year.insertMilkWeight(dateToSet, milkWeight);

    // Add milkWeight to totalMilkWeight for the farm
    totalMilkWeight += milkWeight;
  }

  /**
   * Get total milk weights from farm
   */
  public int getTotalMilkWeight() {
    return totalMilkWeight;
  }

  /**
   * Get milk weights from specific year
   * 
   * @param key- Specific year integer
   * @throws- NoSuchElementException if the year is null
   * @return- milkWeight of given month
   */
  @Override
  public int getMilkWeight(Integer key) throws NoSuchElementException {

    // Create temp FarmYear instance
    FarmYear year = data.get(key);

    // If year is null throw exception
    if (year == null) {
      throw new NoSuchElementException("Year does not exist!");
    }

    // Get totalMilkWeight of that year and return it
    int milk = year.getTotalMilkWeight();
    return milk;
  }

  /**
   * Get total milk weights from specific month and year
   * 
   * @param year
   * @param month
   * @return Milk from that month
   */
  public int getMilkWeight(Integer year, Integer month) throws NoSuchElementException {

    // Create temp FarmYear
    FarmYear yearTemp = data.get(year);

    // Throw exception if year is null
    if (yearTemp == null) {
      throw new NoSuchElementException();
    }

    // Get totalMilkWeight of that month and return it
    int milk = yearTemp.getMilkWeight(month);
    return milk;
  }

  /**
   * Get milk weight for that specific date
   * 
   * @param year
   * @param month
   * @param day
   * @return Milk from that date
   */
  public int getMilkWeight(Integer year, Integer month, Integer day) {

    // Create temp FarmYear instance
    FarmYear yearTemp = data.get(year);

    // Get totalMilkWeight of that date and return it
    int milk = yearTemp.getMilkWeight(month, day);
    return milk;
  }



}
