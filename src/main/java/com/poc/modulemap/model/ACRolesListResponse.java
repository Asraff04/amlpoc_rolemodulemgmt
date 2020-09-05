
package com.poc.modulemap.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ac_rolesList"
})
public class ACRolesListResponse {

    @JsonProperty("ac_rolesList")
    private List<AcRolesList> acRolesList = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ac_rolesList")
    public List<AcRolesList> getAcRolesList() {
        return acRolesList;
    }

    @JsonProperty("ac_rolesList")
    public void setAcRolesList(List<AcRolesList> acRolesList) {
        this.acRolesList = acRolesList;
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
