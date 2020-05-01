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

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.MissingFormatArgumentException;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Defines the FarmGroup class
 * 
 * @author calvinsienatra
 *
 */
public class FarmGroup {

  // HashMap containing all the Farm instances
  HashMap<String, Farm> farms;

  /**
   * Default constructor initializes farms HashMap
   */
  public FarmGroup() {
    farms = new HashMap<>();
  }

  /**
   * Gets the total milk weight of the given year
   * 
   * @param year year to get the milk weight
   * @return the total milk weight of the year
   */
  public int getTotalMilkWeight(int year) {

    Set<String> farmIds = farms.keySet();

    int totalMilkWeight = 0;

    // For each farm add that years MilkWeight to totalMilkWeight
    for (String farmId : farmIds) {
      totalMilkWeight += farms.get(farmId).getMilkWeight(year);
    }

    return totalMilkWeight;
  }

  /**
   * Gets the total milk weight of the given year and month
   * 
   * @param year year to get the milk weight
   * @param month month of the year to get the milk weight
   * @return the total milk weight of the year and month
   */
  private int getTotalMilkWeight(int year, int month) throws MissingFormatArgumentException{
    Set<String> farmIds = farms.keySet();

    int totalMilkWeight = 0;

    // For each farm add that months totalMilkWeight to totalMilkWeight
    for (String farmId : farmIds) {
      try {
        totalMilkWeight += farms.get(farmId).getMilkWeight(year, month);
      } catch (MissingFormatArgumentException e) {
        // Skip incomplete FarmYear instances
      }
    }

    return totalMilkWeight;
  }

  /**
   * Inserts the milk weight for the given date and farm
   * 
   * @param farmId farm id to put the milk weight in
   * @param dateToSet date to set the milk weight in
   * @param milkWeight the milk weight
   */
  public void insertMilkWeight(String farmId, LocalDate dateToSet, int milkWeight) {

    // If the Farm instance doesn't exist, create one and insert into HashMap
    if (!farms.containsKey(farmId)) {
      farms.put(farmId, new Farm(farmId));
    }

    // Create temp farm instance
    Farm farm = farms.get(farmId);
    System.out.println(farm);

    // Insert milkFace into that farm instance
    farm.insertMilkWeight(dateToSet, milkWeight);

  }


  public int getTotalMilkWeightForFarmAndYear(String farmId, Integer year)
      throws NoSuchFieldException {

    // Create temp farm instance from farmID
    Farm farm = farms.get(farmId);

    if (farm == null) { // If farm is null, throw exception
      throw new NoSuchFieldException("Error: Invalid farm id!");
    }

    Integer totalMilkWeight = 0;

    // For each month in given year add milk weight to totalMilkWeight
    for (int i = 0; i < 12; i++) {
      totalMilkWeight += farm.getMilkWeight(year, i + 1);
    }

    return totalMilkWeight;

  }


  /**
   * Gets the report of a farm in a given year
   * 
   * @param farmId farm id to get the report from
   * @param year year to get the report from
   * @throws NoSuchFieldException if the given farm or year is not available
   * @return a ArrayList with the percentage for every month of the year
   */
  public ArrayList<Double> getFarmReport(String farmId, Integer year)
      throws NoSuchFieldException, MissingFormatArgumentException {

    // Create farm instance of given farm
    Farm farm = farms.get(farmId);

    System.out.println(farmId);
    System.out.println(farms.keySet());
    System.out.println(farm);
    System.out.println();

    if (farm == null) { // If farm is null, throw exception
      throw new NoSuchFieldException("Error: Invalid farm id!");
    }

    // Arraylist to add milkWeights of each month in the year
    ArrayList<Integer> farmYear = new ArrayList<>();

    for (int i = 0; i < 12; i++) { // Add milkweight to list for each month from this year
      farmYear.add(farm.getMilkWeight(year, i + 1));
    }

    // Arraylist to of totalMilkWeight of all farms
    ArrayList<Integer> totalMilkWeightPerMonth = new ArrayList<>();

    for (int i = 0; i < 12; i++) { // Add milkweight for each month of every farm
      totalMilkWeightPerMonth.add(getTotalMilkWeight(year, i + 1));
    }

    ArrayList<Double> calculatedPercentage = new ArrayList<>();

    for (int i = 0; i < 12; i++) { // Calculate milkWeightPercentage of given farm
      int totalMilkWeightThisMonth = totalMilkWeightPerMonth.get(i);
      int curMilkWeight = farmYear.get(i);

      Double curPercentage = (((double) curMilkWeight) / totalMilkWeightThisMonth) * 100;

      calculatedPercentage.add((double) Math.round(curPercentage * 100.0) / 100.0);

    }
    // Return percentage of given farm's milkWeight for every month
    return calculatedPercentage;
  }

  /**
   * Get total milk weight for all farms of a given year
   * 
   * @param year
   * @return totalMilkWeightYear list
   * @throws NoSuchElementException
   */
  public int getTotalMilkWeightForAllFarmAndYear(Integer year) throws NoSuchElementException {
    Set<String> farmIds = farms.keySet();
    Set<String> filteredFarmIds = new HashSet<>();

    // For each farm
    for (String farmId : farmIds) {
      try {
        // Get the milkWeight of that farm for the given year
        farms.get(farmId).getMilkWeight(year, 1);
        filteredFarmIds.add(farmId);
      } catch (MissingFormatArgumentException e) {
        // Continue over farms that aren't correct format
      }
    }

    int totalMilkWeightYear = 0;


    for (String farmId : filteredFarmIds) {
      totalMilkWeightYear += farms.get(farmId).getMilkWeight(year);
    }

    return totalMilkWeightYear;

  }


  /**
   * Gets the report of a year for every farm
   * 
   * @param year year to get the report from
   * @return HashMap of the farms with their respective percentage
   */
  public HashMap<String, Double> getAnnualReport(Integer year) throws NoSuchElementException {
    Set<String> farmIds = farms.keySet();
    Set<String> filteredFarmIds = new HashSet<>();

    for (String farmId : farmIds) {
      try {
        farms.get(farmId).getMilkWeight(year, 1);
        filteredFarmIds.add(farmId);
      } catch (MissingFormatArgumentException e) {
        // Continue
      }
    }

    int totalMilkWeightYear = 0;

    for (String farmId : filteredFarmIds) {
      totalMilkWeightYear += farms.get(farmId).getMilkWeight(year);
    }

    HashMap<String, Double> calculatedPercentage = new HashMap<>();
    
    for(String farmId: filteredFarmIds) {
      int curMilkWeight = farms.get(farmId).getMilkWeight(year);
      
      Double curPercentage = (((double)curMilkWeight) / totalMilkWeightYear) * 100;
      
      calculatedPercentage.put(farmId, (double)Math.round(curPercentage * 100.0) / 100.0);
      
    }
    
    return calculatedPercentage;
  }

  public int getTotalMilkWeightForAllFarmAndMonth(Integer year, Integer month)
      throws NoSuchElementException {
    Set<String> farmIds = farms.keySet();
    Set<String> filteredFarmIds = new HashSet<>();

    for (String farmId : farmIds) {
      try {
        farms.get(farmId).getMilkWeight(year, 1);
        filteredFarmIds.add(farmId);
      } catch (MissingFormatArgumentException e) {
        // Continue
      }
    }

    int totalMilkWeightYearMonth = 0;

    for (String farmId : filteredFarmIds) {
      totalMilkWeightYearMonth += farms.get(farmId).getMilkWeight(year, month);
    }

    return totalMilkWeightYearMonth;

  }

  /**
   * Gets the report of a month and year for every farm
   * 
   * @param year year to get the report from
   * @param month month to get the report from
   * @return HashMap of the farms with their respective percentage
   */
  public HashMap<String, Double> getMonthlyReport(Integer year, Integer month) {
    Set<String> farmIds = farms.keySet();
    Set<String> filteredFarmIds = new HashSet<>();

    for (String farmId : farmIds) {
      try {
        farms.get(farmId).getMilkWeight(year, 1);
        filteredFarmIds.add(farmId);
      } catch (MissingFormatArgumentException e) {
        // Continue
      }
    }

    int totalMilkWeightYearMonth = 0;

    for (String farmId : filteredFarmIds) {
      totalMilkWeightYearMonth += farms.get(farmId).getMilkWeight(year, month);
    }

    HashMap<String, Double> calculatedPercentage = new HashMap<>();

    for (String farmId : filteredFarmIds) {
      int curMilkWeight = farms.get(farmId).getMilkWeight(year, month);

      Double curPercentage = (((double) curMilkWeight) / totalMilkWeightYearMonth) * 100;

      calculatedPercentage.put(farmId, (double) Math.round(curPercentage * 100.0) / 100.0);

    }

    return calculatedPercentage;
  }

  public int getTotalMilkWeightForAllFromDateToDate(LocalDate fromDate, LocalDate toDate)
      throws NoSuchElementException {
    Set<String> farmIds = farms.keySet();
    Set<String> filteredFarmIds = new HashSet<>();

    for (String farmId : farmIds) {
      try {
        farms.get(farmId).getMilkWeight(fromDate.getYear(), 1);
        filteredFarmIds.add(farmId);
      } catch (MissingFormatArgumentException e) {
        // Continue
      }
    }

    int totalMilkWeightDateToDate = 0;

    HashMap<String, Integer> farmTotal = new HashMap<>();

    while (!fromDate.equals(toDate.plusDays(1))) {
      for (String farmId : filteredFarmIds) {

        totalMilkWeightDateToDate += farms.get(farmId).getMilkWeight(fromDate.getYear(),
            fromDate.getMonthValue(), fromDate.getDayOfMonth());

        if (farmTotal.get(farmId) == null) {
          farmTotal.put(farmId, farms.get(farmId).getMilkWeight(fromDate.getYear(),
              fromDate.getMonthValue(), fromDate.getDayOfMonth()));
        } else {
          farmTotal.put(farmId,
              farmTotal.get(farmId) + farms.get(farmId).getMilkWeight(fromDate.getYear(),
                  fromDate.getMonthValue(), fromDate.getDayOfMonth()));
        }

      }


      fromDate = fromDate.plusDays(1);
    }

    return totalMilkWeightDateToDate;

  }

  /**
   * Gets the report from a date to a date for every farm
   * 
   * @param fromDate from date (inclusive)
   * @param toDate to date (inclusive)
   * @throws DateTimeException if fromDate is larger than toDate
   * @return HashMap of the farms with their respective percentage
   */
  public HashMap<String, Double> getDateRangeReport(LocalDate fromDate, LocalDate toDate)
      throws DateTimeException {
    if (fromDate.compareTo(toDate) > 0) {
      throw new DateTimeException("Error: dateFrom is more than toDate");
    }

    Set<String> farmIds = farms.keySet();
    Set<String> filteredFarmIds = new HashSet<>();

    for (String farmId : farmIds) {
      try {
        farms.get(farmId).getMilkWeight(fromDate.getYear(), 1);
        filteredFarmIds.add(farmId);
      } catch (MissingFormatArgumentException e) {
        // Continue
      }
    }

    int totalMilkWeightDateToDate = 0;

    HashMap<String, Integer> farmTotal = new HashMap<>();

    while (!fromDate.equals(toDate.plusDays(1))) {
      for (String farmId : filteredFarmIds) {

        totalMilkWeightDateToDate += farms.get(farmId).getMilkWeight(fromDate.getYear(),
            fromDate.getMonthValue(), fromDate.getDayOfMonth());

        if (farmTotal.get(farmId) == null) {
          farmTotal.put(farmId, farms.get(farmId).getMilkWeight(fromDate.getYear(),
              fromDate.getMonthValue(), fromDate.getDayOfMonth()));
        } else {
          farmTotal.put(farmId,
              farmTotal.get(farmId) + farms.get(farmId).getMilkWeight(fromDate.getYear(),
                  fromDate.getMonthValue(), fromDate.getDayOfMonth()));
        }

      }


      fromDate = fromDate.plusDays(1);
    }


    HashMap<String, Double> calculatedPercentage = new HashMap<>();

    for (String farmId : farmTotal.keySet()) {
      Double curPercentage = (((double) farmTotal.get(farmId)) / totalMilkWeightDateToDate) * 100;

      calculatedPercentage.put(farmId, (double) Math.round(curPercentage * 100.0) / 100.0);
    }


    return calculatedPercentage;
  }


  /**
   * Test method for FarmGroup class
   * 
   * @param args input params
   */
  public static void main(String[] args) {

  }
}
