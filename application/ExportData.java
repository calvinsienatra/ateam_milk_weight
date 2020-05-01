/**
 * 
 */
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
 * @author ajshe
 *
 */
public class ExportData {

  private FarmGroup farm;

  public ExportData(FarmGroup farm) {
    this.farm = farm;
  }

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
   * @param faileName
   * @param farmID
   * @param year
   * @throws IOException
   */
  public void exportFarmReport(String fileName, String farmID, Integer year) throws IOException {
    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      File file = new File(fileName);
      if (file.createNewFile()) {
        System.out.println("File created " + file.getName());
      } else {
        System.out.println("File already exists");
      }
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);
      ArrayList<Double> results = farm.getFarmReport(farmID, year);
      System.out.println(results);
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
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }
  }

  /**
   * Export Annual Report to file
   * 
   * @param fileName
   * @param year
   * @throws IOException
   */
  public void exportAnnualreport(String fileName, Integer year) throws IOException {
    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      File file = new File(fileName);
      if (file.createNewFile()) {
        System.out.println("File created " + file.getName());
      } else {
        System.out.println("File already exists");
      }
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);
      HashMap<String, Double> results = farm.getAnnualReport(year);
      Set<String> farmIDs = results.keySet();
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
      e.printStackTrace();
    } finally {
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }

  }

  /**
   * Export Date Range Report to file
   * 
   * @param fileName
   * @param fromDate
   * @param toDate
   * @throws IOException
   */
  public void exportDateRangeReport(String fileName, LocalDate fromDate, LocalDate toDate)
      throws IOException {
    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      File file = new File(fileName);
      if (file.createNewFile()) {
        System.out.println("File created " + file.getName());
      } else {
        System.out.println("File already exists");
      }
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);
      HashMap<String, Double> results = farm.getDateRangeReport(fromDate, toDate);
      Set<String> farmIDs = results.keySet();
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
      e.printStackTrace();
    } finally {
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }
  }

  public void exportMonthlyReport(String fileName, Integer year, Integer month) throws IOException {
    FileWriter writer = null;
    BufferedWriter bw = null;
    try {
      File file = new File(fileName);
      if (file.createNewFile()) {
        System.out.println("File created " + file.getName());
      } else {
        System.out.println("File already exists");
      }
      writer = new FileWriter(file);
      bw = new BufferedWriter(writer);
      HashMap<String, Double> results = farm.getMonthlyReport(year, month);
      Set<String> farmIDs = results.keySet();
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
      e.printStackTrace();
    } finally {
      if (bw != null) {
        bw.flush();
        bw.close();
      }
    }
  }

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    LocalDate date = LocalDate.of(2019, 12, 12);
    System.out.println(date);
  }



}
