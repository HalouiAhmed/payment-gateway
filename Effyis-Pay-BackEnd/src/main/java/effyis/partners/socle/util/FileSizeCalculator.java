package effyis.partners.socle.Util;


import java.io.File;

/**
*
* @author MAHLA Ilyasse Badreddine
*
*/
public class FileSizeCalculator {

	public static double filesizeInMegaBytes(File file) {
        return file.length()/Math.pow(1024, 2);
    }
 
    public static double filesizeInKiloBytes(File file) {
        return  file.length()/1024;
    }
 
    public static double filesizeInBytes(File file) {
        return file.length();
    }
}
