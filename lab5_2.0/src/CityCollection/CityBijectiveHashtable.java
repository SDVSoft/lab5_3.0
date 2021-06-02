package CityCollection;

import Entities.City.City;

import java.util.*;

/**
 * Strings are understood as keys and Cities as values
 */
public class CityBijectiveHashtable extends Hashtable<String, City> {
    private Hashtable<Long, String> idHashtable;
    private NextId nextId;

    private class NextId {
        private long nextIdValue = 1;

        public long get() {
            while (idHashtable.containsKey(nextIdValue)) nextIdValue++;
            return nextIdValue;
        }
    }

    public CityBijectiveHashtable() {
        super();
        this.idHashtable = new Hashtable<>();
        this.nextId = new NextId();
    }

    /**
     * Clears this hashtable so that it contains no keys.
     */
    public void clear() {
        super.clear();
        idHashtable.clear();
    }

    /**
     * Creates a shallow copy of this hashtable. All the structure of the hashtable itself
     * is copied, but the keys and cities are not cloned.
     * This is a relatively expensive operation.
     * @return a clone of the hashtable
     */
    public Object clone() {
        CityBijectiveHashtable cloned = (CityBijectiveHashtable) super.clone();
        @SuppressWarnings("unchecked") Hashtable<Long, String> clonedIdHashtable =
                (Hashtable<Long, String>) idHashtable.clone();
        cloned.idHashtable = clonedIdHashtable;
        cloned.nextId = cloned.new NextId();
        return cloned;
    }

    /**
     * Tests if some key maps into a city that has the same id with specified city in this
     * hashtable.
     * Note that this method does not guarantee that the specified city equals to the city
     * in this hashtable as determined by the equals method.
     * @param city - a city that has id to search for
     * @return true if and only if some key maps to a city that has the same id with the
     * argument in this hashtable; false otherwise.
     */
    public boolean contains(Object city) {
        if (city instanceof City) {
            return idHashtable.containsKey(((City) city).getId());
        }
        return false;
    }

    /**
     * Maps the specified key to the specified city c in this hashtable if the hashtable
     * previously did not contain a mapping for a city c2 that has the same id with
     * specified city c if city c2 was associated with specified key. (A hashtable ht is
     * said to contain a mapping for a city c if and only if ht.containsValue(c) would
     * return true.). If this hashtable already contains a city c2 that has the same id
     * with city c and city c2 is associated with some key k2 that differs from specified
     * key, an exception will be thrown. This ensures that bijective hashtable never
     * contains cities with duplicate ids.
     * @param key - key with which the specified city is to be associated
     * @param city - city to be associated with the specified key
     * @return the previous city of the specified key in this hashtable,
     * or null if it did not have one
     * @throws IllegalArgumentException - if this hashtable previously contained some city
     * associated with different key and has the same id with specified one
     * @throws NullPointerException - if the key or value is null
     */
    public City put(String key, City city) throws IllegalArgumentException, NullPointerException {
        if (idHashtable.containsKey(city.getId()) && !key.equals(idHashtable.get(city.getId())))
            throw new IllegalArgumentException("В коллекции уже есть город с таким же id.");
        if (containsKey(key))
            idHashtable.remove(get(key).getId(), key);
        idHashtable.put(city.getId(), key);
        return super.put(key, city);
    }

    /**
     * Creates a copy of the city with new id that did not already present in this
     * hashtable and maps this copy to the specified value
     * @param key - key with which the copy of the specified city is to be associated
     * @param city - city to be copied
     * @return the previous value associated with key, or null if there was no mapping
     * for key
     * @throws NullPointerException - if the key or value is null
     */
    public City putWithUniqId(String key, City city) throws NullPointerException {
        long uniqId = idHashtable.containsKey(city.getId()) ? nextId.get() : city.getId();
        City newCity = new City(uniqId, city);
        return put(key, newCity);
    }

    /**
     * Copies all of the mappings from the specified map to this hashtable. These mappings
     * will replace any mappings that this hashtable had for any of the keys currently in
     * the specified map. The effect of this call is equivalent to that of calling
     * put(k, v) on this map once for each mapping from key k to city c in the specified
     * map. If some city from specified map t has a duplicate id that already present in
     * this hashtable, mapping of such city will be ignored, and this hashtable will stay
     * unchanged. (Behavior similar to Set.addAll(Collection))
     * @param t - mappings to be stored in this map
     * @throws NullPointerException - if the specified map is null
     */
    public void putAll(Map<? extends String, ? extends City> t) throws NullPointerException {
        for (String key : t.keySet()) {
            try {
                put(key, t.get(key));
            } catch (IllegalArgumentException ignore) {}
        }
    }

    /**
     * Copies all of the mappings from the specified map to this hashtable. These mappings
     * will replace any mappings that this hashtable had for any of the keys currently in
     * the specified map. If some city c associated with key k from specified map t has a
     * duplicate id that already present in this hashtable, copy of such city will be
     * created with new unique id and associated with k in this hashtable. The effect of
     * this call is equivalent to that of calling putWithUniqId(k, v) on this map once for
     * each mapping from key k to city c in the specified map.
     * @param t - mappings to be stored in this map
     * @throws NullPointerException - if the specified map is null
     */
    public void putAllWithUniqId(Map<? extends String, ? extends City> t) throws NullPointerException {
        for (String key : t.keySet())
            putWithUniqId(key, t.get(key));
    }

    /**
     * Returns the key which mapped to a city having the same id with the specified city,
     * or null if this map contains no mapping for id of specified city.
     * More formally, if value is instance of City and this map contains a mapping from
     * a key k to a city c such that ((City)value.getId().equals(c.getId())), then this
     * method returns k; otherwise it returns null.
     * @param value - the city whose id is checked for mapping with key to be returned
     * @return the key being mapped to the id of the specified city, or null if this map
     * contains no mapping for this id
     */
    public String getKeyForValue(Object value) {
        if (value instanceof City)
            return idHashtable.get(((City)value).getId());
        return null;
    }

    /**
     * Returns the key which mapped to a city having the specified id, or null if this map
     * contains no mapping for such city.
     * More formally, if this map contains a mapping from a key k to a city c such that
     * (c.getId().equals(id), then this method returns k; otherwise it returns null.
     * @param id - the id is checked for mapping with key to be returned
     * @return the key being mapped to the specified id, or null if this map contains no
     * mapping for this id
     */
    public String getKeyForId(long id) {
        return idHashtable.get(id);
    }

    /**
     * Removes the key (and its corresponding city) from this hashtable. This method does
     * nothing if the key is not in the hashtable.
     * @param key - the key that needs to be removed
     * @return the city to which the key had been mapped in this hashtable, or null if the
     * key did not have a mapping
     * @throws NullPointerException - if the key is null
     */
    public City remove(Object key) throws NullPointerException {
        City city = super.remove(key);
        if (city != null)
            idHashtable.remove(city.getId());
        return city;
    }

    /**
     * Removes the city (and its associated key) that has the same id with specified city
     * from this hashtable. This method does nothing if there is no such city in the
     * hashtable. Note that this method does not guarantee that the removed city c1 is
     * equal to the specified city c, it only figures out that c1 has the same id with c.
     * (c.getId().equals(c1.getId) = true).
     * @param value - the value that needs to be removed
     * @return the key mapped to the city with specified id in this hashtable, or null if
     * there was no such city in the hashtable
     * @throws NullPointerException - if the value is null
     */
    public String removeValue(Object value) throws NullPointerException {
        if (value == null) throw new NullPointerException();
        if (value instanceof City) {
            String key = idHashtable.remove(((City) value).getId());
            if (key != null)
                super.remove(key);
            return key;
        }
        return null;
    }

    public static void main(String[] args) {
        CityBijectiveHashtable cbh = new CityBijectiveHashtable();
        cbh.put("t", City.getDefaultCity());
        CityBijectiveHashtable cbh2 = (CityBijectiveHashtable) cbh.clone();
        System.out.println(cbh2.nextId.get());
        cbh.putWithUniqId("t2", City.getDefaultCity());
        System.out.println(cbh2.nextId.get());
        System.out.println(cbh);
        System.out.println(City.getDefaultCity().getId().equals((long) 12));
    }
}
