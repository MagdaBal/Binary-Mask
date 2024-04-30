import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class FeaturesCalculator {
    private ArrayList<ArrayList<Integer>> binary_mask;
    private Integer[] mass_center;
    private String filename;
    private int n;

    public FeaturesCalculator(String filename){
        this.filename = filename;
        this.binary_mask = new ArrayList<ArrayList<Integer>>();
        this.mass_center = new Integer[2];

        this.mass_center[0] = 0;
        this.mass_center[1] = 0;
        this.n = 0;

    }

    public void read_data(){
        int ascci_code_0=48;
        int i;
        try {
            File file = new File(filename);
            Scanner fileReader = new Scanner(file);
            while(fileReader.hasNextLine()){
                String data = fileReader.nextLine();
                ArrayList<Integer> dataArray = new ArrayList<Integer>();
                for (i = 0; i < data.length(); i++) {
                    if (data.charAt(i)!= ' '){
                        if(((int) data.charAt(i)) == ascci_code_0){
                            dataArray.add(0);
                        }else {
                            dataArray.add(1);
                            n++;
                        }
                    }
                }
                binary_mask.add(new ArrayList<Integer>(dataArray));
            }
            fileReader.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found. Incorrect name or format.");
            System.exit(-1);
        }
    }

    public void calculate_mass_center(){
        int i, j;
        int sumX=0;
        int sumY=0;

        for (i = 0; i < binary_mask.size(); i++) {
            for (j = 0; j < binary_mask.get(i).size(); j++) {
                if (binary_mask.get(i).get(j) == 1) {
                    sumX += j;
                    sumY += i;
                }
            }
        }
        if(n!=0){
            mass_center[0] = sumX/n;
            mass_center[1] = sumY/n;

            System.out.print("P:("+mass_center[0]+",");
            System.out.println(mass_center[1]+")");
        } else{
            System.out.println("Error. Incorrect input.");
        }
    }

    public void save_results(){
        try{
            int i, j;
            FileWriter fileWriter = new FileWriter(filename.replaceAll("\\.(txt)", "")+"_out.txt");
            for (i = 0; i < binary_mask.size(); i++) {
                for (j = 0; j < binary_mask.get(i).size(); j++) {
                    if(j==mass_center[0] && i==mass_center[1]){
                        fileWriter.write("P ");
                    } else{
                        fileWriter.write(binary_mask.get(i).get(j)+" ");
                    }
                }
                fileWriter.write("\n");
            }

            fileWriter.close();
        }catch (IOException e){
            System.out.println("Error");
        }
    }


    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter file name: ");

        String filename = scan.nextLine();
        filename = System.getProperty("user.dir")+"\\src\\"+filename;
        scan.close();

        FeaturesCalculator calculator = new FeaturesCalculator(filename);
        calculator.read_data();
        calculator.calculate_mass_center();
        calculator.save_results();

    }
}



