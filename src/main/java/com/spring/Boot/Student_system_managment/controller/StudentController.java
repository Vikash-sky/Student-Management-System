package com.spring.Boot.Student_system_managment.controller;


import com.spring.Boot.Student_system_managment.Projection.StudentProjection;
import com.spring.Boot.Student_system_managment.dto.StudentProjectionDto;
import com.spring.Boot.Student_system_managment.dto.StudentRequestDto;
import com.spring.Boot.Student_system_managment.dto.StudentResponseDto;
import com.spring.Boot.Student_system_managment.payLoad.ApiResponse;
import com.spring.Boot.Student_system_managment.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/students")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getAllStudent(){
        List<StudentResponseDto> student = studentService.getAllStudent();
        ApiResponse<List<StudentResponseDto>> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student fetched successfully ",
                student
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> getStudentById(@PathVariable Long id){
        StudentResponseDto student = studentService.getStudentById(id);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
          true,
          LocalDateTime.now(),
          HttpStatus.OK.value(),
          "Student present with id: "+id,
          student
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<StudentResponseDto> getStudentByEmail(@PathVariable String email){
        return ResponseEntity.ok(
                studentService.getStudentByEmail(email)
        );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getStudentCountByCourse(@RequestParam String courseName){
        Long count = studentService.getStudentCountByCourse(courseName);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Student count fetched successfully",
                        count
                ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> saveStudent(@Valid @RequestBody StudentRequestDto dto){
        StudentResponseDto savedStudent = studentService.saveStudent(dto);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                "Student saved successfully ",
                savedStudent
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(@PathVariable Long id,@Valid @RequestBody StudentRequestDto dto){
        StudentResponseDto student = studentService.updateStudent(id,dto);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "student updated successfully",
                student
        );
        return ResponseEntity.ok(response);
    }



    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> patchStudent(@PathVariable Long id,@RequestBody StudentRequestDto dto){
        StudentResponseDto student = studentService.patchStudent(id, dto);
        ApiResponse<StudentResponseDto> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student update successfully ",
                student
        );
        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        ApiResponse<Object> response = new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "student delete successfully",
                null
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<ApiResponse<String>> deleteStudentByEmail(@PathVariable String email){
        studentService.deleteStudentByEmail(email);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "student deleted successfully",
                null
        ));
    }


    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDto>> getStudent(@RequestParam String name,@RequestParam String course){
        return ResponseEntity.ok(studentService.getStudentByNameAndCourse(name,course));
    }

    @GetMapping("/native/email/{email}")
    public ResponseEntity<StudentResponseDto> getStudentByEmailNative(@PathVariable String email){
        StudentResponseDto response = studentService.getStudentByEmailNative(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/native/id/{id}")
    public ResponseEntity<StudentResponseDto> getStudentByIdNative(@PathVariable Long id){
        StudentResponseDto response = studentService.getStudentByIdNative(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<StudentResponseDto>> getSortedStudents(){
        return ResponseEntity.ok(studentService.getAllStudentsSortedByName());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StudentResponseDto>> getAllStudent
            (Pageable pageable){
        return ResponseEntity.ok(studentService.getStudent(pageable));
    }

//    @GetMapping("/projection")
//    public ResponseEntity<List<StudentProjection>> getStudentProjection(){
//        return ResponseEntity.ok(studentService.getStudentProjection());
//    }

    @GetMapping("/projection/dto")
    public List<StudentProjectionDto> getStudentProjection(){
        return studentService.getStudentProjection();
    }

    @GetMapping("/search/specification")
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> searchStudents(
            @RequestParam(required = false)String name,
            @RequestParam(required = false)String email,
            @RequestParam(required = false)String course
    ){
        List<StudentResponseDto> students=studentService.searchStudents(name,email,course);
        ApiResponse<List<StudentResponseDto>> response=new ApiResponse<>(
                true,
                LocalDateTime.now(),
                HttpStatus.OK.value(),
                "Student fetched successfully",
                students
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/custom")
    public List<StudentResponseDto> getAllStudentCustom(){
        return studentService.getAllStudentCustom();
    }

    @GetMapping("/custom/department")
    public List<StudentResponseDto> getStudentByDepartmentCustom(@RequestParam String departmentName){
        return studentService.getStudentByDepartmentCustom(departmentName);
    }

    @GetMapping("/header")
    public ResponseEntity<String> clintVersion(@RequestHeader("x-client-version") String version){
        return ResponseEntity.ok("client version : "+version);
    }
}
