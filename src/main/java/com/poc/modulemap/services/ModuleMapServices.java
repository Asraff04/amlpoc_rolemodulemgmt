package com.poc.modulemap.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.modulemap.model.*;
import com.poc.modulemap.repository.ModuleMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleMapServices {

    @Autowired
    private ModuleMapRepository moduleMapRepository;
    @Autowired
    private RestTemplate restTemplate;


    // List of All roles for role mapping
    public List<AcRolesList> getAllRoles()  {
        String fooResourceUrl = "http://localhost:8081/roles";
        System.out.println("url >>>"+fooResourceUrl);
        ResponseEntity<ACRolesListResponse> response = restTemplate.getForEntity(fooResourceUrl, ACRolesListResponse.class);
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Role Name>>> " +response.getBody().getAcRolesList().get(0).getRoleName());
        return response.getBody().getAcRolesList();
    }

    public List<AcRolesList> getRoles(String roleIds) {
        String fooResourceUrl = "http://localhost:8081/roles/_list/";
        System.out.println("url >>>" + fooResourceUrl + roleIds);
        ResponseEntity<ACRolesListResponse> response = restTemplate.getForEntity(fooResourceUrl + roleIds, ACRolesListResponse.class);
//        AC_Roles rolesDetails = restTemplate.getForObject(fooResourceUrl + roleIds, AC_Roles.class);
        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(response.getBody());

        System.out.println("Role Name>>> " + response.getBody().getAcRolesList().get(0).getRoleName());
//        JsonNode name = root.path("userName");
//        System.out.println("userName" +name);
//        System.out.println("user details "+rolesDetails.getRoleShortName());

//        response = mapper.readValue(response.toString());
        return response.getBody().getAcRolesList();
    }

    // List of All roles for role mapping
    public List<AcRolesList> getAllRoles(String Bearer)  {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, Bearer);
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        String fooResourceUrl = "http://localhost:8081/roles";
        System.out.println("url >>>"+fooResourceUrl);
//        ResponseEntity<ACRolesListResponse> response = restTemplate.getForEntity(fooResourceUrl, ACRolesListResponse.class);
        ResponseEntity<ACRolesListResponse> response = restTemplate.exchange(fooResourceUrl, HttpMethod.GET, entity, ACRolesListResponse.class );
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Role Name>>> " +response.getBody().getAcRolesList().get(0).getRoleName());
        return response.getBody().getAcRolesList();
    }

    public List<AcRolesList> getRoles(String roleIds, String Bearer)  throws JsonProcessingException{
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, Bearer);
        //Create a new HttpEntity
        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        String fooResourceUrl = "http://localhost:8081/roles/_list/";
        System.out.println("url >>>"+fooResourceUrl+roleIds);
//        ResponseEntity<ACRolesListResponse> response = restTemplate.getForEntity(fooResourceUrl+roleIds, ACRolesListResponse.class);
        ResponseEntity<ACRolesListResponse> response = restTemplate.exchange(fooResourceUrl+roleIds, HttpMethod.GET, entity, ACRolesListResponse.class );

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Role Name>>> " +response.getBody().getAcRolesList().get(0).getRoleName());
        return response.getBody().getAcRolesList();
    }

    // Retrieving role id details for mapped modules - Tested
    public List<RolesModules> getMapModulesRoldeId(Integer roleId, String Bearer) throws JsonProcessingException {
        List<AcRoleModueXref> ac_role_module_xref = new ArrayList<>();
        ac_role_module_xref = moduleMapRepository.findByRoleId(roleId);
        List<AcRoleModuleXrefId> acRoleModuleXrefIds = new ArrayList<>();
        System.out.println("module name ..."+ac_role_module_xref.stream().map(z -> z.getModuleName()).collect(Collectors.toList()));
//        ac_role_module_xref.stream().map(z -> z.getModuleName()).collect(Collectors.toList());
        System.out.println("role id " + roleId);
        String rolePId = String.valueOf(roleId);
        List<AcRolesList> rolesLists = getRoles(rolePId, Bearer);
//        System.out.println("rolesList "+rolesLists.stream().map(z-> z.getRoleId()).collect(Collectors.toList()));

        System.out.println("role short name " + rolesLists.get(0).getRoleShortName());

//        List<UserRoles> userRolesList = new ArrayList<>();
        List<RolesModules> rolesModulesList = new ArrayList<>();

        if (roleId.equals(rolesLists.get(0).getRoleId())) {
            RolesModules rolesModules = new RolesModules();

            rolesModules.setAc_role_module_xref(ac_role_module_xref.get(0));
            rolesModules.setAcRolesList(rolesLists.get(0));
            rolesModulesList.add(rolesModules);
        }
        return rolesModulesList;

    }

    public List<AcRoleModueXref> getAllModules() {
        List<AcRoleModueXref> moduleRoles = new ArrayList<>();
        moduleMapRepository.findAll()
                .forEach(moduleRoles::add);
        return moduleRoles;
    }

    //New method to retrive all the roles details - role object - Dummy ???
    public List<RolesModules> getAllModulesRoles(String Bearer) throws JsonProcessingException {
        List<AcRoleModueXref> moduleRoles = new ArrayList<>();
        moduleMapRepository.findAll()
                .forEach(moduleRoles::add);
        String roleId = moduleRoles.stream().map( y -> String.valueOf(y.getRoleId())).collect(Collectors.joining(","));
        System.out.println("Role ids "+roleId);

        List<RolesModules> rolesModulesList = new ArrayList<>();

        List<AcRolesList> rolesLists = getRoles(roleId, Bearer);
        moduleRoles.forEach(mapRole -> {
            RolesModules rolesModules = new RolesModules();
            rolesModules.setAc_role_module_xref(moduleRoles.stream().filter(user -> user.getRoleId().equals(mapRole.getRoleId()))
                            .findFirst().get());
            rolesModules.setAcRolesList(rolesLists.stream().filter(user -> user.getRoleId().equals(mapRole.getRoleId()))
                    .findFirst().get());
            rolesModulesList.add(rolesModules);
        });

        return rolesModulesList;
    }

    public RoleModuleList getAllRolesWithModules(String Bearer) throws JsonProcessingException {
        RoleModuleList roleModuleList = new RoleModuleList();
        List<RoleModule> moduleRoles = roleModuleList.getRoleModuleList();
        List<AcRoleModueXref> acRoleModuleXrefList = new ArrayList<>();

        moduleMapRepository.findAll()
                .forEach(acRoleModuleXrefList::add);


        String roleId = acRoleModuleXrefList.stream().map( y -> String.valueOf(y.getRoleId())).collect(Collectors.joining(","));
        System.out.println("Role ids "+roleId);

//        List<RolesModules> rolesModulesList = new ArrayList<>();

        List<AcRolesList> rolesLists = getRoles(roleId, Bearer);

        acRoleModuleXrefList.forEach(mapRole -> {
            RoleModule roleModule = new RoleModule();
            AcRolesList acRoles = rolesLists.stream().filter(role -> role.getRoleId().equals(mapRole.getRoleId())).findFirst().get();
            roleModule.setRoleId(mapRole.getRoleId());
            roleModule.setModuleName(mapRole.getModuleName());
            roleModule.setRoleName(acRoles.getRoleName());
            roleModule.setRoleShortName(acRoles.getRoleShortName());
//            rolesModules.setAc_role_module_xref(moduleRoles.stream().filter(user -> user.getRoleId().equals(mapRole.getRoleId()))
//                    .findFirst().get());
//            RoleModule roleModule = moduleRoles.stream().filter(user -> user.getRoleId().equals(mapRole.getRoleId()))
//                    .findFirst().get();

//           AcRolesList acRolesList =  rolesLists.stream().filter(user -> user.getRoleId().equals(mapRole.getRoleId()))
//                    .findFirst().get();

            moduleRoles.add(roleModule);
        });
        roleModuleList.setRoleModuleList(moduleRoles);

        return roleModuleList;
    }

    public RoleModuleList getAllMapModulesByRoleIdsDummy(List<Integer> ids) throws JsonProcessingException {
        List<AcRoleModueXref>  modulesRolesList = new ArrayList<>();

        RoleModuleList roleModuleList = new RoleModuleList();
        List<RoleModule> moduleRoles = roleModuleList.getRoleModuleList();// do we need? check

        moduleMapRepository.findAll()
                .forEach(modulesRolesList::add);

        for(int i=0; i<=ids.size()-1; i++){
//            System.out.println("list of modules ..."+modulesRolesList.size()+" no of role ids..."+ids.size()+" role id .."+ids.get(i));
            System.out.println("list of role ids ..."+i+" ..."+modulesRolesList.get(i).getModuleName());
            for(int j=0; j <=modulesRolesList.size()-1; j++) {
                RoleModule roleModule = new RoleModule();

                System.out.println("module role id "+modulesRolesList.get(j).getRoleId()+" passed role ids "+ids.get(i));
                if ((modulesRolesList.get(j).getRoleId()).equals(ids.get(i))) {
                    System.out.println("module name ..." + modulesRolesList.get(j).getModuleName());
                    roleModule.setRoleId(modulesRolesList.get(j).getRoleId());
                    roleModule.setModuleName(modulesRolesList.get(j).getModuleName());
                    moduleRoles.add(roleModule);
                }
            }
        }

//        for(int i=0; i<=ids.size(); i++){
//            System.out.println("list of role ids ..."+i);
//            modulesRolesList = moduleMapRepository.findByRoleId(i);
//        }
//        moduleMapRepository.findAll()
//                .forEach(modulesRolesList::add);
//        System.out.println("module list..."+modulesRolesList.size()+" module names ..."+modulesRolesList.stream().filter(modulesRolesList.get()));
//        String roleId = ids.stream().map( y -> String.valueOf(y.toString())).collect(Collectors.joining(","));
//        System.out.println("role ids.... "+roleId);
//        modulesRolesList.forEach(id -> {
//                    RoleModule roleModule = new RoleModule();
//                    roleModule.setRoleId(id.getRoleId());
//                    roleModule.setModuleName(id.getModuleName());
//
//                    moduleRoles.add(roleModule);
//                });
            roleModuleList.setRoleModuleList(moduleRoles);
            return roleModuleList;
    }

    public List<AcRoleModueXref> getModuleRoleId(Integer id) {
        return moduleMapRepository.findByRoleId(id);
    }

    //retriving by list of modules for role ids-
    public ACRoleModuleXrefList getAllMapModulesByRoleIds(List<Integer> ids) throws JsonProcessingException {
        List<AcRoleModueXref>  modulesRoles = new ArrayList<>();
        String roleId = ids.stream().map( y -> String.valueOf(y.toString())).collect(Collectors.joining(","));
        System.out.println("role ids.... "+roleId);

        ids.forEach(id -> modulesRoles.add(getModuleRoleId(id).get(0)));
        ACRoleModuleXrefList acRoleModuleXrefList = new ACRoleModuleXrefList();
        System.out.println("inside module domain>>> "+modulesRoles.get(0).getModuleName());
        acRoleModuleXrefList.setAcRoleModuleXrefList(modulesRoles);
        return acRoleModuleXrefList;
    }

    public AcRoleModueXref addModule(AcRoleModueXref moduleXref) {
        return moduleMapRepository.save(moduleXref);
    }

    public void updateModule(Integer id, AcRoleModueXref moduleXref) {
        moduleMapRepository.save(moduleXref);
    }

    public void deleteModule(Integer id, AcRoleModueXref ac_role_module_xref) {
        moduleMapRepository.delete(ac_role_module_xref);
    }
}
