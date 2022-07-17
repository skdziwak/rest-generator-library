package ${packageName};

import net.skdziwak.restgen.jsonschema.SchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(path = "${path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class ${className} {
    private final ${serviceClass} service;

    public ${className}(@Autowired ${serviceClass} service) {
        this.service = service;
    }

    <#if endpoints.post == true>
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${dto.view}> post${entityName}(@RequestBody ${dto.post} dto) {
        return ResponseEntity.ok(service.post(dto));
    }
    </#if>

    <#if endpoints.post == true && endpointsSchema == true>
    @GetMapping(path = "/PostSchema")
    public ResponseEntity<Map<String, Object>> post${entityName}Schema() {
        return ResponseEntity.ok(service.postSchema());
    }
    </#if>

    <#if endpoints.patch == true && endpointsSchema == true>
    @GetMapping(path = "/PatchSchema")
    public ResponseEntity<Map<String, Object>> patch${entityName}Schema() {
        return ResponseEntity.ok(service.patchSchema());
    }
    </#if>
        
    <#if endpoints.put == true && endpointsSchema == true>
    @GetMapping(path = "/PutSchema")
    public ResponseEntity<Map<String, Object>> put${entityName}Schema() {
        return ResponseEntity.ok(service.putSchema());
    }
    </#if>

    <#if endpoints.search == true>
    @PostMapping(path = "/Search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<${dto.search}>> search${entityName}s(@RequestBody ${searchSpecificationClass} searchSpecification) {
        return ResponseEntity.ok(service.search(searchSpecification));
    }
    </#if>

    <#if endpoints.patch == true>
    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${dto.view}> patch${entityName}(@PathVariable ${idClass} id, @RequestBody ${dto.patch} dto) {
        return ResponseEntity.ok(service.patch(id, dto));
    }
    </#if>

    <#if endpoints.put == true>
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${dto.view}> put${entityName}(@PathVariable ${idClass} id, @RequestBody ${dto.put} dto) {
        return ResponseEntity.ok(service.put(id, dto));
    }
    </#if>
        
    <#if endpoints.get == true>
    @GetMapping(path = "/{id}")
    public ResponseEntity<${dto.get}> get${entityName}(@PathVariable ${idClass} id) {
        return ResponseEntity.ok(service.get(id));
    }
    </#if>

    <#if endpoints.get == true>
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete${entityName}(@PathVariable ${idClass} id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    </#if>
}