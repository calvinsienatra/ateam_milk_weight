package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FarmYear implements MilkWeight<Integer, Integer> {


  private HashMap<Integer, FarmMonth> data;
  private int totalMilkWeight;

  public FarmYear() {
  }


  @Override
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight) {
    FarmMonth temp = data.get(dateToSet.getMonthValue());
    temp.insertMilkWeight(dateToSet, milkWeight);
    totalMilkWeight += milkWeight;

  }

  @Override
  public int getMilkWeight(Integer key) {
    FarmMonth temp = data.get(key);
    return temp.getTotalMilkWeight();
  }


  public int getMilkWeight(Integer month, Integer day) {
    FarmMonth temp = data.get(month);
    return temp.getMilkWeight(day);
  }

  public int getTotalMilkWeight() {
    return totalMilkWeight;
  }

  @Override
  public List<Integer> getMilkWeightList(Integer key) {
    List<Integer> list = new ArrayList<Integer>();
    FarmMonth temp = data.get(key);
    return temp.getMilkWeightList(1); // return entire month
  }

  public List<Integer> getMilkWeightList(LocalDate start, LocalDate end) {

    List<Integer> list = new ArrayList<Integer>();
    end.plusDays(1);
    while (!start.equals(end)) {
      
      int month = start.getMonthValue();
      list.add(data.get(month).getMilkWeight(start.getDayOfMonth()));
    }
    return list;
  }



}
