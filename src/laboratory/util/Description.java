package laboratory.util;

import java.io.*;

public class Description{

    public static String getDecription(InputStream r){
        String res = "";
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(r, "UTF-8"));
            while(true){
                String temp = in.readLine();
                if(temp == null)break;
                res += "\n" + temp;
            }
        }catch(IOException e){
            e.printStackTrace();
            return "Can't read description";
        }
        return res;
    }

}
