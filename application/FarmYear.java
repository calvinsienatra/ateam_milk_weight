package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class FarmYear implements MilkWeight<Integer, Integer> {


  private HashMap<Integer, FarmMonth> data;
  private int totalMilkWeight;

  public FarmYear() {
    data = new HashMap<>();
    totalMilkWeight = 0;
  }


  @Override
  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight) {
    if(!data.containsKey(dateToSet.getMonthValue())) {
      data.put(dateToSet.getMonthValue(), new FarmMonth());
    }
    
    FarmMonth temp = data.get(dateToSet.getMonthValue());
    System.out.println(dateToSet + ": " + temp);
    temp.insertMilkWeight(dateToSet, milkWeight);
    totalMilkWeight += milkWeight;
  }

  @Override
  public int getMilkWeight(Integer key) throws MissingFormatArgumentException{
    System.out.println(key);
    System.out.println(data.keySet());
    if(data.keySet().size() < 12) {
      throw new MissingFormatArgumentException("Month incomplete. Program should ignore this farm.");
    }
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
