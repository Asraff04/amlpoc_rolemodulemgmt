package com.poc.modulemap.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(AcRoleModuleXrefId.class)
@Table(name = "AC_ROLE_MODULE_XREF", schema = "FirstMule")
public class AcRoleModueXref implements Serializable {

    @Id
    @Column(name = "MODULE_NM")
    private String moduleName;
    @Id
    @Column(name = "ROLE_ID")
    private Integer roleId;


    public AcRoleModueXref(String moduleName, Integer roleId) {
        this.moduleName = moduleName;
        this.roleId = roleId;
    }

    public AcRoleModueXref() {
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
