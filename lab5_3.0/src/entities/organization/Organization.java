package entities.organization;

import entities.organization.attributes.OrganizationType;
import exceptions.OrganizationAlreadyExistsException;

import java.io.*;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class Organization implements Serializable {
    private String fullName; //Значение этого поля должно быть уникальным, Поле не может быть null
    private double annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null

    private transient boolean erased;

    private static HashMap<String, WeakReference<Organization>> accessibleOrgMap = new HashMap<>();
    private static HashMap<String, Organization> readyToEraseMap = new HashMap<>();

    private Organization(String fullName, double annualTurnover, OrganizationType type)
            throws NullPointerException, OrganizationAlreadyExistsException, IllegalArgumentException
    {
        if (fullName == null)
            throw new NullPointerException("Имя Организации не может иметь значение null.");
        if (type == null)
            throw new NullPointerException("Значение поля Organization.type не может иметь значение null.");
        if (annualTurnover <= 0)
            throw new IllegalArgumentException("Значение поля Organization.type должно быть больше 0.");
        if (accessibleOrgMap.containsKey(fullName) || readyToEraseMap.containsKey(fullName))
            throw new OrganizationAlreadyExistsException("Организация с именем " + fullName + " уже существует.");

        accessibleOrgMap.put(fullName, new WeakReference<>(this));

        this.fullName = fullName;
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.erased = false;

        //System.out.println("init: " + this);
    }

    private Organization(Organization other) {
        this.fullName = other.fullName;
        this.annualTurnover = other.annualTurnover;
        this.type = other.type;
        this.erased = other.erased;

        //System.out.println("erasable init: " + this);
    }

    public String getFullName() {
        return fullName;
    }

    public double getAnnualTurnover() { return annualTurnover; }

    public void setAnnualTurnover(double annualTurnover) { this.annualTurnover = annualTurnover; }

    public OrganizationType getType() { return type; }

    public void setType(OrganizationType type) { this.type = type; }

    public static Organization getInstance(String fullName, double annualTurnover, OrganizationType type) {
        if (accessibleOrgMap.containsKey(fullName)) {
            Organization existOrg = accessibleOrgMap.get(fullName).get();
            if (existOrg.annualTurnover == annualTurnover && existOrg.type == type)
                return existOrg;
            throw new OrganizationAlreadyExistsException("Организация с именем " + fullName + " уже существует.");

        } else if (readyToEraseMap.containsKey(fullName)) {
            Organization existOrg = readyToEraseMap.get(fullName);
            if (existOrg.annualTurnover == annualTurnover && existOrg.type == type) {
                accessibleOrgMap.put(fullName, new WeakReference<>(existOrg));
                readyToEraseMap.remove(fullName);
                return existOrg;
            }
            throw new OrganizationAlreadyExistsException("Организация с именем " + fullName + " уже существует.");

        } else {
            return new Organization(fullName, annualTurnover, type);
        }

    }

    public static Organization getInstance(String fullName) {
        if (accessibleOrgMap.containsKey(fullName))
            return accessibleOrgMap.get(fullName).get();

        if (readyToEraseMap.containsKey(fullName)) {
            Organization existOrg = readyToEraseMap.remove(fullName);
            accessibleOrgMap.put(fullName, new WeakReference<>(existOrg));
            return existOrg;
        }
        return null;
    }

    public static boolean exists(String fullName) {
        return (accessibleOrgMap.containsKey(fullName) || readyToEraseMap.containsKey(fullName));
    }

    public void finalize() throws Throwable {
        //System.out.print("fin: " +  this);
        //System.out.print(" " + accessibleOrgMap.get(this.fullName).get());
        if (!this.erased) {
            //System.out.println(" ready to erase");
            readyToEraseMap.put(this.fullName, new Organization(this));
            accessibleOrgMap.remove(this.fullName);
        }/* else {
            System.out.println();
        }*/
        super.finalize();
    }

    public static boolean erase(String fullName) {
        System.gc();
        System.runFinalization();

        /*if (readyToEraseMap.containsKey(fullName)) {
            System.out.println(readyToEraseMap.get(fullName) + " erased");
        }*/
        Organization erasedOrg = readyToEraseMap.remove(fullName);
        return erasedOrg != null && (erasedOrg.erased = true);
    }

    public static boolean isReadyToErase(String fullName) {
        System.gc();
        System.runFinalization();

        return readyToEraseMap.containsKey(fullName);
    }

    private static class OrganizationSerializableData implements Serializable {
        String fullName;
        double annualTurnover;
        OrganizationType type;

        OrganizationSerializableData (Organization org) {
            this.fullName = org.fullName;
            this.annualTurnover = org.annualTurnover;
            this.type = org.type;
        }

        protected Object readResolve() throws ObjectStreamException {
            return Organization.getInstance(this.fullName,
                                            this.annualTurnover,
                                            this.type);
        }
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new OrganizationSerializableData(this);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Organization org = Organization.getInstance("name", 2, OrganizationType.PUBLIC);
        org = null;
        System.out.println(Organization.isReadyToErase("name"));
        System.out.println(Organization.getInstance("name").annualTurnover);
        try {
            Organization.getInstance("name", 3, OrganizationType.TRUST);
        } catch (OrganizationAlreadyExistsException oaee) {
            System.out.println("caught");
        }
        System.out.println(Organization.erase("name"));
        try {
            Organization.getInstance("name", 3, OrganizationType.TRUST);
            System.out.println("didn't catch");
        } catch (OrganizationAlreadyExistsException oaee) {
            System.out.println("caught");
        }
        System.out.println(Organization.getInstance("name").type);
        System.out.println(Organization.getInstance("name", 3, OrganizationType.TRUST).annualTurnover);

        System.out.println();
        System.out.println(Organization.isReadyToErase("name"));
        System.out.println(Organization.getInstance("name").type);

        org = Organization.getInstance("name");

        System.out.println(Organization.erase("name"));

        byte[] data;
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(org);
            data = byteOut.toByteArray();
        }

        //org.annualTurnover = 10;
        Organization org2;
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(data))) {
            org2 = (Organization) objIn.readObject();
        }
        System.out.println("Org2: " + org2.fullName + "; " + org2.annualTurnover + "; " + org2.type + "; " + org2.erased + "; ");
    }
}