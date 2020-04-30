package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Farm implements MilkWeight<Integer, Integer> {

  private Hashtable<Integer, FarmYear> data;

  private final String farmID;

  private int totalMilkWeight;

  /**
   * @param farmID
   *
   */
  public Farm(String farmID) {
    this.farmID = farmID;
  }

  /**
   * 
   * Set milk weight for that specific date
   * 
   */
  @Override
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight) {

    FarmYear year = data.get(dateToSet.getYear());
    year.insertMilkWeight(dateToSet, milkWeight);
    totalMilkWeight += milkWeight;

  }

  /**
   * Get total milk weights from farm
   */
  public int getTotalMilkWeight() {
    return totalMilkWeight;
  }

  /**
   * Get total milk weights from given year
   */
  @Override
  public int getMilkWeight(Integer key) {

    FarmYear year = data.get(key);
    int milk = year.getTotalMilkWeight();
    return milk;
  }

  /**
   * Get total milk weights from specific month and year
   */
  public int getMilkWeight(Integer year, Integer month) {

    FarmYear yearTemp = data.get(year);
    int milk = yearTemp.getMilkWeight(month);
    return milk;
  }

  /**
   * Get milk weight for that specific date
   */
  public int getMilkWeight(Integer year, Integer month, Integer day) {

    FarmYear yearTemp = data.get(year);
    int milk = yearTemp.getMilkWeight(month, day);
    return milk;
  }

  // /**
  // * Get total milk weight from range fromYear and from Month to toYear and toMonth
  // */
  // public int getMilkWeight(Integer fromYear, Integer fromMonth, Integer toYear, Integer toMonth)
  // {
  //
  // return 0;
  // }
  //
  // /**
  // * Get total milk weight from range fromYear, fromMonth, and fromDay to toYear, toMonth, and
  // toDay
  // */
  // public int getMilkWeight(Integer fromYear, Integer fromMonth, Integer fromDay, Integer toYear,
  // Integer toMonth, Integer toDay) {
  //
  // return 0;
  // }


  /**
   * Get total milk weights for each month of the given year
   */
  @Override
  public List<Integer> getMilkWeightList(Integer key) {

    List<Integer> list = new ArrayList<Integer>();
    FarmYear yearTemp = data.get(key);
    for (int i = 1; i < 13; i++) {
      list.addAll(yearTemp.getMilkWeightList(i));
    }

    return list;
  }
  //
  // /**
  // * Get milk weights for each day of the given month
  // */
  // public List<Integer> getMilkWeightList(Integer year, Integer month) {
  // return null;
  // }
  //
  // /**
  // * Get milk weights for each day starting from the given fromDate to the toDate
  // */
  // public List<Integer> getMilkWeightList(Integer fromYear, Integer fromMonth, Integer fromDay,
  // Integer toYear, Integer toMonth, Integer toDay) {
  // return null;
  // }



}
