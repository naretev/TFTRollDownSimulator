package com.naretevprojects.tft.rolldownsimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.naretevprojects.tft.rolldownsimulator.configuration.TftRolldownSimulatorProperties;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Main controller
 */

@Controller
@NoArgsConstructor
@Slf4j
public class MainController {

  @Autowired
  TftRolldownSimulatorProperties simulatorProperties;

  @RequestMapping("/")
  public String getIndexPage(Model model) {

    // Testing logging via Slf4j
    log.debug("The title of the page is {}", simulatorProperties.getHtml().getHomePageTitle());
    log.debug("The heading of the page is {}", simulatorProperties.getHtml().getHeading());
    log.debug("The number of iterations are {} ", simulatorProperties.getIterations());

    model.addAttribute("title", simulatorProperties.getHtml().getHomePageTitle());
    model.addAttribute("heading", simulatorProperties.getHtml().getHeading());

    return "main";
  }
}
