
import java.io.File;

public class NFileServerError {

    public NFileServerError () {

        String internalPath = this.getClass().getName().replace(".", File.separator);
        String externalPath = System.getProperty("user.dir")+File.separator+"src";
        //String workDir = externalPath+File.separator+internalPath.substring(0, internalPath.lastIndexOf(File.separator));

        System.out.println( externalPath + "/" + internalPath );
    }

    public static void main(String[] args) throws Exception {

        NFileServerError nFileServerError = new NFileServerError();

        System.out.println(
            nFileServerError.getClass().getClassLoader().getResource("").getPath()
        );


    }

}