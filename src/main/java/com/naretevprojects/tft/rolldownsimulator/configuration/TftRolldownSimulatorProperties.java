package com.naretevprojects.tft.rolldownsimulator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import lombok.Data;

/**
 * Configuration properties for the TFT Rolldown Simulator service logic
 */
@Configuration
@ConfigurationProperties(prefix = "service")
@Data
@DependsOn()
public class TftRolldownSimulatorProperties {

  private HTMLProperties html;
  private int iterations;

  @Data
  public static class HTMLProperties {

    private String homePageTitle;
    private String heading;

  }
}
