package com.poc.modulemap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.modulemap.model.*;
import com.poc.modulemap.services.ModuleMapServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/mapmodules")
public class ModuleMapController {

    @Autowired
    private ModuleMapServices moduleMapServices;

    //Displaying role details for the single role id - TEST OK
    @GetMapping(path = "/maprolesdetails/{roleId}")
    public List<RolesModules> getAllMapModules(@PathVariable("roleId") Integer roleId, @RequestHeader("Authorization") String bearerToken) throws JsonProcessingException {
        System.out.println(" role id " + roleId);
        AcRoleModueXref acRoleModuleXref = new AcRoleModueXref();
        acRoleModuleXref.setRoleId(roleId);
        return moduleMapServices.getMapModulesRoldeId(roleId, bearerToken);
    }

    // List of All roles for role mapping - Need to test - New
//    @GetMapping(path = "/roles")
//    public List<AcRolesList> getAllRoles() {
//        return moduleMapServices.getAllRoles();
//    }

    //Displaying roledetails for the roles
//    @GetMapping(path = "rolesdetails")
//    public List<RolesModules> getAllRolesMap() {
//        return moduleMapServices.getAllRoles();
//    }


    //Displaying roledetails for the roles - TEST OK
    @GetMapping(path = "/maprolesdetails")
    public RoleModuleList getAllRolesMap(@RequestHeader("Authorization") String bearerToken) throws JsonProcessingException {
        return moduleMapServices.getAllRolesWithModules(bearerToken);
    }

    // Dummy method to be removed
    @RequestMapping("/roles/_list/{id}")
    public ACRoleModuleXrefList getUserList(@PathVariable List<Integer> id) throws JsonProcessingException {
        return moduleMapServices.getAllMapModulesByRoleIds(id);
    }

    // RE-TEST - OK
    @RequestMapping("/rolestest/_list/{id}")
    public RoleModuleList getUserListTest(@PathVariable List<Integer> id) throws JsonProcessingException {
        return moduleMapServices.getAllMapModulesByRoleIdsDummy(id);
    }

    //Displaying all modules for the single role for the user
    //    @RequestMapping("/usermodules/{userId}/{roleId}")
//    @GetMapping(path = "user/{userId}")
//    public List<RolesModules> getAllUserMapModules(@PathVariable("userId") Integer userId) {
//        System.out.println(" user id " + userId);
//        AcRoleModuleXrefId acRoleModuleXrefId = new AcRoleModuleXrefId();
//        acRoleModuleXrefId.setRoleId(userId);
//        return moduleMapServices.getMapModulesRoldeId(userId);
//    }

//    for the user multiple roleIds and get the nultiple modules

    // for the multiples modules for the multiple roles for multiple user ids

//    @GetMapping(path="user/{userId}")
//    public List<AC_Role_Module_Xref> getRoleMap(@PathVariable("userId")  Integer userId) throws JsonProcessingException{
//        System.out.println("user id "+userId);
//        AcRoleModuleXrefId acRoleModuleXrefId = new AcRoleModuleXrefId();
//        acRoleModuleXrefId.setUserId(userId);
//        return moduleMapServices.getMapModulesRoles(userId);
//    }


    //Displaying all roles - TEST OK
    @GetMapping(path = "/mapallmodules")
    public List<AcRoleModueXref> getAllMapRoles() {
        return moduleMapServices.getAllModules();
    }

    //Displaying all roles for the role id- new to test - TEST OK
    @GetMapping(path = "/mapmodule/{id}")
    public List<AcRoleModueXref> getAllMapRolesId(@PathVariable("id") Integer Id) {
        return moduleMapServices.getModuleRoleId(Id);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/module")
    public void addModule(@RequestBody AcRoleModueXref moduleXref) {
        moduleMapServices.addModule(moduleXref);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/module/{id}")
    public void updateModule(@RequestBody AcRoleModueXref moduleXref, @PathVariable Integer id) {
        System.out.println("Inside update method id " + id);
        moduleMapServices.updateModule(id, moduleXref);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/module/{id}")
    public void deleteModule(@RequestBody AcRoleModueXref ac_role_module_xref, @PathVariable Integer id) {
        moduleMapServices.deleteModule(id, ac_role_module_xref);
    }

}
