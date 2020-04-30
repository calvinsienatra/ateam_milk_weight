package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class FarmMonth implements MilkWeight<Integer, Integer> {

  // Hashtable: Key(Day in month), Value(milkWeight of day)
  private HashMap<Integer, Integer> data;

  // Total milk weight from the month
  private int totalMilkWeight;
  
  public FarmMonth() {
    data = new HashMap<>();
    totalMilkWeight = 0;
  }
  
  /**
   * 
   * Insert Milk Weight on certain day in month
   * 
   */
  @Override
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight) {
    data.put(dateToSet.getDayOfMonth(), milkWeight);
    totalMilkWeight += milkWeight;
  }

  /**
   * Get total milk weight from the entire month
   * 
   * @return
   */
  public int getTotalMilkWeight() {
    return totalMilkWeight;
  }

  /**
   * Get milk weights from specific day
   */
  @Override
  public int getMilkWeight(Integer key) {
    return data.get(key);
  }

  /**
   * Get milk weights from the given fromDay to the end of the month
   */
  @Override
  public List<Integer> getMilkWeightList(Integer key) {

    List<Integer> list = new ArrayList<Integer>();
    int counter = key;

    while (data.get(counter) != null) {
      list.add(data.get(counter));
      counter++;
    }

    return list;
  }

  /**
   * 
   * Get milk weights from the given fromDay to toDay in a list
   * 
   */
  public List<Integer> getMilkWeightList(Integer fromDay, Integer toDay) {

    List<Integer> list = new ArrayList<Integer>();
    int counter = fromDay;

    while (counter != (toDay + 1) && data.get(counter) != null) {
      list.add(data.get(counter));
      counter++;
    }

    return list;
  }


}
