package com.hui.huiapiclientsdk;/**
 * 作者:灰爪哇
 * 时间:2023-03-24
 */

import com.hui.huiapiclientsdk.client.huiApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author: shuaihui
 **/
@Data
@Configuration
@ConfigurationProperties("huiapi.client")
@ComponentScan
public class HuiApiClientConfig {

      private String accessKey;

      private String secretKey;

      @Bean
      public huiApiClient huiApiClient(){
            return new huiApiClient(accessKey,secretKey);
      }
}
