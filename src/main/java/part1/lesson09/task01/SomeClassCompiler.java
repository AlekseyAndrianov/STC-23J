package part1.lesson09.task01;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

public class SomeClassCompiler {

    private static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public static void compileClass(String sourceFile){
        try (InputStream source = new FileInputStream(sourceFile)) {

            compiler.run(null,null,null,sourceFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
