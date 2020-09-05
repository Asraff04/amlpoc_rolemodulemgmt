package com.poc.modulemap.repository;

import com.poc.modulemap.model.AcRoleModueXref;
import com.poc.modulemap.model.AcRoleModuleXrefId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleMapRepository extends CrudRepository<AcRoleModueXref, AcRoleModuleXrefId> {

    public List<AcRoleModueXref> findByRoleId(Integer roleId);
    public Optional<AcRoleModuleXrefId> findByRoleIdOrderByRoleId(Integer id);

}
