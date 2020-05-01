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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Main Parser for Milk Weight Application
 * 
 * @author aTeam 131
 *
 */
public class InputParser {

  DataMap<String, String, Integer> data; // Temporary storage for the parsed data
  BufferedReader br = null; // Buffered reader
  String line = ""; // Temporary fields
  String splitBy = ",";

  /**
   * No argument input parser constructor
   */
  public InputParser() {
    this.data = new DataMap<String, String, Integer>(); // Create a new DataMap
  }

  /**
   * parser for input csv files
   * 
   * @param path path to input data
   * @throws IOException if reading error occurs
   */
  public void inputData(String path) throws IOException {
    try {
      // Create a new buffered reader
      br = new BufferedReader(new FileReader(path));
      @SuppressWarnings("unused")
      String headerLine = br.readLine(); // ignore header line

      // Loop through each line
      while ((line = br.readLine()) != null) {
        // Split the line
        String[] row = line.split(splitBy);
        try {
          // Get the farm id
          String farmID = row[1];
          if (farmID.equals("-")) {
            continue;
          }

          // Get the date
          String date = row[0];
          // Get the weight
          int weight = Integer.parseInt(row[2]);

          // Put the data into the datamap
          data.putV(farmID, date, weight);
        } catch (Exception e) {
          throw new IOException("An error occured when reading file.");
        }

      }
    } catch (FileNotFoundException e) { // If file does not exist
      throw new IOException("File does not exist.");
    } catch (Exception e) { // If an unknown error occurs
      throw new IOException("An unknown error occured when reading file.");
    }
  }

  /**
   * utility function to print data from data hashMap
   * 
   * @return StringBuffer of data
   */
  public StringBuffer printData() {
    // Create new string bugger
    StringBuffer dataString = new StringBuffer();
    // Print for every farm data
    for (String farm : data.keySet()) {
      HashMap<String, Integer> value = data.getV(farm);
      System.out.println();
      System.out.println("######## Farm Id: " + farm);
      System.out.println();
      dataString.append(farm);
      for (String date : value.keySet()) { // Print date and weight
        System.out.println("         ---date: " + date);
        dataString.append(date);
        System.out.println("         -------weights: " + value.get(date));
        dataString.append(value.get(date));
      }
    }
    return dataString;

  }

  /**
   * Getter for the data
   * 
   * @return - DataMap data
   */
  public DataMap<String, String, Integer> getData() {
    return data;
  }

}
