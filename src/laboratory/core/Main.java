package laboratory.core;

import laboratory.core.gui.Util;

import javax.swing.*;
import java.util.Locale;

public class Main {


    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){ 
                Util.showMainFrame();
            }
        });
    }
    
}