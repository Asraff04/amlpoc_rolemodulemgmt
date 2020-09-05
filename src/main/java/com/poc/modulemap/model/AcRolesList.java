
package com.poc.modulemap.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "roleId",
        "roleName",
        "roleShortName"
})
public class AcRolesList {

    @JsonProperty("roleId")
    private Integer roleId;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("roleShortName")
    private String roleShortName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("roleId")
    public Integer getRoleId() {
        return roleId;
    }

    @JsonProperty("roleId")
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @JsonProperty("roleName")
    public String getRoleName() {
        return roleName;
    }

    @JsonProperty("roleName")
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @JsonProperty("roleShortName")
    public String getRoleShortName() {
        return roleShortName;
    }

    @JsonProperty("roleShortName")
    public void setRoleShortName(String roleShortName) {
        this.roleShortName = roleShortName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
