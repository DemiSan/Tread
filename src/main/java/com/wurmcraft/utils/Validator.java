package com.wurmcraft.utils;

import com.jfoenix.validation.RegexValidator;

public class Validator {

  public static final RegexValidator NAME = new RegexValidator("Must be least 3 letters long");

  public Validator() {
    NAME.setRegexPattern("^[\\w\\d_. -]{2,}$");
  }
}
