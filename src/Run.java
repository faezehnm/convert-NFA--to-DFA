import java.io.*;
import java.util.ArrayList;

/**
 * read grammar from file
 */
public class Run {
    public static void main(String[] args)
    {
        try {
            File file=new File("NFA_Input_2.txt");
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            StringBuffer sb=new StringBuffer();
            String line;
            ArrayList<String> contents = new ArrayList<>();
            while((line=br.readLine())!=null)
            {
                contents.add(line.toString()) ;
            }
            fr.close();
            NFA ndfa = new NFA(contents);
//            ndfa.transferToDFA();

//            if( ndfa.isLanda()) {
//                System.out.println("with λ");
//                Transform ndfaWithoutLanda = ndfa.transferToWithNoLanda();
//                ndfa.transferToDFA();
//            }
//            else {
//                System.out.println("with no λ");
//                ndfa.transferToDFA();
//            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
