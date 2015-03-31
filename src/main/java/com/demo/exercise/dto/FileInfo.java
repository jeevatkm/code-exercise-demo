package com.demo.exercise.dto;

/**
 * Simple Structure to hold file/dir name and boolean flag of type
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
public class FileInfo {

  private String name;
  private boolean isDir;

  public FileInfo() {}

  public FileInfo(String name, boolean isDir) {
    this.name = name;
    this.isDir = isDir;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the isDir
   */
  public boolean isDir() {
    return isDir;
  }

  /**
   * @param isDir the isDir to set
   */
  public void setDir(boolean isDir) {
    this.isDir = isDir;
  }
}
