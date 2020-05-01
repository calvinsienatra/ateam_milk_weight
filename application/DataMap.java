/**
 * Date: 4/30/2020
 */
package application;
import java.util.HashMap;
import java.util.Set;

/**
 * "HashTable of HashTables" data Dump 
 * 
 * called in Main.java to input the raw data
 * 
 * K1 : Farm ID
 * K2 : Date 
 * V  : weight
 * 
 * @author ajshe
 *
 */
public class DataMap<K1, K2, V> {

  // HashMap of hashMap DS
  private final HashMap<K1, HashMap<K2, V>> data;
  
  /**
   * no-arg constructor
   */
  public DataMap() {
    this.data = new HashMap<K1, HashMap<K2,V>>();
  }
  
  /**
   * Insert value into DataMap
   * 
   * @param key - key
   * @param key2 - second key
   * @param value - value to insert
   */
  public void putV(K1 key, K2 key2, V value) {
    HashMap<K2, V> map;
    if (data.containsKey(key)) {
      map = data.get(key);
    }
    else {
      map = new HashMap<K2,V>();
      data.put(key, map);
    }
    map.put(key2, value);
  }
  
  /**
   * get values for DataMap
   * 
   * @param key - key  
   * @param key2 - key2
   * @return
   */
  public V getDeepV(K1 key, K2 key2) {
    if (data.containsKey(key)) {
      return data.get(key).get(key2);
    }
    else {
      return null;
    }
  }
  
  /**
   * get HashMap from given key
   * @param key
   * @return hashMap 
   */
  public HashMap<K2, V> getV(K1 key) {
    if (data.containsKey(key)) {
      return data.get(key);
    }
    else {
      return null;
    }
  }
    
  /**
   * assert two given keys exist
   * by calling containsKey(key)
   * @param key - key
   * @param key2 - key2
   * @return - true is key and key2 exist, else false
   */
  public boolean containsKeys(K1 key, K2 key2) {
    return data.containsKey(key) && data.get(key).containsKey(key2);
  }
  
  public Set<K1> keySet() {
    return data.keySet();
  }
  
 
} // end DataMap class
