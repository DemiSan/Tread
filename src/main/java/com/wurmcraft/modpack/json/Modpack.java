package com.wurmcraft.modpack.json;

import java.util.*;

public class Modpack {

  public String name;
  public String author;
  public String mcVersion;
  public String forgeVersion;
  public String modpackVersion;
  public List<Mod> mods;
  public ExportSettings export;

  public Modpack(
      String name,
      String author,
      String mcVersion,
      String forgeVersion,
      String modpackVersion,
      List<Mod> mods) {
    this.name = name;
    this.author = author;
    this.mcVersion = mcVersion;
    this.forgeVersion = forgeVersion;
    this.modpackVersion = modpackVersion;
    this.mods = mods;
    this.export = new ExportSettings(true, false, true, true, false, false);
  }

  public class ExportSettings {

    public boolean twitch;
    public boolean ftb;
    public boolean technic;
    public boolean multicraft;
    public boolean skcraft;
    public boolean at;

    public ExportSettings(
        boolean twitch,
        boolean ftb,
        boolean technic,
        boolean multicraft,
        boolean skcraft,
        boolean at) {
      this.twitch = twitch;
      this.ftb = ftb;
      this.technic = technic;
      this.multicraft = multicraft;
      this.skcraft = skcraft;
      this.at = at;
    }
  }
}
