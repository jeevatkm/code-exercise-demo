package com.demo.exercise.service;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.exercise.dto.FileInfo;

/**
 * File System services for files and directory
 * <p>
 * <strong>Note:</strong> I'm not considering adapter based implementation here. i.e interface
 * definition.
 * </p>
 * <p>
 * Abstracted & full fledged implementation is well suited for/like Local file system, remote file
 * system (may be mount point, HDFS, S3, etc), etc.
 * </p>
 * 
 * @author madanj01
 * @since 0.0.1
 */
@Service
public class FileSystemService {

  // For this service root dir is where File("") mean
  private File rootDir;
  private String rootPath;

  @PostConstruct
  public void init() {
    this.rootDir = new File("");
    this.rootPath = rootDir.getAbsolutePath();
  }

  /**
   * Retrieves directory listing for given relative path from Root dir
   * <p>
   * <strong>Note:</strong> No validation is done like directory exists check, etc.
   * </p>
   * 
   * @param path a type of {@link String}
   * @return {@code List<FileInfo>}
   */
  public List<FileInfo> getDirs(String path) {
    List<FileInfo> result = new ArrayList<FileInfo>();

    File[] files = getDirAndFiles(path);
    for (File f : files) {
      result.add(new FileInfo(f.getName(), f.isDirectory()));
    }

    return result;
  }

  /**
   * Simple implementation; no cache or dictionary is maintained here.
   * 
   * @param relativePath
   * @return File[]
   */
  private File[] getDirAndFiles(String relativePath) {
    String targetPath = rootPath;
    if (StringUtils.hasText(relativePath)) {
      targetPath += File.separator + relativePath;
    }

    // Hidden files are not considered for listing
    return new File(targetPath).listFiles(new FileFilter() {
      @Override
      public boolean accept(File file) {
        return !file.isHidden();
      }
    });
  }

}
