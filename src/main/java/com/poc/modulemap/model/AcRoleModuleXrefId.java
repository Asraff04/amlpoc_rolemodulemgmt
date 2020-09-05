package com.poc.modulemap.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

public class AcRoleModuleXrefId implements Serializable {

    public AcRoleModuleXrefId(String moduleName, Integer roleId) {
        this.moduleName = moduleName;
        this.roleId = roleId;
    }

    private Integer roleId;
    private String moduleName;

    public AcRoleModuleXrefId() {
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }


}
