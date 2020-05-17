package part1.lesson09.task01;

import java.io.*;

/**
 * Считывает файл SomeClass.java,
 * Находит строку с методом
 * Записывает в следующую строку передаваемую в параметре начинку метода
 */
public class SomeClassBuilder {

    public static final String sourceClass = "src\\main\\java\\part1\\lesson09\\task01\\SomeClass.java";
    public static final String targetClass = "SomeClass.java";

    public static void buildSomeClassWithMethod(String methodCode) {

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(sourceClass));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = fileReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
                if (line.contains("doWork()")) {
                    stringBuilder.append(methodCode);
                    stringBuilder.append("\n");
                }
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetClass));
            bufferedWriter.write(stringBuilder.toString());

            fileReader.close();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
