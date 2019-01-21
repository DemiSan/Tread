package com.wurmcraft.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

  public static String getFileLines(File file) {
    try {
      return new String(Files.readAllBytes(file.toPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}
