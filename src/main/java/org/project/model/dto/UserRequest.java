package org.project.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    public String loginName;
    public String password;
    public String fullName;
    public String email;
    public String mobilePhoneNumber;
    public String address;
    public String workPhoneNumber;
}
