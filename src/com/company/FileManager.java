package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static com.company.FileType.*;

public class FileManager extends Reader {

    private ArrayList<String> stringArrayList;
    private ArrayList<ArrayList<String>> objectArrayList;
    private FileReader reader;
    private BufferedWriter writer;
    private BufferedReader buffer;
    private String line;
    private String fileName;

    private static String NEXTLINE = ",\n";
    private static String ENDOFFILE = "\n" +
            "\n" +
            "\n" +
            "]\n" +
            "}";
    private static String EMPTYENDFILE = "\n" +
            "\n" +
            "]\n" +
            "}";




    /**
     * Method to generate the Array List of Strings, Can be used for reading in file like the ingredients file
     * @throws IOException
     */
    public void generateStringArrayList() throws IOException {

        this.openFileReader();

        while((this.line = this.buffer.readLine()) != null){

            this.stringArrayList.add(this.line);
            //System.out.println("Added String: " + this.line);
        }
        this.closeFileReader();
    }


    /**
     * Method to Generate a JSON File using the formatted String Array List For Recipes
     * @throws IOException
     */
    public void generateJSONFile(FileType fileType) throws IOException {
        this.openFileWriter();
        //TODO we must clear the JSON file.
        switch (fileType){
            case INGREDIENTS:
                this.writer.write("{\n" +
                        "\"" + INGREDIENTS + "\":\n" +
                        "\n" +
                        "[\n");
                break;
            case RECIPES:
                this.writer.write("{\n" +
                        "\"" + RECIPES + "\":\n" +
                        "\n" +
                        "[\n");
                break;
            case CHANGELOG:
                this.writer.write("{\n" +
                        "\"" + CHANGELOG + "\":\n" +
                        "\n" +
                        "[\n");
            case ACCOUNT:
                this.writer.write("{\n" +
                        "\"" + ACCOUNT + "\":\n" +
                        "\n" +
                        "[\n");

        }

        if(this.stringArrayList.isEmpty()){
            //TODO INCOMPLETE METHOD
            this.writer.write(EMPTYENDFILE);
        }else {
            for (int i = 0; i < this.stringArrayList.size(); i++) {
                //System.out.println("Adding to JSON File Next Element");
                this.writer.write(this.stringArrayList.get(i));

                if (i != this.stringArrayList.size() - 1) {
                    this.writer.write(NEXTLINE);
                } else {
                    this.writer.write(ENDOFFILE);
                }
                //System.out.println("Just added " + this.stringArrayList.get(i).toString());
            }
        }
        this.closeFileWriter();
    }

    /**
     * Updates the json file
     * @param fileType
     * @throws IOException
     */
    public void updateJSONFile(FileType fileType) throws IOException {
        this.openFileWriter();
        //TODO we must clear the JSON file.
        switch (fileType){
            case INGREDIENTS:
                this.writer.write("{\n" +
                        "\"" + INGREDIENTS + "\":\n" +
                        "\n" +
                        "[\n");
                break;
            case RECIPES:
                this.writer.write("{\n" +
                        "\"" + RECIPES + "\":\n" +
                        "\n" +
                        "[\n");
                break;
            case CHANGELOG:
                this.writer.write("{\n" +
                        "\"" + CHANGELOG + "\":\n" +
                        "\n" +
                        "[\n");
            case ACCOUNT:
                this.writer.write("{\n" +
                        "\"" + ACCOUNT + "\":\n" +
                        "\n" +
                        "[\n");

        }

        if(this.stringArrayList.isEmpty()){
            //TODO INCOMPLETE METHOD
            this.writer.write(EMPTYENDFILE);
        }else {
            for (int i = 0; i < this.stringArrayList.size(); i++) {
                //System.out.println("Adding to JSON File Next Element");
                this.writer.write(this.stringArrayList.get(i));

                if (i != this.stringArrayList.size() - 1) {
                    this.writer.write("\n");
                } else {
                    this.writer.write(ENDOFFILE);
                }
                //System.out.println("Just added " + this.stringArrayList.get(i).toString());
            }
        }
        this.closeFileWriter();
    }


    /**
     * Method to allow to append to the end of a file
     * @param appendages
     *
     * @throws IOException
     */
    public void appendToFile(String appendages){
        stringArrayList.add(appendages);
    }


    public boolean fileExists(){
        //TODO INCOMPLETE METHOD
        File file = new File(this.fileName);
        if(file.exists() == true) return true;
        else return false;
    }

    public boolean createFile(){
        //TODO INCOMPLETE METHOD
        File file = new File(this.fileName);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR CREATING FILE");
            return false;
        }
    }

    //METHODS TO OPEN AND CLOSE FILE READER,WRITER, AND BUFFERED READER

    /**
     * Method for Opening the File Reader for Easier Management
     * @throws FileNotFoundException
     */
    private void openFileReader() throws FileNotFoundException {
        if(this.fileName == null){
            System.out.println("Please Enter the File Name or File Path");
            this.fileName = this.getUserInput();
        }
        this.buffer = new BufferedReader(new FileReader(this.fileName));
    }

    /**
     * Clear the fo
     */

    /**
     * Method to to Close the File Reader for Easier Management
     * @throws IOException
     */
    private void closeFileReader() throws IOException {
        //this.reader.close();
        this.buffer.close();
    }

    /**
     * Method to Open File Writer for Easier Management
     * @throws IOException
     */
    private void openFileWriter() throws IOException {
        if(this.fileName == null){
            System.out.println("Please Enter the File Name or File Path");
            //this.fileName = this.getUserInput();//////CODY WANTED TO BE ABLE TO OPEN WHATEVER FILE USER WANTED
           fileName = "DataSource/ingredients.json";
        }
        this.writer = new BufferedWriter(new FileWriter(this.fileName));
    }

    /**
     * Method to Close the File Writer for Easier Management
     * @throws IOException
     */
    private void closeFileWriter() throws IOException {
        this.writer.close();
    }

    /**
     * Method Splits Lines of Input/Read in from File and Splits them in to an Array of String Items
     * This will be used to Create the Object Array to Be Passed to the Item Factory
     * @param objectString
     * @return
     */
    private ArrayList<String> splitInput(String objectString) {

        ArrayList<String> singleItemString = new ArrayList<>();

        //Delimiters for Reading in from a Formatted JSON File
        singleItemString.addAll(Arrays.asList(objectString.split("[{}:\",]")));

        //FOR LOOP CHECK FOR UNNECESSARY SPACE AT BEGINNING OF STRING
        for(int i = 0; i < singleItemString.size();i++) {

            singleItemString.set(i, singleItemString.get(i).trim());
        }
        this.removeNulls(singleItemString);   //Needed to remove Array elements that are empty after being Split.
/*        //UNCOMMENT TO TEST THAT TOKENS IS Reading out Correctly, May be Root of Problems
        for(int i = 0; i < singleItemString.size(); i++){
            System.out.print("<" + singleItemString.get(i) + ">");
        }
        System.out.println("");*/
        return singleItemString;
    }

    /**
     * Method used to remove empty string lines from splitInput() method
     * @param splitInputArrayList  Split Input Array List to Cleaned
     * @return  Returns the Cleaned List
     */
    private ArrayList<String> removeNulls(ArrayList<String> splitInputArrayList){
        for(int i = 0; i < splitInputArrayList.size(); i++){
            if(splitInputArrayList.get(i) == ""){
                splitInputArrayList.remove(i);
                this.removeNulls(splitInputArrayList);
                break;
            }
        }
        return splitInputArrayList;
    }

    /**
     * Method to Split the String Array List Created from Reading in from File
     * Works by first Calling the cleanStringArrayList() method, then sending each element of the String Array
     *      to the splitInput()method. Afterwards just adds to a new Array List, Meant to be able to use for
     *      future developement
     */
    public void createObjectArray(){

        cleanStringArrayList();

        objectArrayList = new ArrayList<>();
        for(int i = 0; i<this.stringArrayList.size(); i++){
            objectArrayList.add(splitInput(this.stringArrayList.get(i)));
        }
    }

    /**
     * Method for Removing the Designated Amount of Extra Lines
     * Currently 4 Lines at both Start and End of the JSON files will not contain any useful information
     */
    private void cleanStringArrayList(){
        //Removes the First 4 Lines
        for(int i = 0; i < 4; i++){
            this.stringArrayList.remove(0);
        }
        //Removes the Last 4 Lines
        for(int i = 0; i < 4; i++){
            int length = this.stringArrayList.size();
            this.stringArrayList.remove(length - 1);
        }
    }

    /**
     * Method to get a line of input from user
     * @return
     */
    private String getUserInput(){
        Scanner userInput = new Scanner(System.in);

        return userInput.nextLine();
    }

    /**
     * Method for Testing to Print all the Strings in the Array List
     */
    public void printStringArrayList(){
        ListIterator<String> itr = this.stringArrayList.listIterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }

    //GETTERS, SETTERS, AND CONSTRUCTORS BELOW
    public FileManager() {
        this.stringArrayList = new ArrayList<>();
        this.objectArrayList = new ArrayList<>();
    }

    /**
     * Constructor for Getting the Data from a String File
     * @param FileName  The name of the File to Read
     */
    public FileManager(String FileName) {
        this.fileName = FileName;
        this.stringArrayList = new ArrayList<>();
    }


    /**
     * Method to Get the String Array List
     * @return
     */
    public ArrayList<String> getStringArrayList() {
        return this.stringArrayList;
    }

    /**
     * Method to Set the String Array List
     * @param stringArrayList
     */
    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }


    /**
     * Method to Get the Object Array List.
     * @return
     */
    public ArrayList<ArrayList<String>> getObjectArrayList() {
        return objectArrayList;
    }

    public void updateData(int index, String jsonInput){

        if (index != this.stringArrayList.size() - 1){
            jsonInput = jsonInput + ",";

        }

            stringArrayList.set(index, jsonInput);



    }

    /**
     * Method to Manually Set the File Name
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     *
     * @param cbuf Destination buffer
     * @param off  Offset at which to start storing characters
     * @param len  Maximum number of characters to read
     * @return The number of characters read, or -1 if the end of the
     * stream has been reached
     * @throws IOException               If an I/O error occurs
     * @throws IndexOutOfBoundsException If {@code off} is negative, or {@code len} is negative,
     *                                   or {@code len} is greater than {@code cbuf.length - off}
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
    }

    /**
     * Closes the stream and releases any system resources associated with
     * it.  Once the stream has been closed, further read(), ready(),
     * mark(), reset(), or skip() invocations will throw an IOException.
     * Closing a previously closed stream has no effect.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException {

    }
}
