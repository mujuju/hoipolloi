package hoipolloi;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.regex.*;

/**
 * The Person Class stores people objects and has methods to send and retrieve their information from the database.
 *
 * @author  Brandon Tanner
 * @version 0.99t Dec 1, 2008
 * @since   November 20, 2006
 */
public class Person implements Comparable {
    
    /** The Person ID for this Person in the Database */
    protected int personID;
    /** The Prefix for this Person, like Mr or Dr */
    protected String prefix;
    /** The Suffix for this Person, like Jr or Sr */
    protected String suffix;
    /** The Person's First Name */
    protected String firstName;
    /** The Person's Middle Name */
    protected String middleName;
    /** The Person's Last Name */
    protected String lastName;
    /** The Person's Maiden Name */
    protected String maidenName;
    /** The Person's Gender, Male or Female */
    protected String gender;
    /** The Person's Eye Color, Green/Hazel/Black etc */
    protected String eyeColor;
    /** The Person's Hair Color */
    protected String hairColor;
    /** The Person's Height (Customary or Metric) */
    protected String height;
    /** The Person's Weight (Customary or Metric) */
    protected String weight;
    /** The Person's Birthday in IS0-8601 Format YYYY-MM-DD */
    protected String birthday;
    /** A Description of this Person or any other optional information */
    protected String description;
    /** The Person's Nick Name */
    protected String nickName;
    /** The Person's Demonym like American, Thai or Singaporean */
    protected KeyValue demonym;
    /** The Person's Profile Picture in Binary or BASE64 Format */
    protected String photoBinary;
    /** The File Name of the Person's Profile Picture */
    protected String photoFileName;
    /** The Date When this Person's Profile Was Last Updated in ISO-8601 Format YYYY-MM-DD */
    protected String lastUpdate;
    /** The List of Categories that this Person is in (each category is a KeyValue pair in this ArrayList) */
    protected ArrayList <KeyValue> categories;
    /** The List of Contacts for this Person */
    protected ArrayList <Contact> contacts;
    /** The List of Addresses for this Person */
    protected ArrayList <Address> addresses;
    /** The Person's Occupation */
    protected String occupation;
    
    /** Creates a new instance of Person (for Adding a new one) */
    public Person() {
        this.personID      = -1;
        this.prefix        = "";
        this.suffix        = "";
        this.firstName     = "";
        this.middleName    = "";
        this.lastName      = "";
        this.maidenName    = "";
        this.gender        = "";
        this.eyeColor      = "";
        this.hairColor     = "";
        this.height        = "";
        this.weight        = "";
        this.birthday      = "0000-00-00";
        this.description   = "";
        this.nickName      = "";
        this.demonym       = new KeyValue();
        this.photoBinary   = "";
        this.photoFileName = "";
        this.lastUpdate    = "0000-00-00";
        this.categories    = new ArrayList<KeyValue>();
        this.contacts      = new ArrayList<Contact>();
        this.addresses     = new ArrayList<Address>();
        this.occupation    = "";
    }
    
    /** 
     * Creates a new instance of Person given a Person ID and populates it from the database.
     *
     * @param pid The Person ID
     * @throws Throws an EmptyQueryException if there is an SQL error or if the PersonID doesn't exist.
     */
    public Person(int pid) throws EmptyQueryException {
        this();                          // Set Default Values
        this.personID = pid;             // Set the Person ID     
        if (!loadFromDatabase()) {       // Load Person Info from Database
            throw new EmptyQueryException("Sorry, there was either an SQL Query Error or that Person ID doesn't exist.");
        }
    }
    
    /** {@inheritDoc} */
    public int compareTo(Object o) {
        return -1;
    }
    
    /**
     * Outputs the Person's Full Name including Prefix, First Name, Middle Name, Last Name and Suffix.
     * If any of the parts of their name are blank or null, they are excluded. White space is also
     * trimmed from the beginning and end. Each part is seperated by a space.
     *
     * @return The Full Name of the Person, each part seperated by spaces.
     */
    @Override public String toString() {
        String name = new String("");
        if (!this.prefix.equals("null"))
            name += this.prefix+" ";
        if (!this.firstName.equals("null"))
            name += this.firstName+" ";
        if (!this.middleName.equals("null"))
            name += this.middleName+" ";
        if (!this.lastName.equals("null"))
            name += this.lastName+" ";
        if (!this.suffix.equals("null"))
            name += this.suffix;
        return name.trim();
    }
    
    /**
     * Sets the Person ID of a newly added Person.
     * saveToDatabase() calls this method internally
     * after inserting a new Person into the database.
     * This method sets the Person ID in this object to
     * the ID of that newly added Person.
     *
     * @return True if Successful, otherwise false.
     */
    private boolean setNewPersonID() {
        if (this.firstName.equals("") || this.lastName.equals("")) {
            return false;
        }
        String sql = "SELECT psnPersonID FROM pmp_people WHERE (psnFirstName = '"+this.firstName+"') AND (psnLastName = '"+this.lastName+"') LIMIT 1";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            int newPersonID = rs.getInt("psnPersonID");
            if (newPersonID < 1) {
                return false;
            }
            else {
                this.personID = newPersonID;
                return true;
            }
        }
        catch (Exception e) { 
            Debug.print("Set New Person ID Failed");
            Debug.print(e.getMessage());
            return false;
        }
        finally {
            // Close the Connection to the Database
            db.closeConnection();
        }
    }
    
    /** 
     * Saves the contents of this object to the database.
     *
     * @return True if Successful, Otherwise False.
     */
    public boolean saveToDatabase() {
        DBConnection db = new DBConnection();
        if (this.personID == -1) {
            try {
                setLastUpdate();
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("INSERT INTO pmp_people (psnPrefix, psnSuffix, psnFirstName, psnMiddleName, psnLastName, psnMaidenName, psnGender, psnEyeColor, psnHairColor, psnHeight, psnWeight, psnBirthday, psnDescription, psnNickName, psnDemonymID, psnPhotoBinary, psnPhotoFileName, psnLastUpdate, psnOccupation) VALUES (\""+this.prefix+"\", \""+this.suffix+"\", \""+this.firstName+"\", \""+this.middleName+"\", \""+this.lastName+"\", \""+this.maidenName+"\", \""+this.gender+"\", \""+this.eyeColor+"\", \""+this.hairColor+"\", \""+this.height+"\", \""+this.weight+"\", \""+this.birthday+"\", \""+this.description+"\", \""+this.nickName+"\", \""+this.demonym.getKey()+"\", \""+this.photoBinary+"\", \""+this.photoFileName+"\", \""+this.lastUpdate+"\", \""+this.occupation+"\")");
                setNewPersonID();
                return true;
            }
            catch (Exception e) { return false; }
            finally { db.closeConnection(); }
        }
        else {
            try {
                setLastUpdate();
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("UPDATE pmp_people SET psnPrefix = \""+this.prefix+"\", psnSuffix = \""+this.suffix+"\", psnFirstName = \""+this.firstName+"\", psnMiddleName = \""+this.middleName+"\", psnLastName = \""+this.lastName+"\", psnMaidenName = \""+this.maidenName+"\", psnGender = \""+this.gender+"\", psnEyeColor = \""+this.eyeColor+"\", psnHairColor = \""+this.hairColor+"\", psnHeight = \""+this.height+"\", psnWeight = \""+this.weight+"\", psnBirthday = \""+this.birthday+"\", psnDescription = \""+this.description+"\", psnNickName = \""+this.nickName+"\", psnDemonymID = \""+this.demonym.getKey()+"\", psnPhotoBinary = \""+this.photoBinary+"\", psnPhotoFileName = \""+this.photoFileName+"\", psnLastUpdate = \""+this.lastUpdate+"\", psnOccupation = \""+this.occupation+"\" WHERE (psnPersonID = \""+this.personID+"\")");
                return true;
            }
            catch (Exception e) { Debug.print(e.getMessage()); return false; }
            finally { db.closeConnection(); }
        }
    }
    
    /**
     * Loads the Categories that this Person is in.
     * When called, the categories list for this person
     * is cleared and the categories that this person is
     * in are reloaded from the database.
     *
     * @return True if Successful, otherwise false.
     */
    private boolean loadCategoriesFromDB() {
        // Reset the Categories List First
        this.categories.clear();
        String sql = "SELECT catCategoryID, catCategoryName FROM pmp_categories, pmp_categorylink WHERE (catCategoryID = clkCategoryID) AND (clkPersonID = '"+this.personID+"')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                this.categories.add(new KeyValue(rs.getInt("catCategoryID"), rs.getString("catCategoryName")));
            }
            return true;
        }
        catch (Exception e) { 
            Debug.print("Load Categories from DB Failed");
            Debug.print(e.getMessage());
            return false;
        }
        finally {
            // Close the Connection to the Database
            db.closeConnection();
        }        
    }
    
    /**
     * Loads the Contacts for this Person from the Database.
     * When called, the contact list for this person
     * is cleared and the contacts for this person are
     * reloaded from the database.
     *
     * @return True if Successful, otherwise false.
     */
    private boolean loadContactsFromDB() {
        // Reset the Contacts List First
        this.contacts.clear();
        String sql = "SELECT ctnContactID, ctnContactTypeID, ctnContact, typContactType FROM pmp_contacts, pmp_ctypes WHERE (typContactTypeID = ctnContactTypeID) AND (ctnPersonID = '"+this.personID+"')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                Contact ctn = new Contact();
                ctn.setContactTypeID(rs.getInt("ctnContactTypeID"));
                ctn.setContactType(rs.getString("typContactType"));
                ctn.setContactID(rs.getInt("ctnContactID"));
                ctn.setContact(rs.getString("ctnContact"));
                this.contacts.add(ctn);
            }
            return true;
        }
        catch (Exception e) {
            Debug.print("Load Contacts from DB Failed");
            Debug.print(e.getMessage());
            return false;
        }
        finally {
            // Close the Connection to the Database
            db.closeConnection();
        }        
    }
    
    /**
     * Loads the Addresses for this Person from the Database.
     * When called, the address list for this person
     * is cleared and the addresses for this person are
     * reloaded from the database.
     *
     * @return True if Successful, otherwise false.
     */
    private boolean loadAddressesFromDB() {
        // Reset Address List
        this.addresses.clear();
        String sql = "SELECT * FROM pmp_addresses, pmp_addresstypes, pmp_countries WHERE (adrAddressTypeID = atpAddressTypeID) AND (adrCountryID = ctyCountryID) AND (adrPersonID = '"+this.personID+"')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                Address tempAddress = new Address();
                tempAddress.setAddressID(rs.getInt("adrAddressID"));
                tempAddress.setAddressType(new KeyValue(rs.getInt("adrAddressTypeID"), rs.getString("atpAddressType")));
                tempAddress.setAddressLabel(rs.getString("adrAddressLabel"));
                tempAddress.setAddressLine1(rs.getString("adrAddressLine1"));
                tempAddress.setAddressLine2(rs.getString("adrAddressLine2"));
                tempAddress.setAddressLine3(rs.getString("adrAddressLine3"));
                tempAddress.setAddressCity(rs.getString("adrCity"));
                tempAddress.setAddressState(rs.getString("adrState"));
                tempAddress.setAddressZip(rs.getString("adrZip"));
                tempAddress.setAddressCountry(new KeyValue(rs.getInt("adrCountryID"), rs.getString("ctyCountryName")));
                tempAddress.setAddressDistrict(rs.getString("adrDistrict"));
                this.addresses.add(tempAddress);
            }
            return true;
        }
        catch (Exception e) {
            Debug.print("Load Addresses from DB Failed");
            Debug.print(e.getMessage());
            return false;
        }
        finally {
            // Close the Connection to the Database
            db.closeConnection();
        }
    }
    
    /**
     * Load all the information for this Person from the Database.
     *
     * @return True if Successful, otherwise false.
     */
    protected boolean loadFromDatabase() {
        String sql = "SELECT * FROM pmp_people, pmp_demonyms WHERE (psnDemonymID = demID) AND (psnPersonID = '"+this.personID+"') LIMIT 1";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            this.prefix        = rs.getString("psnPrefix");
            this.suffix        = rs.getString("psnSuffix");
            this.firstName     = rs.getString("psnFirstName");
            this.middleName    = rs.getString("psnMiddleName");
            this.lastName      = rs.getString("psnLastName");
            this.maidenName    = rs.getString("psnMaidenName");
            this.gender        = rs.getString("psnGender");
            this.eyeColor      = rs.getString("psnEyeColor");
            this.hairColor     = rs.getString("psnHairColor");
            this.height        = rs.getString("psnHeight");
            this.weight        = rs.getString("psnWeight");
            this.birthday      = rs.getString("psnBirthday");
            this.description   = rs.getString("psnDescription");
            this.nickName      = rs.getString("psnNickName");
            this.demonym.setKey(rs.getInt("demID"));
            this.demonym.setValue(rs.getString("demDemonym"));
            this.photoBinary   = rs.getString("psnPhotoBinary");
            this.photoFileName = rs.getString("psnPhotoFileName");
            this.lastUpdate    = rs.getString("psnLastUpdate");
            this.occupation    = rs.getString("psnOccupation");
            db.closeConnection(); 
            // close this so the following methods can open their own db connections
            // if i knew how to pass the existing db connection to them i would, but java doesnt seem
            // to like pass by reference, so i dont know how right now.
            loadCategoriesFromDB();
            loadContactsFromDB();
            loadAddressesFromDB();
            return true;
        }
        catch (Exception e) { 
            Debug.print("Error2: "+e.getMessage());
            return false;
        }
        finally {
            // Close the Connection to the Database if still open
            db.closeConnection();
        }
    }
    
    /**
     * Sets the Person's Prefix (Mr, Mrs, Dr etc).
     *
     * @param prefix The Person's Prefix.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix.trim();
    }

    /**
     * Sets the Person's Suffix (Jr, Sr, III etc).
     *
     * @param suffix The Person's Suffix.
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix.trim();
    }
    
    /**
     * Sets the Person's First Name.
     *
     * @param firstname The Person's First Name.
     */
    public void setFirstName(String firstname) {
        this.firstName = firstname.trim();
    }
    
    /**
     * Sets the Person's Middle Name.
     *
     * @param middlename The Person's Middle Name
     */
    public void setMiddleName(String middlename) {
        this.middleName = middlename.trim();
    }
    
    /**
     * Sets the Person's Last Name.
     *
     * @param lastname The Person's Last Name
     */
    public void setLastName(String lastname) {
        this.lastName = lastname.trim();
    }
    
    /**
     * Sets the Person's Maiden Name.
     *
     * @param maidenname The Person's Maiden Name
     */
    public void setMaidenName(String maidenname) {
        this.maidenName = maidenname.trim();
    }
    
    /**
     * Sets the Person's Gender.
     * Hopefully Male or Female ;)
     *
     * @param gender The Person's Gender, Male or Female.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * Sets the Person's Eye Color
     *
     * @param eyeColor The Person's Eye Color
     */
    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }
    
    /**
     * Sets the Person's Hair Color
     *
     * @param hairColor The Person's Hair Color
     */
    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }
    
    /**
     * Sets the Person's Height
     *
     * @param height The Person's Height
     */
    public void setHeight(String height) {
        // Replace any quotes with "in" for inches.
        this.height = height.replaceAll("\"", "in");
    }
    
    /**
     * Sets the Person's Weight
     *
     * @param weight The Person's Weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    /**
     * Sets the Person's Birthday.
     * The format is YYYY-MM-DD.
     *
     * @param birthday The Person's Birthday
     */
    public void setBirthday(String birthday) {
        // need to verify what we are setting
        // format should be YYYY-MM-DD with leading zeroes.
        // use some regex
        if (birthday.length() != 10) {
            Debug.print("In setBirthday, the given Birthday lenght is NOT 10.");
            System.exit(1);
        }
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher m = p.matcher(birthday);
        if (m.matches()) {
            this.birthday = birthday;
        }
        else {
            Debug.print("Birthday Regex Failed to Match");
            System.exit(1);
        }
    }
    
    /**
     * Sets the Description for this Person
     *
     * @param desc The Person's Description
     */
    public void setDescription(String desc) {
        this.description = desc;
    }
    
    /**
     * Sets the Person's Nick Name
     *
     * @param nickname The Person's Nick Name
     */
    public void setNickName(String nickname) {
        this.nickName = nickname.trim();
    }
    
    /**
     * Sets the Person's Demonym
     *
     * @param demonym The Person's Demonym
     */
    public void setDemonym(KeyValue demonym) {
        this.demonym.setKey(demonym.getKey());
        this.demonym.setValue(demonym.getValue());
    }
    
    /**
     * Stores the Person's Photo as a binary blob.
     *
     * @param photoblob The binary/base64 of a Photo
     */
    public void setPhotoBinary(String photoblob) {
        this.photoBinary = photoblob;
    }
    
    /**
     * Sets the Person's Photo Filename
     *
     * @param filename The Person's Photo Filename
     */
    public void setPhotoFileName(String filename) {
        this.photoFileName = filename;
    }
    
    /**
     * Sets the Person's Last Profile Update to right now.
     */
    public void setLastUpdate() {
        java.util.Date today = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.lastUpdate = sdf.format(today.getTime());
    }
    
    /**
     * Sets the Person's Occupation
     *
     * @param occupation The Person's Occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation.trim();
    }
    
    /**
     * Gets the Person's ID
     *
     * @return The Person's ID
     */
    public int getPersonID() {
        return this.personID;
    }
    
    /**
     * Gets the Person's Name Prefix
     *
     * @return The Person's Prefix (if available, like Mr or Mrs)
     */
    public String getPrefix() {
        return this.prefix;
    }
    
    /**
     * Gets the Person's Name Suffix
     *
     * @return The Person's Suffix (if available, like Jr or Sr)
     */
    public String getSuffix() {
        return this.suffix;
    }
    
    /**
     * Gets the Person's First Name
     *
     * @return The Person's First Name
     */
    public String getFirstName() {
        return this.firstName;
    }
    
    /**
     * Gets the Person's Middle Name
     *
     * @return The Person's Middle Name
     */
    public String getMiddleName() {
        return this.middleName;
    }
    
    /**
     * Gets the Person's Last Name
     *
     * @return The Person's Last Name
     */
    public String getLastName() {
        return this.lastName;
    }
    
    /**
     * Gets the Person's Maiden Name
     *
     * @return The Person's Maiden Name
     */
    public String getMaidenName() {
        return this.maidenName;
    }

    /**
     * Gets the First and Last Name separated by a space.
     *
     * @return The First and Last Name
     */
    public String getFirstLastName() {
        return this.firstName+" "+this.getLastName();
    }
    
    /**
     * Gets the Person's Gender
     *
     * @return The Person's Gender (Male or Female, hopefully)
     */
    public String getGender() {
        return this.gender;
    }
    
    /**
     * Gets the Person's Eye Color
     *
     * @return The Person's Eye Color
     */
    public String getEyeColor() {
        return this.eyeColor;
    }
    
    /**
     * Gets the Person's Hair Color
     *
     * @return The Person's Hair Color
     */
    public String getHairColor() {
        return this.hairColor;
    }
    
    /**
     * Gets the Person's Height
     *
     * @return The Person's Height
     */
    public String getHeight() {
        return this.height;
    }
    
    /**
     * Gets the Person's Weight
     *
     * @return The Person's Weight
     */
    public String getWeight() {
        return this.weight;
    }
    
    /**
     * Gets the Person's Birthday
     *
     * @return The Person's Birthday (YYYY-MM-DD)
     */
    public String getBirthday() {
        if (this.birthday.isEmpty() || this.birthday.equals("") || this.birthday == null || this.birthday.equals("0000-00-00")) {
            Debug.print("Birthday in DB is Empty, returning default");
            return "1970-01-01";
        }
        else {
            return this.birthday;
        }
    }
    
    /**
     * Gets the Person's Description
     *
     * @return The Person's Description
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Gets the Person's Nick Name
     *
     * @return The Person's Nick Name
     */
    public String getNickName() {
        return this.nickName;
    }
    
    /**
     * Gets the Person's Demonym.
     *
     * @return The Person's Demonym
     */
    public KeyValue getDemonym() {
        return this.demonym;
    }
    
    /**
     * Gets the Person's Demonym Text.
     * 
     * @return The Person's Demonym Text
     */
    public String getDemonymText() {
        return this.demonym.getValue();
    }
    
    /**
     * Gets the Person's Photo
     *
     * @return A binary or base64 string of the Person's Photo
     */
    public String getPhotoBinary() {
        return this.photoBinary;
    }
    
    /**
     * Gets the Person's Photo Filename.
     *
     * @return The Person's Photo Filename and Path
     */
    public String getPhotoFileName() {
        
        // The pictures/ folder should be inside same folder as hp.properties, jar etc.
        
        // Get the right slash to use.
        String slash = "/";
        if (DBHPInterface.getOS().equals("WINDOWS")) {
            Debug.print("OS is detected as Windows.");
            slash = "\\";
        }
        
        if (new File("pictures"+slash+(this.personID)+".jpg").exists())
            return "pictures/"+(this.personID)+".jpg";
        
        else if (new File("pictures"+slash+(this.personID)+".png").exists())
            return "pictures/"+(this.personID)+".png";
        
        else if (new File("pictures"+slash+(this.personID)+".gif").exists())
            return "pictures/"+(this.personID)+".gif";
        
        else if (new File("pictures"+slash+"unknown.jpg").exists())
            return "pictures/unknown.jpg";
        
        else 
            return "";
        
    }
    
    /**
     * Gets the date when the Person's profile was last updated.
     * The date returned is in the YYYY-MM-DD format.
     *
     * @return The Last Update Date (YYYY-MM-DD)
     */
    public String getLastUpdate() {
        return this.lastUpdate;
    }
    
    /**
     * Gets the Person's Occupation
     *
     * @return The Person's Occupation
     */
    public String getOccupation() {
        return this.occupation;
    }
    
    /**
     * Gets the list of Categories this Person is in
     *
     * @return The Categories list this Person is in
     */
    public ArrayList getCategories() {
        return this.categories;
    }
    
    /**
     * Gets the Person's Age this Year.
     * Used for showing people with birthdays this month and how old they will be.
     *
     * @return The Person's Age this Year or -1 if their birthday isn't set.
     */
    protected int getAgeThisYear() {
        return DBHPInterface.getAgeThisYear(this.birthday);
    }
    
    /**
     * Gets the textual amount of time until the person's next birthday.
     * 
     * @return How many days, hours, minutes and seconds.
     */
    protected String getTimeToNextBirthday() {
        // Need to implement this.
        return "234 Days, 16 Hours, 33 Minutes and 29 Seconds";
    }
    
    /**
     * Gets the Person's current Age.
     * All times are relative to the user's machine.
     *
     * @return The Person's Current Age or -1 if the Person's Birthday isn't set.
     */
    public int getCurrentAge() {
        return DBHPInterface.getAge(this.birthday);
    }
    
    /**
     * Adds an Address to this Person
     *
     */
    protected boolean addAddress(Address newAddress) {
        // Make Sure All the Necessary Things in this Address are there
        if (!newAddress.areRequiredPartsFilled()) {
            Debug.print("Not All the Required Parts of this Address are Filled.");
            return false;
        }
        else if (this.personID < 1) {
            Debug.print("Need to Call saveToDatabase first before adding an address.");
            return false;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("INSERT INTO pmp_addresses (adrAddressTypeID, adrPersonID, adrAddressLabel, adrAddressLine1, adrAddressLine2, adrAddressLine3, adrCity, adrState, adrZip, adrCountryID, adrDistrict) VALUES (\""+newAddress.getAddressType().getKey()+"\", \""+this.personID+"\", \""+newAddress.getAddressLabel()+"\", \""+newAddress.getAddressLine1()+"\", \""+newAddress.getAddressLine2()+"\", \""+newAddress.getAddressLine3()+"\", \""+newAddress.getAddressCity()+"\", \""+newAddress.getAddressState()+"\", \""+newAddress.getAddressZip()+"\", \""+newAddress.getAddressCountry().getKey()+"\", \""+newAddress.getAddressDistrict()+"\")");
                // Reload Addresses from Database into this Object
                loadAddressesFromDB();
                return true;
            }
            catch (Exception e) { 
                Debug.print("Add New Address Failed.");
                Debug.print(e.getMessage());
                return false;
            }
            finally {
                // Close the Connection to the Database
                db.closeConnection();
            }
        }
    }
    
    /**
     * Adds this Person to a Category
     * This method can only be called for new people 
     * after saveToDatabase has been called at least once.
     *
     * @param categoryTypeID The Category ID to Add this Person to.
     * @return               True if successful, otherwise false.
     */
    protected boolean addCategory(int categoryTypeID) {
        if (categoryTypeID < 1) {
            Debug.print("Error: The Category Type ID was less than 1.");
            return false;
        }
        else if (this.personID < 1) {
            Debug.print("The Person ID was less than 1.");
            return false;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("INSERT INTO pmp_categorylink (clkCategoryID, clkPersonID) VALUES (\""+categoryTypeID+"\", \""+this.personID+"\")");
                // Reload Contacts from Database into this Object
                loadCategoriesFromDB();
                return true;
            }
            catch (Exception e) { 
                Debug.print("Add Contact Failed.");
                Debug.print(e.getMessage());
                return false;
            }
            finally {
                // Close the Connection to the Database
                db.closeConnection();
            }
        }
    }
    
    /**
     * Gets the Categories that the Person is in as a String
     *
     * @return The categories that the Person is in, all in a string.
     */
    protected String getCategoriesAsString() {
        if (this.categories.size() < 1) {
            return "No Categories";
        }
        else if (this.categories.size() < 2) {
            return this.categories.get(0).getValue();
        }
        else {
            String catString = "";
            for (Object item: this.categories) {
                catString += ((KeyValue)item).getValue()+", ";
            }
            return catString.substring(0,catString.length()-2);
        }
    }
    
    /**
     * Removes this Person from a Category.
     *
     * @param categoryTypeID The ID of the Category Type to remove this Person from.
     * @return True if Successful, otherwise false.
     */
    protected boolean removeCategory(int categoryTypeID) {
        if (categoryTypeID < 1) {
            return false;
        }
        else if (this.personID < 1) {
            return false;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("DELETE FROM pmp_categorylink WHERE (clkPersonID = \""+this.personID+"\") AND (clkCategoryID = \""+categoryTypeID+"\")");
                // Reload Categories from Database into this Object
                loadCategoriesFromDB();
                return true;
            }
            catch (Exception e) { 
                Debug.print("Remove Category Failed.");
                Debug.print(e.getMessage());
                return false;
            }
            finally {
                // Close the Connection to the Database
                db.closeConnection();
            }
        }
    }
    
    /**
     * Gets the Person's Contact List
     *
     * @return The Person's Contact List
     */
    protected ArrayList getContacts() {
        return this.contacts;
    }
    
    /**
     * Gets the Person's Address List
     *
     * @return The Person's List of Addresses
     */
    public ArrayList <Address> getAddresses() {
        return this.addresses;
    }
    
    /**
     * Add a Contact for a Person.
     * This method can only be called for new people 
     * after saveToDatabase has been called at least once.
     *
     * @param contactTypeID The ID of the Contact Type to Add.
     * @param contact       The Contact to Add
     * @return              True if successful, otherwise false.
     */
    protected boolean addContact(int contactTypeID, String contact) {
        if (contactTypeID < 1 || contact.equals("")) {
            return false;
        }
        else if (this.personID < 1) {
            return false;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("INSERT INTO pmp_contacts (ctnPersonID, ctnContactTypeID, ctnContact) VALUES (\""+this.personID+"\", \""+contactTypeID+"\", \""+contact+"\")");
                // Reload Contacts from Database into this Object
                loadContactsFromDB();
                return true;
            }
            catch (Exception e) { 
                Debug.print("Add Contact Failed.");
                Debug.print(e.getMessage());
                return false;
            }
            finally {
                // Close the Connection to the Database
                db.closeConnection();
            }
        }
    }
    
    /**
     * Removes an Address for this Person
     *
     * @param addressID The ID of the Address from the database to remove
     * @return True if Successful, otherwise false
     */
    protected boolean removeAddress(int addressID) {
        if (addressID < 1) {
            return false;
        }
        else if (this.personID < 1) {
            return false;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("DELETE FROM pmp_addresses WHERE (adrPersonID = \""+this.personID+"\") AND (adrAddressID = \""+addressID+"\")");
                // Reload Addresses from Database into this Object
                loadAddressesFromDB();
                return true;
            }
            catch (Exception e) { 
                Debug.print("Remove Address Failed.");
                Debug.print(e.getMessage());
                return false;
            }
            finally {
                // Close the Connection to the Database
                db.closeConnection();
            }
        }
    }
    
    /**
     * Gets the ID number of a contact given the contact and type as strings.
     * 
     * This ID number is the one from the database for the contact record.
     * 
     * @param strContactType The Contact Type
     * @param strContact     The Contact
     * @return               The Contact ID Number, or -1 if it can't locate it.
     */
    protected int getContactIDFromStrings(String strContactType, String strContact) {
        if (strContactType.isEmpty() || strContact.isEmpty() || this.personID < 1) {
            return -1;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt = db.getDBStatement();
                ResultSet rs = stmt.executeQuery("SELECT ctnContactID FROM pmp_contacts, pmp_ctypes WHERE (ctnPersonID = \""+this.personID+"\") AND (ctnContact = \""+strContact+"\") AND (typContactType = \""+strContactType+"\") AND (ctnContactTypeID = typContactTypeID)");
                return rs.getInt("ctnContactID");
            }
            catch (Exception e) {
                Debug.print("Failed to get contact id for given type and contact.");
                Debug.print(e.getCause());
                return -1; 
            }
            finally {
                db.closeConnection();
            }
        }
    }

    /**
     * Checks if this person has any addresses.
     */
    protected boolean hasAddresses() {
        if (this.addresses.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * Removes a Contact for this Person.
     *
     * @param contactType The Contact Type String
     * @param contact     The Contact String
     * @return            True if Successful, otherwise false
     */
    protected boolean removeContact(String contactType, String contact) {
        int contactID = getContactIDFromStrings(contactType, contact);
        if (removeContact(contactID)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Removes a Contact for this Person.
     *
     * @param contactID The ID of the Contact from the database to remove
     * @return True if Successful, otherwise false
     */
    protected boolean removeContact(int contactID) {
        if (contactID < 1) {
            return false;
        }
        else if (this.personID < 1) {
            return false;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("DELETE FROM pmp_contacts WHERE (ctnPersonID = \""+this.personID+"\") AND (ctnContactID = \""+contactID+"\")");
                // Reload Contacts from Database into this Object
                loadContactsFromDB();
                return true;
            }
            catch (Exception e) { 
                Debug.print("Remove Contact Failed.");
                Debug.print(e.getMessage());
                return false;
            }
            finally {
                // Close the Connection to the Database
                db.closeConnection();
            }
        }
    }
    
    /**
     * Gets a list of columns available for export.
     * 
     * @return List of Columns
     */
    public static ArrayList getColumnsForExport() {
        ArrayList cols = new ArrayList();
        
        cols.add("Prefix");
        cols.add("Suffix");
        cols.add("First Name");
        cols.add("Middle Name");
        cols.add("Last Name");
        cols.add("Maiden Name");
        cols.add("Gender");
        cols.add("Eye Color");
        cols.add("Hair Color");
        cols.add("Height");
        cols.add("Weight");
        cols.add("Birthday");
        cols.add("Description");
        cols.add("Nick Name");
        
        return cols;
    }
    
    /** 
     * Used for testing and debugging methods in this class.
     * @param args Not Used
     */
    public static void main(String[] args) {
        Debug.turnOn();
        try {
            // do some stuff
        }
        catch (Exception e) {
            Debug.print(e.getMessage());
        }
    }
    
}
