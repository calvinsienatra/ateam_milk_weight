/**
 * 
 */
package application;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Charlie
 *
 */
public interface MilkWeight<T, U> {

  public void insertMilkWeight(LocalDate dateToSet, Integer milkWeight);

  public int getMilkWeight(T key);

  public List<Integer> getMilkWeightList(T key);

}
