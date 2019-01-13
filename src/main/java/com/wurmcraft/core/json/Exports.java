package com.wurmcraft.core.json;

public class Exports {

  public boolean twitchEnabled;
  public boolean ftbEnabled;
  public boolean technicEnabled;
  public boolean multicraftEnabled;
  public boolean skcraftEnabled;
  public boolean atEnabled;

  public Exports(boolean twitchEnabled, boolean ftbEnabled, boolean technicEnabled,
      boolean multicraftEnabled, boolean skcraftEnabled, boolean atEnabled) {
    this.twitchEnabled = twitchEnabled;
    this.ftbEnabled = ftbEnabled;
    this.technicEnabled = technicEnabled;
    this.multicraftEnabled = multicraftEnabled;
    this.skcraftEnabled = skcraftEnabled;
    this.atEnabled = atEnabled;
  }
}
