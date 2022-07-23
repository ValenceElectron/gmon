package scripts;

import java.io.File;
import java.io.IOException;

public class ExecScripts {
    //final private String userName = System.getProperty("user.name");
    final private String workingDir = System.getProperty("user.dir");
    final private String tempMon = workingDir+"/src/scripts/LogTemp";
    final private String fSpeedMon = workingDir+"/src/scripts/LogFanSpeed";

    public ExecScripts() {

    }

    public void LogTemp() {
        Process p;
        try{
            String[] cmd = {"sh", tempMon};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void LogFSpeed() {
        Process p;
        try{
            String[] cmd = {"sh", fSpeedMon};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
