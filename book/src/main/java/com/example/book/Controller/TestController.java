package com.example.book.Controller;

import com.example.book.DTO.BookDTO;
import com.example.book.Exception.BookNotFoundException;
import com.example.book.Mappers.BookMapper;
import com.example.book.Model.BookEntity;
import com.example.book.Model.BookModelAssembler;
import com.example.book.Model.EmployeeEntity;
import com.example.book.Repository.BookRepository;
import com.example.book.Service.BookService;
import com.example.book.Service.EmployeeService;
import com.example.book.Service.MessageProducer;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("book/api/v3")
@Tag(name = "HomeWork", description = "Homework api ")
public class TestController {

    // @Autowired
    private BookRepository repository;

    // @Autowired
    private BookModelAssembler assembler;

    // @Autowired
    private BookService service;

    // @Autowired
    private BookMapper mapper;

    private EmployeeService employeeService;
    // private EurekaClient eurekaClient;
    private DiscoveryClient discoveryClient;
    private RestClient restClient;
    private MessageProducer messageProducer;

    public TestController(
        BookRepository repository,
        BookModelAssembler assembler,
        BookService service,
        BookMapper mapper,
        // EurekaClient eurekaClient,
        DiscoveryClient discoveryClient,
        EmployeeService employeeService,
        MessageProducer messageProducer,
        RestClient.Builder restClientBuilder
    ) {
        this.repository = repository;
        this.assembler = assembler;
        this.service = service;
        this.mapper = mapper;
        // this.eurekaClient = eurekaClient;
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
        this.employeeService = employeeService;
        this.messageProducer = messageProducer;

        System.out.println(
            "Controller Loaded: BookController with path /book/api/v2"
        );
    }

    // -=======================

    @GetMapping("/senMessageKafka/{message}")
    public void senMessageKafka(String message) {
        System.out.println("senMessageKafka(String message) {: " + message);
        messageProducer.sendMessage("my-topic", message);
    }

    @GetMapping("/employee/{id}")
    public EmployeeEntity getEmployee(
        @Parameter(
            name = "id",
            description = "id of empl",
            example = "1"
        ) long id
    ) {
        System.out.println("getEmployee" + id);
        return employeeService.getEmployeeDetails(id);
    }
    // -=======================

}
