package com.wurmcraft.core.json;

import java.util.List;
import java.util.Map;

/**
 * Based on ForgeUtil from Voodoo created by NikkyAI
 * https://github.com/elytra/Voodoo/blob/master/core/src/main/kotlin/voodoo/forge/ForgeUtil.kt
 */
public class ForgeData {

  public String adfocus;
  public String artifact;
  public String homepage;
  public String webpath;
  public String name;
  public Map<String, List<Integer>> branches;
  public Map<String, List<Integer>> mcversion;
  public Map<String, Artifact> number;
  public Map<String, Integer> promos;

  public ForgeData(
      String adfocus,
      String artifact,
      String homepage,
      String webpath,
      String name,
      Map<String, List<Integer>> branches,
      Map<String, List<Integer>> mcversion,
      Map<String, Artifact> number,
      Map<String, Integer> promos) {
    this.adfocus = adfocus;
    this.artifact = artifact;
    this.homepage = homepage;
    this.webpath = webpath;
    this.name = name;
    this.branches = branches;
    this.mcversion = mcversion;
    this.number = number;
    this.promos = promos;
  }

  public class Artifact {

    public String branch;
    public int build;
    public List<List<String>> files;
    public String mcversion;
    public double modified;
    public String version;

    public Artifact(
        String branch,
        int build,
        List<List<String>> files,
        String mcversion,
        double modified,
        String version) {
      this.branch = branch;
      this.build = build;
      this.files = files;
      this.mcversion = mcversion;
      this.modified = modified;
      this.version = version;
    }
  }
}
