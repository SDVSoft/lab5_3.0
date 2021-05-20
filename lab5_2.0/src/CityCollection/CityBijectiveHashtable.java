package CityCollection;

import Entities.City.City;

import java.util.Hashtable;

/**
 * Strings are understood as keys and Cities as values
 */
public class CityBijectiveHashtable extends Hashtable<String, City> {
    private Hashtable<Long, String> idHashtable;
    private NextId nextId;

    private class NextId {
        private long value = 1;

        public long get() {
            while (idHashtable.containsKey(value)) value++;
            return value;
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
        cloned.idHashtable = (Hashtable<Long, String>) idHashtable.clone();
        cloned.nextId = cloned.new NextId();
        return cloned;
    }

    //TODO: override other methods from hashteble

    /**
     * Tests if some key maps into a city that has the same id with specified city in this
     * hashtable.
     * Note that this method does not guarantee that the specified city equals to the city
     * in this hashtable as determined by the equals method.
     * @param city - a city that has id to search for
     * @return true if and only if some key maps to a city that has the same id with the
     * argument in this hashtable; false otherwise.
     */
    public boolean containsValue(Object city) {
        if (city instanceof City) {
            return idHashtable.containsKey(((City) city).getId());
        }
        return false;
    }

    public boolean contains(Object city) {
        return containsValue(city);
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
     * associated with different and has the same id with specified one
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

    public City putWithNewId(String key, City city) {
        //Creates a copy of the city with new id
        City newCity = new City(nextId.get(), city);
        return put(key, newCity);
    }

    public static void main(String[] args) {
        CityBijectiveHashtable cbh = new CityBijectiveHashtable();
        cbh.put("t", City.getTestCity());
        CityBijectiveHashtable cbh2 = (CityBijectiveHashtable) cbh.clone();
        System.out.println(cbh2.nextId.get());
        cbh.putWithNewId("t2", City.getTestCity());
        System.out.println(cbh2.nextId.get());
    }
}
