package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class ReadTxtFile {


    public String getTxtLine(String filePathAndName, int lineIndex) throws Throwable {
        String fileName = filePathAndName;
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        StringBuilder sb1 = new StringBuilder();
        for (String line = null; (line = lineNumberReader.readLine()) != null; ) {
            if (lineNumberReader.getLineNumber()==lineIndex) {
                return new String(sb1.append(line).append(File.pathSeparatorChar)).replace(";","");
            }
        }
       throw new Throwable("file is empty");
    }

 }
