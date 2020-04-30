package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FarmYear extends DataMap<String, String, Integer>
    implements MilkWeight<String, String> {

  private DataMap<String, String, Integer> data;

  public FarmYear() {
    this.data = new DataMap<String, String, Integer>();
  }

  /**
   * input main data map
   * 
   * @param data
   */
  public FarmYear(DataMap<String, String, Integer> orgData) {
    this.data = orgData;
  }

 
  
  public List<Integer> getMilkWeight(String farmID, String year) {
    
    ArrayList<Integer> milkList = new ArrayList<Integer>();
    HashMap<String, Integer> farm = data.getV(farmID);
    System.out.println(farm.keySet());
    for (String key : farm.keySet()) {
      String yearString = key.split("-", 0)[0];
      if (yearString.equals(year)) {
        milkList.add(farm.get(key));
      }
    }
    return milkList;

  }

  @Override
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight) {
    // TODO Auto-generated method stub

  }

  @Override
  public int getMilkWeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<Integer> getMilkWeightList() {
    // TODO Auto-generated method stub
    return null;
  }
  
  public DataMap<String, String, Integer> getData() {
    return data;
  }
  
  /**
   * print data from data hashMap
   */
  public void printData() {
    StringBuffer dataString = new StringBuffer();
    for (String farm : data.keySet()) {
      HashMap<String, Integer> value = data.getV(farm);
      System.out.println();
      System.out.println("######## Farm Id: " + farm);
      System.out.println();
      dataString.append(farm);
      for (String date : value.keySet()) {
        System.out.println("         ---date: " + date);
        dataString.append(date);
        System.out.println("         -------weights: " + value.get(date));
        dataString.append(value.get(date));
      }
    }

  }

  public static void main(String[] args) throws IOException {
    InputParser parser = new InputParser();

    // edit file path to run on your machine
    parser.inputData(
        "C:\\Users\\ajshe\\OneDrive\\Documents\\cs400\\ateam_milk_weight\\milk\\small\\2019-1.csv");
    FarmYear farmYear = new FarmYear(parser.data);
    System.out.println(farmYear.getMilkWeight("Farm 1", "2019"));
    
  }

}
