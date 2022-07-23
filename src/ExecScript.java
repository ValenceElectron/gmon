import java.io.IOException;

public class ExecScript {
    final private String userName = System.getProperty("user.name");
    final private String gmonDir = "/home/" + userName + "/.local/bin/gmon";
    public ExecScript() {
        Process p;
        try{
            String[] cmd = {"sh", gmonDir};
            p = Runtime.getRuntime().exec(cmd);
            //p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}
