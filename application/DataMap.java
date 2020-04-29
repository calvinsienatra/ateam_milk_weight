/**
 * 
 */
package application;
import java.util.HashMap;
import java.util.Set;

/**
 * "HashTable of HashTables" data dump 
 * 
 * K1 (String) : Farm ID
 * K2 (String) : Date 
 * V  (Integer): weight
 * 
 * @author ajshe
 *
 */
public class DataMap<K1, K2, V> {
  
  private final HashMap<K1, HashMap<K2, V>> data;
  
  public DataMap() {
    this.data = new HashMap<K1, HashMap<K2,V>>();
  }
  
  /**
   * 
   * @param key
   * @return
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
  
  public V getDeepV(K1 key, K2 key2) {
    if (data.containsKey(key)) {
      return data.get(key).get(key2);
    }
    else {
      return null;
    }
  }
  
  /**
   * 
   * @param key
   * @return
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
   * 
   * @param key
   * @param key2
   * @return
   */
  public boolean containsKeys(K1 key, K2 key2) {
    return data.containsKey(key) && data.get(key).containsKey(key2);
  }
  
  public Set<K1> keySet() {
    return data.keySet();
  }


} // end DataMap class
