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

import java.util.HashMap;
import java.util.Set;

/**
 * "HashTable of HashTables" data Dump
 * 
 * called in Main.java to input the raw data
 * 
 * K1 : Farm ID K2 : Date V : weight
 * 
 * @author aTeam 131
 *
 */
public class DataMap<K1, K2, V> {

  // HashMap of hashMap DS
  private final HashMap<K1, HashMap<K2, V>> data;

  /**
   * no-arg constructor
   */
  public DataMap() {
    this.data = new HashMap<K1, HashMap<K2, V>>(); // Create a new hashmap
  }

  /**
   * Insert value into DataMap
   * 
   * @param key   - key
   * @param key2  - second key
   * @param value - value to insert
   */
  public void putV(K1 key, K2 key2, V value) {
    HashMap<K2, V> map;
    if (data.containsKey(key)) { // Check if already contains the key
      map = data.get(key); // Get the key
    } else {
      map = new HashMap<K2, V>(); // Create a new hashmap
      data.put(key, map);
    }
    map.put(key2, value); // Put the value
  }

  /**
   * get values for DataMap
   * 
   * @param key  - key
   * @param key2 - key2
   * @return the deep data value of the given keys
   */
  public V getDeepV(K1 key, K2 key2) {
    if (data.containsKey(key)) {
      return data.get(key).get(key2);
    } else {
      return null;
    }
  }

  /**
   * get HashMap from given key
   * 
   * @param key key to get the value of
   * @return hashMap value of the key
   */
  public HashMap<K2, V> getV(K1 key) {
    if (data.containsKey(key)) {
      return data.get(key);
    } else {
      return null;
    }
  }

  /**
   * assert two given keys exist by calling containsKey(key)
   * 
   * @param key  - key
   * @param key2 - key2
   * @return - true is key and key2 exist, else false
   */
  public boolean containsKeys(K1 key, K2 key2) {
    return data.containsKey(key) && data.get(key).containsKey(key2);
  }

  public Set<K1> keySet() {
    return data.keySet();
  }


}
