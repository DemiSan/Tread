package com.wurmcraft.curse.json;

import java.util.*;

public class ProjectData {

  public int id;
  public String name;
  public List<Author> authors;
  public List<Attachment> attachments;
  public String webSiteURL;
  public int gameId;
  public String summary;
  public int defaultFileId;
  public int commentCount;
  public double downloadCount;
  public int rating;
  public int installCount;
  public List<ModFile> latestFiles;
  public List<Category> categories;
  public String primaryAuthorName;
  public String externalUrl;
  public String status;
  public String stage;
  public String donationUrl;
  public String primaryCategoryName;
  public String primaryCategoryAvatorUrl;
  public int likes;
  public CategorySection categorySection;
  public String packageType;
  public String avatarUrl;
  public String slug;
  public String clientUrl;
  public List<GameFile> gameVersionLatestFiles;
  public double popularityScore;
  public int gamePopularityRank;
  public String fullDescription;
  public String gameName;
  public String portalName;
  public String sectionName;
  public long dateModified;
  public String dateCreated;
  public long dateReleased;
  public String categoryList;
  public boolean available;

  public ProjectData(
      int id,
      String name,
      List<Author> authors,
      List<Attachment> attachments,
      String webSiteURL,
      int gameId,
      String summary,
      int defaultFileId,
      int commentCount,
      double downloadCount,
      int rating,
      int installCount,
      List<ModFile> latestFiles,
      List<Category> categories,
      String primaryAuthorName,
      String externalUrl,
      String status,
      String stage,
      String donationUrl,
      String primaryCategoryName,
      String primaryCategoryAvatorUrl,
      int likes,
      CategorySection categorySection,
      String packageType,
      String avatarUrl,
      String slug,
      String clientUrl,
      List<GameFile> gameVersionLatestFiles,
      double popularityScore,
      int gamePopularityRank,
      String fullDescription,
      String gameName,
      String portalName,
      String sectionName,
      long dateModified,
      String dateCreated,
      long dateReleased,
      String categoryList,
      boolean available) {
    this.id = id;
    this.name = name;
    this.authors = authors;
    this.attachments = attachments;
    this.webSiteURL = webSiteURL;
    this.gameId = gameId;
    this.summary = summary;
    this.defaultFileId = defaultFileId;
    this.commentCount = commentCount;
    this.downloadCount = downloadCount;
    this.rating = rating;
    this.installCount = installCount;
    this.latestFiles = latestFiles;
    this.categories = categories;
    this.primaryAuthorName = primaryAuthorName;
    this.externalUrl = externalUrl;
    this.status = status;
    this.stage = stage;
    this.donationUrl = donationUrl;
    this.primaryCategoryName = primaryCategoryName;
    this.primaryCategoryAvatorUrl = primaryCategoryAvatorUrl;
    this.likes = likes;
    this.categorySection = categorySection;
    this.packageType = packageType;
    this.avatarUrl = avatarUrl;
    this.slug = slug;
    this.clientUrl = clientUrl;
    this.gameVersionLatestFiles = gameVersionLatestFiles;
    this.popularityScore = popularityScore;
    this.gamePopularityRank = gamePopularityRank;
    this.fullDescription = fullDescription;
    this.gameName = gameName;
    this.portalName = portalName;
    this.sectionName = sectionName;
    this.dateModified = dateModified;
    this.dateCreated = dateCreated;
    this.dateReleased = dateReleased;
    this.categoryList = categoryList;
    this.available = available;
  }

  public class Author {

    public String name;
    public String url;
  }

  public class Attachment {

    public int id;
    public int projectID;
    public String description;
    public String thumbnailUrl;
    public String title;
    public String url;
  }

  public class ModFile {

    public int id;
    public String fileName;
    public String fileNameOnDisk;
    public long fileDate;
    public String releaseType;
    public String fileStatus;
    public String downloadURL;
    public int alternativeFileId;
    public List<Dependencicy> dependencicies;
    public List<Module> modules;
    public int packageFingerprint;
    public String[] gameVersion;
    public String installMetadata;
    public long fileLength;
    public boolean alternate;
    public boolean available;
  }

  public class Dependencicy {

    public int addOnId;
    public String type;
  }

  public class Module {

    public int fingerprint;
    public String foldername;
  }

  public class Category {

    public int id;
    public String name;
    public String url;
    public String avatarUrl;
  }

  public class CategorySection {

    public int id;
    public int gameID;
    public String name;
    public String packageType;
    public String path;
    public String initialInclusionPattern;
    public String extraIncludePattern;
  }

  public class GameFile {

    public String gameVersion;
    public int projectFileID;
    public String projectFileName;
    public String fileType;
  }
}
