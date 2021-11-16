package pl.devcezz.animalshelter.zookeeper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController("/shelter/zookeepers")
class ZookeeperController {

    private final ZookeeperService zookeeperService;

    ZookeeperController(final ZookeeperService zookeeperService) {
        this.zookeeperService = zookeeperService;
    }

    @PostMapping
    ResponseEntity<Void> addZookeeper(@RequestBody @Valid AddZookeeperRequest request) {
        zookeeperService.addZookeeper(request.name(), request.email());
        return ResponseEntity.ok().build();
    }
}

record AddZookeeperRequest(
        @NotBlank @Size(min=2, max=25) String name,
        @Email String email) {}