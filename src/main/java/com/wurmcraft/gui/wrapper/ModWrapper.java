package com.wurmcraft.gui.wrapper;

public class ModWrapper {

  public String author;
  public String name;
  public String version;
  public String type;

  public ModWrapper(String author, String name, String version, String type) {
    this.author = author;
    this.name = name;
    this.version = version;
    this.type = type;
  }

  public String getAuthor() {
    return author;
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public String getType() {
    return type;
  }
}
