package com.wurmcraft.core.json;

public class Modpack {

  public String name;
  public String authors;
  public String mcVersion;
  public String forgeVersion;
  public String modpackVersion;
  public Exports export;

  public Modpack(String name, String authors, String mcVersion, String forgeVersion,
      String modpackVersion) {
    this.name = name;
    this.authors = authors;
    this.mcVersion = mcVersion;
    this.forgeVersion = forgeVersion;
    this.modpackVersion = modpackVersion;
    export = new Exports(true, false, true, true, false, false);
  }
}
