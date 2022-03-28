import java.io.*;
import java.util.*;

public class Main {

    private static Scanner reader;
    public static void main (String[] args) throws IOException {
        int i=0;
        String filepath = "";
        List fileElements;
        List insuranceCompany;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please insert filename WITH extension now: ");
        filepath = reader.readLine();
        System.out.println("File selected is: "+ filepath);
        fileElements = readFile(filepath);
        insuranceCompany = searchInsuranceCompany(fileElements);
        System.out.println(insuranceCompany);
        while (i < insuranceCompany.size()){
          createNewFile((String) insuranceCompany.get(i), filepath);
           i++;
       }
        i=0;
        while (i < insuranceCompany.size()){
            sortAlphabeticalByLastName((String) insuranceCompany.get(i));
            i++;
        }



    }

    public static List readFile(String filepath){
        String userId= "";
        String firstName = "";
        String lastName = "";
        String version = "";
        String insuranceComp = "";
        List fileElements = new ArrayList<>();

        try {


            reader = new Scanner(new File(filepath));
            reader.useDelimiter(",|\\r\\n");

            
            while (reader.hasNext()){
                userId = reader.next();
                firstName = reader.next();
                lastName = reader.next();
                version = reader.next();
                insuranceComp = reader.next();
                fileElements.add(userId);
                fileElements.add(firstName);
                fileElements.add(lastName);
                fileElements.add(version);
                fileElements.add(insuranceComp);
            }


        }catch (Exception e){

            e.printStackTrace();

        }

        return fileElements;
    }

    public static List searchInsuranceCompany(List fileElements){
       List insuranceCompany = new ArrayList();
       int i = 4;
       while (i < fileElements.size()){

            insuranceCompany.add(fileElements.get(i));
            i = i + 5;
       }
        insuranceCompany.remove(0);
        HashSet h = new HashSet(insuranceCompany);
        insuranceCompany = new ArrayList(h);
        return insuranceCompany;
    }

    public static void createNewFile(String fileName, String filepath){
        String tempFile = "temp.txt";
        File oldFile = new File(fileName + ".txt");
        File newFile = new File(tempFile);
        String userId= "";
        String firstName = "";
        String lastName = "";
        String version = "";
        String insuranceComp = "";

        try {

            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            reader = new Scanner(new File(filepath));
            reader.useDelimiter(",|\\r\\n");

            while (reader.hasNext()){
                userId = reader.next();
                firstName = reader.next();
                lastName = reader.next();
                version = reader.next();
                insuranceComp = reader.next();

                if (insuranceComp.equals(fileName)){

                    pw.println(userId +"," + firstName + "," + lastName + "," + version + "," + insuranceComp);

                }

            }
            reader.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(fileName+".txt");
            newFile.renameTo(dump);

        }catch (Exception e){

            e.printStackTrace();
        }


    }

    public static void editFile(List sortedList, String filepath,HashMap<String,Integer> idAndVersion){
        String tempFile = "temp.txt";
        File oldFile = new File(filepath + ".txt");
        File newFile = new File(tempFile);
        String userId;
        String firstName;
        String lastName;
        int version;
        String insuranceComp;
        int i=0;
        boolean done = false;
            try {

                FileWriter fw = new FileWriter(tempFile, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                while (i < sortedList.size()){
                    reader = new Scanner(new File(filepath+".txt"));
                    reader.useDelimiter(",|\\r\\n");
                    while (reader.hasNext()){
                        userId = reader.next();
                        firstName = reader.next();
                        lastName = reader.next();
                        version = Integer.parseInt(reader.next());
                        insuranceComp = reader.next();

                        if (sortedList.get(i).equals((Object) lastName)){
                            if (!done){
                                version = idAndVersion.get(userId);
                                pw.println(userId +"," + firstName + "," + lastName + "," + version + "," + insuranceComp);
                                done = true;
                            }
                            }

                    }
                    reader.close();
                    done = false;
                    i++;
                }

                pw.flush();
                pw.close();
                oldFile.delete();
                File dump = new File(filepath+".txt");
                newFile.renameTo(dump);

            }catch (Exception e){
                e.printStackTrace();

            }
    }

    public static void sortAlphabeticalByLastName(String filepath){
        List sortingList = new ArrayList();
        List<String> inspectionList = new ArrayList<String>();
        String userId= "";
        String firstName = "";
        String lastName = "";
        int version = 0;
        String insuranceComp = "";
        HashMap<String, Integer> idAndVersion = new HashMap<String, Integer>();
        try {

            reader = new Scanner(new File(filepath + ".txt"));
            reader.useDelimiter(",|\\r\\n");
            while (reader.hasNext()){

                   userId = reader.next();
                   firstName = reader.next();
                   lastName = reader.next();
                   version = Integer.parseInt(reader.next());
                   insuranceComp = reader.next();
                   sortingList.add(lastName);
                    if (idAndVersion.containsKey(userId)){
                        if (idAndVersion.get(userId) < version){
                            idAndVersion.put(userId,version);
                        }
                    }else {
                        idAndVersion.put(userId,version);
                    }


            }
            reader.close();
            Collections.sort(sortingList);
            HashSet h = new HashSet(sortingList);
            sortingList = new ArrayList(h);
            editFile(sortingList,filepath,idAndVersion);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
