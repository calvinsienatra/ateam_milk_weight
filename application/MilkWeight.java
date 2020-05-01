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


/**
 * Defines an interface for MilkWeight
 * 
 * @author aTeam 131
 */
package application;

import java.time.LocalDate;

/**
 * Interface for MilkWeight
 * 
 * @author aTeam 131
 *
 */
public interface MilkWeight<T, U> {

  /**
   * Insert Milk Weight on certain day in month
   */
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight);

  /**
   * Get milk weights from specific date time
   */
  public int getMilkWeight(T key);

}
