/**
 * 
 */
package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Main Parser for application
 * 
 * @author ajshe
 *
 */
public class InputParser {

  DataMap<String, String, Integer> data;
  BufferedReader br = null;
  String line = "";
  String splitBy = ",";

  /**
   * no-arg constructor setup 
   */
  public InputParser() {
    this.data = new DataMap<String, String, Integer>();
  }

  /**
   * parser for input csv files 
   * 
   * @TODO add more exception handling and format checking
   * @param path
   * @throws IOException
   */
  public void inputData(String path) throws IOException {
    try {

      br = new BufferedReader(new FileReader(path));
      @SuppressWarnings("unused")
      String headerLine = br.readLine(); // ignore header line
      while ((line = br.readLine()) != null) {

        String[] row = line.split(splitBy);
        System.out.println(Arrays.deepToString(row));
        String farmID = row[1];
        String date = row[0];
        int weight = Integer.parseInt(row[2]);

        data.putV(farmID, date, weight);


      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
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

  /**
   * @param args
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    InputParser parser = new InputParser();
    
    // edit file path to run on your machine
    parser.inputData(
        "C:\\Users\\ajshe\\OneDrive\\Documents\\cs400\\ateam_milk_weight\\milk\\small\\2019-1.csv");
    System.out.println(parser.data.keySet());
    System.out.println(parser.data.getV("Farm 2"));

    parser.printData();
  }

}
