package com.naretevprojects.tft.rolldownsimulator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller
 */

@Controller
public class MainController {

  @RequestMapping("/")
  public String getIndexPage() {
    return "main";
  }


}
