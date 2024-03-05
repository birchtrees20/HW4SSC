package io.muzoo.ssc.webapp.config;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigProperties {
    private String databaseDriverClassName;
    private String databaseConnectionURL;
    private String databaseUsername;
    private String databasePassword;
}
