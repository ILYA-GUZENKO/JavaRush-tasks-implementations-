package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
Проход по дереву файлов
*/
public class Solution {

    List<File> primaryFiles = new ArrayList<>();
    // custom recursion sort method
    public void fillTheList(File path){
        for (File f : path.listFiles()){
            if (f.isFile() && f.length() <= 50){
                primaryFiles.add(f);
            }else if (f.isDirectory()) fillTheList(new File(f.getAbsolutePath()));
        }
    }

    public static void main(String[] args) {
        List<File> files;
        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        File allFilesContent = new File(resultFileAbsolutePath.getParent() + "/allFilesContent.txt");
        FileUtils.renameFile(resultFileAbsolutePath, allFilesContent);

        Solution solution = new Solution();

        solution.fillTheList(path);

        files = new ArrayList<>(solution.primaryFiles);

        // just recognized how to use lambdas and method references
        files.sort(Comparator.comparing(File::getName));


        //main action
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(allFilesContent))) {
            for (File f : files){
                BufferedReader reader = new BufferedReader(new FileReader(f));
                while (reader.ready()){
                    writer.write(reader.readLine());
                    writer.write('\n');
                }
                reader.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
