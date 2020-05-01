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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Export data Reports
 * 
 * 
 * @author aTeam 131
 *
 */
public class ExportData {

  private FarmGroup farm; // Field for FarmGroup

  /**
   * Constructor for ExportData
   * 
   * @param farm farm to be exported
   */
  public ExportData(FarmGroup farm) {
    this.farm = farm;
  }

  /**
   * Defines the months
   * 
   * @param monthNumber numeric value of a month
   * @return string name of the month
   */
  private String generateMonth(int monthNumber) {
    if (monthNumber == 0) {
      return Months.JAN.toString();
    } else if (monthNumber == 1) {
      return Months.FEB.toString();
    } else if (monthNumber == 2) {
      return Months.MARCH.toString();
    } else if (monthNumber == 3) {
      return Months.APRIL.toString();
    } else if (monthNumber == 4) {
      return Months.MAY.toString();
    } else if (monthNumber == 5) {
      return Months.JUNE.toString();
    } else if (monthNumber == 6) {
      return Months.JULY.toString();
    } else if (monthNumber == 7) {
      return Months.AUG.toString();
    } else if (monthNumber == 8) {
      return Months.SEP.toString();
    } else if (monthNumber == 9) {
      return Months.OCT.toString();
    } else if (monthNumber == 10) {
      return Months.NOV.toString();
    } else if (monthNumber == 11) {
      return Months.DEC.toString();
    } else
      return null;
  }

  /**
   * export Farm Report to a given File
   * 
   * @param fileName file name to save
   * @param farmID   farm id to save
   * @param year     year to save
   * @throws IOException if writing error occurs
   */
  public void exportFarmReport(String fileName, String farmID, Integer year) throws IOException {

    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      // Create a new file object
      File file = new File(fileName);

      // Create a new writer and buffer writer
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);

      // Get the farm report
      ArrayList<Double> results = farm.getFarmReport(farmID, year);

      // Write the report into the file
      bw.write("################## Farm Report ##################");
      bw.newLine();
      bw.write("---Farm Report: " + farmID);
      bw.newLine();
      bw.write("---Year: " + year);
      bw.newLine();
      bw.write(
          "***Total Milk Weight: " + farm.getTotalMilkWeightForFarmAndYear(farmID, year) + " lbs");
      bw.newLine();

      for (int i = 0; i < 12; i++) {
        Double milkWeightPercentage = results.get(i);
        String month = generateMonth(i);
        bw.write(month + " : " + milkWeightPercentage + "%" + "\n");
      }

    } catch (Exception e) { // If an error occurs
      throw new IOException("Error occured when writing file.");
    } finally {
      // Close the buffered writer
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }
  }

  /**
   * Export Annual Report to file
   * 
   * @param fileName file name to save the report to
   * @param year     year to get the report from
   * @throws IOException if writing failed
   */
  public void exportAnnualreport(String fileName, Integer year) throws IOException {
    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      // Create a new file object
      File file = new File(fileName);
      // Create a new file writer and buffered writer
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);

      // Get the data
      HashMap<String, Double> results = farm.getAnnualReport(year);
      Set<String> farmIDs = results.keySet();

      // Write into the file
      bw.write("################## Annual Report ##################");
      bw.newLine();
      bw.write("---Annual Report for FarmIDs: " + farmIDs.toString());
      bw.newLine();
      bw.write("---Year: " + year);
      bw.newLine();
      bw.write("***Total Milk Weight: " + farm.getTotalMilkWeightForAllFarmAndYear(year) + " lbs");
      bw.newLine();

      for (String farmIDString : farmIDs) {
        bw.newLine();
        bw.write("#########Farm Id: " + farmIDString);
        bw.newLine();
        bw.write("        -Milk Weight %: " + results.get(farmIDString));
        bw.newLine();
      }
    } catch (Exception e) {
      throw new IOException("Error occurred when writing the file!");
    } finally {
      // Close the buffered writer
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }

  }

  /**
   * Export Date Range Report to file
   * 
   * @param fileName file name to write the file to
   * @param fromDate from date to get the report
   * @param toDate   to date to get the report
   * @throws IOException if a writing error occurs
   */
  public void exportDateRangeReport(String fileName, LocalDate fromDate, LocalDate toDate)
      throws IOException {
    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      // Create a new file object
      File file = new File(fileName);

      // Create a new file and buffered writer
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);

      // Get the data
      HashMap<String, Double> results = farm.getDateRangeReport(fromDate, toDate);
      Set<String> farmIDs = results.keySet();

      // Write the report
      bw.write("################## Date Range Report ##################");
      bw.newLine();
      bw.write("---Date Range Report for FarmIDs: " + farmIDs.toString());
      bw.newLine();
      bw.write("---From Date: " + fromDate);
      bw.newLine();
      bw.write("---To Date: " + toDate);
      bw.newLine();
      bw.write("***Total Milk Weight: "
          + farm.getTotalMilkWeightForAllFromDateToDate(fromDate, toDate) + " lbs");
      bw.newLine();

      for (String farmIDString : farmIDs) {
        bw.newLine();
        bw.write("#########Farm Id: " + farmIDString);
        bw.newLine();
        bw.write("        -Milk Weight %: " + results.get(farmIDString));
      }

    } catch (Exception e) {
      throw new IOException("An error occurred when writing file!");
    } finally {
      // Close the buffered writer
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }
  }

  /**
   * Export the monthly report
   * 
   * @param fileName file name to save it to
   * @param year     year to get the report from
   * @param month    month to get the report from
   * @throws IOException if a writing error occurs
   */
  public void exportMonthlyReport(String fileName, Integer year, Integer month) throws IOException {
    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      // Create a new file object
      File file = new File(fileName);
      // Create a new file and buffered writer
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);

      // Get the data
      HashMap<String, Double> results = farm.getMonthlyReport(year, month);
      Set<String> farmIDs = results.keySet();

      // Write the data
      bw.write("################## Monthly Report ##################");
      bw.newLine();
      bw.write("---Date Range Report for FarmIDs: " + farmIDs.toString());
      bw.newLine();
      bw.write("---Year: " + year);
      bw.newLine();
      bw.write("---Month: " + generateMonth(month));
      bw.newLine();
      bw.write("***Total Milk Weight: " + farm.getTotalMilkWeightForAllFarmAndMonth(year, month)
          + " lbs");
      bw.newLine();


      for (String farmIDString : farmIDs) {
        bw.newLine();
        bw.write("#########Farm Id: " + farmIDString);
        bw.newLine();
        bw.write("        -Milk Weight %: " + results.get(farmIDString));
        bw.newLine();
      }
    } catch (Exception e) {
      throw new IOException("An error occured when writing the file");
    } finally {
      // Close the buffered writer
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }
  }
}
