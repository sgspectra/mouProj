import java.util.Scanner;
import java.io.*;
import java.util.Date;
import java.util.Calendar;

class mouFileParse{

    /*
     * This method checks to see if the date passed to it
     * is within 9 months of the date today. It does not take
     * the days into account.
     *
     * @param anotherDate
     *      the date to checks
     *
     * @returns false if the date is further than 9 months out.
     */
    public static boolean checkDate(Date anotherDate){
        Date plusNineMonths = Calendar.getInstance().getTime();
        //add nine months
        int newMon = plusNineMonths.getMonth();
        int newYear = plusNineMonths.getYear();
        if((newMon + 9)/12 > 0){
            newYear++;
            newMon = (newMon + 9) % 12;
        }else{
            newMon = newMon + 9;
        }
        plusNineMonths.setMonth(newMon);
        plusNineMonths.setYear(newYear);
        Boolean returnVal = false;

        if (plusNineMonths.compareTo(anotherDate) > 0){
            returnVal = true;
        }
        return returnVal;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the name of a properly formatted MoU CSV file:");
        String fileName = in.nextLine();
        System.out.println("Please enter the name of output file:");
        String outputFileName = in.nextLine();

        try{
            //create new FileReader
            FileReader input = new FileReader(fileName);
            //wrap in BufferedReader
            BufferedReader reader = new BufferedReader(input);
            //create a file writer
            FileWriter output = new FileWriter(outputFileName);
            //wrap in BufferedWriter
            BufferedWriter toOut = new BufferedWriter(output);
            //wrap it again in a printwriter?
            PrintWriter toOutput = new PrintWriter(toOut);
            //disregard column headers
            String fromFile = reader.readLine();
            toOutput.println(fromFile);
            //load first entry
            fromFile = reader.readLine();

            while (fromFile != null){
                //index of character to check for comma
                int scanPos = 0;
                //index of where the comma before the expiration date is
                int commaOnePos = 0;
                //index of comma after the expiration date
                int commaTwoPos = 0;
                //number of commas that the scanner has passed
                int commaCount = 0;

                //while the entry still has characters to scan
                while (scanPos < fromFile.length()){
                    if(fromFile.charAt(scanPos) == ','){
                        commaCount++;
                        if(commaCount == 4){
                            commaOnePos = scanPos;
                        }else if(commaCount == 5){
                            commaTwoPos = scanPos;
                        }
                    }else if(fromFile.charAt(scanPos) == '"'){
                        scanPos++;
                        while(fromFile.charAt(scanPos) != '"'){
                            scanPos++;
                        }
                    }
                    scanPos++;
                }
                String date = fromFile.substring(commaOnePos+1,commaTwoPos);

                //this if statement will catch if the date column was left empty
                if(date.length() == 8){

                    int year = Integer.parseInt(date.substring(0,4));
                    int mon = Integer.parseInt(date.substring(4,6));
                    int day = Integer.parseInt(date.substring(6,8));

                    Date dateFromFile = new Date(year-1900,mon-1,day);

                    if(checkDate(dateFromFile)){
                        System.out.println("Expires");
                        toOutput.println(fromFile);
                    }
                }
                toOutput.flush();
                System.out.println(date);
                fromFile = reader.readLine();
            }

            //closing IO
            reader.close();
            input.close();
            toOutput.close();
            toOut.close();
            output.close();
        }catch(FileNotFoundException ex){
            System.out.println("File Not Found");
        }catch(IOException ex){
            System.out.println("Error Reading From File");
        }
    }
}