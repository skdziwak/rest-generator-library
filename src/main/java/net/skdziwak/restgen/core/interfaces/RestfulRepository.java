package net.skdziwak.restgen.core.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RestfulRepository<ENTITY extends IEntity<ID>, ID> extends JpaRepository<ENTITY, ID>, JpaSpecificationExecutor<ENTITY> {

}
