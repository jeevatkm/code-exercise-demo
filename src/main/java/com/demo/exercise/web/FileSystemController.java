package com.demo.exercise.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.exercise.dto.FileInfo;
import com.demo.exercise.service.FileSystemService;

/**
 * File System endpoint handler
 * 
 * @author Jeevanandam M.
 * @since 0.0.1
 */
@RestController
@RequestMapping("/fs")
@PreAuthorize("hasRole('USER')")
public class FileSystemController {

  @Autowired
  private FileSystemService fileSystemService;

  @RequestMapping(method = RequestMethod.GET)
  public List<FileInfo> index(
      @RequestParam(value = "rp", defaultValue = "", required = false) String relativePath) {
    // There is room for validation, I have not applied it for demo

    return fileSystemService.getDirs(relativePath);
  }
}
