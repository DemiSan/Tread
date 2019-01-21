package com.wurmcraft.utils;

import com.wurmcraft.Tread;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {

  public static String getFileLines(File file) {
    try {
      return new String(Files.readAllBytes(file.toPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static <T extends Object> void saveFile(File file, T t) {
    try {
      Files.write(file.toPath(), Tread.GSON.toJson(t).getBytes(), StandardOpenOption.CREATE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void appendFile(File file, String text) {
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try {
      Files.write(file.toPath(), text.getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // https://stackoverflow.com/questions/29076439/java-8-copy-directory-recursively
  public static void copyFolder(Path src, Path dest) {
    try {
      Files.walk(src)
          .forEach(
              s -> {
                try {
                  Path d = dest.resolve(src.relativize(s));
                  if (Files.isDirectory(s)) {
                    if (!Files.exists(d)) {
                      Files.createDirectory(d);
                    }
                    return;
                  }
                  Files.copy(s, d); // use flag to override existing
                } catch (Exception e) {
                  e.printStackTrace();
                }
              });
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void zipDir(File dir, File saveDir) {
    if (!saveDir.getParentFile().exists()) {
      saveDir.getParentFile().mkdir();
    }
    try {
      if (!saveDir.exists()) {
        saveDir.createNewFile();
      }
      zip(dir, saveDir);
    } catch (IOException e) {
    }
  }

  // https://stackoverflow.com/questions/1399126/java-util-zip-recreating-directory-structure
  public static void zip(File directory, File zipfile) throws IOException {
    URI base = directory.toURI();
    Deque<File> queue = new LinkedList<>();
    queue.push(directory);
    OutputStream out = new FileOutputStream(zipfile);
    Closeable res = out;
    try {
      ZipOutputStream zout = new ZipOutputStream(out);
      res = zout;
      while (!queue.isEmpty()) {
        directory = queue.pop();
        for (File kid : directory.listFiles()) {
          String name = base.relativize(kid.toURI()).getPath();
          if (kid.isDirectory()) {
            queue.push(kid);
            name = name.endsWith("/") ? name : name + "/";
            zout.putNextEntry(new ZipEntry(name));
          } else {
            zout.putNextEntry(new ZipEntry(name));
            copy(kid, zout);
            zout.closeEntry();
          }
        }
      }
    } finally {
      res.close();
    }
  }

  // https://stackoverflow.com/questions/1399126/java-util-zip-recreating-directory-structure
  private static void copy(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    while (true) {
      int readCount = in.read(buffer);
      if (readCount < 0) {
        break;
      }
      out.write(buffer, 0, readCount);
    }
  }

  // https://stackoverflow.com/questions/1399126/java-util-zip-recreating-directory-structure
  private static void copy(File file, OutputStream out) throws IOException {
    InputStream in = new FileInputStream(file);
    try {
      copy(in, out);
    } finally {
      in.close();
    }
  }

  // https://stackoverflow.com/questions/1399126/java-util-zip-recreating-directory-structure
  public static void unzip(File zipfile, File directory) throws IOException {
    ZipFile zfile = new ZipFile(zipfile);
    Enumeration<? extends ZipEntry> entries = zfile.entries();
    while (entries.hasMoreElements()) {
      ZipEntry entry = entries.nextElement();
      File file = new File(directory, entry.getName());
      if (entry.isDirectory()) {
        file.mkdirs();
      } else {
        file.getParentFile().mkdirs();
        InputStream in = zfile.getInputStream(entry);
        try {
          copy(in, file);
        } finally {
          in.close();
        }
      }
    }
  }

  private static void copy(InputStream in, File file) throws IOException {
    OutputStream out = new FileOutputStream(file);
    try {
      copy(in, out);
    } finally {
      out.close();
    }
  }

  public static boolean delete(File dir) {
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        delete(file);
      }
    }
    return dir.delete();
  }
}
